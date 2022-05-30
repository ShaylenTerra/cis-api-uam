package com.dw.ngms.cis.persistence.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by nirmal on 2020/11/09.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_ASSISTANTS")
public class UserAssistant {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_assistant_id_sequence")
	@SequenceGenerator(name = "user_assistant_id_sequence", sequenceName = "USER_ASSISTANTS_SEQ", allocationSize = 1)
	private Long id;

	@Column(name = "USERID")
	private Long userId;

	@Column(name = "ASSISTANTID")
	private Long assistantId;

	@Column(name = "STATUSID")
	private Long statusId;

}
