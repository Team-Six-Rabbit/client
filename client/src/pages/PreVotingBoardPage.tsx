import { useState, useEffect } from "react";
import styled from "styled-components";
import Header from "@/components/header";
import BoardHeader from "@/components/Board/BoardHeader";
import Ticket from "@/components/Board/fanning/Ticket";
import WindIcon from "@/assets/images/Wind.png";
import PlusButton from "@/components/Board/fanning/PlusButton";
import { TicketType } from "@/types/Board/ticket";
import { categories } from "@/constant/boardCategory";
import { sampleTickets } from "@/constant/exampleTicket";
import { convertToTimeZone } from "@/utils/dateUtils";

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
								ticket={{
									...ticket,
									startDate: convertToTimeZone(ticket.startDate, "Asia/Seoul"),
									endDate: convertToTimeZone(ticket.endDate, "Asia/Seoul"),
								}}
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
