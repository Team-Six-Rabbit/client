/** @type {import('tailwindcss').Config} */
export default {
	content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
	theme: {
		extend: {
			colors: {
				orange: "#F66C23",
				blue: "#0B68EC",
				yellow: "#FBCA27",
				olive: "#B4CC38",
			},
			scale: {
				"120": "1.2",
			},
			height: {
				"116": "28rem", // ?px
				"128": "32rem", // 512px
				"144": "36rem", // 576px
				"160": "40rem", // 640px
				"192": "48rem", // 768px
				"224": "56rem", // 896px
				"8/9": "88.888889%",
				"15/16": "93.75%",
			},
			clipPath: {
				trapezoid: "polygon(25% 0%, 75% 0%, 100% 100%, 0% 100%)",
			},
		},
	},
	plugins: [require("tailwind-scrollbar-hide")],
};
