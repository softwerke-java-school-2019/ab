package ru.softwerke.rofleksey.app2019;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import ru.softwerke.rofleksey.app2019.model.Bill;
import ru.softwerke.rofleksey.app2019.model.Color;
import ru.softwerke.rofleksey.app2019.model.Customer;
import ru.softwerke.rofleksey.app2019.model.Device;
import ru.softwerke.rofleksey.app2019.service.ColorService;
import ru.softwerke.rofleksey.app2019.service.DataService;
import ru.softwerke.rofleksey.app2019.service.StorageService;

import javax.ws.rs.ApplicationPath;
import java.util.LinkedHashMap;

@ApplicationPath("/")
public class ShopApplication extends ResourceConfig {
    public ShopApplication() {
        packages("ru.softwerke.rofleksey.app2019;com.fasterxml.jackson.jaxrs");

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(new StorageService<Customer>()).to(new TypeLiteral<DataService<Customer>>() {
                });
                bind(new StorageService<Device>()).to(new TypeLiteral<DataService<Device>>() {
                });
                bind(new StorageService<Bill>()).to(new TypeLiteral<DataService<Bill>>() {
                });
                bind(colorService()).to(ColorService.class);
            }
        });
        setProperties(new LinkedHashMap<String, Object>() {{
            put(org.glassfish.jersey.server.ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);
        }});
    }

    private ColorService colorService() {
        ColorService service = new ColorService();
        service.addEntity(new Color("black", 0, 0, 0));
        service.addEntity(new Color("gray", 128, 128, 128));
        service.addEntity(new Color("red", 255, 0, 0));
        service.addEntity(new Color("golden", 255, 215, 0));
        service.addEntity(new Color("blue", 0, 0, 255));
        service.addEntity(new Color("silver", 192, 192, 192));
        service.addEntity(new Color("white", 255, 255, 255));
        service.addEntity(new Color("brown", 150, 75, 0));
        service.addEntity(new Color("orange", 255, 165, 0));
        service.addEntity(new Color("beige", 245, 245, 220));
        service.addEntity(new Color("yellow", 255, 255, 0));
        service.addEntity(new Color("green", 0, 128, 0));
        service.addEntity(new Color("light blue", 66, 170, 255));
        service.addEntity(new Color("purple", 139, 0, 255));
        service.addEntity(new Color("pink", 252, 15, 192));
        return service;
    }
}
