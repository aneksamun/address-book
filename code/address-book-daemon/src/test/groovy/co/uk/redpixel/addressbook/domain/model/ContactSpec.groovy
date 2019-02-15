package co.uk.redpixel.addressbook.domain.model

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

import static co.uk.redpixel.addressbook.domain.model.Gender.Female
import static co.uk.redpixel.addressbook.domain.model.Gender.Male

class ContactSpec extends Specification {

    def 'should compare contacts by age'() {
        given: 'contacts'
        def youngest = new Contact('Tom', Male, LocalDate.of(1989, 11, 11))
        def oldest = new Contact('Samanta', Female, LocalDate.of(1966, 9, 11))

        expect: 'oldest to be upfront'
        oldest > youngest
    }

    @Unroll
    def 'should resolve age for #contact.name'() {
        expect: 'age to be correct'
        contact.age == expected

        where:
        contact                                             | expected
        new Contact('Alex', Male, LocalDate.of(2011, 1, 8)) | 8
        new Contact('Sam', Male, LocalDate.of(2016, 10, 8)) | 2
    }

    @Unroll
    def 'should resolve gender for #contact.name'() {
        expect: 'gender to be correct'
        contact.male == expected

        where:
        contact                                                 | expected
        new Contact('Alex', Male, LocalDate.of(2011, 1, 8))     | true
        new Contact('Casey', Female, LocalDate.of(1988, 10, 8)) | false
    }

    def 'should determine how many days one contact is older than other'() {
        given: 'Alex'
        def alex = new Contact('Alex', Male, LocalDate.of(2018, 12, 8))

        and: 'Casey'
        def casey = new Contact('Casey', Female, LocalDate.of(2017, 12, 8))

        when: 'difference is compute'
        final days = casey.getDaysOlderThan(alex)

        then: 'it is done correct'
        days == 365
    }
}
