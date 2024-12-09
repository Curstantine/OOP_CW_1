package moe.curstantine.shared;

public class GenericResponse<T> {
	public ResponseStatus status;
	public T data;
	public String error;

	public GenericResponse(ResponseStatus status, T data, String error) {
		this.status = status;
		this.data = data;
		this.error = error;
	}

	public static <T> GenericResponse<T> fromSuccess(T data) {
		return new GenericResponse<>(ResponseStatus.SUCCESS, data, null);
	}

	public static <T> GenericResponse<T> fromError(String error) {
		return new GenericResponse<>(ResponseStatus.FAILURE, null, error);
	}
}
