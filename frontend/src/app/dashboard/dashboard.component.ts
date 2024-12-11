import { Component, OnInit, signal } from "@angular/core";
import { Observable } from "rxjs";
import { AsyncPipe, NgClass } from "@angular/common";
import { map } from "rxjs/operators";

import { DashboardService } from "./dashboard.service";
import { AllocationTickComponent } from "./allocation-tick/allocation-tick.component";
import { AggregatedTicket } from "../../types/ticket";
import { ButtonComponent } from "../button/button.component";
import { LabelValueStatComponent } from "./label-value-stat/label-value-stat.component";
import { Configuration } from "../../types/configuration";

@Component({
	selector: "app-dashboard",
	imports: [AsyncPipe, AllocationTickComponent, ButtonComponent, LabelValueStatComponent, NgClass],
	templateUrl: "./dashboard.component.html"
})
export class DashboardComponent implements OnInit {
	tickets$!: Observable<AggregatedTicket[]>;
	configuration$!: Observable<Configuration>;
	vendorCount$!: Observable<number>;
	customerCount$!: Observable<number>;

	showStop = signal(false);

	constructor(private service: DashboardService) {

	}

	ngOnInit() {
		this.tickets$ = this.service.getAggregatedTickets();
		this.configuration$ = this.service.getConfiguration().pipe(
			map((resp) => resp.data!)
		);
		this.vendorCount$ = this.service.getVendorCount().pipe(map((x) => x.data!));
		this.customerCount$ = this.service.getCustomerCount().pipe(map((x) => x.data!))
	}

	onAddVendor(): void {
		this.service.createVendor().subscribe();
	}

	onAddCustomer(): void {
		this.service.createCustomer().subscribe();
	}

	onStartStop() {
		if (this.showStop()) this.service.endAllVendors();
		else this.service.startAllVendors();

		this.showStop.set(!this.showStop());
	}
}
