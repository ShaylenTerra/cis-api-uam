package com.dw.ngms.cis.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 10/05/21, Mon
 **/
@Data
public class SearchDocumentDto {


    private String documentno;

    private String sgno;

    private String title;

    private LocalDateTime scandate;

    private Long pageno;

    private Long fileSize;

    private String url;

    private String preview;

    private String thumbnail;

    private Long documentId;

    private Long recordId;

}
