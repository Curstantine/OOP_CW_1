import { Component, Input } from "@angular/core";

@Component({
	selector: "app-button",
	imports: [],
	template: `
		<button
			class="inline-flex items-center justify-center gap-2 text-center h-8 rounded-md border border-neutral-600 text-neutral-200 text-sm px-4 transition-colors hover:bg-neutral-800">
			@if (iconClass !== undefined) {
				<div class="iconify size-5 {{iconClass}}"></div>
			}
			{{ label }}
		</button>`
})
export class ButtonComponent {
	@Input() label: string = "Label";
	@Input() iconClass?: string;

}
