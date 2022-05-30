package com.dw.ngms.cis.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 30/12/20, Wed
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SEARCH_SINGLE_REQUEST_DOWNLOAD")
public class SearchSingleRequestDownload {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "search_single_request_download_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "search_single_request_download_seq",
            sequenceName = "SEARCH_SINGLE_REQ_DOWNLOAD_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "PROVINCEID")
    private Long provinceId;

    @Column(name = "DATED")
    private Date dated;

    @Column(name = "DATAKEY_NAME")
    private String dataKeyName;

    @Column(name = "DOCUMENT_NAME")
    private String documentName;

    @Column(name = "DOCUMENT_ID")
    private String documentId;

    @Column(name = "DOCUMENT_URL")
    private String documentUrl;

}

