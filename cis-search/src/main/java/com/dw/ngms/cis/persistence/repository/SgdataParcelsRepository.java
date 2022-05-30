package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domain.number.SgdataParcels;
import com.dw.ngms.cis.persistence.projection.SearchProfileRelatedData;
import com.dw.ngms.cis.persistence.projection.number.*;
import com.dw.ngms.cis.persistence.projection.parcel.ParcelErfProjection;
import com.dw.ngms.cis.persistence.projection.parcel.ParcelFarmProjection;
import com.dw.ngms.cis.persistence.projection.sectional.SectionalSearchErfProjection;
import com.dw.ngms.cis.persistence.projection.sectional.SectionalSearchFarmProjection;
import com.dw.ngms.cis.persistence.projection.sectional.SectionalSearchProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : prateekgoel
 * @since : 06/05/21, Thu
 **/
@Repository
public interface SgdataParcelsRepository extends JpaRepository<SgdataParcels, Long> {

    SgdataParcels findByRecordId(final Long recordId);

    @Query(value = "SELECT SGNO  as sgNo,\n" +
            "       RECORD_ID     as recordId,\n" +
            "       PROVINCE_ID   as provinceId,\n" +
            "       PROVINCE    as province,\n" +
            "       LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "       LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "       DOCUMENT_TYPE as documentType,\n" +
            "       DOCUMENT_TYPE_ID as documentTypeId,\n" +
            "       DOCUMENT_SUBTYPE as documentSubType,\n" +
            "       DOCUMENT_SUBTYPE_ID as documentSubTypeId,\n" +
            "       CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "       DESCRIPTION as description ,\n" +
            "       PARCEL_TYPE as parcelType,\n" +
            "       PARCEL AS parcel,\n" +
            "       PORTION AS portion,\n" +
            "       LPI AS lpi\n" +
            "FROM SGDATA_PARCELS\n" +
            "WHERE PROVINCE_ID = :provinceId\n" +
            "  AND SGNO = UPPER(:sgNo)\n" +
            "ORDER BY PROVINCE_ID",
            nativeQuery = true,
            countQuery = "select * from SGDATA_PARCELS where PROVINCE_ID=:provinceId and SGNO=UPPER(:sgNo)")
    Page<NumberProjection> findUsingSgNumberAndProvinceId(final String sgNo,
                                                          final Long provinceId,
                                                          final Pageable pageable);


    @Query(value = "SELECT\n" +
            "   SGNO as sgNo,\n" +
            "   RECORD_ID     as recordId,\n" +
            "   REFERENCE_NUMBER as compilationNo,\n" +
            "   PROVINCE_ID as provinceId,\n" +
            "   PROVINCE as province,\n" +
            "   LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "   LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "   CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "   DOCUMENT_TYPE as documentType,\n" +
            "   DOCUMENT_TYPE_ID as documentTypeId,\n" +
            "   DOCUMENT_SUBTYPE_ID as documentSubTypeId,\n" +
            "   DOCUMENT_SUBTYPE as documentSubType,\n" +
            "   PARCEL_TYPE as parcelType,\n" +
            "   DESCRIPTION as description,\n" +
            "   PARCEL AS parcel,\n" +
            "   PORTION AS portion,\n" +
            "   LPI AS lpi\n" +
            "FROM SGDATA_PARCELS\n" +
            "    where\n" +
            "PROVINCE_ID = :provinceId and RECORD_TYPE_ID='1' and REFERENCE_NUMBER = UPPER(:compilationNo)",
            nativeQuery = true,
            countQuery = "select * from SGDATA_PARCELS where PROVINCE_ID = :provinceId and REFERENCE_NUMBER = UPPER(:compilationNo) ")
    Page<CompilationNumberProjection> findUsingCompilationNoAndProvinceId(final String compilationNo,
                                                                          final Long provinceId,
                                                                          final Pageable pageable);


    @Query(value = "SELECT SGNO as sgNo,\n" +
            "   RECORD_ID     as recordId,\n" +
            "   REFERENCE_NUMBER as filingNo,\n" +
            "   PROVINCE_ID as provinceId,\n" +
            "   PROVINCE as province,\n" +
            "   LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "   LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "   DOCUMENT_TYPE as documentType,\n" +
            "   DOCUMENT_TYPE_ID as documentTypeId,\n" +
            "   DOCUMENT_SUBTYPE_ID as documentSubTypeId,\n" +
            "   DOCUMENT_SUBTYPE as documentSubType,\n" +
            "   PARCEL_TYPE as parcelType,\n" +
            "   DESCRIPTION as description,\n" +
            "   CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "   PARCEL AS parcel,\n" +
            "   PORTION AS portion,\n" +
            "   LPI AS lpi\n" +
            "FROM SGDATA_PARCELS\n" +
            "where PROVINCE_ID = :provinceId and RECORD_TYPE_ID='3' and REFERENCE_NUMBER = UPPER(:fillingNo)",
            nativeQuery = true,
            countQuery = "select * from SGDATA_PARCELS where PROVINCE_ID = :provinceId and RECORD_TYPE_ID='3' and REFERENCE_NUMBER = UPPER(:fillingNo) ")
    Page<FilingProjection> findUsingFillingNoAndProvinceId(final String fillingNo,
                                                           final Long provinceId,
                                                           final Pageable pageable);


