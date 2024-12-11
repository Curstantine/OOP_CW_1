import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import type { AggregatedTicketCollectionResponse } from "../../types/ticket";
import type { ConfigurationResponse } from "../../types/configuration";

@Injectable({ providedIn: "root" })
export class DashboardService {
	constructor(private http: HttpClient) {
	}

	getConfiguration(): Observable<ConfigurationResponse> {
		return this.http.get<ConfigurationResponse>("/config");
	}

	getAggregated(): Observable<AggregatedTicketCollectionResponse> {
		return this.http.get<AggregatedTicketCollectionResponse>("/tickets/aggregate");
	}
}
