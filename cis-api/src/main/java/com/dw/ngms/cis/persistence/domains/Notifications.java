package com.dw.ngms.cis.persistence.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NOTIFICATIONS")
public class Notifications {

	@Id
	@Column(name = "NOTIFICATIONID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_id_sequence")
	@SequenceGenerator(name = "notification_id_sequence", sequenceName = "NOTIFICATIONS_SEQ", allocationSize = 1)
	private Long notificationId;

	@Column(name = "NOTIFICATIONTYPEITEMID")
	private Long notificationTypeItemId;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "BODY")
	private String body;

	@Column(name = "REGARDS")
	private String regards;

	@Column(name = "NOTIFICATIONUSERTYPESITEMID")
	private Long notificationUserTypesItemId;

	@Column(name = "NOTIFICATIONSTATUS")
	private String notificationStatus;

	@Column(name = "CREATEDBY")
	private String createdBy;

	@Column(name = "CREATEDDATE")
	private LocalDateTime createdDate;

	@Column(name = "NOTIFICATIONDOCS")
	private String notificationDocs;

	@Column(name = "NOTIFICATIONSUBTYPEITEMID")
	private long notificationSubTypeItemId;

	@Column(name = "PROVINCEID")
	private long provinceId;

	@Column(name = "DOCUMENTSTORECODE")
	private String documentStoreCode;

}




