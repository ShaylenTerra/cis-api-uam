package com.dw.ngms.cis.persistence.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SEARCH_DOCUMENT_MAPPING")
public class SearchDocumentMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "search_document_mapping_seq")
	@SequenceGenerator(name = "search_document_mapping_seq", sequenceName = "SEARCH_DOCUMENT_MAPPING_SEQ", allocationSize = 1)
	@Column(name = "MAPID")
	private Long mapId;

	@Column(name = "DOCUMENT_TYPE")
	private String documentType;

	@Column(name = "DOCUMENT_TYPE_ID")
	private Long documentTypeId;

	@Column(name = "DOCUMENT_SUBTYPE")
	private String documentSubType;

	@Column(name = "DOCUMENT_SUBTYPE_ID")
	private Long documentSubTypeId;

	@Column(name = "DATA_TYPE")
	private String dataType;

	@Column(name = "DATA_TYPE_ID")
	private Long dataTypeId;
}
