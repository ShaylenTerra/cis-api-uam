package com.dw.ngms.cis.persistence.domains;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author : Name
 * @since : 19/11/20, Thu
 **/
@Entity
@Getter
@Setter
@Table(name = "M_TEMPLATES")
public class Template {

	@Id
	@Column(name = "TEMPLATEID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "template_id_sequence")
	@SequenceGenerator(name = "template_id_sequence", sequenceName = "M_TEMPLATES_SEQ", allocationSize = 1)
	private Long templateId;

    @Column(name = "TEMPLATENAME")
    private String templateName;

    @Column(name = "ITEMID_MODULE")
    private Long itemIdModule;

    @Column(name = "ISACTIVE")
    private Integer isActive;

    @Column(name = "PDF_DETAILS")
    @Lob
    private String pdfDetails;

    @Column(name = "EMAIL_DETAILS")
    private String emailDetails;

    @Column(name = "SMS_DETAILS")
    private String smsDetails;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "DATED")
    private LocalDateTime dated;

}
