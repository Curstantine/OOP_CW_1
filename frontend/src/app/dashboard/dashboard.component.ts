import { Component, OnInit, signal } from "@angular/core";
import { Observable } from "rxjs";
import { AsyncPipe, NgClass } from "@angular/common";
import { map } from "rxjs/operators";

import { DashboardService } from "./dashboard.service";
import { AllocationTickComponent } from "./allocation-tick/allocation-tick.component";
import { AggregatedTicket, TicketCountResponse } from "../../types/ticket";
import { ButtonComponent } from "../button/button.component";
import { LabelValueStatComponent } from "./label-value-stat/label-value-stat.component";
import { Configuration } from "../../types/configuration";
import { VendorCountResponse } from "../../types/vendor";
import { CustomerCountResponse } from "../../types/customer";

@Component({
	selector: "app-dashboard",
	imports: [AsyncPipe, AllocationTickComponent, ButtonComponent, LabelValueStatComponent, NgClass],
	templateUrl: "./dashboard.component.html"
})
export class DashboardComponent implements OnInit {
	tickets$!: Observable<AggregatedTicket[]>;
	configuration$!: Observable<Configuration>;
	ticketCount$!: Observable<TicketCountResponse>;
	vendorCount$!: Observable<VendorCountResponse>;
	customerCount$!: Observable<CustomerCountResponse>;

	showStop = signal(false);

	constructor(private service: DashboardService) {
	}

	ngOnInit() {
		this.tickets$ = this.service.getAggregatedTickets();
		this.configuration$ = this.service.getConfiguration().pipe(map((resp) => resp.data!));
		this.ticketCount$ = this.service.getTicketCount();
		this.vendorCount$ = this.service.getVendorCount();
		this.customerCount$ = this.service.getCustomerCount();
		this.service.isAtLeastOneRunning()
			.pipe(map((x) => x.data!))
			.subscribe((data) => this.showStop.set(data));
	}

	onAddVendor(): void {
		this.service.createVendor().subscribe();
	}

	onAddCustomer(): void {
		this.service.createCustomer().subscribe();
	}

	onStartStop() {
		if (this.showStop()) this.service.endAllVendors().subscribe();
		else this.service.startAllVendors().subscribe();

		this.showStop.set(!this.showStop());
	}
}
