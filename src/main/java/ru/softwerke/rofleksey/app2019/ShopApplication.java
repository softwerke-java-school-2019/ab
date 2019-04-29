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


//TODO: remove 'message' fields?
//TODO: frontend

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
        ColorService service = ColorService.getInstance();
        service.addEntity(Color.fromRGB("black", 0, 0, 0));
        service.addEntity(Color.fromRGB("gray", 128, 128, 128));
        service.addEntity(Color.fromRGB("red", 255, 0, 0));
        service.addEntity(Color.fromRGB("golden", 255, 215, 0));
        service.addEntity(Color.fromRGB("blue", 0, 0, 255));
        service.addEntity(Color.fromRGB("silver", 192, 192, 192));
        service.addEntity(Color.fromRGB("white", 255, 255, 255));
        service.addEntity(Color.fromRGB("brown", 150, 75, 0));
        service.addEntity(Color.fromRGB("orange", 255, 165, 0));
        service.addEntity(Color.fromRGB("beige", 245, 245, 220));
        service.addEntity(Color.fromRGB("yellow", 255, 255, 0));
        service.addEntity(Color.fromRGB("green", 0, 128, 0));
        service.addEntity(Color.fromRGB("light blue", 66, 170, 255));
        service.addEntity(Color.fromRGB("purple", 139, 0, 255));
        service.addEntity(Color.fromRGB("pink", 252, 15, 192));
        return service;
    }
}
