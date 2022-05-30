package com.dw.ngms.cis.vms;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author : prateekgoel
 * @since : 13/01/21, Wed
 **/
public class TemplateSearchVmSerializer extends StdSerializer<TemplateSearchVm> {

    public TemplateSearchVmSerializer() {
        this(null);
    }

    public TemplateSearchVmSerializer(Class<TemplateSearchVm> type) {
        super(type);
    }

    @Override
    public void serialize(TemplateSearchVm value,
                          JsonGenerator gen,
                          SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("provinceShortName", value.getProvinceShortName());
        gen.writeNumberField("searchTypeId", value.getSearchTypeId());
        gen.writeNumberField("searchFilterId", value.getSearchFilterId());
        gen.writeNumberField("provinceId", value.getProvinceId());
        gen.writeNumberField("userId", value.getUserId());
        gen.writeBinaryField("file", value.getFile().getBytes());
        gen.writeEndObject();
    }
}
