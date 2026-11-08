package kz.assignment;

public record ApiServer(int instanceCount, int cpuCores, boolean tlsEnabled)
        implements InfrastructureComponent {

    public ApiServer {
        ResourceValidation.requirePositive(instanceCount, "instanceCount");
        ResourceValidation.requirePositive(cpuCores, "cpuCores");
    }

    @Override
    public void accept(InfrastructureVisitor visitor) {
        visitor.visit(this);
    }
}
