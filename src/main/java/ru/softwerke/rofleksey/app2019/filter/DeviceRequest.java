package ru.softwerke.rofleksey.app2019.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.softwerke.rofleksey.app2019.model.Device;
import ru.softwerke.rofleksey.app2019.model.DeviceType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class DeviceRequest extends SearchRequest<Device> {
    private static final String ID_CRITERIA = "id";
    private static final String PRICE_CRITERIA = "price";
    private static final String TYPE_CRITERIA = "type";
    private static final String DATE_CRITERIA = "date";
    private static final String COLOR_NAME_CRITERIA = "colorName";
    private static final String COLOR_RGB_CRITERIA = "colorRGB";
    private static final String ISSUER_CRITERIA = "issuer";
    private static final String MODEL_CRITERIA = "model";

    private static final Map<String, FilterFactory<Device>> filterFactories;
    private static final Map<String, Comparator<Device>> comparators;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern(Device.DATE_FORMAT);

    static {
        Map<String, FilterFactory<Device>> filterFactoriesTemp = new HashMap<>();
        Map<String, Comparator<Device>> comparatorTemp = new HashMap<>();
//        filterFactoriesTemp.put(PRICE_CRITERIA, p -> {
////            double price = SearchRequestUtils.parseString(p, Double::valueOf);
////            return device -> Double.compare(device.getPriceDouble(), price) == 0;
////        });
////        filterFactoriesTemp.put(PRICE_FROM_CRITERIA, p -> {
////            double price = SearchRequestUtils.parseString(p, Double::valueOf);
////            return device -> Double.compare(device.getPriceDouble(), price) >= 0;
////        });
////        filterFactoriesTemp.put(PRICE_TO_CRITERIA, p -> {
////            double price = SearchRequestUtils.parseString(p, Double::valueOf);
////            return device -> Double.compare(device.getPriceDouble(), price) <= 0;
////        });
        SearchRequestUtils.addRange(filterFactoriesTemp, PRICE_CRITERIA, p -> {
            double price = SearchRequestUtils.parseString(p, Double::valueOf);
            return device -> Double.compare(device.getPriceDouble(), price);
        });
        SearchRequestUtils.addRange(filterFactoriesTemp, DATE_CRITERIA, date -> {
            LocalDate tmpDate = SearchRequestUtils.parseString(date, it -> LocalDate.parse(it, format));
            long tmpLong = tmpDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
            return device -> Long.compare(device.getDateLong(), tmpLong);
        });
        filterFactoriesTemp.put(TYPE_CRITERIA, t -> {
            DeviceType type = SearchRequestUtils.parseString(t, it -> {
                try {
                    return mapper.readValue("\"" + it + "\"", DeviceType.class);
                } catch (IOException e) {
                    throw new IllegalArgumentException();
                }
            });
            return device -> device.getType().equals(type);
        });
        filterFactoriesTemp.put(COLOR_NAME_CRITERIA, name -> d -> d.getColorName().equals(name));
        filterFactoriesTemp.put(COLOR_RGB_CRITERIA, color -> {
            int colorInt = SearchRequestUtils.parseString(color, Integer::valueOf);
            return device -> device.getColorRGB() == colorInt;
        });
        filterFactoriesTemp.put(ISSUER_CRITERIA, is -> d -> d.getIssuer().equals(is));
        filterFactoriesTemp.put(MODEL_CRITERIA, m -> d -> d.getModel().equals(m));
        comparatorTemp.put(ID_CRITERIA, Comparator.comparing(Device::getId));
        comparatorTemp.put(PRICE_CRITERIA, Comparator.comparing(Device::getPrice));
        comparatorTemp.put(TYPE_CRITERIA, Comparator.comparing(Device::getType));
        comparatorTemp.put(COLOR_NAME_CRITERIA, Comparator.comparing(Device::getColorName));
        comparatorTemp.put(COLOR_RGB_CRITERIA, Comparator.comparing(Device::getColorRGB));
        comparatorTemp.put(ISSUER_CRITERIA, Comparator.comparing(Device::getIssuer));
        comparatorTemp.put(MODEL_CRITERIA, Comparator.comparing(Device::getModel));

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
