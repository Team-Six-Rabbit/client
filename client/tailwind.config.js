/** @type {import('tailwindcss').Config} */
export default {
	content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
	theme: {
		extend: {
			colors: {
				orange: '#F66C23',
				blue: '#0B68EC',
				yellow: '#FBCA27',
				olive: '#B4CC38',
			},
			scale: {
        '120': '1.2',
      },
		},
	},
	plugins: [],
};
