package ru.softwerke.rofleksey.app2019.storage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.softwerke.rofleksey.app2019.filter.DeviceRequest;
import ru.softwerke.rofleksey.app2019.filter.MalformedSearchRequestException;
import ru.softwerke.rofleksey.app2019.filter.SearchRequest;
import ru.softwerke.rofleksey.app2019.model.Device;
import ru.softwerke.rofleksey.app2019.util.TestUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
class DeviceQueryTest extends DataStorageGenericTest<Device> {
    private static final Logger logger = LoggerFactory.getLogger(DeviceQueryTest.class);
    private static final int DATASET_SIZE = 900;
    private static final String DATASET_SIZE_STRING = DATASET_SIZE + "";
    private final ArrayList<Device> testList = new ArrayList<>();

    @BeforeAll
    void init() {
        try {
            for (int i = 0; i < DATASET_SIZE; i++) {
                Device device = TestUtils.getRandomDevice(random);
                device.init();
                storage.addEntity(device);
                testList.add(device);
            }
        } catch (StorageError e) {
            e.printStackTrace();
        }
    }

    @Test
    void byId() {
        sampleTest(request -> {
            request.withOrderType("id");
        }, Comparator.comparing(Device::getId));
    }

    @Test
    void byIdReverse() {
        sampleTest(request -> {
            request.withOrderType("-id");
        }, Comparator.comparing(Device::getId).reversed());
    }

    @Test
    void byDateAndIdReversed() {
        sampleTest(request -> {
            request.withOrderType("manufactureDate,-id");
        }, Comparator.comparing(Device::getManufactureDate), Comparator.comparing(Device::getId).reversed());
    }

    @Test
    void byManufacturerAndModel() {
        sampleTest(request -> {
            request.withOrderType("-modelName");
            request.withFilterOptions("manufacturer", "HP");
        }, Comparator.comparing(Device::getModelName).reversed(), device -> device.getManufacturer().equals("HP"));
    }

    @Test
    void byPriceRange() {
        sampleTest(request -> {
                    request.withFilterOptions("priceFrom", "1417188420");
                    request.withFilterOptions("priceTo", "1417188422");
                }, device -> device.getPrice().compareTo(BigDecimal.valueOf(1417188420)) >= 0,
                device -> device.getPrice().compareTo(BigDecimal.valueOf(1417188422)) <= 0);
    }

    @Test
    void byPrice() {
        sampleTest(request -> {
            request.withFilterOptions("price", "1417188421");
        }, device -> device.getPrice().equals(BigDecimal.valueOf(1417188421)));
    }

    @Test
    void byColorName() {
        sampleTest(request -> {
            request.withFilterOptions("colorName", "red");
        }, device -> device.getColorName().equals("red"));
    }

    @Test
    void byColorRgb() {
        sampleTest(request -> {
            request.withOrderType("colorRgb");
        }, Comparator.comparing(Device::getColorRGB));
    }

    @Test
    void byType1() {
        sampleTest(request -> {
            request.withFilterOptions("deviceType", "Smartphone");
        }, device -> device.getType().equalsIgnoreCase("smartphone"));
    }

    @Test
    void byType2() {
        sampleTest(request -> {
            request.withOrderType("deviceType");
        }, Comparator.comparing(Device::getType));
    }

    @Test
    void invalidManufacturer() {
        emptyTest(request -> {
            request.withFilterOptions("manufacturer", "HP1");
        });
    }

    @Test
    void invalidOrderTypeComa() {
        errorTest(request -> {
            request.withOrderType("manufactureDate, ,-id");
        }, "invalid order type: ''");
    }

    @Test
    void invalidDate() {
        errorTest(request -> {
            request.withFilterOptions("manufactureDate", "lul");
        }, "invalid date/time format: 'lul'");
    }

    @Test
    void invalidPrice() {
        errorTest(request -> {
            request.withFilterOptions("price", "a");
        }, "number expected: 'a'");
    }

    @Test
    void invalidFilter() {
        errorTest(request -> {
            request.withFilterOptions("ya", "doma");
        }, "invalid filter type: 'ya'");
    }

    @Test
    void invalidOrderType() {
        errorTest(request -> {
            request.withOrderType("ya");
        }, "invalid order type: 'ya'");
    }

    @Test
    void invalidOrderTypeMinus() {
        errorTest(request -> {
            request.withOrderType("-");
        }, "invalid order type: ''");
    }

    @SafeVarargs
    private final void sampleTest(TestUtils.UnsafeConsumer<SearchRequest<Device>> optionsConsumer, Comparator<Device>... comparators) {
        sampleTest(optionsConsumer, Arrays.asList(comparators), Collections.emptyList());
    }

    @SafeVarargs
    private final void sampleTest(TestUtils.UnsafeConsumer<SearchRequest<Device>> optionsConsumer, Predicate<Device>... filter) {
        sampleTest(optionsConsumer, Collections.emptyList(), Arrays.asList(filter));
    }

    private void sampleTest(TestUtils.UnsafeConsumer<SearchRequest<Device>> optionsConsumer, Comparator<Device> comparator,
                            Predicate<Device> filter) {
        sampleTest(optionsConsumer, Collections.singletonList(comparator), Collections.singletonList(filter));
    }


    private void emptyTest(TestUtils.UnsafeConsumer<SearchRequest<Device>> optionsConsumer) {
        try {
            List<Device> requestResponse = executeRequest(optionsConsumer);
            assertTrue(requestResponse.isEmpty(), "response is not empty");
        } catch (MalformedSearchRequestException e) {
            fail("malformed search request", e);
        }
    }

    private void errorTest(TestUtils.UnsafeConsumer<SearchRequest<Device>> optionsConsumer, String message) {
        try {
            executeRequest(optionsConsumer);
            fail("no error occured");
        } catch (MalformedSearchRequestException e) {
            assertEquals(message, e.getMessage(), "invalid error message");
        }
    }

    private void sampleTest(TestUtils.UnsafeConsumer<SearchRequest<Device>> optionsConsumer, List<Comparator<Device>> comparators,
                            List<Predicate<Device>> filters) {
        try {
            List<Device> requestResponse = executeRequest(optionsConsumer);
            if (requestResponse.isEmpty()) {
                logger.warn("requestResponse is empty");
            }
            Stream<Device> stream = testList.stream();
            for (Predicate<Device> filter : filters) {
                stream = stream.filter(filter);
            }
            Optional<Comparator<Device>> foldedComparatorHolder = comparators.stream().reduce(Comparator::thenComparing);
            if (foldedComparatorHolder.isPresent()) {
                stream = stream.sorted(foldedComparatorHolder.get());
            }
            List<Device> testResult = stream.collect(Collectors.toList());
            assertEquals(testResult, requestResponse, "lists are not equal");
        } catch (MalformedSearchRequestException e) {
            fail("malformered search request", e);
        }
    }

    private List<Device> executeRequest(TestUtils.UnsafeConsumer<SearchRequest<Device>> optionsConsumer) throws MalformedSearchRequestException {
        SearchRequest<Device> request = new DeviceRequest();
        request.withPageItemsCount(DATASET_SIZE_STRING);
        optionsConsumer.accept(request);
        return storage.search(request.buildQuery());
    }
}