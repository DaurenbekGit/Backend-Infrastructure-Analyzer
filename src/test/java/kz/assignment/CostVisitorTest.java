package kz.assignment;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CostVisitorTest {
    @Test
    void chargesForEveryCoreInEveryInstance() {
        assertMonthlyCost(new ApiServer(3, 4, false), "480.00");
    }

    @Test
    void chargesForDatabaseStorageToTheCent() {
        assertMonthlyCost(new PostgresDatabase(3, false, false), "1.35");
    }

    @Test
    void chargesForRedisMemory() {
        assertMonthlyCost(new RedisCache(7, true, false), "70.00");
    }

    @Test
    void handlesCoreCountsLargerThanAnInt() {
        assertMonthlyCost(new ApiServer(65_536, 65_536, true), "171798691840.00");
    }

    @Test
    void startsWithZeroCost() {
        assertEquals(new BigDecimal("0.00"), new CostVisitor().getTotalMonthlyCost());
    }

    @Test
    void itemizesTheAssignmentConfigurationAndTotalsItsCharges() {
        List<InfrastructureComponent> components = List.of(
                new ApiServer(2, 2, true),
                new PostgresDatabase(100, true, true),
                new RedisCache(2, false, true)
        );
        CostVisitor visitor = new CostVisitor();
        components.forEach(component -> component.accept(visitor));

        assertEquals(List.of(
                new CostVisitor.Charge("API server", new BigDecimal("160.00")),
                new CostVisitor.Charge("PostgreSQL database", new BigDecimal("45.00")),
                new CostVisitor.Charge("Redis cache", new BigDecimal("20.00"))
        ), visitor.getCharges());
        assertEquals(new BigDecimal("225.00"), visitor.getTotalMonthlyCost());
    }

    private static void assertMonthlyCost(InfrastructureComponent component, String expected) {
        CostVisitor visitor = new CostVisitor();
        component.accept(visitor);
        assertEquals(new BigDecimal(expected), visitor.getTotalMonthlyCost());
    }
}
