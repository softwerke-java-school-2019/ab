package ru.softwerke.rofleksey.app2019;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import ru.softwerke.rofleksey.app2019.service.*;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class ShopApplication extends ResourceConfig {
    public ShopApplication() {
        packages("ru.softwerke.rofleksey.app2019;com.fasterxml.jackson.jaxrs");

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(customerService()).to(CustomerDataService.class);
                bind(deviceService()).to(DeviceDataService.class);
                bind(billService()).to(BillDataService.class);
            }
        });
    }

    private CustomerDataService customerService() {
        CustomerDataService service = CustomerDataServiceImpl.getInstance();
//        service.addEntity(new Customer("Borisov", "Aleksey", "Mikhailovich", 100));
//        service.addEntity(new Customer("Kurilenko", "Vlad", "Privet", 90));
//        service.addEntity(new Customer("Solyanov", "Ivan", "Privet", 200));
        return service;
    }

    private DeviceDataService deviceService() {
        DeviceDataService service = DeviceDataServiceImpl.getInstance();
//        service.addEntity(new Device("30000", "computer", "green", 0x00FF00, "Sony", "B"));
//        service.addEntity(new Device("9000", "phone", "red", 0xFF0000, "Sony", "A"));
//        service.addEntity(new Device("12000", "phone", "green", 0x00FF00, "Samsung", "C"));
        return service;
    }

    private BillDataService billService() {
        BillDataService service = BillDataServiceImpl.getInstance();
//        {
//            ArrayList<BillItem> items = new ArrayList<>();
//            items.add(new BillItem(0, 1, BigDecimal.valueOf(30000)));
//            service.addEntity(new Bill(0, items, 0, 50));
//        }
//        {
//            ArrayList<BillItem> items = new ArrayList<>();
//            items.add(new BillItem(1, 10, BigDecimal.valueOf(9000)));
//            service.addEntity(new Bill(0, items, 0, 20));
//        }
//        {
//            ArrayList<BillItem> items = new ArrayList<>();
//            items.add(new BillItem(1, 5, BigDecimal.valueOf(200)));
//            items.add(new BillItem(0, 100, BigDecimal.valueOf(100)));
//            items.add(new BillItem(2, 1, BigDecimal.valueOf(0)));
//            service.addEntity(new Bill(0, items, 1, 40));
//        }
        return service;
    }
}
