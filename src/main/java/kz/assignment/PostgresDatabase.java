package kz.assignment;

public record PostgresDatabase(int storageGb, boolean backupEnabled, boolean encryptionEnabled)
        implements InfrastructureComponent {

    public PostgresDatabase {
        ResourceValidation.requirePositive(storageGb, "storageGb");
    }

    @Override
    public void accept(InfrastructureVisitor visitor) {
        visitor.visit(this);
    }
}
