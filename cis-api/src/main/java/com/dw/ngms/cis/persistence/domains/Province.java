package com.dw.ngms.cis.persistence.domains;

import com.dw.ngms.cis.persistence.domains.province.ProvinceAddress;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by nirmal on 2020/11/06.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "M_PROVINCES")
public class Province {

	@Id
	@Column(name = "PROVINCEID")
	private Long provinceId;

	@Column(name = "ORGCODE")
	private String orgCode;

	@Column(name = "ORGNAME")
	private String orgName;

	@Column(name = "PROVINCENAME")
	private String provinceName;

	@Column(name = "PROVINCESHORTNAME")
	private String provinceShortName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "ISACTIVE")
	private String isActive;

	@Column(name = "CREATEDDATE")
	private String createdDate;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "PROVINCEID")
	private ProvinceAddress provinceAddress;

}
