package com.dw.ngms.cis.persistence.domains.dataviewer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DATA_VIEWER_LOG")
public class DataViewerLog {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "data_viewer_log_seq")
    @SequenceGenerator(name = "data_viewer_log_seq", sequenceName = "DATA_VIEWER_LOG_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "USERID")
    private Long userid;

    @Column(name = "DATED")
    private LocalDateTime dated;

    @Column(name = "QUERY")
    private String query;

    @Column(name = "OBJECT_NAME")
    private String objectName;

    @Column(name = "ISFTP")
    private Long isFtp;

    @Column(name = "ISEMAIL")
    private Long isEmail;

}
