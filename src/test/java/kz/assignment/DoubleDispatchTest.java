package kz.assignment;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class DoubleDispatchTest {
    @Test
    void eachAcceptCallsItsMatchingOverloadOnceWithTheOriginalElement() {
        ApiServer server = new ApiServer(1, 2, true);
        PostgresDatabase database = new PostgresDatabase(50, true, false);
        RedisCache cache = new RedisCache(2, false, true);
        List<InfrastructureComponent> components = List.of(cache, server, database, cache);
        List<String> calls = new ArrayList<>();

        InfrastructureVisitor visitor = new InfrastructureVisitor() {
            @Override
            public void visit(ApiServer element) {
                assertSame(server, element);
                calls.add("server");
            }

            @Override
            public void visit(PostgresDatabase element) {
                assertSame(database, element);
                calls.add("database");
            }

            @Override
            public void visit(RedisCache element) {
                assertSame(cache, element);
                calls.add("cache");
            }
        };

        components.forEach(component -> component.accept(visitor));
        assertEquals(List.of("cache", "server", "database", "cache"), calls);
    }
}
