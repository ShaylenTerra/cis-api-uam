package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.dto.*;
import com.dw.ngms.cis.enums.TemplateTypes;
import com.dw.ngms.cis.exception.ResourceNotFoundException;
import com.dw.ngms.cis.persistence.domain.SearchUserLog;
import com.dw.ngms.cis.persistence.projection.SearchProfileRelatedData;
import com.dw.ngms.cis.persistence.projection.ngi.NgiSearchDataProjection;
import com.dw.ngms.cis.persistence.projection.number.*;
import com.dw.ngms.cis.persistence.projection.parcel.ParcelErfProjection;
import com.dw.ngms.cis.persistence.projection.parcel.ParcelFarmProjection;
import com.dw.ngms.cis.persistence.projection.sectional.SectionalSearchErfProjection;
import com.dw.ngms.cis.persistence.projection.sectional.SectionalSearchFarmProjection;
import com.dw.ngms.cis.persistence.projection.sectional.SectionalSearchProjection;
import com.dw.ngms.cis.service.*;
import com.dw.ngms.cis.vms.*;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import com.dw.ngms.cis.web.vm.SearchDetailsShareVm;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.zip.ZipOutputStream;

@BaseResponse
@AllArgsConstructor
@RestController
@RequestMapping(AppConstants.API_BASE_MAPPING + "/search")
public class SearchResource {

    private final SearchService searchService;

    private final SearchDocumentMappingService searchDocumentMappingService;

    private final SearchSingleRequestDownloadService searchSingleRequestDownloadService;

    private final NumberSearchService numberSearchService;

    private final ParcelSearchService parcelSearchService;

    private final SectionalSearchService sectionalSearchService;

    private final TemplateSearchService templateSearchService;

    private final ResourceLoader resourceLoader;

    private final RangeSearchService rangeSearchService;

    private final TextSearchService textSearchService;

    private final ShareSearchDetailsService shareSearchDetailsService;

    private final NgiSearchService ngiSearchService;


