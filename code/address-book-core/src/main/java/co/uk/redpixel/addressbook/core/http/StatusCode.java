package co.uk.redpixel.addressbook.core.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum StatusCode {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(400, "Bad Request");

    @Getter
    private final int code;

    @Getter
    private final String status;
}
