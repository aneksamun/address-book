package co.uk.redpixel.addressbook.infrastructure.property;

import co.uk.redpixel.addressbook.common.Error;

class ConfigurationException extends RuntimeException {

    ConfigurationException(Error error, Object... args) {
        super(error.getErrorMessage(args));
    }
}
