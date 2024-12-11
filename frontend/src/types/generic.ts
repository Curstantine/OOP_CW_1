export interface GenericResponse<T> {
	status: "SUCCESS" | "FAILURE";
	data: T | null;
	error: string | null;
}
