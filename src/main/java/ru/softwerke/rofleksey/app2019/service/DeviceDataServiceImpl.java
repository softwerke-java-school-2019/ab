package ru.softwerke.rofleksey.app2019.service;

import ru.softwerke.rofleksey.app2019.model.Device;

import java.util.Comparator;
import java.util.function.Predicate;

public class DeviceDataServiceImpl extends DeviceDataService {
    private static final String ID_CRITERIA = "id";
    private static final String PRICE_CRITERIA = "price";
    private static final String TYPE_CRITERIA = "type";
    private static final String COLOR_NAME_CRITERIA = "color_name";
    private static final String COLOR_RGB_CRITERIA = "color_rgb";
    private static final String ISSUER_CRITERIA = "issuer";
    private static final String MODEL_CRITERIA = "model";

    private static DeviceDataServiceImpl INSTANCE;

    private DeviceDataServiceImpl() {
        super();
        filters.put(PRICE_CRITERIA, priceString -> d -> d.getPrice().toString().equals(priceString));
        filters.put(TYPE_CRITERIA, type -> d -> d.getType().equals(type));
        filters.put(COLOR_NAME_CRITERIA, name -> d -> d.getColorName().equals(name));
        filters.put(COLOR_RGB_CRITERIA, color -> new Predicate<Device>() {
            int colorInt = Integer.valueOf(color);

            @Override
            public boolean test(Device device) {
                return device.getColorRGB() == colorInt;
            }
        });
        filters.put(ISSUER_CRITERIA, is -> d -> d.getIssuer().equals(is));
        filters.put(MODEL_CRITERIA, m -> d -> d.getModel().equals(m));
        sorts.put(ID_CRITERIA, Comparator.comparing(Device::getId));
        sorts.put(PRICE_CRITERIA, Comparator.comparing(Device::getPrice));
        sorts.put(TYPE_CRITERIA, Comparator.comparing(Device::getType));
        sorts.put(COLOR_NAME_CRITERIA, Comparator.comparing(Device::getColorName));
        sorts.put(COLOR_RGB_CRITERIA, Comparator.comparing(Device::getColorRGB));
        sorts.put(ISSUER_CRITERIA, Comparator.comparing(Device::getIssuer));
        sorts.put(MODEL_CRITERIA, Comparator.comparing(Device::getModel));
    }

    public static DeviceDataServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DeviceDataServiceImpl();
        }
        return INSTANCE;
    }
}
