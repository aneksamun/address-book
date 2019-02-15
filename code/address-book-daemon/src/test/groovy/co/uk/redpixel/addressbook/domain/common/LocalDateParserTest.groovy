package co.uk.redpixel.addressbook.domain.common

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class LocalDateParserTest extends Specification {

    @Unroll
    def 'should correctly parse date of #source'() {
        expect: 'to succeed'
        LocalDateParser.fromString(source).get() == expected

        where:
        source     | expected
        '16/03/77' | LocalDate.of(1977, 3, 16)
        '15/01/85' | LocalDate.of(1985, 1, 15)
        '20/11/91' | LocalDate.of(1991, 11, 20)
        '20/09/80' | LocalDate.of(1980, 9, 20)
        '14/08/74' | LocalDate.of(1974, 8, 14)
    }

    def 'should return nothing on failure'() {
        expect: 'to fail'
        !LocalDateParser.fromString('').isPresent()
    }
}
