package ru.softwerke.rofleksey.app2019.storage;

import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;
import ru.softwerke.rofleksey.app2019.model.Device;
import ru.softwerke.rofleksey.app2019.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataStorageIdTest extends DataStorageGenericTest<Device> {
    private static final int REPEAT_COUNT = 1000;
    private int lastId = -1;


    @Test
    @RepeatedTest(value = REPEAT_COUNT, name = "adding {currentRepetition} / {totalRepetitions}")
    void add() {
        int curId = ++lastId;
        Device device = TestUtils.getRandomDevice(random);
        Device addedDevice = storage.add(device);
        assertNotNull(addedDevice, "returned device is null");
        assertEquals(addedDevice.getId(), curId, "addedDevice id is invalid");
    }
}