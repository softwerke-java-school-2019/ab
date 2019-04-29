package ru.softwerke.rofleksey.app2019.service;

import ru.softwerke.rofleksey.app2019.model.Color;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * StorageService for Color
 * Additionally stores map of names to colors for fast retrieval
 */
public class ColorService extends StorageService<Color> {
    private static final ColorService INSTANCE = new ColorService();

    private final Map<String, Color> nameMap;

    private ColorService() {
        super();
        nameMap = new ConcurrentHashMap<>();
    }


    @Override
    public Color addEntity(Color entity) {
        nameMap.put(entity.getName(), entity);
        return super.addEntity(entity);
    }

    public static ColorService getInstance() {
        return INSTANCE;
    }

    public Color getByName(String name) {
        return nameMap.get(name);
    }
}
