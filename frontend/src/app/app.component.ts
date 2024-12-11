import { Component } from "@angular/core";
import { RouterLink, RouterLinkActive, RouterOutlet } from "@angular/router";

@Component({
	selector: "app-root",
	imports: [RouterOutlet, RouterLink, RouterLinkActive],
	templateUrl: "./app.component.html",
	styleUrl: "./app.component.css"
})
export class AppComponent {
	destinations: { href: string; label: string; iconClass: string }[] = [
		{ href: "/", label: "Dashboard", iconClass: "ms--space-dashboard-rounded" },
		{ href: "/settings", label: "Settings", iconClass: "ms--settings" },
	];
}
