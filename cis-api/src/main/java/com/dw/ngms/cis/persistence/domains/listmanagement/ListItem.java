package com.dw.ngms.cis.persistence.domains.listmanagement;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by nirmal on 2020/11/09.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "M_LIST_ITEM")
public class ListItem {

	@Id
	@Column(name = "ITEMID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "list_item_id_sequence")
	@SequenceGenerator(name = "list_item_id_sequence", sequenceName = "M_LIST_ITEM_SEQ", allocationSize = 1)
	private Long itemId;

	@Column(name = "ITEMCODE")
	private String itemCode;

	@Column(name = "LISTCODE")
	private Long listCode;

	@Column(name = "CAPTION")
	private String caption;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "ISACTIVE")
	private Long isActive;

	@Column(name = "ISDEFAULT")
	private Long isDefault;

}
