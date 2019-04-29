package ru.softwerke.rofleksey.app2019.util;

import ru.softwerke.rofleksey.app2019.filter.MalformedSearchRequestException;
import ru.softwerke.rofleksey.app2019.model.Color;
import ru.softwerke.rofleksey.app2019.model.Device;
import ru.softwerke.rofleksey.app2019.model.DeviceType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.function.Supplier;

public class TestUtils {
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String[] issers = new String[]{"Samsung", "Nokia", "Apple", "HP"};
    private static Supplier<Color[]> colors;

    static {
        colors = new LazySupplier<Color[]>() {
            @Override
            Color[] init() {
                return new Color[]{
                        Color.fromRGB("red", 255, 0, 0),
                        Color.fromRGB("green", 0, 255, 0),
                        Color.fromRGB("blue", 0, 0, 255)
                };
            }
        };
    }

    @SafeVarargs
    private static <T> T oneOf(Random random, T... arr) {
        return arr[random.nextInt(arr.length)];
    }

    private static char randomChar(Random random) {
        return CHARS.charAt(random.nextInt(CHARS.length()));
    }

    private static String randomString(Random random, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(randomChar(random));
        }
        return sb.toString();
    }

    public static Device getRandomDevice(Random random) {
        BigDecimal price = BigDecimal.valueOf(random.nextInt(Integer.MAX_VALUE));
        DeviceType type = oneOf(random, DeviceType.LAPTOP, DeviceType.SMART_WATCH, DeviceType.SMARTPHONE, DeviceType.TABLET);
        LocalDate date = Instant.ofEpochMilli(random.nextInt(Integer.MAX_VALUE)).atZone(ZoneId.systemDefault()).toLocalDate();
        Color color = oneOf(random, colors.get());
        String manufacturer = oneOf(random, issers);
        String modelName = randomString(random, 10);
        return new Device(price, type, date, color, manufacturer, modelName);
    }

    public interface UnsafeConsumer<T> {
        void accept(T t) throws MalformedSearchRequestException;
    }
}
