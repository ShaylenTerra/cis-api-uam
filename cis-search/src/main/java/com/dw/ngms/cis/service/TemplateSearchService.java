package com.dw.ngms.cis.service;

import com.dw.ngms.cis.dto.*;
import com.dw.ngms.cis.enums.SearchContext;
import com.dw.ngms.cis.persistence.domain.SearchTemplateAudit;
import com.dw.ngms.cis.persistence.projection.number.CompilationNumberProjection;
import com.dw.ngms.cis.persistence.projection.number.NumberProjection;
import com.dw.ngms.cis.persistence.projection.number.SurveyRecordsProjection;
import com.dw.ngms.cis.persistence.projection.parcel.ParcelErfProjection;
import com.dw.ngms.cis.persistence.repository.SearchTemplateAuditRepository;
import com.dw.ngms.cis.persistence.repository.SgdataParcelsRepository;
import com.dw.ngms.cis.utilities.SearchUtils;
import com.dw.ngms.cis.vms.TemplateSearchVm;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 10/01/21, Sun
 **/
@Service
@Slf4j
@AllArgsConstructor
public class TemplateSearchService {

    private final NumberSearchService numberSearchService;

    private final UserSearchHistoryService userSearchHistoryService;

    private final SgdataParcelsRepository sgdataParcelsRepository;

    private final CsvWriter csvWriter;

    private final SearchTemplateAuditRepository searchTemplateAuditRepository;

    /**
     * @param file uploaded csv file
     * @return Collection<String> SgNumber collections
     */
    private List<TemplateSearchSgNumberDto> parseSgNumberCsv(final MultipartFile file) {

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            return new CsvToBeanBuilder<>(reader)
                    .withType(TemplateSearchSgNumberDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .stream()
                    .map(o -> ((TemplateSearchSgNumberDto) o))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error(" an error occurred while processing the csv {}", file.getOriginalFilename());
            e.printStackTrace();
        }
        return null;

    }

