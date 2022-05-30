package com.dw.ngms.cis.enums;

/**
 * @author : prateekgoel
 * @since : 03/12/20, Thu
 * <p>
 * Return status code
 **/
public enum ResponseCode {

    USER_NOT_REGISTERED(409, "USER not registered"),

    PENDING_USER_ERROR(418, "User is in pending state"),

    LOCKED_USER_ERROR(423, "User is in Locked state"),
    /**
     * Successful return status code
     */
    SUCCESS(10000, "success"),
    /**
     * State codes for non-existent resources
     */
    RESOURCES_NOT_EXIST(10001, "Resources do not exist"),
    /**
     * Default return status codes for all unrecognized exceptions
     */
    SERVICE_ERROR(50000, "Server exception"),
    /**
     * BadRequest for validation error.
     */
    BAD_REQUEST(10001, "Bad Request");
    /**
     * Status code
     */
    private final int code;
    /**
     * Return information
     */
    private final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
