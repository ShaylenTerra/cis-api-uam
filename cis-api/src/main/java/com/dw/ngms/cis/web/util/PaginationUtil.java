package com.dw.ngms.cis.web.util;

import com.dw.ngms.cis.dto.SearchDocumentDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 16/12/20, Wed
 * <p>
 * Utility class for handling pagination.
 * <p>
 * Pagination uses the same principles as the <a href="https://developer.github.com/v3/#pagination">GitHub API</a>,
 * and follow <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link header)</a>.
 **/
public final class PaginationUtil {

    private PaginationUtil() {
    }

    public static HttpHeaders generatePaginationHttpHeaders(Page page) {

        HttpHeaders headers = new HttpHeaders();

        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));

        return headers;
    }

    public static HttpHeaders generateSearchSummaryHttpHeaders(Page page) {
        final HttpHeaders httpHeaders = generatePaginationHttpHeaders(page);
        final Object collect = page.getContent().stream()
                .map(o -> ((SearchDocumentDto) o).getFileSize())
                .collect(Collectors.summingLong(Long::longValue));
        httpHeaders.add("X-Total-File-Size", getDecoratedSize((Long) collect));
        return httpHeaders;
    }

    private static String generateUri(String baseUrl, int page, int size) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("page", page)
                .queryParam("size", size)
                .toUriString();
    }

    private static String getDecoratedSize(Long size) {
        if (size >= 1024)
            return size / 1024 + " MB";
        return size + " KB";
    }

}
