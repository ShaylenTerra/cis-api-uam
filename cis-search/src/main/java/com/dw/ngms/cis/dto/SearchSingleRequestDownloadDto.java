package com.dw.ngms.cis.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 30/12/20, Wed
 **/
@Data
public class SearchSingleRequestDownloadDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long provinceId;

    private Date dated;

    private String dataKeyName;

    private String documentName;

    private String documentId;

    @NotNull
    @Size(min = 1)
    private Collection<String> documentUrl;

}
