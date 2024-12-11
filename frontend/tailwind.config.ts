import type { Config } from "tailwindcss";
import { addIconSelectors } from "@iconify/tailwind";

const config: Config = {
	content: [
		"./src/**/*.{html,ts}"
	],
	theme: {
		container: {
			center: true,
		},
		extend: {
			fontFamily: {
				sans: ["Inter", "sans-serif"],
			},
			transitionDuration: {
				DEFAULT: "300ms",

				"emphasized": "500ms",
				"emphasized-decelerate": "400ms",
				"emphasized-accelerate": "200ms",

				"standard": "300ms",
				"standard-decelerate": "250ms",
				"standard-accelerate": "200ms",
			},
			transitionTimingFunction: {
				DEFAULT: "cubic-bezier(0.2, 0.0, 0, 1.0)",

				"emphasized-decelerate": "cubic-bezier(0.05, 0.7, 0.1, 1.0)",
				"emphasized-accelerate": "cubic-bezier(0.3, 0.0, 0.8, 0.15)",

				"standard": "cubic-bezier(0.2, 0.0, 0, 1.0)",
				"standard-decelerate": "cubic-bezier(0, 0, 0, 1)",
				"standard-accelerate": "cubic-bezier(0.3, 0, 1, 1)",
			},
			transitionProperty: {
				"colors_opacity":
					"color, background-color, border-color, text-decoration-color, fill, stroke, opacity",
			},
		},
	},
	plugins: [
		addIconSelectors([{ prefix: "ms", source: "material-symbols" }]),
	],
}

export default config;
