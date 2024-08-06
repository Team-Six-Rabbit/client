import { useEffect, useState } from "react";
import styled from "styled-components";
import Header from "@/components/header";
import BoardHeader from "@/components/Board/BoardHeader";
import PlusButton from "@/components/Board/fanning/PlusButton";
import BalanceGameCard from "@/components/Board/bonfire/BalanceGameCard";
import { categories } from "@/constant/boardCategory";
import { LiveStatus } from "@/types/Board/liveStatus";
import { balanceGameService } from "@/services/balanceGameService";
import { BalanceGameCardType } from "@/types/Board/balancegameCard";
import { ApiResponse, BalanceGameResponse } from "@/types/api";
import bonfireIcon from "@/assets/images/bonfire.gif";

const BalanceGameBoardContainer = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
	width: 100%;
`;

const BoardCardContainer = styled.div`
	display: grid;
	grid-template-columns: repeat(1, 1fr);
	gap: 10px;
	width: 100%;
	max-width: 1050px;

	@media (min-width: 640px) {
		grid-template-columns: repeat(2, 1fr);
	}
`;

function BalanceGameBoardPage() {
	const [selectedCategory, setSelectedCategory] = useState<string>("전체");
	const [selectedStatus, setSelectedStatus] = useState<LiveStatus>("live");
	const [filteredCards, setFilteredCards] = useState<BalanceGameCardType[]>([]);
	const [isLoading, setIsLoading] = useState<boolean>(false);

	const handleCategorySelect = (category: string) => {
		setSelectedCategory(category);
	};

	const handleStatusSelect = (status: LiveStatus) => {
		setSelectedStatus(status);
	};

	useEffect(() => {
		const fetchBalanceGames = async () => {
			try {
				setIsLoading(true);

				const categoryIndex = categories.findIndex(
					(category) => category.name === selectedCategory,
				);

				let response: ApiResponse<BalanceGameResponse[]>;
				switch (selectedStatus) {
					case "live":
						response = await balanceGameService.getBalanceGames(
							1,
							6,
							categoryIndex,
							10,
						);
						break;
					case "ended":
						response = await balanceGameService.getBalanceGames(
							1,
							7,
							categoryIndex,
							10,
						);
						break;
					default:
						return;
				}

				const balanceGames: BalanceGameCardType[] =
					response.data?.map((game) => {
						if (game.id === undefined) {
							throw new Error("Invalid data: id is undefined");
						}

						return {
							id: game.id,
							title: game.title,
							opinions: game.opinions,
							currentState: game.currentState,
							startDate: game.startDate,
							endDate: game.endDate,
							category: game.category,
							userVote: game.userVote ?? null, // userVote가 undefined일 수 있으므로 기본값 설정
						};
					}) || [];

				setFilteredCards(balanceGames);
			} catch (error) {
				console.error("Failed to fetch balance games:", error);
			} finally {
				setIsLoading(false);
			}
		};

		fetchBalanceGames();
	}, [selectedCategory, selectedStatus]);

	const handleVote = (cardId: number, option: number) => {
		setFilteredCards((prevState) => {
			const newState = prevState.map((card) => {
				if (card.id === cardId) {
					const updatedCard = { ...card };

					if (option === 0) {
						updatedCard.opinions[0].count += 1;
					} else {
						updatedCard.opinions[1].count += 1;
					}

					const newTotalVotes = updatedCard.opinions.reduce(
						(total, opinion) => total + opinion.count,
						0,
					);

					let percentage1 = Math.round(
						(updatedCard.opinions[0].count / newTotalVotes) * 100,
					);
					const percentage2 = Math.round(
						(updatedCard.opinions[1].count / newTotalVotes) * 100,
					);

					if (percentage1 + percentage2 !== 100) {
						percentage1 = 100 - percentage2;
					}

					updatedCard.opinions[0].percentage = percentage1;
					updatedCard.opinions[1].percentage = percentage2;

					return updatedCard;
				}
				return card;
			});
			return newState;
		});
	};

	return (
		<div>
			<Header />
			<div className="p-10 pt-20">
				<BoardHeader
					boardName="모닥불"
					categories={categories}
					selectedCategory={selectedCategory}
					onCategorySelect={handleCategorySelect}
					selectedStatus={selectedStatus}
					onStatusSelect={handleStatusSelect}
					boardIcon={bonfireIcon}
				/>
				<BalanceGameBoardContainer>
					{isLoading ? (
						<div>Loading...</div>
					) : (
						<BoardCardContainer>
							{filteredCards.map((card) => {
								const isEnded = card.currentState === 7; // Check currentState to determine if the game is ended
								return (
									<BalanceGameCard
										key={card.id}
										data={card}
										onVote={handleVote}
										disabled={selectedStatus === "ended"}
										isEnded={isEnded}
									/>
								);
							})}
						</BoardCardContainer>
					)}
				</BalanceGameBoardContainer>
			</div>
			<PlusButton strokeColor="#000000" fillColor="#F66C23" />
		</div>
	);
}

export default BalanceGameBoardPage;
