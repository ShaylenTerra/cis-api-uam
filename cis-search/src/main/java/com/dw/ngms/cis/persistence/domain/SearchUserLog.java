package com.dw.ngms.cis.persistence.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 07/12/20, Mon
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SEARCH_USER_LOG")
public class SearchUserLog {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "srch_user_log_seq")
	@SequenceGenerator(name = "srch_user_log_seq", sequenceName = "SEARCH_USER_LOG_SEQ", allocationSize = 1)
	private Long id;

	@Column(name = "USERID")
	private Long userId;

	@Column(name = "PROVINCEID")
	private Long provinceId;

	@Column(name = "SEARCHTYPEID")
	private Long searchTypeId;

	@Column(name = "SEARCHFILTERID")
	private Long searchFilterId;

	@Column(name = "DATED")
	private LocalDateTime dated;

	@Column(name = "CAPTION")
	private String caption;

	@Column(name = "DATA")
	private String data;

}
