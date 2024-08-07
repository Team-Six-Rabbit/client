import { useEffect, useState, useCallback } from "react";
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
		// 현재 스크롤 위치와 문서 전체 높이를 계산하여 하단에 근접했는지 체크
		if (
			window.innerHeight + document.documentElement.scrollTop + 200 >=
			document.documentElement.scrollHeight
		) {
			// 상태를 콘솔에 출력하여 확인
			console.log(`hasMore: ${hasMore}, isLoading: ${isLoading}`);

			// 더 많은 데이터를 불러올 수 있는 상태인지 확인
			if (hasMore && !isLoading) {
				console.log("Fetching more data...");
				setPage((prevPage) => prevPage + 1);
			}
		}
	}, [hasMore, isLoading]);

	// Fetch data when category, status, or page changes
	useEffect(() => {
		const fetchBalanceGames = async () => {
			if (isLoading || !hasMore) return; // 이미 로딩 중이거나 더 이상 데이터가 없으면 종료

			setIsLoading(true);

			try {
				const categoryIndex =
					selectedCategory === "전체"
						? undefined
						: categories.find((category) => category.name === selectedCategory)
								?.id;

				const status = selectedStatus === "live" ? 6 : 7;

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

				// 기존 카드 목록에 새로운 카드 추가
				setFilteredCards((prevCards) => [...prevCards, ...balanceGames]);

				// 요청한 것보다 적은 항목이 반환되면 더 이상 페이지가 없는 것으로 설정
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
					{isLoading && <div>Loading...</div>}
				</BalanceGameBoardContainer>
			</div>
			<PlusButton strokeColor="#000000" fillColor="#F66C23" />
		</div>
	);
}

export default BalanceGameBoardPage;
