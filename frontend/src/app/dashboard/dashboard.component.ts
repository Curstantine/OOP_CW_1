import { Component, OnInit } from "@angular/core";
import { map, Observable } from "rxjs";
import { AsyncPipe } from "@angular/common";

import { DashboardService } from "./dashboard.service";
import { AllocationTickComponent } from "./allocation-tick/allocation-tick.component";
import { AggregatedTicket } from "../../types/ticket";

@Component({
	selector: "app-dashboard",
	imports: [AsyncPipe, AllocationTickComponent],
	templateUrl: "./dashboard.component.html"
})

export class DashboardComponent implements OnInit {
	tickets$!: Observable<AggregatedTicket[]>;

	constructor(private service: DashboardService) {
	}

	ngOnInit(): void {
		this.tickets$ = this.service.getAggregated().pipe(
			map((resp) => resp.data ?? [])
		)
	}
}
