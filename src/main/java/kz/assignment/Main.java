package kz.assignment;

import java.util.List;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        List<InfrastructureComponent> infrastructure = List.of(
                new ApiServer(2, 2, true),
                new PostgresDatabase(100, true, true),
                new RedisCache(2, false, true)
        );

        CostVisitor costs = new CostVisitor();
        SecurityVisitor security = new SecurityVisitor();
        for (InfrastructureComponent component : infrastructure) {
            component.accept(costs);
            component.accept(security);
        }

        System.out.println("Monthly cost report");
        for (CostVisitor.Charge charge : costs.getCharges()) {
            System.out.println(charge.component() + ": $" + charge.monthlyCost().toPlainString());
        }
        System.out.println("Total Monthly Cost: $" + costs.getTotalMonthlyCost().toPlainString());

        System.out.println();
        System.out.println("Security report");
        List<String> warnings = security.getWarnings();
        warnings.forEach(warning -> System.out.println("- " + warning));
        System.out.println("Total Warnings: " + warnings.size());
    }
}
