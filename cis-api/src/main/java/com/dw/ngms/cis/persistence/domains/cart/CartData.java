package com.dw.ngms.cis.persistence.domains.cart;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CART_DATA")
public class CartData {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_data_sequence")
	@SequenceGenerator(name = "cart_data_sequence", sequenceName = "CART_DATA_SEQUENCE", allocationSize = 1)
	private Long id;

	@Column(name = "CART_ID")
	private Long cartId;

	@Column(name = "DATED")
	private Date dated;

	@Column(name = "PROVINCEID")
	private Long provinceId;

	@Column(name = "USERID")
	private Long userId;

	@Column(name = "SEARCH_TYPEID")
	private Long searchTypeId;

	@Column(name = "DATA_KEY")
	private String dataKey;

	@Column(name = "JSON_DATA")
	private String jsonData;

	@Column(name = "WORKFLOWID")
	private Long workflowId;


}
