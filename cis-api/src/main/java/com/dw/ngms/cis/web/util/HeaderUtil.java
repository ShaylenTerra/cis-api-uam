package com.dw.ngms.cis.web.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Slf4j
public final class HeaderUtil {

    private HeaderUtil() {
    }

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-cisApp-alert", message);
        headers.add("X-cisApp-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("A new " + entityName + " is created with identifier " + param, param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("A " + entityName + " is updated with identifier " + param, param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("A " + entityName + " is deleted with identifier " + param, param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        log.error("Entity processing failed, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-cisApp-error", defaultMessage);
        headers.add("X-cisApp-params", entityName);
        return headers;
    }
}
