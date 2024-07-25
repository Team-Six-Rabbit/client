export const reorderCategories = (
	categories: { id: number; name: string }[],
) => {
	const allCategory = categories.find((category) => category.name === "전체");
	const otherCategories = categories.filter(
		(category) => category.name !== "전체",
	);
	return allCategory ? [allCategory, ...otherCategories] : categories;
};
