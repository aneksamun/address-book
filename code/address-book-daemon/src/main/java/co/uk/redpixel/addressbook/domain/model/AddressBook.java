package co.uk.redpixel.addressbook.domain.model;

import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.*;
import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static java.util.Comparator.naturalOrder;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public final class AddressBook implements Iterable<Contact>  {

    private final Set<Contact> contacts;

    public Optional<Contact> findOldest() {
        return contacts.stream().max(naturalOrder());
    }

    public long countMales() {
        return contacts.stream().filter(Contact::isMale).count();
    }

    public Optional<Contact> tryMatch(String namePattern) {
        val pattern = namePattern.toLowerCase();
        return contacts.stream()
                .filter(person -> person.getName().toLowerCase().contains(pattern))
                .findFirst();
    }

    @Override
    public Iterator<Contact> iterator() {
        return contacts.iterator();
    }

    @Override
    public void forEach(Consumer<? super Contact> action) {
        contacts.forEach(action);
    }

    @Override
    public Spliterator<Contact> spliterator() {
        return contacts.spliterator();
    }

    public static AddressBook of(Contact... contacts) {
        val book = new AddressBook(new HashSet<>());
        book.contacts.addAll(asList(contacts));
        return book;
    }

    public static AddressBook of(Collection<Contact> contacts) {
        return new AddressBook(new HashSet<>(contacts));
    }
}
