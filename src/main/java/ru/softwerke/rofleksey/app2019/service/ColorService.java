package ru.softwerke.rofleksey.app2019.service;

import ru.softwerke.rofleksey.app2019.model.Color;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ColorService extends StorageService<Color> {
    private final Map<String, Color> nameMap;

    public ColorService() {
        super();
        nameMap = new ConcurrentHashMap<>();
    }


    @Override
    public Color addEntity(Color entity) {
        nameMap.put(entity.getName(), entity);
        return super.addEntity(entity);
    }

    public Color getByName(String name) {
        return nameMap.get(name);
    }
}
