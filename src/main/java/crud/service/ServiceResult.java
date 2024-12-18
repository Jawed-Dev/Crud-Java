package crud.service;

public class ServiceResult<T> {
    private final boolean success;
    private final String errorMessage;
    private final T data;

    public ServiceResult(T data) {
        this.success = true;
        this.errorMessage = null;
        this.data = data;
    }

    public ServiceResult(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
        this.data = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getData() {
        return data;
    }
}