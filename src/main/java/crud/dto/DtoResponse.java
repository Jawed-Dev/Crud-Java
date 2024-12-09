package crud.dto;

public class DtoResponse <T> {
    private boolean success;
    private String message;
    private T data;

    public DtoResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }

    public DtoResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
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
}
