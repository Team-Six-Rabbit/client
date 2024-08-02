import React from "react";
import ReactDOM from "react-dom/client";

import { RouterProvider } from "react-router-dom";

import router from "@/routes/router";

import "./index.css";

if (import.meta.env.MODE === "mock") {
	import("./mocks/browser").then(({ worker }) => {
		worker.start();
	});
}

ReactDOM.createRoot(document.getElementById("root")!).render(
	<React.StrictMode>
		<RouterProvider router={router} />
	</React.StrictMode>,
);
