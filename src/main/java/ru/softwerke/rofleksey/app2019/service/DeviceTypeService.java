package ru.softwerke.rofleksey.app2019.service;

import ru.softwerke.rofleksey.app2019.model.DeviceType;

public class DeviceTypeService extends StringMapService<DeviceType> {
    private static final DeviceTypeService INSTANCE = new DeviceTypeService();

    private DeviceTypeService() {
        super(false);
    }

    public static DeviceTypeService getInstance() {
        return INSTANCE;
    }

    @Override
    public String entityTypeName() {
        return "device";
    }
}
