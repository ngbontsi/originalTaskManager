package za.co.ngbontsi.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityResponse<T> {
    private T data;
    private String message;
    private int code;
    public EntityResponse(int statusCode, String message, T data) {
    }

    public EntityResponse(int statusCode, String message) {
        this(statusCode, message, null);
    }
    public static <T> EntityResponse<T> success(T data) {
        EntityResponse<T> res = new EntityResponse<>();
        res.setData(data);
        res.setCode(200);
        res.setMessage("Success");
        return res;
    }

}