    @Query(value = "SELECT\n" +
            "    SGNO as sgNo,\n" +
            "   RECORD_ID     as recordId,\n" +
            "    REFERENCE_NUMBER as deedNo,\n" +
            "    PROVINCE_ID as provinceId,\n" +
            "    PROVINCE as province,\n" +
            "    LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "    LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "    DOCUMENT_TYPE as documentType,\n" +
            "   DOCUMENT_TYPE_ID as documentTypeId,\n" +
            "   DOCUMENT_SUBTYPE_ID as documentSubTypeId,\n" +
            "    DOCUMENT_SUBTYPE as documentSubType,\n" +
            "    PARCEL_TYPE as parcelType,\n" +
            "    DESCRIPTION as description,\n" +
            "    CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "    PARCEL AS parcel,\n" +
            "    PORTION AS portion,\n" +
            "    LPI AS lpi\n" +
            "FROM SGDATA_PARCELS \n" +
            "where PROVINCE_ID = :provinceId AND RECORD_TYPE_ID='2'  AND REFERENCE_NUMBER = UPPER(:deedNo)",
            nativeQuery = true,
            countQuery = "select * from SGDATA_PARCELS where PROVINCE_ID = :provinceId AND RECORD_TYPE_ID='2'  and REFERENCE_NUMBER = UPPER(:deedNo)")
    Page<DeedNumberProjection> findUsingDeedNoAndProvinceId(final String deedNo,
                                                            final Long provinceId,
                                                            final Pageable pageable);

    @Query(value = "SELECT\n" +
            "   SGNO as sgNo,\n" +
            "   RECORD_ID     as recordId,\n" +
            "   REFERENCE_NUMBER as leaseNo,\n" +
            "   PROVINCE_ID as provinceId,\n" +
            "   PROVINCE as province,\n" +
            "   LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "   LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "   DOCUMENT_TYPE as documentType,\n" +
            "   DOCUMENT_TYPE_ID as documentTypeId,\n" +
            "   DOCUMENT_SUBTYPE_ID as documentSubTypeId,\n" +
            "   DOCUMENT_SUBTYPE as documentSubType,\n" +
            "   PARCEL_TYPE as parcelType,\n" +
            "   DESCRIPTION as description,\n" +
            "   CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "   PARCEL AS parcel,\n" +
            "   PORTION AS portion,\n" +
            "   LPI AS lpi\n" +
            "FROM SGDATA_PARCELS \n" +
            "where PROVINCE_ID = :provinceId and RECORD_TYPE_ID='4'  and REFERENCE_NUMBER = UPPER(:leaseNo)",
            nativeQuery = true,
            countQuery = "select * from SGDATA_PARCELS where PROVINCE_ID = :provinceId and RECORD_TYPE_ID='4'  and REFERENCE_NUMBER = UPPER(:leaseNo)")
    Page<LeaseNumberProjection> findUsingLeaseNoAndProvinceId(final String leaseNo,
                                                              final Long provinceId,
                                                              final Pageable pageable);

    @Query(value = "SELECT\n" +
            "    SGNO as sgNo,\n" +
            "    RECORD_ID     as recordId,\n" +
            "    REFERENCE_NUMBER as surveyRecordNo,\n" +
            "    PROVINCE_ID as provinceId,\n" +
            "    PROVINCE as province,\n" +
            "    LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "    LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "    DOCUMENT_TYPE as documentType,\n" +
            "    DOCUMENT_TYPE_ID as documentTypeId,\n" +
            "    DOCUMENT_SUBTYPE_ID as documentSubTypeId,\n" +
            "    DOCUMENT_SUBTYPE as documentSubType,\n" +
            "    PARCEL_TYPE as parcelType,\n" +
            "    DESCRIPTION as description,\n" +
            "    CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "    PARCEL AS parcel,\n" +
            "    PORTION AS portion,\n" +
            "    LPI AS lpi\n" +
            "FROM SGDATA_PARCELS \n" +
            "where REFERENCE_NUMBER = :surveyRecordNo and RECORD_TYPE_ID='5' and PROVINCE_ID = :provinceId",
            nativeQuery = true,
            countQuery = "select * from SGDATA_PARCELS where PROVINCE_ID = :provinceId and RECORD_TYPE_ID='5' and REFERENCE_NUMBER = UPPER(:surveyRecordNo)")
    Page<SurveyRecordsProjection> findUsingSurveyRecordNoAndProvinceId(final String surveyRecordNo,
                                                                       final Long provinceId,
                                                                       final Pageable pageable);


    @Query(value = "SELECT SGNO as sgNo,\n" +
            "       RECORD_ID     as recordId,\n" +
            "       REGISTRATION_TOWNSHIP AS townshipName,\n" +
            "       FARMNAME as farmName,\n" +
            "       PROVINCE_ID as provinceId,\n" +
            "       PROVINCE as province,\n" +
            "       LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "       LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "       DOCUMENT_TYPE as documentType,\n" +
            "       DOCUMENT_TYPE_ID as documentTypeId,\n" +
            "       DOCUMENT_SUBTYPE as documentSubType,\n" +
            "       DOCUMENT_SUBTYPE_ID as documentSubTypeId,\n" +
            "       PARCEL_TYPE as parcelType,\n" +
            "       CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "       PARCEL AS farmNumber,\n" +
            "       PORTION AS portion,\n" +
            "       LPI AS lpi,\n" +
            "       DESCRIPTION as description\n" +
            "FROM SGDATA_PARCELS\n" +
            "WHERE (:provinceId is null or PROVINCE_ID =:provinceId) AND\n" +
            "      RECORD_TYPE_ID='7' AND\n" +
            "      (:township is null or REGISTRATION_TOWNSHIP=:township) AND\n" +
            "      (:farmName is null or FARMNAME = UPPER(:farmName)) AND\n" +
            "      (:parcelNumber is null or PARCEL=:parcelNumber) AND\n" +
            "      (:portion is null or PORTION=:portion)",
            nativeQuery = true,
            countQuery = "SELECT *\n" +
                    "FROM SGDATA_PARCELS\n" +
                    "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
                    "  AND RECORD_TYPE_ID = '7'\n" +
                    "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
                    "  AND (:farmName is null or FARMNAME = UPPER(:farmName))\n" +
                    "  AND (:parcelNumber is null or PARCEL = :parcelNumber)\n" +
                    "  AND (:portion is null or PORTION = :portion)")
    Page<ParcelFarmProjection> findUsingParcelFarm(final Long provinceId,
                                                   final String township,
                                                   final String farmName,
                                                   final String parcelNumber,
                                                   final String portion,
                                                   final Pageable pageable);

