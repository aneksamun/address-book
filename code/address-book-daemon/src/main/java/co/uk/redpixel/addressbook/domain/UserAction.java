package co.uk.redpixel.addressbook.domain;

import co.uk.redpixel.addressbook.core.HttpRequest;
import co.uk.redpixel.addressbook.core.HttpResponse;

import java.util.function.Function;

import static co.uk.redpixel.addressbook.core.HttpResponse.ok;

public interface UserAction<T> extends Function<HttpRequest, HttpResponse<T>> {

    static UserAction<String> welcome() {
        return request -> ok("Welcome to Address Book API!");
    }
}
