import { Component, Input } from "@angular/core";

@Component({
	selector: "app-label-value-stat",
	imports: [],
	template: `
		<div class="inline-flex flex-col text-center items-center gap-1">
			<span class="text-lg text-neutral-400">{{ label }}</span>
			<span class="text-neutral-200">{{ value }}</span>
		</div>
	`
})
export class LabelValueStatComponent {
	@Input() label: string = "Label";
	@Input() value: string = "Value";
}
