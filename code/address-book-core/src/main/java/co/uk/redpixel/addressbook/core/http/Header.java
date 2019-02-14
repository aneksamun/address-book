package co.uk.redpixel.addressbook.core.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Header {
    CONTENT_LENGTH("content-length");

    @Getter
    private final String key;
}
