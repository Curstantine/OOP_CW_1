import { Component, OnInit } from "@angular/core";
import { Observable } from "rxjs";
import { AsyncPipe } from "@angular/common";

import { DashboardService } from "./dashboard.service";
import { AllocationTickComponent } from "./allocation-tick/allocation-tick.component";
import { AggregatedTicket } from "../../types/ticket";
import { ButtonComponent } from "../button/button.component";

@Component({
	selector: "app-dashboard",
	imports: [AsyncPipe, AllocationTickComponent, ButtonComponent],
	templateUrl: "./dashboard.component.html"
})

export class DashboardComponent implements OnInit {
	tickets$: Observable<AggregatedTicket[]>;

	constructor(private service: DashboardService) {
		this.tickets$ = this.service.getAggregatedTickets();
		this.tickets$.subscribe((data) => console.log("Dashboard tickets data:", data));
	}

	ngOnInit() {
	}

	onAddVendor(): void {
		this.service.createVendor().subscribe((data) => {
			console.log("Vendor created", data);
		});
	}

	onAddCustomer(): void {
		this.service.createCustomer().subscribe((data) => {
			console.log("Customer created", data);
		});
	}

}
