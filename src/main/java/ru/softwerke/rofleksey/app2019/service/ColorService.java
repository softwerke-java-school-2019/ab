package ru.softwerke.rofleksey.app2019.service;

import ru.softwerke.rofleksey.app2019.model.Color;

public class ColorService extends StringMapService<Color> {
    private static final ColorService INSTANCE = new ColorService();

    private ColorService() {
        super(false);
    }

    public static ColorService getInstance() {
        return INSTANCE;
    }

    @Override
    public String entityTypeName() {
        return "color";
    }
}
