package kz.assignment;

final class ResourceValidation {
    private ResourceValidation() {
    }

    static void requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be greater than zero");
        }
    }
}
