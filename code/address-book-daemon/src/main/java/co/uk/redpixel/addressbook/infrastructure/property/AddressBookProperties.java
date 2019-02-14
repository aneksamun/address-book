package co.uk.redpixel.addressbook.infrastructure.property;

import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static co.uk.redpixel.addressbook.common.Error.CONFIG_LOAD_ERROR;

public final class AddressBookProperties extends Properties {

    private static final int DEFAULT_PORT = 8091;

    private static final String ADDRESS_BOOK_PORT = "address.book.daemon.port";
    private static final String PROPERTIES_FILE = "application.properties";

    private AddressBookProperties() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            try (InputStream stream = classLoader.getResourceAsStream(PROPERTIES_FILE)) {
                this.load(stream);
            }
        } catch (Exception e) {
            throw new ConfigurationException(CONFIG_LOAD_ERROR, e.getMessage());
        }
    }

    public int getPort() {
        return Optional.ofNullable(getProperty(ADDRESS_BOOK_PORT))
                       .map(Integer::parseInt)
                       .orElse(DEFAULT_PORT);
    }

    public static AddressBookProperties load() {
        return new AddressBookProperties();
    }
}
