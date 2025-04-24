package za.co.ngbontsi.api.response;

public class EntityResponse<T> {

    final private String message;
    final private T data;

    public EntityResponse(int statusCode, String message, T data) {
        this.message = message;
        this.data = data;
    }

    public EntityResponse(int statusCode, String message) {
        this(statusCode, message, null);
    }


}
