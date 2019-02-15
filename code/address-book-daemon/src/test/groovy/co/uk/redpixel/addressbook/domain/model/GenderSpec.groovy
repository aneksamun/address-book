package co.uk.redpixel.addressbook.domain.model

import spock.lang.Specification
import spock.lang.Unroll

import static co.uk.redpixel.addressbook.domain.model.Gender.Female
import static co.uk.redpixel.addressbook.domain.model.Gender.Male

class GenderSpec extends Specification {

    @Unroll
    def 'should resolve gender for #value'() {
        expect: 'to succeed'
        Gender.of(value).get() == expected

        where:
        value    | expected
        'Female' | Female
        'Male'   | Male
        'female' | Female
        'MALE'   | Male
    }
}
