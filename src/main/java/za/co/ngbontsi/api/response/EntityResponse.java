package za.co.ngbontsi.api.response;

public class EntityResponse<T> {

    private int statusCode;
    private String message;
    private T data;

    public EntityResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public EntityResponse(int statusCode, String message) {
        this(statusCode, message, null);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
