package co.uk.redpixel.addressbook.domain.model

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

import static co.uk.redpixel.addressbook.domain.model.Gender.Female
import static co.uk.redpixel.addressbook.domain.model.Gender.Male

class AddressBookSpec extends Specification {

    def 'should find oldest contact'() {
        given: 'book'
        def book = AddressBook.of([
                new Contact('Tom', Male, LocalDate.of(1989, 9, 24)),
                new Contact('Jim', Male, LocalDate.of(1989, 12, 22)),
                new Contact('Sonny', Female, LocalDate.of(1992, 6, 8))
        ])

        when: 'the oldest is searched'
        def oldest = book.findOldest()

        then: 'provides correct value'
        oldest.get().name == 'Tom'
    }

    def 'should not find oldest when no contacts when book empty'() {
        given: 'empty book'
        def book = AddressBook.of()

        expect: 'nothing found'
        !book.findOldest().isPresent()
    }

    def 'should count males'() {
        given: 'book'
        def book = AddressBook.of([
                new Contact('Tom', Male, LocalDate.now()),
                new Contact('Jim', Male, LocalDate.now()),
                new Contact('Sonny', Female, LocalDate.now())
        ])

        expect: 'given correct count'
        book.countMales() == 2
    }

    @Unroll
    def 'should find contact by #pattern'() {
        given: 'book'
        def book = AddressBook.of([
                new Contact('Tom Cook', Male, LocalDate.now()),
                new Contact('Jim BadAss', Male, LocalDate.now()),
                new Contact('Sonny Brooks', Female, LocalDate.now())
        ])

        expect: 'to succeed'
        book.tryMatch(pattern).get() == expected

        where:
        pattern        | expected
        'Tom'          | new Contact('Tom Cook', Male, LocalDate.now())
        'BadAss'       | new Contact('Jim BadAss', Male, LocalDate.now())
        'Sonny Brooks' | new Contact('Sonny Brooks', Female, LocalDate.now())
    }

    def 'should not find contact when book empty'() {
        given: 'empty book'
        def book = AddressBook.of()

        expect: 'nothing found'
        !book.tryMatch("^^^").isPresent()
    }
}
