package kz.assignment;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ComponentValidationTest {
    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void rejectsNonPositiveResourceQuantities(int quantity) {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new ApiServer(quantity, 1, true)),
                () -> assertThrows(IllegalArgumentException.class, () -> new ApiServer(1, quantity, true)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PostgresDatabase(quantity, true, true)),
                () -> assertThrows(IllegalArgumentException.class, () -> new RedisCache(quantity, true, true))
        );
    }
}
