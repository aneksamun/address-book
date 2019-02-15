package co.uk.redpixel.addressbook.domain.model;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

public enum Gender {
    Male,
    Female;

    public static Optional<Gender> of(String value) {
        return Arrays.stream(values())
                     .filter(anyMatchingValue(value))
                     .findFirst();
    }

    private static Predicate<Gender> anyMatchingValue(String value) {
        return gender -> gender.name().equalsIgnoreCase(value);
    }
}
