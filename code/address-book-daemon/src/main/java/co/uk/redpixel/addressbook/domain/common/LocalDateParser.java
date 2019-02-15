package co.uk.redpixel.addressbook.domain.common;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class LocalDateParser {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static Optional<LocalDate> fromString(String string) {
        try {
            return Optional.of(LocalDate.parse(string, DATE_FORMATTER));

        } catch (DateTimeParseException dateParseException) {
            return Optional.empty();
        }
    }
}
