package com.dw.ngms.cis.persistence.domains.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CART_STAGE")
public class CartStage {

	@Id
	@Column(name = "CART_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_stage_sequence")
	@SequenceGenerator(name = "cart_stage_sequence", sequenceName = "CART_STAGE_SEQ", allocationSize = 1)
	private Long cartId;

	@Column(name = "PROVINCEID")
	private Long provinceId;

	@Column(name = "USERID")
	private Long userId;

	@Column(name = "DATED")
	@Builder.Default
	private Date dated = new Date();

	@Column(name = "REQUESTOR_DATA")
	private String requesterInformation;

	@Column(name = "WORKFLOWID")
	private Long workflowId;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "CART_ID")
	private Set<CartStageData> cartStageData;
}
