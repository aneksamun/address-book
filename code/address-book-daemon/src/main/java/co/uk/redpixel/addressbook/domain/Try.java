package co.uk.redpixel.addressbook.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor(staticName = "of")
public final class Try<T, E> implements Function<T, E> {

    private final Function<T, E> map;

    private final Consumer<Exception> onError;

    @Override
    public E apply(T t) {
        try {
            return map.apply(t);
        } catch (Exception e) {
            onError.accept(e);
            return null;
        }
    }
}
