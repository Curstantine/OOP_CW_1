import { Component, Input } from "@angular/core";

@Component({
	selector: "app-label-value-stat",
	imports: [],
	template: `
		<div class="inline-flex flex-col text-center items-center">
			<span class="text-neutral-400 font-medium">{{ label }}</span>
			<span class="text-neutral-200 text-lg">{{ value }}</span>
		</div>
	`
})
export class LabelValueStatComponent {
	@Input() label: string = "Label";
	@Input() value: string = "Value";
}
