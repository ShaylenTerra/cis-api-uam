package com.dw.ngms.cis.persistence.domain;

import com.dw.ngms.cis.enums.UserTypes;
import lombok.Data;

import javax.persistence.*;

/**
 * @author : prateekgoel
 * @since : 07/12/20, Mon
 **/

@Data
@Entity
@Table(name = "SEARCH_TYPE_OFFICE_MAPPING")
public class SearchTypeOfficeMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "search_type_office_mapping_seq")
    @SequenceGenerator(name = "search_type_office_mapping_seq", sequenceName = "SEARCH_TYPE_OFFICE_MAPPING_SEQ", allocationSize = 1)
    @Column(name = "MAPID")
    private Long mapId;

    @Column(name = "PROVINCEID")
    private Long provinceId;

    @Column(name = "SEARCHTYPEID")
    private Long searchTypeId;

    @Column(name = "USERTYPE")
    private UserTypes userType;

    @Column(name = "ISACTIVE")
    private Long isActive;
}
