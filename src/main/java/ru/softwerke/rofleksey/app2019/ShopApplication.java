package ru.softwerke.rofleksey.app2019;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import ru.softwerke.rofleksey.app2019.model.Bill;
import ru.softwerke.rofleksey.app2019.model.Customer;
import ru.softwerke.rofleksey.app2019.model.Device;
import ru.softwerke.rofleksey.app2019.service.DataService;
import ru.softwerke.rofleksey.app2019.service.StorageService;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class ShopApplication extends ResourceConfig {
    public ShopApplication() {
        packages("ru.softwerke.rofleksey.app2019;com.fasterxml.jackson.jaxrs");

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(new TypeLiteral<StorageService<Customer>>() {

                }).to(new TypeLiteral<DataService<Customer>>() {

                });

                bindAsContract(new TypeLiteral<StorageService<Device>>() {

                }).to(new TypeLiteral<DataService<Device>>() {

                });

                bindAsContract(new TypeLiteral<StorageService<Bill>>() {

                }).to(new TypeLiteral<DataService<Bill>>() {

                });
            }
        });
    }
}