    @Query(value = "SELECT SGNO                    as sgNo,\n" +
            "       RECORD_ID     as recordId,\n" +
            "       REGISTRATION_TOWNSHIP   AS townshipName,\n" +
            "       PROVINCE_ID             as provinceId,\n" +
            "       PROVINCE                as province,\n" +
            "       LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "       LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "       DOCUMENT_TYPE           as documentType,\n" +
            "       DOCUMENT_TYPE_ID        as documentTypeId,\n" +
            "       DOCUMENT_SUBTYPE_ID     as documentSubTypeId,\n" +
            "       DOCUMENT_SUBTYPE        as documentSubType,\n" +
            "       PARCEL_TYPE             as parcelType,\n" +
            "       CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "       PARCEL                  AS erfNumber,\n" +
            "       PORTION                 AS portion,\n" +
            "       LPI                     AS lpi\n" +
            "FROM SGDATA_PARCELS\n" +
            "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
            "  AND (:municipalityCode is null or LOCAL_MUNICIPALITY_CODE = :municipalityCode)\n" +
            "  AND RECORD_TYPE_ID = '6'\n" +
            "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
            "  AND (:parcel is null or PARCEL = :parcel)\n" +
            "  AND (:portion is null or PORTION = :portion)",
            nativeQuery = true,
            countQuery = "SELECT *\n" +
                    "FROM SGDATA_PARCELS\n" +
                    "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
                    "  AND (:municipalityCode is null or LOCAL_MUNICIPALITY_CODE = :municipalityCode)\n" +
                    "  AND RECORD_TYPE_ID = '6'\n" +
                    "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
                    "  AND (:parcel is null or PARCEL = :parcel)\n" +
                    "  AND (:portion is null or PORTION = :portion)")
    Page<ParcelErfProjection> findUsingParcelErf(final Long provinceId,
                                                 final String municipalityCode,
                                                 final String township,
                                                 final String parcel,
                                                 final String portion,
                                                 final Pageable pageable);

    @Query(value = "SELECT SGNO             AS sgNo,\n" +
            "       RECORD_ID               As recordId,\n" +
            "       REGISTRATION_TOWNSHIP   AS townshipName,\n" +
            "       PROVINCE_ID             as provinceId,\n" +
            "       PROVINCE                as province,\n" +
            "       LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "       LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "       DOCUMENT_TYPE           as documentType,\n" +
            "       DOCUMENT_TYPE_ID        as documentTypeId,\n" +
            "       DOCUMENT_SUBTYPE_ID     as documentSubTypeId,\n" +
            "       DOCUMENT_SUBTYPE        as documentSubType,\n" +
            "       PARCEL_TYPE             as parcelType,\n" +
            "       CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "       PARCEL                  AS erfNumber,\n" +
            "       PORTION                 AS portion,\n" +
            "       LPI                     AS lpi,\n" +
            "       DESCRIPTION             AS description\n" +
            "FROM SGDATA_PARCELS\n" +
            "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
            "  AND RECORD_TYPE_ID = '8'\n" +
            "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
            "  AND (:parcel is null or PARCEL = :parcel)\n" +
            "  AND (:portion is null or PORTION = :portion)",
            nativeQuery = true,
            countQuery = "SELECT *\n" +
                    "FROM SGDATA_PARCELS\n" +
                    "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
                    "  AND RECORD_TYPE_ID = '8'\n" +
                    "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
                    "  AND (:parcel is null or PARCEL = :parcel)\n" +
                    "  AND (:portion is null or PORTION = :portion)")
    Page<ParcelErfProjection> findUsingParcelHolding(final Long provinceId,
                                                     final String township,
                                                     final String parcel,
                                                     final String portion,
                                                     final Pageable pageable);

    @Query(value = "SELECT SGNO             AS sgNo,\n" +
            "       RECORD_ID               AS recordId,\n" +
            "       REGISTRATION_TOWNSHIP   AS townshipName,\n" +
            "       PROVINCE_ID             as provinceId,\n" +
            "       PROVINCE                as province,\n" +
            "       LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "       LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "       DOCUMENT_TYPE           as documentType,\n" +
            "       DOCUMENT_TYPE_ID        as documentTypeId,\n" +
            "       DOCUMENT_SUBTYPE_ID     as documentSubTypeId,\n" +
            "       DOCUMENT_SUBTYPE        as documentSubType,\n" +
            "       PARCEL_TYPE             as parcelType,\n" +
            "       CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "       PARCEL                  AS erfNumber,\n" +
            "       PORTION                 AS portion,\n" +
            "       LPI                     AS lpi,\n" +
            "       DESCRIPTION             AS description\n" +
            "FROM SGDATA_PARCELS\n" +
            "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
            "  AND (:municipalityCode is null or LOCAL_MUNICIPALITY_CODE = :municipalityCode)\n" +
            "  AND (:lpi is null or LPI = UPPER(:lpi))",
            nativeQuery = true,
            countQuery = "SELECT *\n" +
                    "FROM SGDATA_PARCELS\n" +
                    "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
                    "  AND (:municipalityCode is null or LOCAL_MUNICIPALITY_CODE = :municipalityCode)\n" +
                    "  AND (:lpi is null or LPI = UPPER(:lpi))")
    Page<ParcelErfProjection> findUsgParcelLpi(final Long provinceId,
                                               final String municipalityCode,
                                               final String lpi,
                                               final Pageable pageable);


