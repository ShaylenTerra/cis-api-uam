package com.dw.ngms.cis.persistence.domains.document;

import com.dw.ngms.cis.enums.UserDocumentType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USER_DOCUMENTS")
public class UserDocument {

    @Id
    @Column(name = "DOC_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_documents_seq")
    @SequenceGenerator(name = "user_documents_seq", sequenceName = "USER_DOCUMENTS_SEQ", allocationSize = 1)
    private Long documentId;

    @Column(name = "CONTEXT_ID")
    private Long contextId;

    @Column(name = "CONTEXT_TYPE_ID")
    private Long contextTypeId;

    @Column(name = "CONTEXT")
    private String context;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ISACTIVE")
    private Long isActive;

    @Column(name = "DOCUMENT_TYPE_ID")
    private UserDocumentType documentTypeId;

    @Column(name = "CONTENT_TYPE")
    private String contentType;

    @Column(name = "FILE_NAME")
    private String fileName;
}