    /**
     * @param userId   userId of logged in user
     * @param pageable {@link Pageable}
     * @return Collection<SearchUserLog>
     */
    @GetMapping("/log")
    @ApiPageable
    public ResponseEntity<Collection<SearchUserLog>>
    getSearchData(@RequestParam @NotNull final Long userId,
                  @ApiIgnore @SortDefault(sort = "dated", direction = Sort.Direction.DESC) final Pageable pageable) {
        final Page<SearchUserLog> searchData = searchService.getUserSearchDataByUserId(userId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(searchData);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(searchData.getContent());
    }

    /**
     * @param searchHistoryDto {@link SearchHistoryDto}
     * @return {@link SearchHistoryDto}
     */
    @PostMapping("/log")
    public ResponseEntity<SearchHistoryDto> persistSearchHistory(@RequestBody @Valid SearchHistoryDto searchHistoryDto) {
        return ResponseEntity.ok()
                .body(searchService.persistSearchHistory(searchHistoryDto));
    }

    /**
     * @param documentType    documentType
     * @param documentSubtype documentSubtype
     * @return Collection<SearchDocumentMapping>
     */
    @GetMapping("/getInformationType")
    public ResponseEntity<Collection<SearchDocumentMappingDto>> getInformationType(@RequestParam @NotNull final Long documentType,
                                                                                   @RequestParam @NotNull final Long documentSubtype) {
        return ResponseEntity.ok()
                .body(searchDocumentMappingService.getByDocumentTypeAndDocumentSubtype(documentType, documentSubtype));
    }

    /**
     * @param searchSingleRequestDownloadDto {@link SearchSingleRequestDownloadDto}
     * @return InputStreamResource {@link InputStreamResource} file to be downloaded
     */
    @PostMapping("/downloadImage")
    public ResponseEntity<InputStreamResource> downloadImage(@RequestBody @Valid SearchSingleRequestDownloadDto searchSingleRequestDownloadDto) {
        InputStream inputStream = searchSingleRequestDownloadService.downloadSingleRequest(searchSingleRequestDownloadDto);
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        ContentDisposition disposition = ContentDisposition
                .inline()
                .filename(searchSingleRequestDownloadDto.getDocumentName())
                .build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDisposition(disposition);
        return new ResponseEntity<>(inputStreamResource, httpHeaders, HttpStatus.OK);
    }

    /**
     * @param searchSingleRequestDownloadDto {@link SearchSingleRequestDownloadDto}
     */
    @PostMapping(value = "/downloadZippedImage", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadZippedImages(@RequestBody @Valid SearchSingleRequestDownloadDto searchSingleRequestDownloadDto,
                                     HttpServletResponse httpServletResponse) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(httpServletResponse.getOutputStream());
        searchSingleRequestDownloadService.downloadZipRequest(searchSingleRequestDownloadDto, zipOutputStream);
        zipOutputStream.close();
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=images.zip");

    }

    /**
     * @param numberSearchVm {@link NumberSearchVm}
     * @return Collection<SgNumbersProjection>
     */
    @PostMapping("/number/sgnumber")
    @ApiPageable
    public ResponseEntity<Collection<NumberProjection>> searchByNumberSgNoV2(
            @RequestBody @NotNull final NumberSearchVm numberSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<NumberProjection> numberProjections = numberSearchService
                .searchNumberBySgNo(numberSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(numberProjections);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(numberProjections.getContent());
    }

    /**
     * @param numberSearchVm {@link NumberSearchVm}
     * @return Collection<CompilationNumberProjection> {@link CompilationNumberProjection}
     */
    @PostMapping("/number/compilationNo")
    @ApiPageable
    public ResponseEntity<Collection<CompilationNumberProjection>> searchByCompilationNo(
            @RequestBody @Valid final NumberSearchVm numberSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<CompilationNumberProjection> compilationNumberProjections = numberSearchService
                .searchByCompilationNumber(numberSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(compilationNumberProjections);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(compilationNumberProjections.getContent());
    }

    /**
     * detail
     *
     * @param numberSearchVm {@link NumberSearchVm}
     * @return Collection<FilingProjection> {@link FilingProjection}
     */
    @PostMapping("/number/filingNo")
    @ApiPageable
    public ResponseEntity<Collection<FilingProjection>> searchByFilingNo(
            @RequestBody @Valid final NumberSearchVm numberSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<FilingProjection> filingProjections = numberSearchService
                .searchByFilingNumber(numberSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(filingProjections);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(filingProjections.getContent());
    }


    /**
     * @param numberSearchVm {@link NumberSearchVm}
     * @return Collection<DeedNumberProjection> {@link DeedNumberProjection}
     */
    @PostMapping("/number/deedNo")
    @ApiPageable
    public ResponseEntity<Collection<DeedNumberProjection>> searchByDeedNo(
            @RequestBody @Valid final NumberSearchVm numberSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<DeedNumberProjection> deedNumberProjections = numberSearchService
                .searchByDeedNumber(numberSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(deedNumberProjections);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(deedNumberProjections.getContent());
    }

    /**
     * @param numberSearchVm {@link NumberSearchVm}
     * @return Collection<LeaseNumberProjection> {@link LeaseNumberProjection}
     */
    @PostMapping("/number/leaseNo")
    @ApiPageable
    public ResponseEntity<Collection<LeaseNumberProjection>> searchByLeaseNo(
            @RequestBody @Valid final NumberSearchVm numberSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<LeaseNumberProjection> leaseNumberProjections = numberSearchService
                .searchByLeaseNumber(numberSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(leaseNumberProjections);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(leaseNumberProjections.getContent());
    }

    /**
     * @param numberSearchVm {@link NumberSearchVm}
     * @return Collection<SurveyRecordsProjection> {@link SurveyRecordsProjection}
     */
    @PostMapping("/number/surveyRecordNo")
    @ApiPageable
    public ResponseEntity<Collection<SurveyRecordsProjection>> searchBySurveyRecordNo(
            @RequestBody @Valid final NumberSearchVm numberSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<SurveyRecordsProjection> surveyRecordsProjections = numberSearchService
                .searchBySurveyRecordNumber(numberSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(surveyRecordsProjections);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(surveyRecordsProjections.getContent());
    }

    /**
     * @param parcelSearchVm {@link ParcelSearchVm}
     * @return Collection<SgdataNumberSearchFarmProjection>
     */
    @PostMapping("/parcel/farm")
    @ApiPageable
    public ResponseEntity<Collection<ParcelFarmProjection>> farmSearch(
            @RequestBody @Valid final ParcelSearchVm parcelSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<ParcelFarmProjection> parcelDescriptionFarmSearch = parcelSearchService
                .getParcelDescriptionFarmSearch(parcelSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(parcelDescriptionFarmSearch);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(parcelDescriptionFarmSearch.getContent());
    }

    /**
     * @param parcelSearchVm {@link ParcelSearchVm}
     * @return Collection<SgdataNumberSearchProjection>
     */
    @PostMapping("/parcel/erf")
    @ApiPageable
    public ResponseEntity<Collection<ParcelErfProjection>> erfSearch(
            @RequestBody @Valid final ParcelSearchVm parcelSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<ParcelErfProjection> parcelDescriptionUsingErf = parcelSearchService
                .getParcelDescriptionUsingErf(parcelSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(parcelDescriptionUsingErf);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(parcelDescriptionUsingErf.getContent());
    }

    /**
     * @param parcelSearchVm {@link ParcelSearchVm}
     * @return Collection<SgdataNumberSearchProjection>
     */
    @PostMapping("/parcel/holding")
    @ApiPageable
    public ResponseEntity<Collection<ParcelErfProjection>> holdingSearch(
            @RequestBody @Valid final ParcelSearchVm parcelSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<ParcelErfProjection> parcelDescriptionUsingHoldings = parcelSearchService
                .getParcelDescriptionUsingHoldings(parcelSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(parcelDescriptionUsingHoldings);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(parcelDescriptionUsingHoldings.getContent());
    }

    /**
     * @param parcelSearchVm {@link ParcelSearchVm}
     * @return Collection<SgdataNumberSearchProjection>
     */
    @PostMapping("/parcel/lpi")
    @ApiPageable
    public ResponseEntity<Collection<ParcelErfProjection>> lpiSearch(
            @RequestBody @Valid final ParcelSearchVm parcelSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<ParcelErfProjection> parcelDescriptionUsingLpi = parcelSearchService
                .getParcelDescriptionUsingLpi(parcelSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(parcelDescriptionUsingLpi);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(parcelDescriptionUsingLpi.getContent());
    }

    /**
     * @param sectionalSearchVm sectionalSearchQueryVm
     * @return Collection<SectionalSearchProjection>
     */
    @PostMapping("/sectional/title")
    @ApiPageable
    public ResponseEntity<Collection<SectionalSearchProjection>> sectionalTitleSearch(
            @RequestBody @Valid final SectionalSearchVm sectionalSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<SectionalSearchProjection> sectionalTitleSearch = sectionalSearchService
                .getSectionalTitleSearch(sectionalSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(sectionalTitleSearch);
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(sectionalTitleSearch.getContent());
    }

    /**
     * @param sectionalSearchVm sectionalSearchQueryVm
     * @return Collection<SectionalSearchProjection>
     */
    @PostMapping("/sectional/sgNo")
    @ApiPageable
    public ResponseEntity<Collection<SectionalSearchProjection>> sectionalSgNoSearch(
            @RequestBody @Valid final SectionalSearchVm sectionalSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<SectionalSearchProjection> sectionalTitleSearch = sectionalSearchService
                .getSectionalTitleSearch(sectionalSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(sectionalTitleSearch);
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(sectionalTitleSearch.getContent());
    }

    /**
     * @param sectionalSearchVm {@link SectionalSearchVm}
     * @return Collection<SectionalSearchProjection>
     */
    @PostMapping("/sectional/filingNo")
    @ApiPageable
    public ResponseEntity<Collection<SectionalSearchProjection>> sectionalFilingNoSearch(
            @RequestBody @Valid final SectionalSearchVm sectionalSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<SectionalSearchProjection> sectionalTitleSearch = sectionalSearchService
                .getSectionalTitleSearch(sectionalSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(sectionalTitleSearch);
        return ResponseEntity
                .ok().headers(httpHeaders)
                .body(sectionalTitleSearch.getContent());
    }

    /**
     * @param sectionalSearchVm {@link SectionalSearchVm}
     * @return Collection<SectionalSearchProjection>
     */
    @PostMapping("/sectional/schemeName")
    @ApiPageable
    public ResponseEntity<Collection<SectionalSearchProjection>> sectionalSchemeNameSearch(
            @RequestBody @Valid final SectionalSearchVm sectionalSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<SectionalSearchProjection> sectionalTitleSearch = sectionalSearchService
                .getSectionalTitleSearch(sectionalSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(sectionalTitleSearch);
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(sectionalTitleSearch.getContent());
    }

    /**
     * @param sectionalSearchVm {@link SectionalSearchVm}
     * @return Collection<SectionalSearchProjection>
     */
    @PostMapping("/sectional/schemeNo")
    @ApiPageable
    public ResponseEntity<Collection<SectionalSearchProjection>> sectionalSchemeNoSearch(
            @RequestBody @Valid final SectionalSearchVm sectionalSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<SectionalSearchProjection> sectionalTitleSearch = sectionalSearchService
                .getSectionalTitleSearch(sectionalSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(sectionalTitleSearch);
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(sectionalTitleSearch.getContent());
    }

    /**
     * @param sectionalSearchVm {@link SectionalSearchVm}
     * @return Collection<SectionalSearchErfProjection>
     */
    @PostMapping("/sectional/erf")
    @ApiPageable
    public ResponseEntity<Collection<SectionalSearchErfProjection>> sectionalErfSearch(
            @RequestBody @Valid final SectionalSearchVm sectionalSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<SectionalSearchErfProjection> sectionalErfSearch = sectionalSearchService
                .getSectionalErfSearch(sectionalSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(sectionalErfSearch);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(sectionalErfSearch.getContent());
    }

    /**
     * @param sectionalSearchVm {@link SectionalSearchVm}
     * @return Collection<SectionalSearchFarmProjection>
     */
    @PostMapping("/sectional/farm")
    @ApiPageable
    public ResponseEntity<Collection<SectionalSearchFarmProjection>> sectionalFarmSearch(
            @RequestBody @Valid final SectionalSearchVm sectionalSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<SectionalSearchFarmProjection> sectionalFarmSearch = sectionalSearchService
                .getSectionalFarmSearch(sectionalSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(sectionalFarmSearch);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(sectionalFarmSearch.getContent());
    }

    /**
     * @param templateSearchVm {@link TemplateSearchVm}
     * @return Collection<SgNumbersProjection>
     */
    @PostMapping(value = "/template/sgNumber", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TemplateSearchResultDto>
    templateSearchSgNumber(@ModelAttribute final TemplateSearchVm templateSearchVm) {

        TemplateSearchResultDto templateSearchResultDto = templateSearchService
                .searchBySgNumberTemplate(templateSearchVm);

        return ResponseEntity.ok().body(templateSearchResultDto);
    }

    /**
     * @param templateSearchVm {@link TemplateSearchVm}
     * @return Collection<CompilationNumberProjection>
     */
    @PostMapping(value = "/template/compilationNumber", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TemplateSearchResultDto>
    templateSearchCompilationNumber(@ModelAttribute final TemplateSearchVm templateSearchVm) {
        TemplateSearchResultDto templateSearchResultDto = templateSearchService
                .searchByCompilationNumberTemplate(templateSearchVm);

        return ResponseEntity.ok().body(templateSearchResultDto);
    }

    /**
     * @param templateSearchVm {@link TemplateSearchVm}
     * @return Collection<SurveyRecordsProjection>
     */
    @PostMapping(value = "/template/surveyRecordNumber", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TemplateSearchResultDto>
    templateSearchSurveyRecordNumber(@ModelAttribute final TemplateSearchVm templateSearchVm) {
        TemplateSearchResultDto templateSearchResultDto = templateSearchService
                .searchBySurveyRecordNumberTemplate(templateSearchVm);

        return ResponseEntity.ok().body(templateSearchResultDto);
    }

    /**
     * @param templateSearchVm {@link TemplateSearchVm}
     * @return Collection<SgdataNumberSearchProjection>
     */
    @PostMapping(value = "/template/parcelErf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TemplateSearchResultDto>
    templateSearchParcelErf(@ModelAttribute @Valid final TemplateSearchVm templateSearchVm) {
        TemplateSearchResultDto templateSearchResultDto = templateSearchService
                .searchByParcelErfTemplate(templateSearchVm);

        return ResponseEntity.ok().body(templateSearchResultDto);
    }

    /**
     * @param auditNo auditNo
     * @return Resource
     */
    @GetMapping(value = "/template/resultAuditFile/{auditNo}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadTemplateSearchResultFile(@PathVariable final Long auditNo) {
        Resource resource = templateSearchService.getTemplateSearchResultAuditFile(auditNo);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * @return template_sgnumber.csv file as a octet stream.
     */
    @GetMapping(value = "/template/sample", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadSampleTemplate(@RequestParam @NotNull final TemplateTypes type) {

        Resource resource;
        if (TemplateTypes.SG_NUMBER.equals(type)) {
            resource = resourceLoader.getResource("classpath:/sample/template_sgnumber.csv");
        } else if (TemplateTypes.PARCEL_ERF.equals(type)) {
            resource = resourceLoader.getResource("classpath:/sample/template_parcel_erf.csv");
        } else if (TemplateTypes.SURVEY_RECORD.equals(type)) {
            resource = resourceLoader.getResource("classpath:/sample/template_surveyrecord.csv");
        } else if (TemplateTypes.COMPILATION_NUMBER.equals(type)) {
            resource = resourceLoader.getResource("classpath:/sample/template_compilation.csv");
        } else {
            throw new ResourceNotFoundException(" Expected Resource not found ");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * @param rangeSearchVm {@link RangeSearchVm}
     * @return Collection<SgdataNumberSearchFarmProjection>
     */
    @PostMapping("/range/farm")
    @ApiPageable
    public ResponseEntity<Collection<ParcelFarmProjection>> getRangeFarmSearchData(
            @RequestBody @Valid RangeSearchVm rangeSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<ParcelFarmProjection> searchDataBasedOnFarmRange = rangeSearchService
                .getSearchDataBasedOnFarmRange(rangeSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(searchDataBasedOnFarmRange);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(searchDataBasedOnFarmRange.getContent());
    }

    /**
     * @param rangeSearchVm {@link RangeSearchVm}
     * @return Collection<SgdataNumberSearchProjection>
     */
    @PostMapping("/range/erf/portion")
    @ApiPageable
    public ResponseEntity<Collection<ParcelErfProjection>> getRangeErfPortionSearchData(
            @RequestBody @Valid RangeSearchVm rangeSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<ParcelErfProjection> searchDataBasedOnErfPortionRange = rangeSearchService
                .getSearchDataBasedOnErfPortionRange(rangeSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil
                .generatePaginationHttpHeaders(searchDataBasedOnErfPortionRange);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(searchDataBasedOnErfPortionRange.getContent());
    }

    /**
     * @param rangeSearchVm {@link RangeSearchVm}
     * @return Collection<SgdataNumberSearchProjection>
     */
    @PostMapping("/range/erf/parcel")
    @ApiPageable
    public ResponseEntity<Collection<ParcelErfProjection>> getRangeErfParcelSearchData(
            @RequestBody @Valid RangeSearchVm rangeSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<ParcelErfProjection> searchDataBasedOnErfParcelRange = rangeSearchService
                .getSearchDataBasedOnErfParcelRange(rangeSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil
                .generatePaginationHttpHeaders(searchDataBasedOnErfParcelRange);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(searchDataBasedOnErfParcelRange.getContent());
    }

    @PostMapping("/range/holding/parcel")
    public ResponseEntity<Collection<ParcelErfProjection>> getRangeHoldingParcelSearchData(
            @RequestBody @Valid RangeSearchVm rangeSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<ParcelErfProjection> searchDataBasedOnHoldingParcelRange = rangeSearchService
                .getSearchDataBasedOnHoldingParcelRange(rangeSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil
                .generatePaginationHttpHeaders(searchDataBasedOnHoldingParcelRange);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(searchDataBasedOnHoldingParcelRange.getContent());

    }

    @PostMapping("/range/holding/portion")
    public ResponseEntity<Collection<ParcelErfProjection>> getRangeHoldingPortionSearchData(
            @RequestBody @Valid RangeSearchVm rangeSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<ParcelErfProjection> searchDataBasedOnHoldingParcelRange = rangeSearchService
                .getSearchDataBasedOnHoldingPortionRange(rangeSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil
                .generatePaginationHttpHeaders(searchDataBasedOnHoldingParcelRange);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(searchDataBasedOnHoldingParcelRange.getContent());

    }

    /**
     * @param textSearchVm {@link TextSearchVm}
     * @return Collection<SgdataNumberSearchProjection>
     */
    @PostMapping("/text")
    @ApiPageable
    public ResponseEntity<Collection<SgdataParcelsDto>> getTextSearch(
            @RequestBody @Valid final TextSearchVm textSearchVm,
            @ApiIgnore final Pageable pageable) {
        final Page<SgdataParcelsDto> byTextSearch = textSearchService.findByTextSearch(textSearchVm, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(byTextSearch);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(byTextSearch.getContent());
    }

    /**
     * @param recordId recordId
     * @return Collection<SgdataNumberSearchProjection
     */
    @GetMapping("/dataProfileFromLpiCode")
    public ResponseEntity<Collection<SgdataParcelsDto>> getDataProfileForLpiCode(
            @RequestParam @NotNull final Long recordId) {
        final Page<SgdataParcelsDto> dataProfileUsingOnlyLpi = parcelSearchService
                .getDataProfileUsingOnlyLpi(recordId, Pageable.unpaged());
        return ResponseEntity.ok()
                .body(dataProfileUsingOnlyLpi.getContent());
    }

    /**
     * @param lpiCode lpiCode
     * @return Collection<SgdataNumberSearchProjection>
     */
    @GetMapping("/relatedDataForLpiCode")
    @ApiPageable
    public ResponseEntity<Collection<SearchProfileRelatedData>> getRelatedDataForLpiCode(
            @RequestParam @NotNull final String lpiCode,
            @ApiIgnore final Pageable pageable) {
        final Page<SearchProfileRelatedData> relatedDataByLpi = searchService
                .getRelatedDataByLpi(lpiCode, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(relatedDataByLpi);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(relatedDataByLpi.getContent());
    }

    /**
     * @param recordId recordId
     * @param pageable {@link Pageable}
     * @return Collection<SearchDocumentDto>
     */
    @GetMapping("/details/{recordId}")
    @ApiPageable
    public ResponseEntity<Collection<SearchDocumentDto>> getDocumentForRecord(@PathVariable final Long recordId,
                                                                              @ApiIgnore final Pageable pageable) {
        final Page<SearchDocumentDto> documents = searchService.getDocuments(recordId, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generateSearchSummaryHttpHeaders(documents);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(documents.getContent());
    }

    /**
     * @param searchDetailsShareVm {@link SearchDetailsShareVm}
     */
    @PostMapping("/share")
    public void shareSearchDetails(@RequestBody final SearchDetailsShareVm searchDetailsShareVm) {
        shareSearchDetailsService.shareSearchDetails(searchDetailsShareVm);
    }

    /**
     * @param searchVm {@link SearchVm}
     * @return Collection<NgiSearchDataProjection>
     */
    @PostMapping("/ngi")
    @ApiPageable
    public ResponseEntity<Collection<NgiSearchDataProjection>> getNgiSearchResult(@RequestBody @Valid SearchVm searchVm,
                                                                                  @ApiIgnore final Pageable pageable) {

        Page<NgiSearchDataProjection> ngiSearchDataProjections = ngiSearchService.searchNgiData(searchVm,pageable);

        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(ngiSearchDataProjections);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(ngiSearchDataProjections.getContent());

    }

}
