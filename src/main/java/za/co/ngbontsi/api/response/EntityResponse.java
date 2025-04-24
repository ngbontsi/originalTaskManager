package za.co.ngbontsi.api.response;

public class EntityResponse<T> {

    final private int statusCode;
   final private String message;
    final private T data;

    public EntityResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public EntityResponse(int statusCode, String message) {
        this(statusCode, message, null);
    }


}
