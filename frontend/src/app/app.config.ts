import { ApplicationConfig, provideZoneChangeDetection } from "@angular/core";
import { provideRouter } from "@angular/router";
import { provideClientHydration, withEventReplay } from "@angular/platform-browser";
import { provideHttpClient, withFetch, withInterceptors } from "@angular/common/http";

import { routes } from "./app.routes";
import { backendUrlInterceptor } from "../utils/interceptor";

export const appConfig: ApplicationConfig = {
	providers: [
		provideZoneChangeDetection({ eventCoalescing: true }),
		provideRouter(routes),
		provideHttpClient(
			withFetch(),
			withInterceptors([backendUrlInterceptor])
		),
		provideClientHydration(withEventReplay()),
	]
};
