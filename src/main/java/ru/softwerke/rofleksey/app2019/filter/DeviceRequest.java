package ru.softwerke.rofleksey.app2019.filter;

import org.apache.commons.lang3.StringUtils;
import ru.softwerke.rofleksey.app2019.model.Device;
import ru.softwerke.rofleksey.app2019.model.ModelUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class DeviceRequest extends SearchRequest<Device> {
    private static final String ID_CRITERIA = "id";
    private static final String PRICE_CRITERIA = "price";
    private static final String DEVICE_TYPE_CRITERIA = "deviceType";
    private static final String MANUFACTURE_DATE_CRITERIA = "manufactureDate";
    private static final String COLOR_NAME_CRITERIA = "colorName";
    private static final String COLOR_RGB_CRITERIA = "colorRgb";
    private static final String MANUFACTURER_CRITERIA = "manufacturer";
    private static final String MODEL_NAME_CRITERIA = "modelName";

    private static final Map<String, FilterFactory<Device>> filterFactories;
    private static final Map<String, Comparator<Device>> comparators;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern(Device.DATE_FORMAT);

    static {

        Map<String, FilterFactory<Device>> filterFactoriesTemp = new HashMap<>();
        Map<String, Comparator<Device>> comparatorTemp = new HashMap<>();
        SearchRequestUtils.addRange(filterFactoriesTemp, PRICE_CRITERIA, p -> {
            double price = SearchRequestUtils.parseString(p, Double::valueOf);
            return device -> Double.compare(device.getPriceDouble(), price);
        });
        SearchRequestUtils.addRange(filterFactoriesTemp, MANUFACTURE_DATE_CRITERIA, date -> {
            LocalDate tmpDate = SearchRequestUtils.parseString(date, it -> LocalDate.parse(it, format));
            long tmpLong = ModelUtils.localDateToLong(tmpDate);
            return device -> Long.compare(device.getDateLong(), tmpLong);
        });
        filterFactoriesTemp.put(DEVICE_TYPE_CRITERIA, t -> {
            String type = StringUtils.lowerCase(t);
            return device -> device.getType().equals(type);
        });
        filterFactoriesTemp.put(COLOR_NAME_CRITERIA, name -> {
            String colorName = StringUtils.lowerCase(name);
            return d -> d.getColorName().equals(colorName);
        });
        filterFactoriesTemp.put(COLOR_RGB_CRITERIA, color -> {
            int colorInt = SearchRequestUtils.parseString(color, Integer::valueOf);
            return device -> device.getColorRGB() == colorInt;
        });
        filterFactoriesTemp.put(MANUFACTURER_CRITERIA, is -> d -> d.getManufacturer().equals(is));
        filterFactoriesTemp.put(MODEL_NAME_CRITERIA, m -> d -> d.getModelName().equals(m));
        comparatorTemp.put(ID_CRITERIA, Comparator.comparing(Device::getId));
        comparatorTemp.put(PRICE_CRITERIA, Comparator.comparing(Device::getPrice));
        comparatorTemp.put(DEVICE_TYPE_CRITERIA, Comparator.comparing(Device::getType));
        comparatorTemp.put(COLOR_NAME_CRITERIA, Comparator.comparing(Device::getColorName));
        comparatorTemp.put(COLOR_RGB_CRITERIA, Comparator.comparing(Device::getColorRGB));
        comparatorTemp.put(MANUFACTURER_CRITERIA, Comparator.comparing(Device::getManufacturer));
        comparatorTemp.put(MANUFACTURE_DATE_CRITERIA, Comparator.comparing(Device::getDateLong));
        comparatorTemp.put(MODEL_NAME_CRITERIA, Comparator.comparing(Device::getModelName));

        filterFactories = Collections.unmodifiableMap(filterFactoriesTemp);
        comparators = Collections.unmodifiableMap(comparatorTemp);
    }

    @Override
    Map<String, FilterFactory<Device>> getFilterFactories() {
        return filterFactories;
    }

    @Override
    Map<String, Comparator<Device>> getComparators() {
        return comparators;
    }
}
