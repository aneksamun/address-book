package co.uk.redpixel.addressbook;

import co.uk.redpixel.addressbook.core.HttpServer;
import co.uk.redpixel.addressbook.core.config.Routes;
import co.uk.redpixel.addressbook.infrastructure.persistence.AddressBookRepository;
import co.uk.redpixel.addressbook.infrastructure.property.AddressBookProperties;
import lombok.val;

import static co.uk.redpixel.addressbook.core.config.Endpoint.get;
import static co.uk.redpixel.addressbook.domain.UserAction.*;

public class Application {

    public static void main(String[] args) {
        try {
            val port = AddressBookProperties.load().getPort();
            val addressBook = AddressBookRepository.fetch();

            val routes = Routes.of(
                get("/$", welcome()),
                get("/contacts/oldest$", findOldest(addressBook)),
                get("/contacts/males/count$", countMales(addressBook)),
                get("/contacts/.+/compare/.+$", tellHowManyDaysSomeOneIsOlderThanOther(addressBook))
            );

            try (val server = HttpServer.on(port).with(routes).create()) {
                System.out.printf("Starting server on port %d...\n", server.getPort());
                System.out.println("Press Ctrl+C to exit");
                server.start();
            }

        } catch (Exception e) {
            System.out.printf("An error occurred: %s\n", e.getMessage());
        }
    }
}
