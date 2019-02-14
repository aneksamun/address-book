package co.uk.redpixel.addressbook.infrastructure.property;

import co.uk.redpixel.addressbook.common.Error;

import static java.lang.String.format;

public class ConfigurationException extends RuntimeException {

    public ConfigurationException(Error error, Object... args) {
        super(format(error.getErrorMessage(), args));
    }

    public ConfigurationException(Error error) {
        super(error.getErrorMessage());
    }
}