    private List<TemplateSearchCompilationNumberDto> parseCompilationNumberCsv(final MultipartFile file) {


        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            return new CsvToBeanBuilder<>(reader)
                    .withType(TemplateSearchCompilationNumberDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .stream()
                    .map(o -> ((TemplateSearchCompilationNumberDto) o))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toCollection(LinkedList::new));

        } catch (IOException e) {
            log.error(" an error occurred while processing the csv {}", file.getOriginalFilename());
            e.printStackTrace();
        }
        return null;

    }

    private List<TemplateSearchParcelErfDto> parseParcelErfCsv(final MultipartFile file) {

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            return new CsvToBeanBuilder<>(reader)
                    .withType(TemplateSearchParcelErfDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .stream()
                    .map(o -> ((TemplateSearchParcelErfDto) o))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toCollection(LinkedList::new));

        } catch (IOException e) {
            log.error(" an error occurred while processing the csv {}", file.getOriginalFilename());
            e.printStackTrace();
        }
        return null;

    }

    private List<TemplateSearchSurveyRecordNumberDto> parseSurveyRecordCsv(final MultipartFile file) {


        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            return new CsvToBeanBuilder<>(reader)
                    .withType(TemplateSearchSurveyRecordNumberDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .stream()
                    .map(o -> ((TemplateSearchSurveyRecordNumberDto) o))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toCollection(LinkedList::new));

        } catch (IOException e) {
            log.error(" an error occurred while processing the csv {}", file.getOriginalFilename());
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @param templateSearchVm {@link TemplateSearchVm}
     * @return TemplateSearchResultDto {@link TemplateSearchResultDto}
     */
    public TemplateSearchResultDto searchBySgNumberTemplate(final TemplateSearchVm templateSearchVm) {

        try {

            userSearchHistoryService.storeUserSearchData(templateSearchVm, SearchContext.TEMPLATE_SEARCH);

            List<TemplateSearchSgNumberDto> templateSearchSgNumberCsvData = parseSgNumberCsv(templateSearchVm.getFile());

            AtomicInteger totalRecordFoundCount = new AtomicInteger();

            if (CollectionUtils.isNotEmpty(templateSearchSgNumberCsvData)) {
                List<NumberProjection> collect = templateSearchSgNumberCsvData.stream()
                        .map(o -> {
                            NumberProjection numberProjection = numberSearchService
                                    .searchBySgNo(o.getSgNumber(), templateSearchVm.getProvinceId());
                            if (null != numberProjection) {
                                o.setResult("FOUND");
                                totalRecordFoundCount.getAndIncrement();
                            } else {
                                o.setResult("NOT FOUND");
                            }

                            return numberProjection;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                Resource sgNoTemplateResultResource = csvWriter
                        .generateCsvResource(templateSearchSgNumberCsvData, TemplateSearchSgNumberDto.class, "SgNoTemplate");
                // save this resource in db with other parameters.

                SearchTemplateAudit searchTemplateAudit = new SearchTemplateAudit();
                searchTemplateAudit.setTemplatePath(sgNoTemplateResultResource.getFile().getAbsolutePath());
                searchTemplateAudit.setCreatedDate(LocalDateTime.now());
                searchTemplateAudit.setTemplateType("SGNO_TEMPLATE");
                searchTemplateAudit.setFoundRecords(totalRecordFoundCount.get());
                searchTemplateAudit.setTotalRecords(templateSearchSgNumberCsvData.size());
                searchTemplateAudit.setUserId(templateSearchVm.getUserId());
                SearchTemplateAudit templateAudit = searchTemplateAuditRepository.save(searchTemplateAudit);

                TemplateSearchResultDto templateSearchResultDto = new TemplateSearchResultDto<NumberProjection>();
                templateSearchResultDto.setTemplateAuditId(templateAudit.getSearchTemplateAuditId());
                templateSearchResultDto.setFoundRecords(totalRecordFoundCount.get());
                templateSearchResultDto.setResults(collect);
                templateSearchResultDto.setTotalRecords(templateSearchSgNumberCsvData.size());

                return templateSearchResultDto;
            }

        } catch (Exception e) {
            log.error("exception occurs while writing template search result csv with cause [{}]", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param templateSearchVm templateSearchVm
     * @return TemplateSearchResultDto {@link TemplateSearchResultDto}
     */
    public TemplateSearchResultDto
    searchBySurveyRecordNumberTemplate(final TemplateSearchVm templateSearchVm) {

        try {

            userSearchHistoryService.storeUserSearchData(templateSearchVm, SearchContext.TEMPLATE_SEARCH);

            List<TemplateSearchSurveyRecordNumberDto> templateSearchSurveyRecordNumberCsvData =
                    parseSurveyRecordCsv(templateSearchVm.getFile());

            AtomicInteger totalRecordFoundCount = new AtomicInteger();

            if (CollectionUtils.isNotEmpty(templateSearchSurveyRecordNumberCsvData)) {

                List<SurveyRecordsProjection> collect = templateSearchSurveyRecordNumberCsvData.stream()
                        .map(o -> {
                            List<SurveyRecordsProjection> content = sgdataParcelsRepository
                                    .findUsingSurveyRecordNoAndProvinceId(o.getSurveyRecordNumber(),
                                            templateSearchVm.getProvinceId(),
                                            Pageable.unpaged()).getContent();

                            if (CollectionUtils.isNotEmpty(content)) {
                                totalRecordFoundCount.getAndIncrement();
                                o.setResult("FOUND");
                            } else {
                                o.setResult("NOT FOUND");
                            }

                            return content;

                        })
                        .filter(Objects::nonNull)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toCollection(LinkedList::new));

                Resource resource = csvWriter
                        .generateCsvResource(templateSearchSurveyRecordNumberCsvData,
                                TemplateSearchSurveyRecordNumberDto.class, "SurveyRecordNoTemplate");

                // save this resource in db with other parameters.

                SearchTemplateAudit searchTemplateAudit = new SearchTemplateAudit();
                searchTemplateAudit.setTemplatePath(resource.getFile().getAbsolutePath());
                searchTemplateAudit.setCreatedDate(LocalDateTime.now());
                searchTemplateAudit.setTemplateType("SURVEY_RECORD_TEMPLATE");
                searchTemplateAudit.setFoundRecords(totalRecordFoundCount.get());
                searchTemplateAudit.setTotalRecords(templateSearchSurveyRecordNumberCsvData.size());
                searchTemplateAudit.setUserId(templateSearchVm.getUserId());
                SearchTemplateAudit templateAudit = searchTemplateAuditRepository.save(searchTemplateAudit);

                TemplateSearchResultDto templateSearchResultDto = new TemplateSearchResultDto<NumberProjection>();
                templateSearchResultDto.setTemplateAuditId(templateAudit.getSearchTemplateAuditId());
                templateSearchResultDto.setFoundRecords(totalRecordFoundCount.get());
                templateSearchResultDto.setResults(collect);
                templateSearchResultDto.setTotalRecords(templateSearchSurveyRecordNumberCsvData.size());

                return templateSearchResultDto;


            }

        } catch (Exception e) {
            log.error(" error occurred while creating survey record template with cause [{}]", e.getMessage());
            e.printStackTrace();
        }

        return null;

    }

    /**
     * @param templateSearchVm {@link TemplateSearchVm}
     * @return TemplateSearchResultDto {@link TemplateSearchResultDto}
     */
    public TemplateSearchResultDto searchByCompilationNumberTemplate(final TemplateSearchVm templateSearchVm) {

        try {

            userSearchHistoryService.storeUserSearchData(templateSearchVm, SearchContext.TEMPLATE_SEARCH);

            AtomicInteger totalRecordFoundCount = new AtomicInteger();

            List<TemplateSearchCompilationNumberDto> templateSearchCompilationNoCsvData =
                    parseCompilationNumberCsv(templateSearchVm.getFile());

            if (CollectionUtils.isNotEmpty(templateSearchCompilationNoCsvData)) {
                Collection<CompilationNumberProjection> collect = templateSearchCompilationNoCsvData.stream()
                        .map(o -> {
                            List<CompilationNumberProjection> content = sgdataParcelsRepository
                                    .findUsingCompilationNoAndProvinceId(o.getCompilationNumber(),
                                            templateSearchVm.getProvinceId(), Pageable.unpaged()).getContent();

                            if (CollectionUtils.isNotEmpty(content)) {
                                o.setResult("FOUND");
                                totalRecordFoundCount.incrementAndGet();
                            } else {
                                o.setResult("NOT FOUND");
                            }

                            return content;

                        })
                        .filter(Objects::nonNull)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toCollection(LinkedList::new));

                Resource compilationNoTemplateResultResource = csvWriter
                        .generateCsvResource(templateSearchCompilationNoCsvData,
                                TemplateSearchCompilationNumberDto.class, "CompilationNoTemplate");

                // save this resource in db with other parameters.
                SearchTemplateAudit searchTemplateAudit = new SearchTemplateAudit();
                searchTemplateAudit.setTemplatePath(compilationNoTemplateResultResource.getFile().getAbsolutePath());
                searchTemplateAudit.setCreatedDate(LocalDateTime.now());
                searchTemplateAudit.setTemplateType("COMPILATION_NO_TEMPLATE");
                searchTemplateAudit.setFoundRecords(totalRecordFoundCount.get());
                searchTemplateAudit.setTotalRecords(templateSearchCompilationNoCsvData.size());
                searchTemplateAudit.setUserId(templateSearchVm.getUserId());
                SearchTemplateAudit templateAudit = searchTemplateAuditRepository.save(searchTemplateAudit);

                TemplateSearchResultDto templateSearchResultDto = new TemplateSearchResultDto<NumberProjection>();
                templateSearchResultDto.setTemplateAuditId(templateAudit.getSearchTemplateAuditId());
                templateSearchResultDto.setFoundRecords(totalRecordFoundCount.get());
                templateSearchResultDto.setResults(collect);
                templateSearchResultDto.setTotalRecords(templateSearchCompilationNoCsvData.size());

                return templateSearchResultDto;
            }

        } catch (Exception e) {
            log.error(" error occurred while while writing compilation template search result csv with cause [{}]", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param templateSearchVm {@link TemplateSearchVm}
     * @return TemplateSearchResultDto {@link TemplateSearchResultDto}
     */
    public TemplateSearchResultDto searchByParcelErfTemplate(final TemplateSearchVm templateSearchVm) {

        try {
            userSearchHistoryService.storeUserSearchData(templateSearchVm, SearchContext.TEMPLATE_SEARCH);

            List<TemplateSearchParcelErfDto> templateSearchParcelErfCsvData = parseParcelErfCsv(templateSearchVm.getFile());

            AtomicInteger totalRecordFoundCount = new AtomicInteger();

            if (CollectionUtils.isNotEmpty(templateSearchParcelErfCsvData)) {
                LinkedList<ParcelErfProjection> collect = templateSearchParcelErfCsvData.stream()
                        .map(o -> {
                            List<ParcelErfProjection> content = sgdataParcelsRepository
                                    .findUsingParcelErf(templateSearchVm.getProvinceId(),
                                            null,
                                            null,
                                            SearchUtils.padStringWithZero(o.getErfNumber(), 8),
                                            SearchUtils.padStringWithZero(o.getPortion(), 5),
                                            Pageable.unpaged()).getContent();

                            if (CollectionUtils.isNotEmpty(content)) {
                                o.setResult("FOUND");
                                totalRecordFoundCount.getAndIncrement();
                            } else {
                                o.setResult("NOT FOUND");
                            }

                            return content;

                        })
                        .filter(Objects::nonNull)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toCollection(LinkedList::new));

                Resource compilationNoTemplateResultResource = csvWriter
                        .generateCsvResource(templateSearchParcelErfCsvData,
                                TemplateSearchParcelErfDto.class, "ParcelErfTemplate");

                // save this resource in db with other parameters.
                SearchTemplateAudit searchTemplateAudit = new SearchTemplateAudit();
                searchTemplateAudit.setTemplatePath(compilationNoTemplateResultResource.getFile().getAbsolutePath());
                searchTemplateAudit.setCreatedDate(LocalDateTime.now());
                searchTemplateAudit.setTemplateType("PARCEL_ERF_TEMPLATE");
                searchTemplateAudit.setFoundRecords(totalRecordFoundCount.get());
                searchTemplateAudit.setTotalRecords(templateSearchParcelErfCsvData.size());
                searchTemplateAudit.setUserId(templateSearchVm.getUserId());
                SearchTemplateAudit templateAudit = searchTemplateAuditRepository.save(searchTemplateAudit);

                TemplateSearchResultDto templateSearchResultDto = new TemplateSearchResultDto<NumberProjection>();
                templateSearchResultDto.setTemplateAuditId(templateAudit.getSearchTemplateAuditId());
                templateSearchResultDto.setFoundRecords(totalRecordFoundCount.get());
                templateSearchResultDto.setResults(collect);
                templateSearchResultDto.setTotalRecords(templateSearchParcelErfCsvData.size());

                return templateSearchResultDto;


            }

        } catch (Exception e) {
            log.error("error occurred while creating parcel erf result template with cause [{}]", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param auditNo auditNo
     * @return  Resource {@link Resource}
     */
    public Resource getTemplateSearchResultAuditFile(final Long auditNo) {

        SearchTemplateAudit bySearchTemplateAuditId = searchTemplateAuditRepository
                .findBySearchTemplateAuditId(auditNo);

        if (null != bySearchTemplateAuditId) {

            String templatePath = bySearchTemplateAuditId.getTemplatePath();

            return new FileSystemResource(new File(templatePath));

        }
        return null;
    }


}
