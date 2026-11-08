package kz.assignment;

public interface InfrastructureVisitor {
    void visit(ApiServer server);

    void visit(PostgresDatabase database);

    void visit(RedisCache cache);
}
