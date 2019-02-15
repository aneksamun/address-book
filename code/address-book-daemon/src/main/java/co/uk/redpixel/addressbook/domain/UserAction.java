package co.uk.redpixel.addressbook.domain;

import co.uk.redpixel.addressbook.core.HttpRequest;
import co.uk.redpixel.addressbook.core.HttpResponse;
import co.uk.redpixel.addressbook.domain.model.AddressBook;
import lombok.val;

import java.util.function.Function;

import static co.uk.redpixel.addressbook.core.HttpResponse.notFound;
import static co.uk.redpixel.addressbook.core.HttpResponse.ok;
import static java.lang.String.format;

public interface UserAction<T> extends Function<HttpRequest, HttpResponse<T>> {

    static UserAction<String> welcome() {
        return request -> ok("Welcome to Address Book API!");
    }

    static UserAction<String> findOldest(AddressBook addressBook) {
        return request -> addressBook.findOldest()
                .map(oldest -> ok(format("The oldest person is %s who is %s years old.", oldest.getName(), oldest.getName())))
                .orElseGet(() -> notFound("Address book has no contacts"));
    }

    static UserAction<Long> countMales(AddressBook addressBook) {
        return request -> ok(addressBook.countMales());
    }

    static UserAction<String> tellHowManyDaysSomeOneIsOlderThanOther(AddressBook addressBook) {
        return request -> {
            val parts = request.getUri().getPath().split("/");

            val namePattern1 = parts[2];
            val maybeContact1 = addressBook.tryMatch(namePattern1);
            if (!maybeContact1.isPresent()) {
                return notFound(format("Contact %s not found", namePattern1));
            }

            val namePattern2 = parts[4];
            val maybeContact2 = addressBook.tryMatch(namePattern2);
            if (!maybeContact2.isPresent()) {
                return notFound(format("Contact %s not found", namePattern2));
            }

            val contact1 = maybeContact1.get();
            val contact2 = maybeContact2.get();

            return ok(format("%s is older than %s by %d years",
                    contact1.getName(),
                    contact2.getName(),
                    contact1.getDaysOlderThan(contact2)));
        };
    }
}
