import { useState, useEffect } from "react";
import styled from "styled-components";
import Header from "@/components/header";
import BoardHeader from "@/components/Board/BoardHeader";
import Ticket from "@/components/Board/fanning/Ticket";
import WindIcon from "@/assets/images/Wind.png";
import PlusButton from "@/components/Board/fanning/PlusButton";
import { categories } from "@/constant/boardCategory";

interface TicketType {
	id: string;
	title: string;
	opinion1: string;
	opinion2: string;
	date: string;
	attendees: number;
	maxAttendees: number;
}

const PreVotingBoardContainer = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 30px 0;
	width: 100%;
`;

const BoardTicketContainer = styled.div`
	display: grid;
	grid-template-columns: repeat(1, 1fr);
	gap: 10px;
	width: 100%;
	max-width: 1200px;

	@media (min-width: 640px) {
		grid-template-columns: repeat(2, 1fr);
	}
`;

function PreVotingBoardPage() {
	const [selectedCategory, setSelectedCategory] = useState<string>("전체");
	const [filteredTickets, setFilteredTickets] = useState<TicketType[]>([]);

	const handleCategorySelect = (category: string) => {
		setSelectedCategory(category);
	};

	useEffect(() => {
		const sampleTickets: TicketType[] = [
			{
				id: "1",
				title: "AI의 미래",
				opinion1: "AI가 직업 대체",
				opinion2: "AI는 협력",
				date: "2024.07.12 13:00 - 14:00",
				attendees: 16,
				maxAttendees: 50,
			},
			{
				id: "2",
				title: "스마트폰 중독",
				opinion1: "사용 시간 줄여야 함",
				opinion2: "문제가 아니다",
				date: "2024.07.12 14:00 - 15:00",
				attendees: 20,
				maxAttendees: 50,
			},
			{
				id: "3",
				title: "채식주의 건강",
				opinion1: "건강에 좋다",
				opinion2: "균형 식단이 중요",
				date: "2024.07.13 13:00 - 14:00",
				attendees: 10,
				maxAttendees: 50,
			},
			{
				id: "4",
				title: "원격 근무",
				opinion1: "생산성 높인다",
				opinion2: "사무실이 효과적",
				date: "2024.07.12 13:00 - 14:00",
				attendees: 16,
				maxAttendees: 50,
			},
			{
				id: "5",
				title: "인터넷 검열",
				opinion1: "필요하다",
				opinion2: "자유로워야 함",
				date: "2024.07.12 14:00 - 15:00",
				attendees: 20,
				maxAttendees: 50,
			},
			{
				id: "6",
				title: "전기차 vs 내연차",
				opinion1: "전기차가 대세",
				opinion2: "내연차가 우수",
				date: "2024.07.13 13:00 - 14:00",
				attendees: 10,
				maxAttendees: 50,
			},
		];

		setFilteredTickets(sampleTickets);
	}, [selectedCategory]);

	const getTheme = (index: number) => {
		return index === 1 || index === 2 || (index >= 5 && (index - 1) % 4 < 2)
			? "yellow"
			: "navy";
	};

	return (
		<div>
			<Header />
			<div className="p-10 pt-20">
				<BoardHeader
					boardName="부채질"
					categories={categories}
					selectedCategory={selectedCategory}
					onCategorySelect={handleCategorySelect}
					boardIcon={WindIcon}
				/>
				<PreVotingBoardContainer>
					<BoardTicketContainer>
						{filteredTickets.map((ticket, index) => (
							<Ticket
								key={ticket.id}
								title={ticket.title}
								opinion1={ticket.opinion1}
								opinion2={ticket.opinion2}
								date={ticket.date}
								attendees={ticket.attendees}
								maxAttendees={ticket.maxAttendees}
								theme={getTheme(index)}
							/>
						))}
					</BoardTicketContainer>
				</PreVotingBoardContainer>
			</div>
			<PlusButton />
		</div>
	);
}

export default PreVotingBoardPage;
