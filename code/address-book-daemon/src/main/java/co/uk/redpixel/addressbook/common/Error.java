package co.uk.redpixel.addressbook.common;

import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public enum Error {
    CONFIG_LOAD_ERROR("Cannot load configuration: %s"),
    INVALID_GENDER("Could parse gender of %s"),
    INVALID_DOB("Could not parse date of birth %s");

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorMessage(Object... args) {
        return format(errorMessage, args);
    }
}
