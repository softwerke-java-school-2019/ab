package ru.softwerke.rofleksey.app2019.storage;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.softwerke.rofleksey.app2019.filter.DeviceRequest;
import ru.softwerke.rofleksey.app2019.filter.MalformedSearchRequestException;
import ru.softwerke.rofleksey.app2019.filter.SearchRequest;
import ru.softwerke.rofleksey.app2019.model.Device;
import ru.softwerke.rofleksey.app2019.util.TestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataStorageMultipleLoadTest extends DataStorageGenericTest<Device> {
    private static final int REPEAT_COUNT = 1000;

    @BeforeAll
    void init() {
        storage.add(TestUtils.getRandomDevice(random));
    }

    @Test
    @RepeatedTest(value = REPEAT_COUNT, name = "byId {currentRepetition} / {totalRepetitions}")
    void byId() {
        long id = addDevice().getId();
        Device deviceById = storage.getById(id);
        assertEquals(id, deviceById.getId(), "getById returned wrong device");
    }


    @Test
    @RepeatedTest(value = REPEAT_COUNT, name = "search {currentRepetition} / {totalRepetitions}")
    void bySearch() {
        Device addedDevice = addDevice();
        SearchRequest<Device> request = new DeviceRequest();
        try {
            request.withFilterOptions("modelName", addedDevice.getModelName());
            List<Device> result = storage.executeQuery(request.buildQuery());
            assertFalse(result.isEmpty(), "search returned nothing");
            Device device = result.get(0);
            assertEquals(addedDevice.getId(), device.getId(), "search returned wrong device");
        } catch (MalformedSearchRequestException e) {
            fail("malformed search request", e);
        }
    }

    private Device addDevice() {
        Device device = TestUtils.getRandomDevice(random);
        storage.add(device);
        return device;
    }
}