import { Component } from "@angular/core";
import { RouterLink, RouterLinkActive } from "@angular/router";

@Component({
	selector: "app-nav",
	imports: [
		RouterLink,
		RouterLinkActive
	],
	templateUrl: "./nav.component.html",
})
export class NavComponent {
	destinations: { href: string; label: string; iconClass: string }[] = [
		{ href: "/dashboard", label: "Dashboard", iconClass: "ms--space-dashboard-rounded" },
		// { href: "/settings", label: "Settings", iconClass: "ms--settings" },
	];
}
