package solvas.authentication.utils;

import java.util.Date;

import org.springframework.http.HttpStatus;

/**
 * Error model for interacting with client.
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
public class ErrorResponse {
    // HTTP Response Status Code
    private final HttpStatus status;

    // General Error message
    private final String message;

    // Error code
    private final ErrorCode errorCode;

    private final Date timestamp;

    protected ErrorResponse(final String message, final ErrorCode errorCode, HttpStatus status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = new java.util.Date();
    }

    /**
     * Create ErrorResponse
     * @param message ErrorMessage
     * @param errorCode ErrorCode
     * @param status Http response status (e.g.: 401 UNAUTHORIZED)
     * @return
     */
    public static ErrorResponse of(final String message, final ErrorCode errorCode, HttpStatus status) {
        return new ErrorResponse(message, errorCode, status);
    }

    /**
     * @return HTTP Statuscode of the error
     */
    public Integer getStatus() {
        return status.value();
    }

    /**
     * @return Errormessage
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Error code
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * @return Time of occurrence of the error
     */
    public Date getTimestamp() {
        return timestamp;
    }
}