    @Query(value = "SELECT sgp FROM SgdataParcels sgp WHERE recordId = :recordId")
    Page<SgdataParcels> findDataProfileUsingRecordId(final Long recordId, Pageable pageable);


    @Query(value = "SELECT SCHEME_NUMBER           AS deedRegistrationNumber,\n" +
            "       RECORD_ID               AS recordId,\n" +
            "       REFERENCE_NUMBER        as filingNumber,\n" +
            "       SGNO                    as sgNo,\n" +
            "       SCHEME_NAME             as schemeName,\n" +
            "       PROVINCE_ID             as provinceId,\n" +
            "       PROVINCE                as province,\n" +
            "       LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "       LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "       PARCEL_TYPE             as parcelType,\n" +
            "       DOCUMENT_TYPE           as documentType,\n" +
            "       DOCUMENT_TYPE_ID        as documentTypeId,\n" +
            "       DOCUMENT_SUBTYPE_ID     as documentSubTypeId,\n" +
            "       DOCUMENT_SUBTYPE        as documentSubType,\n" +
            "       CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "       PARCEL                  AS parcel,\n" +
            "       PORTION                 AS portion,\n" +
            "       LPI                     AS lpi\n" +
            "FROM SGDATA_PARCELS\n" +
            "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
            "  AND (:municipalityCode is null or LOCAL_MUNICIPALITY_CODE = :municipalityCode)\n" +
            "  AND (:schemeNumber is null or SCHEME_NUMBER = :schemeNumber)\n" +
            "  AND (:schemeName is null or SCHEME_NAME = UPPER(:schemeName))\n" +
            "  AND (:filingNumber is null or REFERENCE_NUMBER = UPPER(:filingNumber))\n" +
            "  AND (:sgNumber is null or SGNO = UPPER(:sgNumber))",
            nativeQuery = true,
            countQuery = "SELECT *\n" +
                    "FROM SGDATA_PARCELS\n" +
                    "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
                    "  AND (:municipalityCode is null or LOCAL_MUNICIPALITY_CODE = :municipalityCode)\n" +
                    "  AND (:schemeNumber is null or SCHEME_NUMBER = UPPER(:schemeNumber))\n" +
                    "  AND (:schemeName is null or SCHEME_NAME = UPPER(:schemeName))\n" +
                    "  AND (:filingNumber is null or REFERENCE_NUMBER = UPPER(:filingNumber))\n" +
                    "  AND (:sgNumber is null or SGNO = UPPER(:sgNumber))")
    Page<SectionalSearchProjection> findUsingSectionalTitleSearch(final Long provinceId,
                                                                  final String municipalityCode,
                                                                  final String schemeNumber,
                                                                  final String schemeName,
                                                                  final String filingNumber,
                                                                  final String sgNumber,
                                                                  final Pageable pageable);


    @Query(value = "SELECT SCHEME_NUMBER           as schemeNumber,\n" +
            "       REFERENCE_NUMBER        as filingNumber,\n" +
            "       SGNO                    as sgNo,\n" +
            "       RECORD_ID               AS recordId,\n" +
            "       SCHEME_NAME             as schemeName,\n" +
            "       REGISTRATION_TOWNSHIP   AS townshipAllotmentName,\n" +
            "       PROVINCE_ID             as provinceId,\n" +
            "       PROVINCE                as province,\n" +
            "       LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "       LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "       PARCEL_TYPE             as parcelType,\n" +
            "       DOCUMENT_TYPE           as documentType,\n" +
            "       DOCUMENT_TYPE_ID        as documentTypeId,\n" +
            "       DOCUMENT_SUBTYPE_ID     as documentSubTypeId,\n" +
            "       DOCUMENT_SUBTYPE        as documentSubType,\n" +
            "       CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "       PARCEL                  AS erfNumber,\n" +
            "       PORTION                 AS portion,\n" +
            "       LPI                     AS lpi\n" +
            "FROM SGDATA_PARCELS\n" +
            "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
            "  AND (:municipalityCode is null or LOCAL_MUNICIPALITY_CODE = :municipalityCode)\n" +
            "  AND RECORD_TYPE_ID = '10' \n" +
            "  AND (:township is  null or REGISTRATION_TOWNSHIP = :township)\n" +
            "  AND (:parcel is null or PARCEL = :parcel)\n" +
            "  AND (:portion is null or PORTION = :portion)",
            nativeQuery = true,
            countQuery = "SELECT *\n" +
                    "FROM SGDATA_PARCELS\n" +
                    "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
                    "  AND (:municipalityCode is null or LOCAL_MUNICIPALITY_CODE = :municipalityCode)\n" +
                    "  AND  RECORD_TYPE_ID = '10' \n" +
                    "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
                    "  AND (:parcel is  null or PARCEL = :parcel)\n" +
                    "  AND (:portion is  null or PORTION = :portion)")
    Page<SectionalSearchErfProjection> findUsingSectionalErfSearch(final Long provinceId,
                                                                   final String municipalityCode,
                                                                   final String township,
                                                                   final String parcel,
                                                                   final String portion,
                                                                   final Pageable pageable);


