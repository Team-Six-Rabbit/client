export const formatDate = (dateString: string): string => {
	const options: Intl.DateTimeFormatOptions = {
		year: "numeric",
		month: "2-digit",
		day: "2-digit",
		hour: "2-digit",
		minute: "2-digit",
		hour12: false,
	};
	const date = new Date(dateString);
	return date
		.toLocaleString("ko-KR", options)
		.replace(/\. /g, "/")
		.replace(". ", " ");
};
