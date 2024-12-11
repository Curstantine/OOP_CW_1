import { Routes } from "@angular/router";

import { DashboardComponent } from "./dashboard/dashboard.component";
import { SettingsComponent } from "./settings/settings.component";

export const routes: Routes = [
	{
		path: "",
		pathMatch: "full",
		redirectTo: "dashboard",
	},
	{
		path: "dashboard",
		component: DashboardComponent,
	},
	{
		path: "settings",
		component: SettingsComponent,
	}
];
