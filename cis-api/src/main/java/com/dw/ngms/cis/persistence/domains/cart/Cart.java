package com.dw.ngms.cis.persistence.domains.cart;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CART")
public class Cart {

	@Id
	@Column(name = "CART_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_sequence")
	@SequenceGenerator(name = "cart_sequence", sequenceName = "CART_SEQ", allocationSize = 1)
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "CART_ID", referencedColumnName = "CART_ID")
	private Set<CartData> cartData;
}
