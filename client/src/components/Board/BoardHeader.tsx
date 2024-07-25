import styled from "styled-components";
import fireIcon from "@/assets/images/Fire.png";
import NintendoCarousel from "@/components/Board/firework/NintendoCarousel";
import RadioButtonGroup from "@/components/Board/firework/RadioButtonGroup";
import BoardCategory from "@/components/Board/BoardCategory";

interface BoardHeaderProps {
	boardName: string;
	categories: { id: number; name: string }[];
	selectedCategory: string;
	onCategorySelect: (category: string) => void;
	selectedStatus: "live" | "upcoming" | "ended";
	onStatusSelect: (status: "live" | "upcoming" | "ended") => void;
	boardIcon?: string;
}

const BoardHeaderContainer = styled.div`
	display: flex;
	flex-direction: column;
	width: 100%;
	gap: 1rem;
`;

const HeaderTop = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
	gap: 1rem;
`;

const HeaderTitle = styled.h1`
	font-size: 3rem;
	white-space: nowrap;
`;

const HeaderIcon = styled.img`
	height: 3.5rem;
`;

const HeaderCategoryContainer = styled.div`
	flex-grow: 1;
`;

const CarouselContainer = styled.div`
	display: flex;
	justify-content: center;
	margin-bottom: 2rem;
`;

const CategoryTitle = styled.h2`
	font-size: 2rem;
	margin-top: 1rem;
`;

function BoardHeader({
	boardName,
	categories,
	selectedCategory,
	onCategorySelect,
	selectedStatus,
	onStatusSelect,
	boardIcon,
}: BoardHeaderProps) {
	const getStatuses = (boardName: string) => {
		if (boardName === "불구경") {
			return ["live", "upcoming", "ended"];
		}
		if (boardName === "모닥불") {
			return ["live", "ended"];
		}
		return [];
	};

	return (
		<BoardHeaderContainer>
			<HeaderTop>
				<div className="flex items-center space-x-3 flex-shrink-0">
					<HeaderTitle>{boardName}</HeaderTitle>
					<HeaderIcon src={boardIcon} alt="Board Icon" />
				</div>
				<HeaderCategoryContainer>
					<BoardCategory
						categories={categories}
						onCategorySelect={onCategorySelect}
						selectedCategory={selectedCategory}
					/>
				</HeaderCategoryContainer>
			</HeaderTop>
			{boardName === "불구경" && selectedStatus === "live" && (
				<CarouselContainer>
					<NintendoCarousel />
				</CarouselContainer>
			)}
			<div className="flex items-center justify-between mt-4">
				<CategoryTitle>#{selectedCategory}</CategoryTitle>
				{boardName !== "부채질" && (
					<RadioButtonGroup
						statuses={
							getStatuses(boardName) as ("live" | "upcoming" | "ended")[]
						}
						selectedStatus={selectedStatus}
						onStatusSelect={onStatusSelect}
					/>
				)}
			</div>
		</BoardHeaderContainer>
	);
}

BoardHeader.defaultProps = {
	boardIcon: fireIcon,
};

export default BoardHeader;
