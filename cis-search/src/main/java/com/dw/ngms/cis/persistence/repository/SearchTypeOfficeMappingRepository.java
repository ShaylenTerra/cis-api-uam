package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.enums.UserTypes;
import com.dw.ngms.cis.persistence.domain.SearchTypeOfficeMapping;
import com.dw.ngms.cis.persistence.projection.SearchTypeAndFilterProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author : prateekgoel
 * @since : 07/12/20, Mon
 **/
@Repository
public interface SearchTypeOfficeMappingRepository extends JpaRepository<SearchTypeOfficeMapping, Long> {

    Collection<SearchTypeOfficeMapping> findAllBySearchTypeIdAndUserType(final Long searchTypeItemId,
                                                                         final UserTypes userTypes);

    SearchTypeOfficeMapping findByProvinceIdAndSearchTypeIdAndUserType(final Long provinceId,
                                                                       final Long searchTypeId,
                                                                       final UserTypes userTypes);

    @Modifying
    @Query("update SearchTypeOfficeMapping stom \n" +
            "    set stom.isActive =:isActive \n" +
            "where stom.provinceId =:provinceId and\n" +
            "      stom.searchTypeId =:searchTypeId and \n" +
            "      stom.userType =:userTypes")
    int updateSearchTypeOfficeMapping(final Long isActive,
                                      final Long provinceId,
                                      final Long searchTypeId,
                                      final UserTypes userTypes);

    @Query(value = "SELECT st.SEARCHTYPEID as searchTypeId,\n" +
            "   st.NAME as name , st.TAG as tag,\n" +
            "   st.PARENTSEARCHTYPEID as parentSearchTypeId,\n" +
            "   st.DESCRIPTION as description,\n" +
            "   st.CONTROLTYPE as controlType,\n" +
            "   st.TEMPLATE_LIST_ITEMID as templateListItemId,\n" +
            "   stom.PROVINCEID as provinceId,\n" +
            "   stom.USERTYPE as userType,\n" +
            "   st.config as config \n" +
            "FROM SEARCH_TYPE_OFFICE_MAPPING stom\n" +
            "    INNER JOIN SEARCH_TYPE st ON st.SEARCHTYPEID = stom.SEARCHTYPEID\n" +
            "WHERE stom.PROVINCEID = :provinceId AND\n" +
            "   stom.USERTYPE = :#{#userTypes.getUserTypes()} AND\n" +
            "   stom.ISACTIVE = 1 AND\n" +
            "   st.PARENTSEARCHTYPEID = :parentSearchTypeId",
            nativeQuery = true,
            countQuery = "SELECT count(*)\n" +
                    "FROM SEARCH_TYPE_OFFICE_MAPPING stom\n" +
                    "INNER JOIN SEARCH_TYPE st ON st.SEARCHTYPEID = stom.SEARCHTYPEID\n" +
                    "WHERE stom.PROVINCEID = :provinceId AND\n" +
                    "        stom.USERTYPE = :#{#userTypes.getUserTypes()} AND\n" +
                    "        stom.ISACTIVE = 1 AND\n" +
                    "        st.PARENTSEARCHTYPEID = :parentSearchTypeId")
    List<SearchTypeAndFilterProjection> findSearchTypeByProvinceIdAndUserTypeAndParentSearchTypeId(
            @Param("provinceId") final Long provinceId, @Param("userTypes") final UserTypes userTypes,
            @Param("parentSearchTypeId") final Long parentSearchTypeId, Pageable pageable);


    @Query(value = "SELECT st.SEARCHTYPEID as searchTypeId,\n" +
            "   st.NAME as name , st.TAG as tag,\n" +
            "   st.PARENTSEARCHTYPEID as parentSearchTypeId,\n" +
            "   st.DESCRIPTION as desription,\n" +
            "   st.CONTROLTYPE as controlType,\n" +
            "   st.config as config,\n" +
            "   st.TEMPLATE_LIST_ITEMID as templateListItemId,\n" +
            "   stom.PROVINCEID as provinceId,\n" +
            "   stom.USERTYPE as userType\n" +
            "FROM SEARCH_TYPE_OFFICE_MAPPING stom\n" +
            "    INNER JOIN SEARCH_TYPE st ON st.SEARCHTYPEID = stom.SEARCHTYPEID\n" +
            "WHERE stom.ISACTIVE = 1 AND\n" +
            "   st.PARENTSEARCHTYPEID = :parentSearchTypeId",
            nativeQuery = true,
            countQuery = "SELECT count(*)\n" +
                    "FROM SEARCH_TYPE_OFFICE_MAPPING stom\n" +
                    "INNER JOIN SEARCH_TYPE st ON st.SEARCHTYPEID = stom.SEARCHTYPEID\n" +
                    "WHERE stom.ISACTIVE = 1 AND\n" +
                    "        st.PARENTSEARCHTYPEID = :parentSearchTypeId")
    List<SearchTypeAndFilterProjection> findSearchTypeByUserTypeAndParentSearchTypeId(
            @Param("parentSearchTypeId") final Long parentSearchTypeId, Pageable pageable);

}
