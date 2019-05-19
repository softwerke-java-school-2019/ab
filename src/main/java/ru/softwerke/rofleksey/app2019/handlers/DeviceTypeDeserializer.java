package ru.softwerke.rofleksey.app2019.handlers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang3.StringUtils;
import ru.softwerke.rofleksey.app2019.model.Color;
import ru.softwerke.rofleksey.app2019.model.DeviceType;
import ru.softwerke.rofleksey.app2019.service.DeviceTypeService;

import java.io.IOException;

/**
 * Uses ColorService to determine if target color exists
 */
public class DeviceTypeDeserializer extends JsonDeserializer<DeviceType> {
    //TODO: idk how to fix this, injection doesn't work
    private final DeviceTypeService service = DeviceTypeService.getInstance();

    @Override
    public DeviceType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        DeviceType type = service.getByName(StringUtils.lowerCase(p.getText()));
        if (type == null) {
            throw new InvalidFormatException(p, "device type doesn't exist", p.getText(), Color.class);
        }
        return type;
    }
}
