package com.dw.ngms.cis.enums;

import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 20/03/21, Sat
 **/
public enum DataViewerRequestStatus {

    IN_PROCESS("0"),
    PROCESSED("1");

    private final String msg;


    DataViewerRequestStatus(String i) {
        this.msg = i;
    }

    public static DataViewerRequestStatus of(String dataViewerRequestStatus) {
        return Stream.of(DataViewerRequestStatus.values())
                .filter(dataViewerRequestStatus1 -> dataViewerRequestStatus1
                        .getDataViewerRequestStatus().equals(dataViewerRequestStatus))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getDataViewerRequestStatus() {
        return msg;
    }
}
