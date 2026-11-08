package kz.assignment;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityVisitorTest {
    @ParameterizedTest
    @MethodSource("disabledControls")
    void warnsAboutTheSpecificDisabledControl(InfrastructureComponent component, String warning) {
        SecurityVisitor visitor = new SecurityVisitor();
        component.accept(visitor);
        assertEquals(List.of(warning), visitor.getWarnings());
    }

    static Stream<Arguments> disabledControls() {
        return Stream.of(
                Arguments.of(new ApiServer(1, 1, false), "API server has TLS disabled."),
                Arguments.of(new PostgresDatabase(1, false, true), "PostgreSQL database has backups disabled."),
                Arguments.of(new PostgresDatabase(1, true, false), "PostgreSQL database has encryption disabled."),
                Arguments.of(new RedisCache(1, false, true), "Redis cache has password authentication disabled."),
                Arguments.of(new RedisCache(1, true, false), "Redis cache is outside a private network.")
        );
    }

    @Test
    void secureInfrastructureProducesNoWarnings() {
        List<String> warnings = inspect(new ApiServer(1, 1, true),
                new PostgresDatabase(1, true, true), new RedisCache(1, true, true));
        assertTrue(warnings.isEmpty());
    }

    @Test
    void collectsEveryFailureEvenWhenOneComponentHasSeveral() {
        List<String> warnings = inspect(new ApiServer(1, 1, false),
                new PostgresDatabase(1, false, false), new RedisCache(1, false, false));
        assertEquals(5, warnings.size());
    }

    @Test
    void assignmentConfigurationOnlyWarnsAboutRedisAuthentication() {
        List<String> warnings = inspect(new ApiServer(2, 2, true),
                new PostgresDatabase(100, true, true), new RedisCache(2, false, true));
        assertEquals(List.of("Redis cache has password authentication disabled."), warnings);
    }

    private static List<String> inspect(InfrastructureComponent... components) {
        SecurityVisitor visitor = new SecurityVisitor();
        for (InfrastructureComponent component : components) {
            component.accept(visitor);
        }
        return visitor.getWarnings();
    }
}
