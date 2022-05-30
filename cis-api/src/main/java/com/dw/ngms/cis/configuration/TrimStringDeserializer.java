package com.dw.ngms.cis.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * @author : prateekgoel
 * @since : 22/06/21, Tue
 **/
@JsonComponent
public class TrimStringDeserializer extends StringDeserializer {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String text = super.deserialize(p, ctx);
        return text != null ? text.trim() : null;
    }
}
