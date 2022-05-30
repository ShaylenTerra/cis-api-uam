package com.dw.ngms.cis.web.vm.workflow;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
@Data
public class UploadWorkflowDocsVm {

    private Long workflowId;

    @NotNull
    private MultipartFile file;

    @NotNull
    private Long documentType;

    @NotNull
    private String comment;

    @NotNull
    private Long userId;

    private Long cartId;
}
