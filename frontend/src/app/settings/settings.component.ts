import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { NgClass, NgIf } from "@angular/common";

@Component({
	selector: "app-settings",
	templateUrl: "./settings.component.html",
	imports: [
		ReactiveFormsModule,
		NgClass,
		NgIf
	]
})
export class SettingsComponent implements OnInit {
	settingsForm!: FormGroup;

	constructor(private fb: FormBuilder) {
	}

	ngOnInit() {
		this.initForm();
		this.loadCurrentSettings();
	}

	initForm() {
		this.settingsForm = this.fb.group({
			maxTicketCapacity: [null, [Validators.required, Validators.min(1)]],
			totalTickets: [{ value: null, disabled: true }],
			ticketReleaseRate: [null, [Validators.required, Validators.min(100)]],
			customerRetrievalRate: [null, [Validators.required, Validators.min(100)]]
		});
	}

	loadCurrentSettings() {
		// TODO: Implement method to load current settings from a service
		// This is a placeholder. Replace with actual service call
		this.settingsForm.patchValue({
			maxTicketCapacity: 100,
			totalTickets: 0,
			ticketReleaseRate: 1000,
			customerRetrievalRate: 500
		});
	}

	onSubmit() {
		if (this.settingsForm.valid) {
			const settings = this.settingsForm.getRawValue();
			// TODO: Implement method to save settings via a service
			console.log("Saving settings:", settings);
		}
	}
}
