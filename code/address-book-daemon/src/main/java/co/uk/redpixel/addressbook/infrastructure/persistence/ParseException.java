package co.uk.redpixel.addressbook.infrastructure.persistence;

import co.uk.redpixel.addressbook.common.Error;

class ParseException extends RuntimeException {

    ParseException(Error error, Object... args) {
        super(error.getErrorMessage(args));
    }
}
