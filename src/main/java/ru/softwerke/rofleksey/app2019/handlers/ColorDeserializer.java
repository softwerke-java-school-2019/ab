package ru.softwerke.rofleksey.app2019.handlers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang3.StringUtils;
import ru.softwerke.rofleksey.app2019.model.Color;
import ru.softwerke.rofleksey.app2019.service.ColorService;

import java.io.IOException;

/**
 * Uses ColorService to determine if target color exists
 */
public class ColorDeserializer extends JsonDeserializer<Color> {
    //TODO: idk how to fix this, injection doesn't work
    private final ColorService service = ColorService.getInstance();

    @Override
    public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Color color = service.getByName(StringUtils.lowerCase(p.getText()));
        if (color == null) {
            throw new InvalidFormatException(p, "color doesn't exist", p.getText(), Color.class);
        }
        return color;
    }
}
