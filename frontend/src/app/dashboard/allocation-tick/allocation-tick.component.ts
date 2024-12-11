import { Component, Input } from "@angular/core";

@Component({
	selector: "app-allocation-tick",
	imports: [],
	template: `
		<div [attr.aria-checked]="isBooked"
			 [attr.aria-disabled]="!isCreated"
			 class="size-4 rounded-md border transition-colors_opacity border-neutral-600 bg-neutral-950 aria-checked:bg-cyan-600 aria-checked:border-cyan-400 aria-disabled:opacity-40"></div>
	`,
})
export class AllocationTickComponent {
	@Input() isBooked = false;
	@Input() isCreated = true;
}
