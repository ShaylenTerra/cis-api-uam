package com.dw.ngms.cis.persistence.domains.dataviewer;

import com.dw.ngms.cis.enums.DataViewerRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DATA_VIEWER_REQUEST")
public class DataViewerRequest {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "data_viewer_req_seq")
    @SequenceGenerator(name = "data_viewer_req_seq", sequenceName = "DATA_VIEWER_REQ_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "REFERENCEID")
    private String referenceId;

    @Column(name = "USERID")
    private Long userid;

    @Column(name = "QUERY")
    private String query;

    @Column(name = "OBJECT_NAME")
    private String objectName;

    @Column(name = "TOTAL_RECORD")
    private Integer totalRecord;

    @Column(name = "REQUEST_DATE")
    private LocalDateTime requestDate;

    @Column(name = "PRCOESS", columnDefinition = "CHAR")
    private DataViewerRequestStatus process;

    @Column(name = "PROCESS_DATE")
    private LocalDateTime processDate;


}
