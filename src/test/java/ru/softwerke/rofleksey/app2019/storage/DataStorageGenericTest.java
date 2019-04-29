package ru.softwerke.rofleksey.app2019.storage;

import ru.softwerke.rofleksey.app2019.model.Model;

import java.util.Random;

public class DataStorageGenericTest<T extends Model> {
    Storage<T> storage = new DataStorage<T>();
    Random random = new Random(1337);
}
