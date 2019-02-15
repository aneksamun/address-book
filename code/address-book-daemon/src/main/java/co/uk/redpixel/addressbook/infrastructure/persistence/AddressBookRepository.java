package co.uk.redpixel.addressbook.infrastructure.persistence;

import co.uk.redpixel.addressbook.domain.Try;
import co.uk.redpixel.addressbook.domain.common.LocalDateParser;
import co.uk.redpixel.addressbook.domain.model.AddressBook;
import co.uk.redpixel.addressbook.domain.model.Contact;
import co.uk.redpixel.addressbook.domain.model.Gender;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static co.uk.redpixel.addressbook.common.Error.INVALID_DOB;
import static co.uk.redpixel.addressbook.common.Error.INVALID_GENDER;
import static java.util.stream.Collectors.toList;

@Slf4j
public final class AddressBookRepository {

    private static final String SOURCE = "AddressBook";

    @SneakyThrows
    public static AddressBook fetch() {

        val fileUrl = ClassLoader.getSystemResource(SOURCE);

        try (Stream<String> stream = Files.lines(Paths.get(fileUrl.toURI()))) {
            return AddressBook.of(
                    stream.map(Try.of(parseLine().andThen(mapToContact()), logError()))
                          .filter(Objects::nonNull)
                          .collect(toList())
            );
        }
    }

    private static Function<String, String[]> parseLine() {
        return line -> line.split(", ");
    }

    private static Function<String[], Contact> mapToContact() {
        return fields -> Contact.builder()
                .name(fields[0])
                .gender(parseGender(fields[1]))
                .dateOfBirth(parseDateOfBirth(fields[2]))
                .build();
    }

    private static LocalDate parseDateOfBirth(String value) {
        return LocalDateParser.fromString(value).orElseThrow(() -> new ParseException(INVALID_DOB, value));
    }

    private static Gender parseGender(String value) {
        return Gender.of(value).orElseThrow(() -> new ParseException(INVALID_GENDER, value));
    }

    private static Consumer<Exception> logError() {
        return error -> log.error(error.getMessage());
    }
}