    @Query(value = "SELECT SCHEME_NUMBER           as schemeNumber,\n" +
            "       REFERENCE_NUMBER        as filingNumber,\n" +
            "       SGNO                    as sgNo,\n" +
            "       RECORD_ID               AS recordId,\n" +
            "       SCHEME_NAME             as schemeName,\n" +
            "       FARMNAME                as farmName,\n" +
            "       REGISTRATION_TOWNSHIP   AS administrativeDistrict,\n" +
            "       PROVINCE_ID             as provinceId,\n" +
            "       PROVINCE                as province,\n" +
            "       LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "       LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "       PARCEL_TYPE             as parcelType,\n" +
            "       DOCUMENT_TYPE           as documentType,\n" +
            "       DOCUMENT_TYPE_ID        as documentTypeId,\n" +
            "       DOCUMENT_SUBTYPE_ID     as documentSubTypeId,\n" +
            "       DOCUMENT_SUBTYPE        as documentSubType,\n" +
            "       CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "       PARCEL                  AS farmNumber,\n" +
            "       PORTION                 AS portion,\n" +
            "       LPI                     AS lpi\n" +
            "FROM SGDATA_PARCELS\n" +
            "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
            "  AND RECORD_TYPE_ID = 9\n" +
            "  AND (:municipalityCode is null or LOCAL_MUNICIPALITY_CODE = :municipalityCode)\n" +
            "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
            "  AND (:farmName is null or FARMNAME = UPPER(:farmName))\n" +
            "  AND (:parcel is null or PARCEL = :parcel)\n" +
            "  AND (:portion is null or PORTION = :portion)",
            nativeQuery = true,
            countQuery = "SELECT *\n" +
                    "FROM SGDATA_PARCELS\n" +
                    "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
                    "  AND RECORD_TYPE_ID = 9\n" +
                    "  AND (:municipalityCode is null or LOCAL_MUNICIPALITY_CODE = :municipalityCode)\n" +
                    "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
                    "  AND (:farmName is null or FARMNAME = UPPER(:farmName))\n" +
                    "  AND (:parcel is null or PARCEL = :parcel)\n" +
                    "  AND (:portion is null or PORTION = :portion)")
    Page<SectionalSearchFarmProjection> findUsingSectionalFarmSearch(final Long provinceId,
                                                                     final String municipalityCode,
                                                                     final String township,
                                                                     final String farmName,
                                                                     final String parcel,
                                                                     final String portion,
                                                                     final Pageable pageable);


    @Query(value = "SELECT SGNO             AS    sgNo,\n" +
            "       RECORD_ID               AS recordId,\n" +
            "       REGISTRATION_TOWNSHIP   AS townshipName,\n" +
            "       FARMNAME                as farmName,\n" +
            "       PROVINCE_ID             as provinceId,\n" +
            "       PROVINCE                as province,\n" +
            "       LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "       LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "       DOCUMENT_TYPE           as documentType,\n" +
            "       DOCUMENT_TYPE_ID        as documentTypeId,\n" +
            "       DOCUMENT_SUBTYPE_ID        as documentSubTypeId,\n" +
            "       DOCUMENT_SUBTYPE        as documentSubType,\n" +
            "       PARCEL_TYPE             as parcelType,\n" +
            "       CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "       PARCEL                  AS farmNumber,\n" +
            "       PORTION                 AS portion,\n" +
            "       LPI                     AS lpi,\n" +
            "       DESCRIPTION             AS description\n" +
            "FROM SGDATA_PARCELS\n" +
            "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
            "  AND RECORD_TYPE_ID = '7'\n" +
            "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
            "  AND (:farmName is null or FARMNAME = UPPER(:farmName))\n" +
            "  AND (:parcel is null or PARCEL = :parcel)\n" +
            "  AND PORTION BETWEEN :portionFrom AND :portionTo",
            nativeQuery = true,
            countQuery = "SELECT *\n" +
                    "FROM SGDATA_PARCELS\n" +
                    "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
                    "  AND RECORD_TYPE_ID = '7'\n" +
                    "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
                    "  AND (:farmName is null or FARMNAME = UPPER(:farmName))\n" +
                    "  AND (:parcel is null or PARCEL = :parcel)\n" +
                    "  AND PORTION BETWEEN :portionFrom AND :portionTo")
    Page<ParcelFarmProjection> findFarmUsingRange(final Long provinceId,
                                                  final String township,
                                                  final String farmName,
                                                  final String parcel,
                                                  final String portionFrom,
                                                  final String portionTo,
                                                  final Pageable pageable);

