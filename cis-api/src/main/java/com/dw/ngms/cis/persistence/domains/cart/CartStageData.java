package com.dw.ngms.cis.persistence.domains.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CART_STAGE_DATA")
public class CartStageData {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_stage_data_sequence")
	@SequenceGenerator(name = "cart_stage_data_sequence", sequenceName = "CART_STAGE_DATA_SEQUENCE", allocationSize = 1)
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

	@Column(name = "DATAKEY_VALUE")
	private String dataKeyValue;
}
