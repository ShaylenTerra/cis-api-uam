package com.dw.ngms.cis.service.dto.prepackage;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 04/01/21, Mon
 **/
@Data
public class PrePackageConfigurationDto {

    private Long prePackageId;

    @NotNull
    private String name;

    @NotNull
    private String description;


    private MultipartFile sampleImageFile;

    private String sampleFileName;

    @NotNull
    private Long prePackageDataType;

    @NotNull
    private Long cost;

    @NotNull
    private Integer active;

    @JsonRawValue
    private String configurationData;

    private Long transactionId;

    @NotNull
    private String folder;

}
