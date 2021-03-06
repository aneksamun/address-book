package co.uk.redpixel.addressbook.core;

import co.uk.redpixel.addressbook.core.config.Endpoint;
import co.uk.redpixel.addressbook.core.http.StatusCode;
import co.uk.redpixel.addressbook.core.util.GMTDateFormatter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static co.uk.redpixel.addressbook.core.http.StatusCode.*;
import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
public final class HttpResponse<T> {

    @Getter
    private final StatusCode statusCode;

    @Getter
    private final Optional<T> body;

    @Override
    public String toString() {
        return String.format("HTTP/1.1 %d %s\r\n" +
                        "Date: %s\r\n" +
                        "Content-Type: text/plain; charset=UTF-8\r\n" +
                        "Content-Length: %d\r\n" +
                        "\r\n" +
                        body.map(Object::toString).orElse(""),
                statusCode.getCode(),
                statusCode.getStatus(),
                GMTDateFormatter.format(new Date()),
                body.map(content -> content.toString().length()).orElse(0)
        );
    }

    public static <T> HttpResponse<T> notFound() {
        return notFound(null);
    }

    public static <T> HttpResponse<T> notFound(T body) {
        return new HttpResponse<>(NOT_FOUND, Optional.ofNullable(body));
    }

    public static <T> HttpResponse<T> badRequest(T body) {
        return new HttpResponse<>(BAD_REQUEST, Optional.of(body));
    }

    public static <T> HttpResponse<T> ok(T body) {
        return new HttpResponse<>(OK, Optional.of(body));
    }

    static HttpResponseBuilder of(HttpRequest request) {
        return new HttpResponseBuilder(request);
    }

    @RequiredArgsConstructor(access = PRIVATE)
    static class HttpResponseBuilder<T> {

        private final HttpRequest httpRequest;

        private Optional<Endpoint<T>> maybeExecutable = Optional.empty();

        HttpResponseBuilder with(Set<Endpoint<T>> endpoints) {
            maybeExecutable = endpoints.stream()
                    .filter(matchEndpoint(httpRequest))
                    .findFirst();
            return this;
        }

        HttpResponse<T> build() {
            return maybeExecutable.map(execute(httpRequest)).orElse(notFound());
        }

        private static <T> Predicate<? super Endpoint<T>> matchEndpoint(HttpRequest request) {
            return endpoint -> endpoint.getMethod() == request.getMethod() &&
                    Pattern.compile(endpoint.getPath())
                            .matcher(request.getUri().getPath())
                            .matches();
        }

        private static <T> Function<Endpoint<T>, HttpResponse<T>> execute(HttpRequest httpRequest) {
            return endpoint -> endpoint.action.apply(httpRequest);
        }
    }
}
