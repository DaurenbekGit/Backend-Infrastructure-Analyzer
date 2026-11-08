package kz.assignment;

public record RedisCache(int memoryGb, boolean passwordEnabled, boolean privateNetwork)
        implements InfrastructureComponent {

    public RedisCache {
        ResourceValidation.requirePositive(memoryGb, "memoryGb");
    }

    @Override
    public void accept(InfrastructureVisitor visitor) {
        visitor.visit(this);
    }
}
