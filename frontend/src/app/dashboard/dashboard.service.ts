import { Observable, pipe, Subject, timer } from "rxjs";
import { map, share, switchMap, takeUntil, tap } from "rxjs/operators";
import { Inject, Injectable, OnDestroy, PLATFORM_ID } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import type { AggregatedTicket, AggregatedTicketCollectionResponse } from "../../types/ticket";
import type { ConfigurationResponse } from "../../types/configuration";
import { CreateVendor, Vendor } from "../../types/vendor";
import { CreateCustomer, Customer } from "../../types/customer";
import { isPlatformServer } from "@angular/common";

@Injectable({ providedIn: "root" })
export class DashboardService implements OnDestroy {
	private stopPolling = new Subject<void>();
	private readonly aggregatedTickets$: Observable<AggregatedTicket[]>;

	constructor(@Inject(PLATFORM_ID) private platformId: Object, private http: HttpClient) {
		// if (isPlatformServer(this.platformId)) {
		// 	this.aggregatedTickets$ = this.http.get<AggregatedTicketCollectionResponse>("/tickets/aggregate")
		// 		.pipe(map((resp) => resp.data ?? []),
		// 			map((resp) => resp.map((x) => ({
		// 				...x,
		// 				id: x.id ?? Math.random().toString()
		// 			}))),);
		//
		// 	return;
		// }
		this.aggregatedTickets$ = timer(1, 3000).pipe(
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

	createVendor(): Observable<Vendor> {
		return this.http.post<Vendor>("/vendor", { name: "Testing " } as CreateVendor);
	}

	createCustomer(): Observable<Customer> {
		return this.http.post<Customer>("/customer", { name: "Testing" } as CreateCustomer);
	}

	ngOnDestroy() {
		this.stopPolling.next();
	}
}
