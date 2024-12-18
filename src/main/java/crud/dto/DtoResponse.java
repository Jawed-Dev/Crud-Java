package crud.dto;

public class DtoResponse <T> {
    private boolean success;
    private String message;
    private T data;
    private String errorCode;

    public DtoResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errorCode = null;
    }

    public DtoResponse(boolean success, String errorCode) {
        this.success = success;
        this.message = "";
        this.errorCode = errorCode;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setErrorCode(String codeError) {
        this.errorCode = codeError;
    }
}
