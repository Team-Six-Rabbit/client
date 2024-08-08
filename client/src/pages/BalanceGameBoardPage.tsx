import { useEffect, useState, useCallback } from "react";
import styled from "styled-components";
import Header from "@/components/header";
import BoardHeader from "@/components/Board/BoardHeader";
import PlusButton from "@/components/Board/fanning/PlusButton";
import BalanceGameCard from "@/components/Board/bonfire/BalanceGameCard";
import { categories } from "@/constant/boardCategory";
import { LiveStatus } from "@/types/Board/liveStatus";
import { balanceGameService } from "@/services/balanceGameService";
import {
	BalanceGameCardType,
	OpinionType,
} from "@/types/Board/balancegameCard";
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
	const [page, setPage] = useState<number>(0);
	const [hasMore, setHasMore] = useState<boolean>(true);

	const handleCategorySelect = (category: string) => {
		setSelectedCategory(category);
		setPage(0);
		setFilteredCards([]);
		setHasMore(true);
	};

	const handleStatusSelect = (status: LiveStatus) => {
		setSelectedStatus(status);
		setPage(0);
		setFilteredCards([]);
		setHasMore(true);
	};

	const handleScroll = useCallback(() => {
		if (
			window.innerHeight + document.documentElement.scrollTop + 200 >=
			document.documentElement.scrollHeight
		) {
			console.log(`hasMore: ${hasMore}, isLoading: ${isLoading}`);

			if (hasMore && !isLoading) {
				console.log("Fetching more data...");
				setPage((prevPage) => prevPage + 1);
			}
		}
	}, [hasMore, isLoading]);

	useEffect(() => {
		const fetchBalanceGames = async () => {
			if (isLoading || !hasMore) return;

			setIsLoading(true);

			try {
				const categoryIndex =
					selectedCategory === "전체"
						? undefined
						: categories.find((category) => category.name === selectedCategory)
								?.id;

				const status = selectedStatus === "live" ? 5 : 6;

				const response: ApiResponse<BalanceGameResponse[]> =
					await balanceGameService.getBalanceGames(
						categoryIndex,
						status,
						page,
						15,
					);

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
							userVote: game.userVote ?? null,
						};
					}) || [];

				setFilteredCards((prevCards) => [...prevCards, ...balanceGames]);

				setHasMore(balanceGames.length === 10);
			} catch (error) {
				console.error("Failed to fetch balance games:", error);
			} finally {
				setIsLoading(false);
			}
		};

		fetchBalanceGames();
	}, [selectedCategory, selectedStatus, page, hasMore, isLoading]);

	useEffect(() => {
		window.addEventListener("scroll", handleScroll);
		return () => {
			window.removeEventListener("scroll", handleScroll);
		};
	}, [handleScroll]);

	const handleVote = (cardId: number, updatedOpinions: OpinionType[]) => {
		setFilteredCards((prevState) => {
			const newState = prevState.map((card) => {
				if (card.id === cardId) {
					const updatedCard = { ...card, opinions: updatedOpinions };

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
					<BoardCardContainer>
						{filteredCards.map((card) => {
							const isEnded = card.currentState === 6;
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
					{isLoading && <div>Loading...</div>}
				</BalanceGameBoardContainer>
			</div>
			<PlusButton strokeColor="#000000" fillColor="#F66C23" />
		</div>
	);
}

export default BalanceGameBoardPage;