    @Query(value = "SELECT SGNO as sgNo,\n" +
            "   RECORD_ID               AS recordId,\n" +
            "   REGISTRATION_TOWNSHIP AS townshipName,\n" +
            "   PROVINCE_ID as provinceId,\n" +
            "   PROVINCE as province,\n" +
            "   LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "   LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "   DOCUMENT_TYPE as documentType,\n" +
            "   DOCUMENT_TYPE_ID        as documentTypeId,\n" +
            "   DOCUMENT_SUBTYPE_ID        as documentSubTypeId,\n" +
            "   DOCUMENT_SUBTYPE as documentSubType,\n" +
            "   PARCEL_TYPE as parcelType,\n" +
            "   CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "   PARCEL AS erfNumber,\n" +
            "   PORTION AS portion,\n" +
            "   LPI AS lpi,\n" +
            "   DESCRIPTION AS  DESCRIPTION\n" +
            "FROM SGDATA_PARCELS\n" +
            "WHERE (:provinceId is null or PROVINCE_ID=:provinceId) AND\n" +
            "      RECORD_TYPE_ID='6' AND\n" +
            "      (:township is null or REGISTRATION_TOWNSHIP=:township) AND\n" +
            "      (:parcel is null or PARCEL=:parcel) AND\n" +
            "      PORTION BETWEEN  :portionFrom AND :portionTo",
            nativeQuery = true,
            countQuery = "SELECT *\n" +
                    "FROM SGDATA_PARCELS\n" +
                    "WHERE (:provinceId is null or PROVINCE_ID=:provinceId) AND\n" +
                    "        RECORD_TYPE_ID='6' AND\n" +
                    "    (:township is null or REGISTRATION_TOWNSHIP=:township) AND\n" +
                    "    (:parcel is null or PARCEL=:parcel) AND\n" +
                    "    PORTION BETWEEN  :portionFrom AND :portionTo")
    Page<ParcelErfProjection> findErfUsingPortionNoRange(final Long provinceId,
                                                         final String township,
                                                         final String parcel,
                                                         final String portionFrom,
                                                         final String portionTo,
                                                         final Pageable pageable);

    @Query(value = "SELECT SGNO                    as sgNo,\n" +
            "       RECORD_ID               AS recordId,\n" +
            "       REGISTRATION_TOWNSHIP   AS townshipName,\n" +
            "       PROVINCE_ID             as proviceId,\n" +
            "       PROVINCE                as province,\n" +
            "       LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "       LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "       DOCUMENT_TYPE           as documentType,\n" +
            "       DOCUMENT_TYPE_ID        as documentTypeId,\n" +
            "       DOCUMENT_SUBTYPE_ID     as documentSubTypeId,\n" +
            "       DOCUMENT_SUBTYPE        as documentSubType,\n" +
            "       PARCEL_TYPE             as parcelType,\n" +
            "       CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "       PARCEL                  AS erfNumber,\n" +
            "       PORTION                 AS portion,\n" +
            "       LPI                     AS lpi,\n" +
            "       DESCRIPTION             as description\n" +
            "FROM SGDATA_PARCELS\n" +
            "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
            "  AND RECORD_TYPE_ID = '6'\n" +
            "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
            "  AND (:sgNo is null or SGNO = UPPER(:sgNo))\n" +
            "  AND PARCEL BETWEEN :parcelFrom AND :parcelTo",
            nativeQuery = true,
            countQuery = "SELECT *\n" +
                    "FROM SGDATA_PARCELS\n" +
                    "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
                    "  AND RECORD_TYPE_ID = '6'\n" +
                    "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
                    "  AND (:sgNo is null or SGNO = UPPER(:sgNo))\n" +
                    "  AND PARCEL BETWEEN :parcelFrom AND :parcelTo")
    Page<ParcelErfProjection> findErfUsingParcelRange(final Long provinceId,
                                                      final String township,
                                                      final String sgNo,
                                                      final String parcelFrom,
                                                      final String parcelTo,
                                                      final Pageable pageable);


    @Query(value = "SELECT SGNO                    as sgNo,\n" +
            "       RECORD_ID               AS recordId,\n" +
            "       REGISTRATION_TOWNSHIP   AS townshipName,\n" +
            "       PROVINCE_ID             as proviceId,\n" +
            "       PROVINCE                as province,\n" +
            "       LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "       LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "       DOCUMENT_TYPE           as documentType,\n" +
            "       DOCUMENT_TYPE_ID        as documentTypeId,\n" +
            "       DOCUMENT_SUBTYPE_ID     as documentSubTypeId,\n" +
            "       DOCUMENT_SUBTYPE        as documentSubType,\n" +
            "       PARCEL_TYPE             as parcelType,\n" +
            "       CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "       PARCEL                  AS erfNumber,\n" +
            "       PORTION                 AS portion,\n" +
            "       LPI                     AS lpi,\n" +
            "       DESCRIPTION             as description\n" +
            "FROM SGDATA_PARCELS\n" +
            "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
            "  AND RECORD_TYPE_ID = '8'\n" +
            "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
            "  AND (:municipalityCode is null or LOCAL_MUNICIPALITY_CODE = :municipalityCode)\n" +
            "  AND PARCEL BETWEEN :parcelFrom AND :parcelTo",
            nativeQuery = true,
            countQuery = "SELECT *\n" +
                    "FROM SGDATA_PARCELS\n" +
                    "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
                    "  AND RECORD_TYPE_ID = '8'\n" +
                    "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
                    "  AND (:municipalityCode is null or LOCAL_MUNICIPALITY_CODE = :municipalityCode)\n" +
                    "  AND PARCEL BETWEEN :parcelFrom AND :parcelTo")
    Page<ParcelErfProjection> findHoldingUsingParcelRange(final Long provinceId,
                                                          final String township,
                                                          final String municipalityCode,
                                                          final String parcelFrom,
                                                          final String parcelTo,
                                                          final Pageable pageable);

