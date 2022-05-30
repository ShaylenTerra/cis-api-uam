package com.dw.ngms.cis.vms;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : prateekgoel
 * @since : 12/01/21, Tue
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@JsonSerialize(using = TemplateSearchVmSerializer.class)
public class TemplateSearchVm extends SearchVm {

    private MultipartFile file;

}
