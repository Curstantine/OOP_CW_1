import { HttpEvent, HttpHandlerFn, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";

export function backendUrlInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
	if (!req.url.startsWith("/")) return next(req);

	const reqWithNewUrl = req.clone({
		url: "http://localhost:8080" + req.url,
	});

	return next(reqWithNewUrl);
}
