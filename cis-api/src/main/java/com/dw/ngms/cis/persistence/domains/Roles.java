package com.dw.ngms.cis.persistence.domains;

import com.dw.ngms.cis.enums.UserType;
import com.dw.ngms.cis.persistence.domains.sitemap.SiteMapRole;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by nirmal on 2020/11/09.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ROLES")
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_sequence")
	@SequenceGenerator(name = "roles_sequence", sequenceName = "ROLES_SEQ", allocationSize = 1)
	@Column(name = "ROLEID")
	private Long roleId;

	@Column(name = "ROLENAME")
	private String roleName;

	@Column(name = "USERTYPEITEMID")
	private UserType userTypeItemId;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "ISACTIVE")
	private Long isActive;

	@Column(name = "ROLECODE")
	private String roleCode;

	@Column(name = "PARENTROLEID")
	private Long parentRoleId;

	@Column(name = "SECTION_ITEMID")
	private Long sectionItemId;

	@Column(name = "APPROVAL_REQUIRED")
	private Long approvalRequired;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ROLE_ID")
	private Set<SiteMapRole> siteMapRoles;

}
