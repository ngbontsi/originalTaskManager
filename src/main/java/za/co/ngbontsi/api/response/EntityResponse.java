package za.co.ngbontsi.api.response;

public class EntityResponse<T> {

    public EntityResponse(int statusCode, String message, T data) {
    }

    public EntityResponse(int statusCode, String message) {
        this(statusCode, message, null);
    }


}
