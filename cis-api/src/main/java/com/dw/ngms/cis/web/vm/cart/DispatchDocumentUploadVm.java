package com.dw.ngms.cis.web.vm.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 11/02/21, Thu
 **/
@Data
public class DispatchDocumentUploadVm {

    @JsonProperty("file")
    @NotNull
    private MultipartFile file;

    private String notes;

    @NotNull
    private Long documentTypeId;

    @NotNull
    private Long cartItemId;

    @NotNull
    private Long workflowId;

    @NotNull
    private Long userId;

}
