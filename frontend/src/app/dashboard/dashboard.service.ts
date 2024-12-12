import { Observable, Subject, timer } from "rxjs";
import { map, share, switchMap, takeUntil } from "rxjs/operators";
import { Injectable, OnDestroy } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import type {
	AggregatedTicket,
	AggregatedTicketCollectionResponse,
	TicketCountResponse
} from "../../types/ticket";
import type { ConfigurationResponse } from "../../types/configuration";
import type { CreateVendor, VendorCountResponse, VendorResponse } from "../../types/vendor";
import type { CreateCustomer, CustomerCountResponse, CustomerResponse } from "../../types/customer";
import { GenericResponse } from "../../types/generic";

@Injectable({ providedIn: "root" })
export class DashboardService implements OnDestroy {
	private stopPolling = new Subject<void>();
	private readonly aggregatedTickets$: Observable<AggregatedTicket[]>;

	constructor(private http: HttpClient) {
		this.aggregatedTickets$ = timer(1, 1000).pipe(
			switchMap(() => this.http.get<AggregatedTicketCollectionResponse>("/tickets/aggregate")),
			map((resp) => resp.data ?? []),
			map((resp) => resp.map((x) => ({
				...x,
				id: x.id ?? Math.random().toString()
			}))),
			share(),
			takeUntil(this.stopPolling)
		);
	}

	getAggregatedTickets(): Observable<AggregatedTicket[]> {
		return this.aggregatedTickets$;
	}

	getConfiguration(): Observable<ConfigurationResponse> {
		return this.http.get<ConfigurationResponse>("/config");
	}

	getTicketCount(): Observable<TicketCountResponse> {
		return this.http.get<TicketCountResponse>("/tickets/count");
	}

	getVendorCount(): Observable<VendorCountResponse> {
		return this.http.get<VendorCountResponse>("/vendor/count");
	}

	getCustomerCount(): Observable<CustomerCountResponse> {
		return this.http.get<CustomerCountResponse>("/customer/count");
	}

	createVendor(): Observable<VendorResponse> {
		return this.http.post<VendorResponse>("/vendor", { name: "Testing " } as CreateVendor);
	}

	createCustomer(): Observable<CustomerResponse> {
		return this.http.post<CustomerResponse>("/customer", { name: "Testing" } as CreateCustomer);
	}

	isAtLeastOneRunning(): Observable<GenericResponse<boolean>> {
		return this.http.get<GenericResponse<boolean>>("/vendor/status");
	}

	startAllVendors(): Observable<GenericResponse<boolean>> {
		return this.http.post<GenericResponse<boolean>>("/vendor/startAll", {});
	}

	endAllVendors(): Observable<GenericResponse<boolean>> {
		return this.http.post<GenericResponse<boolean>>("/vendor/endAll", {});
	}

	ngOnDestroy() {
		this.stopPolling.next();
	}
}
