package com.dw.ngms.cis.service.dto.dataviewer;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 11/03/21, Thu
 **/
@Data
public class DataViewerRequestDto {

    private Long id;

    private Long userid;

    private String query;

    private String objectName;

    private Long totalRecord;

    private LocalDateTime requestDate;

    private String process;

    private LocalDateTime processDate;

}
