import { useState, useEffect } from "react";
import styled from "styled-components";
import Header from "@/components/header";
import BoardHeader from "@/components/Board/BoardHeader";
import Ticket from "@/components/Board/fanning/Ticket";
import WindIcon from "@/assets/images/Wind.gif";
import PlusButton from "@/components/Board/fanning/PlusButton";
import { TicketType } from "@/types/Board/ticket";
import { categories } from "@/constant/boardCategory";
import { convertToTimeZone } from "@/utils/dateUtils";
import { battleService } from "@/services/battleService";
import { BattleWaitingParticipant } from "@/types/battle";
import { Opinion } from "@/types/vote";

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
	const [isLoading, setIsLoading] = useState<boolean>(false);

	const handleCategorySelect = (category: string) => {
		setSelectedCategory(category);
	};

	useEffect(() => {
		const fetchBattles = async () => {
			try {
				setIsLoading(true);

				const categoryIndex = categories.findIndex(
					(category) => category.name === selectedCategory,
				);

				const response = await battleService.getApplyList(categoryIndex);
				const battles: BattleWaitingParticipant[] = response.data!;
				console.log(battles);

				const tickets: TicketType[] = battles.map((battle) => {
					return {
						id: battle.id,
						title: battle.title,
						opinions: battle.opinions.map((opinion: Opinion) => ({
							index: opinion.index,
							opinion: opinion.opinion,
						})),
						startDate: convertToTimeZone(battle.startDate, "Asia/Seoul"),
						endDate: convertToTimeZone(battle.endDate, "Asia/Seoul"),
						maxPeopleCount: battle.maxPeopleCount,
						currentPeopleCount: battle.currentPeopleCount,
						isVoted: battle.isVoted,
					};
				});

				setFilteredTickets(tickets);
			} catch (error) {
				console.error("Failed to fetch battles:", error);
			} finally {
				setIsLoading(false);
			}
		};

		fetchBattles();
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
					{isLoading ? (
						<div>Loading...</div>
					) : (
						<BoardTicketContainer>
							{filteredTickets.map((ticket, index) => (
								<Ticket
									key={ticket.id}
									ticket={ticket}
									theme={getTheme(index)}
								/>
							))}
						</BoardTicketContainer>
					)}
				</PreVotingBoardContainer>
			</div>
			<PlusButton />
		</div>
	);
}

export default PreVotingBoardPage;