    @Query(value = "SELECT SGNO                    as sgNo,\n" +
            "       RECORD_ID               AS recordId,\n" +
            "       REGISTRATION_TOWNSHIP   AS townshipName,\n" +
            "       PROVINCE_ID             as proviceId,\n" +
            "       PROVINCE                as province,\n" +
            "       LOCAL_MUNICIPALITY_CODE as localMunicipalityCode,\n" +
            "       LOCAL_MUNICIPALITY_NAME as localMunicipalityName,\n" +
            "       DOCUMENT_TYPE           as documentType,\n" +
            "       DOCUMENT_TYPE_ID        as documentTypeId,\n" +
            "       DOCUMENT_SUBTYPE_ID     as documentSubTypeId,\n" +
            "       DOCUMENT_SUBTYPE        as documentSubType,\n" +
            "       PARCEL_TYPE             as parcelType,\n" +
            "       CONCAT ( CONCAT(REGION, ' (') ,  concat(REGISTRATION_TOWNSHIP_NAME,')') )  as region,\n" +
            "       PARCEL                  AS erfNumber,\n" +
            "       PORTION                 AS portion,\n" +
            "       LPI                     AS lpi,\n" +
            "       DESCRIPTION             as description\n" +
            "FROM SGDATA_PARCELS\n" +
            "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
            "  AND RECORD_TYPE_ID = '8'\n" +
            "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
            "  AND (:parcel is null or PARCEL = :parcel) \n" +
            "  AND PARCEL BETWEEN :portionFrom AND :portionTo",
            nativeQuery = true,
            countQuery = "SELECT *\n" +
                    "FROM SGDATA_PARCELS\n" +
                    "WHERE (:provinceId is null or PROVINCE_ID = :provinceId)\n" +
                    "  AND RECORD_TYPE_ID = '8'\n" +
                    "  AND (:township is null or REGISTRATION_TOWNSHIP = :township)\n" +
                    "  AND (:parcel is null or PARCEL = :parcel) \n" +
                    "  AND PORTION BETWEEN :portionFrom AND :portionTo")
    Page<ParcelErfProjection> findHoldingUsingPortionRange(final Long provinceId,
                                                           final String township,
                                                           final String portionFrom,
                                                           final String portionTo,
                                                           final String parcel,
                                                           final Pageable pageable);


    @Query(value = "SELECT SP.* " +
            "FROM SGDATA_PARCELS SP\n" +
            "WHERE PROVINCE_ID = :provinceId\n" +
            "  AND (SGNO like UPPER(:sgNo) OR\n" +
            "       PARCEL like :parcel OR\n" +
            "       UPPER(LOCAL_MUNICIPALITY_NAME) like UPPER(:localMunicipalityName) OR\n" +
            "       REGION like UPPER(:region) OR\n" +
            "       LPI like UPPER(:lpi))",
            nativeQuery = true,
            countQuery = "SELECT *\n" +
                    "FROM SGDATA_PARCELS\n" +
                    "WHERE PROVINCE_ID = :provinceId\n" +
                    "  AND (SGNO like UPPER(:sgNo) OR\n" +
                    "       PARCEL like :parcel OR\n" +
                    "       UPPER(LOCAL_MUNICIPALITY_NAME) like UPPER(:localMunicipalityName) OR\n" +
                    "       REGION like UPPER(:region) OR\n" +
                    "       LPI like UPPER(:lpi))")
    Page<SgdataParcels> findUsingTextSearch(final String sgNo,
                                            final String parcel,
                                            final Long provinceId,
                                            final String localMunicipalityName,
                                            final String region,
                                            final String lpi,
                                            final Pageable pageable);

    @Query(value = "SELECT SP.* " +
            "FROM SGDATA_PARCELS SP\n" +
            "WHERE PROVINCE_ID = :provinceId\n" +
            "  AND (SGNO like :sgNo OR\n" +
            "      UPPER(DESIGNATION) like UPPER(:designation) OR\n"+
            "      REGISTRATION_TOWNSHIP_ID = :registrationTownshipId OR\n" +
            "      LPI like :lpi" +
            " )",
            nativeQuery = true)
    List<SgdataParcels> findUsingTextSearchForReservationDraft(final String sgNo,
                                            final Long provinceId,
                                            final Long registrationTownshipId,
                                            final String designation,
                                            final String lpi);


    @Query(value = "SELECT DISTINCT DOCUMENT_TYPE as documentType,\n" +
            "                SGNO as sgNo,\n" +
            "                REFERENCE_NUMBER as documentNo,\n" +
            "                DOCUMENT_SUBTYPE as documentSubType,\n" +
            "                DESCRIPTION as description,\n" +
            "                RECORD_ID as recordId\n" +
            "FROM SGDATA_PARCELS WHERE LPI = UPPER(:lpi)",
            nativeQuery = true,
            countQuery = "SELECT DISTINCT DOCUMENT_TYPE FROM SGDATA_PARCELS where LPI = UPPER(:lpi)")
    Page<SearchProfileRelatedData> findRelatedDataByLpi(final String lpi, final Pageable pageable);

    @Query("SELECT sp FROM SgdataParcels sp " +
            "where sp.portion = :portion " +
            "and sp.documentSubtype =:documentSubtype " +
            "and sp.documentType = :documentType" +
            " and sp.recordTypeId =:recordTypeId " +
            "  and sp.provinceId =:provinceId " +
            "and sp.registrationTownshipId=:registrationTownshipId")
    List<SgdataParcels>
    findSgdataParcelsForSubdivisionErf(final String portion,
                                       final Long provinceId,
                                       final String documentType,
                                       final String documentSubtype,
                                       final Long recordTypeId,
                                       final Long registrationTownshipId);


