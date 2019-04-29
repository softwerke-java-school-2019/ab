package ru.softwerke.rofleksey.app2019.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DeviceType {
    @JsonProperty("Smartphone")
    SMARTPHONE,

    @JsonProperty("Laptop")
    LAPTOP,

    @JsonProperty("Smart Watch")
    SMART_WATCH,

    @JsonProperty("Tablet")
    TABLET
}
