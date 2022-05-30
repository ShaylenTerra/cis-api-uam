package com.dw.ngms.cis.persistence.domains.listmanagement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by pragayanshu on 2020/12/20.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "M_LIST_ITEM_DATA")
public class ListItemData {

	@Id
	@Column(name = "DATAID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_list_item_data_seq")
	@SequenceGenerator(name = "m_list_item_data_seq", sequenceName = "M_LIST_ITEM_DATA_SEQ", allocationSize = 1)
	private Long dataId;

	@Column(name = "DATATYPE_ITEMID")
	private Long dataTypeItemId;

	@Column(name = "ITEMID")
	private Long itemId;

	@Column(name = "DATA1")
	private Long data1;

	@Column(name="TRANSACTIONDATE")
	private LocalDate transactionDate;

	@Column(name="USERID")
	private Long userId;
}