    @Query(value = "SELECT sp.* FROM SGDATA_PARCELS sp " +
            "where sp.PORTION = :portion " +
            " and sp.RECORD_TYPE_ID =:recordTypeId " +
            "  and sp.PROVINCE_ID =:provinceId " +
            " and sp.REGISTRATION_TOWNSHIP_ID=:registrationTownshipId" +
            " and sp.REMAINDER_IND=1", nativeQuery = true)
    List<SgdataParcels>
    findSgdataParcelsForSubdivisionErfErfRem(final String portion,
                                       final Long provinceId,
                                       final Long recordTypeId,
                                       final Long registrationTownshipId);

    @Query(value = "Select *\n" +
            "from SGDATA_PARCELS\n" +
            "where DOCUMENT_SUBTYPE in (:documentSubtype)\n" +
            "  and RECORD_TYPE_ID = :recordTypeId\n" +
            "  and PROVINCE_ID = :provinceId\n" +
            "  and PORTION = :portion\n" +
            "  and REGISTRATION_TOWNSHIP_ID = :registrationTownshipId\n" +
            "  and FarmName is not null \n" +
            "  and remainder_ind = 0", nativeQuery = true)
    List<SgdataParcels>
    findSgdataParcelsForSubdivisionFarmSubFarm(final String portion,
                                               final Long provinceId,
                                               final List<String> documentSubtype,
                                               final Long recordTypeId,
                                               final Long registrationTownshipId);

    @Query(value = "Select *\n" +
            "from SGDATA_PARCELS\n" +
            "where DOCUMENT_SUBTYPE in (:documentSubtype)\n" +
            "  and record_type_id = :recordTypeId\n" +
            "  and PROVINCE_ID = :provinceId\n" +
            "  and PORTION != :portion\n" +
            "  and REGISTRATION_TOWNSHIP_ID = :registrationTownshipId\n" +
            "  and FarmName is not null\n" +
            "  and remainder_ind = 0", nativeQuery = true)
    List<SgdataParcels>
    findSgdataParcelsForSubdivisionFarmSubFarmPortion(final String portion,
                                               final Long provinceId,
                                               final List<String> documentSubtype,
                                               final Long recordTypeId,
                                               final Long registrationTownshipId);


    @Query("SELECT sp FROM SgdataParcels sp " +
            "where sp.portion <> :portion " +
            "and sp.documentSubtype =:documentSubtype " +
            "and sp.documentType = :documentType" +
            " and sp.recordTypeId =:recordTypeId " +
            "  and sp.provinceId =:provinceId " +
            "and sp.registrationTownshipId=:registrationTownshipId")
    List<SgdataParcels>
    findSgdataParcelsForSubdivisionErfPortion(final String portion,
                                              final Long provinceId,
                                              final String documentType,
                                              final String documentSubtype,
                                              final Long recordTypeId,
                                              final Long registrationTownshipId);


    @Query("SELECT sp from SgdataParcels sp  where sp.recordTypeId = :recordTypeId \n" +
            "  and sp.documentType = :documentType \n" +
            "  and sp.documentSubtype in ('SUBDIVISIONAL', 'CONSOLIDATION')\n" +
            "  and sp.portion = :portion \n" +
            "  and sp.provinceId = :provinceId \n" +
            "  and sp.registrationTownshipId = :registrationTownshipId ")
    List<SgdataParcels> findSgdataParcelsForConsolidationErf(final String portion,
                                                             final Long provinceId,
                                                             final String documentType,
                                                             final Long recordTypeId,
                                                             final Long registrationTownshipId);

    @Query("SELECT sp from SgdataParcels sp  where sp.recordTypeId = :recordTypeId \n" +
            "  and sp.documentType = :documentType \n" +
            "  and sp.documentSubtype in ('SUBDIVISIONAL', 'CONSOLIDATION')\n" +
            "  and sp.portion <> :portion \n" +
            "  and sp.provinceId = :provinceId \n" +
            "  and sp.registrationTownshipId = :registrationTownshipId ")
    List<SgdataParcels> findSgdataParcelsForConDiffErfDiffPortion(final String portion,
                                                                  final Long provinceId,
                                                                  final String documentType,
                                                                  final Long recordTypeId,
                                                                  final Long registrationTownshipId);


    @Query("SELECT sp from SgdataParcels sp  " +
            " where sp.recordTypeId = :recordTypeId \n" +
            "  and sp.documentType = :documentType \n" +
            "  and sp.documentSubtype in ('SUBDIVISIONAL', 'CONSOLIDATION')\n" +
            "  and sp.provinceId = :provinceId \n" +
            "  and sp.registrationTownshipId = :registrationTownshipId ")
    List<SgdataParcels> findSgdataParcelsForConErfWithDiffErfPortion(final Long provinceId,
                                                                     final String documentType,
                                                                     final Long recordTypeId,
                                                                     final Long registrationTownshipId);

    @Query(value = "SELECT sp.* from SGDATA_PARCELS sp  " +
            " where sp.RECORD_TYPE_ID = :recordTypeId \n" +
            "  and sp.DOCUMENT_TYPE = :documentType \n" +
            "  and sp.DOCUMENT_SUBTYPE in ('SUBDIVISIONAL', 'CONSOLIDATION')\n" +
            "  and sp.PORTION != :portion \n" +
            "  and sp.PARCEL = :parcel \n" +
            "  and sp.PROVINCE_ID = :provinceId \n" +
            "  and sp.REGISTRATION_TOWNSHIP_ID = :registrationTownshipId ", nativeQuery = true)
    List<SgdataParcels> findSgdataParcelsForConSameErfDiffPortion(final String portion,
                                                                  final String parcel,
                                                                  final Long provinceId,
                                                                  final String documentType,
                                                                  final Long recordTypeId,
                                                                  final Long registrationTownshipId);


}
