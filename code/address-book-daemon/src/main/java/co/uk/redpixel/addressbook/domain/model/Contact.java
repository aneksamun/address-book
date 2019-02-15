package co.uk.redpixel.addressbook.domain.model;

import com.sun.istack.internal.NotNull;
import lombok.*;

import java.time.LocalDate;

import static co.uk.redpixel.addressbook.domain.model.Gender.Male;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.YEARS;

@ToString
@EqualsAndHashCode
public class Contact implements Comparable<Contact> {

    @Getter
    private final String name;

    @Getter
    private final Gender gender;

    @Getter
    private final LocalDate dateOfBirth;

    @Builder
    public Contact(@NonNull String name, @NonNull Gender gender, @NonNull LocalDate dateOfBirth) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isMale() {
        return gender == Male;
    }

    public long getDaysOlderThan(Contact other) {
        return DAYS.between(dateOfBirth, other.dateOfBirth);
    }

    public long getAge() {
        return YEARS.between(LocalDate.now(), dateOfBirth);
    }

    @Override
    public int compareTo(@NotNull Contact other) {
        return dateOfBirth.compareTo(other.dateOfBirth);
    }
}
