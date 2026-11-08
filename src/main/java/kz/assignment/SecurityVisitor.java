package kz.assignment;

import java.util.ArrayList;
import java.util.List;

public final class SecurityVisitor implements InfrastructureVisitor {
    private final List<String> warnings = new ArrayList<>();

    @Override
    public void visit(ApiServer server) {
        check(server.tlsEnabled(), "API server has TLS disabled.");
    }

    @Override
    public void visit(PostgresDatabase database) {
        check(database.backupEnabled(), "PostgreSQL database has backups disabled.");
        check(database.encryptionEnabled(), "PostgreSQL database has encryption disabled.");
    }

    @Override
    public void visit(RedisCache cache) {
        check(cache.passwordEnabled(), "Redis cache has password authentication disabled.");
        check(cache.privateNetwork(), "Redis cache is outside a private network.");
    }

    private void check(boolean enabled, String warning) {
        if (!enabled) {
            warnings.add(warning);
        }
    }

    public List<String> getWarnings() {
        return List.copyOf(warnings);
    }
}
