package kz.assignment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class CostVisitor implements InfrastructureVisitor {
    private static final BigDecimal PRICE_PER_CORE = new BigDecimal("40.00");
    private static final BigDecimal PRICE_PER_STORAGE_GB = new BigDecimal("0.45");
    private static final BigDecimal PRICE_PER_MEMORY_GB = new BigDecimal("10.00");

    public record Charge(String component, BigDecimal monthlyCost) {
    }

    private final List<Charge> charges = new ArrayList<>();

    @Override
    public void visit(ApiServer server) {
        long totalCores = (long) server.instanceCount() * server.cpuCores();
        addCharge("API server", totalCores, PRICE_PER_CORE);
    }

    @Override
    public void visit(PostgresDatabase database) {
        addCharge("PostgreSQL database", database.storageGb(), PRICE_PER_STORAGE_GB);
    }

    @Override
    public void visit(RedisCache cache) {
        addCharge("Redis cache", cache.memoryGb(), PRICE_PER_MEMORY_GB);
    }

    private void addCharge(String component, long quantity, BigDecimal unitPrice) {
        charges.add(new Charge(component, unitPrice.multiply(BigDecimal.valueOf(quantity))));
    }

    public List<Charge> getCharges() {
        return List.copyOf(charges);
    }

    public BigDecimal getTotalMonthlyCost() {
        return charges.stream()
                .map(Charge::monthlyCost)
                .reduce(BigDecimal.ZERO.setScale(2), BigDecimal::add);
    }
}
