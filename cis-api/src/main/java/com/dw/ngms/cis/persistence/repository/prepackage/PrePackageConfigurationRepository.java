package com.dw.ngms.cis.persistence.repository.prepackage;

import com.dw.ngms.cis.persistence.domains.prepackage.PrePackageConfiguration;
import com.dw.ngms.cis.persistence.projection.prepackage.PrepackageConfigurationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 04/01/21, Mon
 **/
@Repository
public interface PrePackageConfigurationRepository extends
        JpaRepository<PrePackageConfiguration, Long> {

    PrePackageConfiguration findByPrePackageId(final Long prePackageId);


    @Query(value = "SELECT PPC .PRE_PACKAGE_ID as prePackageId,\n" +
            "       PPC.NAME as name, \n" +
            "       PPC.DESCRIPTION as description,\n" +
            "       PPC.SAMPLE_IMAGE as sampleFileName ,\n" +
            "       PPC.COST as cost , \n" +
            "       PPC.CONFIGRATION_DATA as configurationData,\n" +
            "       PPC.ACTIVE as active,\n " +
            "       PPC.PRE_PACKAGE_DATA_TYPE as prepackageDataTypeId, \n" +
            "       PPC.FOLDER as folder, \n" +
            "       MLI.CAPTION as prepackageDataType\n" +
            "FROM PRE_PACKAGE_CONFIGURATION PPC , M_LIST_ITEM MLI\n" +
            "WHERE PPC.PRE_PACKAGE_DATA_TYPE = MLI.ITEMID",
            countQuery = "SELECT count(*)\n" +
                    "FROM PRE_PACKAGE_CONFIGURATION PPC , M_LIST_ITEM MLI\n" +
                    "where PPC.PRE_PACKAGE_DATA_TYPE = MLI.ITEMID",
            nativeQuery = true)
    Page<PrepackageConfigurationProjection> getAllPrepackagesWithStatus(final Pageable pageable);

    @Query(value = "SELECT PPC .PRE_PACKAGE_ID as prePackageId,\n" +
            "       PPC.NAME as name, \n" +
            "       PPC.DESCRIPTION as description,\n" +
            "       PPC.SAMPLE_IMAGE as sampleFileName ,\n" +
            "       PPC.COST as cost , \n" +
            "       PPC.CONFIGRATION_DATA as configurationData,\n" +
            "       PPC.ACTIVE as active,\n " +
            "       PPC.PRE_PACKAGE_DATA_TYPE as prepackageDataTypeId," +
            "       PPC.FOLDER as folder, \n" +
            "       MLI.CAPTION as prepackageDataType\n" +
            "FROM PRE_PACKAGE_CONFIGURATION PPC , M_LIST_ITEM MLI\n" +
            "WHERE PPC.ACTIVE = 1 and PPC.PRE_PACKAGE_DATA_TYPE = MLI.ITEMID",
            countQuery = "SELECT count(*)\n" +
                    "FROM PRE_PACKAGE_CONFIGURATION PPC , M_LIST_ITEM MLI\n" +
                    "where PPC.ACTIVE = 1 and PPC.PRE_PACKAGE_DATA_TYPE = MLI.ITEMID",
            nativeQuery = true)
    Page<PrepackageConfigurationProjection> getAllPrepackages(final Pageable pageable);


}
