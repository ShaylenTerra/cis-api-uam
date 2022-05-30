package com.dw.ngms.cis.service;

import com.dw.ngms.cis.dto.SearchSingleRequestDownloadDto;
import com.dw.ngms.cis.persistence.domain.SearchSingleRequestDownload;
import com.dw.ngms.cis.persistence.repository.SearchSingleRequestDownloadRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author : prateekgoel
 * @since : 30/12/20, Wed
 **/
@Service
@Slf4j
@AllArgsConstructor
public class SearchSingleRequestDownloadService {

    private final SearchSingleRequestDownloadRepository searchSingleRequestDownloadRepository;

    /**
     * @param searchSingleRequestDownloadDto {@link SearchSingleRequestDownloadDto}
     * @return {@link InputStream}
     */
    public InputStream downloadSingleRequest(SearchSingleRequestDownloadDto searchSingleRequestDownloadDto) {

        return searchSingleRequestDownloadDto.getDocumentUrl().stream().findFirst().map(s -> {
            log.debug(" generating download for url {} ", s);
            SearchSingleRequestDownload searchSingleRequestDownload = new SearchSingleRequestDownload();
            searchSingleRequestDownload.setDated(new Date());
            searchSingleRequestDownload.setDocumentId(searchSingleRequestDownloadDto.getDocumentId());
            searchSingleRequestDownload.setProvinceId(searchSingleRequestDownloadDto.getProvinceId());
            searchSingleRequestDownload.setUserId(searchSingleRequestDownloadDto.getUserId());
            searchSingleRequestDownload.setDocumentName(searchSingleRequestDownloadDto.getDocumentName());
            searchSingleRequestDownload.setDataKeyName(searchSingleRequestDownloadDto.getDataKeyName());
            searchSingleRequestDownload.setDocumentUrl(s);
            searchSingleRequestDownloadRepository.save(searchSingleRequestDownload);

            try {
                return new URL(s).openStream();
            } catch (IOException e) {
                log.error(" exception occur while fetching image from Url  {}", searchSingleRequestDownloadDto.getDocumentUrl());
                e.printStackTrace();
                return null;
            }
        }).get();

    }

    /**
     * @param searchSingleRequestDownloadDto {@link SearchSingleRequestDownloadDto}
     */
    public void downloadZipRequest(final SearchSingleRequestDownloadDto searchSingleRequestDownloadDto, ZipOutputStream zipOutputStream) {
        try {
            searchSingleRequestDownloadDto.getDocumentUrl().forEach(s -> {
                log.debug(" generating zipped stream for url {} ", s);
                SearchSingleRequestDownload searchSingleRequestDownload = new SearchSingleRequestDownload();
                searchSingleRequestDownload.setDated(new Date());
                searchSingleRequestDownload.setDocumentId(searchSingleRequestDownloadDto.getDocumentId());
                searchSingleRequestDownload.setProvinceId(searchSingleRequestDownloadDto.getProvinceId());
                searchSingleRequestDownload.setUserId(searchSingleRequestDownloadDto.getUserId());
                searchSingleRequestDownload.setDocumentName(searchSingleRequestDownloadDto.getDocumentName());
                searchSingleRequestDownload.setDataKeyName(searchSingleRequestDownloadDto.getDataKeyName());
                searchSingleRequestDownload.setDocumentUrl(s);
                searchSingleRequestDownloadRepository.save(searchSingleRequestDownload);
                try {
                    String filename = s.substring(s.lastIndexOf("/") + 1);
                    ZipEntry zipEntry = new ZipEntry(filename);
                    zipOutputStream.putNextEntry(zipEntry);
                    StreamUtils.copy(new URL(s).openStream(), zipOutputStream);
                    zipOutputStream.closeEntry();


                } catch (IOException e) {
                    log.error(" exception occur while fetching image from Url  {}", searchSingleRequestDownloadDto.getDocumentUrl());
                    e.printStackTrace();
                }
            });

            zipOutputStream.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
