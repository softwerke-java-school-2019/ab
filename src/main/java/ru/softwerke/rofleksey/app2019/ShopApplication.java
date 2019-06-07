package ru.softwerke.rofleksey.app2019;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.softwerke.rofleksey.app2019.model.*;
import ru.softwerke.rofleksey.app2019.service.ColorService;
import ru.softwerke.rofleksey.app2019.service.DeviceTypeService;
import ru.softwerke.rofleksey.app2019.service.StorageService;
import ru.softwerke.rofleksey.app2019.storage.Storage;
import ru.softwerke.rofleksey.app2019.storage.StorageError;

import javax.ws.rs.ApplicationPath;
import java.util.LinkedHashMap;


//TODO: remove 'message' fields?
//TODO: frontend + byId?
//TODO: russian names?
//TODO: tweak api readme
//TODO: substring search
//TODO: init state -> file
//TODO: swagger?? - javadoc

@ApplicationPath("/")
public class ShopApplication extends ResourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(ShopApplication.class);

    public ShopApplication() {
        packages("ru.softwerke.rofleksey.app2019;com.fasterxml.jackson.jaxrs");

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(new StorageService<Customer>() {
                    @Override
                    public String entityTypeName() {
                        return "customer";
                    }
                }).to(new TypeLiteral<Storage<Customer>>() {
                });
                bind(new StorageService<Device>() {
                    @Override
                    public String entityTypeName() {
                        return "device";
                    }
                }).to(new TypeLiteral<Storage<Device>>() {
                });
                bind(new StorageService<Bill>() {
                    @Override
                    public String entityTypeName() {
                        return "bill";
                    }
                }).to(new TypeLiteral<Storage<Bill>>() {
                });
                bind(colorService()).to(new TypeLiteral<Storage<Color>>() {
                });
                bind(deviceTypeService()).to(new TypeLiteral<Storage<DeviceType>>() {
                });
            }
        });
        setProperties(new LinkedHashMap<String, Object>() {{
            put(org.glassfish.jersey.server.ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);
        }});
    }

    private ColorService colorService() {
        ColorService service = ColorService.getInstance();
        try {
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
        } catch (StorageError e) {
            logger.error("error occurred during colorService initialization", e);
        }
        return service;
    }

    private DeviceTypeService deviceTypeService() {
        DeviceTypeService service = DeviceTypeService.getInstance();
        try {
            service.addEntity(new DeviceType("laptop"));
            service.addEntity(new DeviceType("smart watch"));
            service.addEntity(new DeviceType("smartphone"));
            service.addEntity(new DeviceType("tablet"));
        } catch (StorageError e) {
            logger.error("error occurred during deviceTypeService initialization", e);
        }
        return service;
    }
}
