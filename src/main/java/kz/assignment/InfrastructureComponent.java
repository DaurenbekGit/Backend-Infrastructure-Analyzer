package kz.assignment;

public interface InfrastructureComponent {
    void accept(InfrastructureVisitor visitor);
}
