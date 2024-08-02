import { useState, useEffect } from "react";
import Header from "@/components/header";
import BoardHeader from "@/components/Board/BoardHeader";
import LiveCard from "@/components/Board/firework/LiveCard";
import { CardType } from "@/types/Board/liveBoardCard";
import { LiveStatus } from "@/types/Board/liveStatus";
import { categories } from "@/constant/boardCategory";
import { liveBattleService } from "@/services/liveBattleService";
import { LiveBattleCardInfo } from "@/types/live";

function LiveBoardPage() {
	const [selectedCategory, setSelectedCategory] = useState<string>("전체");
	const [selectedStatus, setSelectedStatus] = useState<LiveStatus>("live");
	const [filteredCards, setFilteredCards] = useState<CardType[]>([]);
	const [isLoading, setIsLoading] = useState<boolean>(false);

	const handleCategorySelect = (category: string) => {
		setSelectedCategory(category);
	};

	const handleStatusSelect = (status: LiveStatus) => {
		setSelectedStatus(status);
	};

	useEffect(() => {
		const fetchLiveBattles = async () => {
			try {
				setIsLoading(true);

				const categoryIndex = categories.findIndex(
					(category) => category.name === selectedCategory,
				);

				let response;
				switch (selectedStatus) {
					case "live":
						response = await liveBattleService.getActiveList(categoryIndex);
						break;
					case "upcoming":
						response = await liveBattleService.getWaitList(categoryIndex);
						break;
					case "ended":
						response = await liveBattleService.getEndList(categoryIndex);
						break;
					default:
						return;
				}

				const liveBattles: LiveBattleCardInfo[] = response.data || [];

				const cards: CardType[] = liveBattles.map((battle) => ({
					id: battle.id,
					title: battle.title,
					regist_user_id: battle.registerUser.id.toString(),
					opposite_user_id: battle.oppositeUser.id.toString(),
					start_date: battle.startDate,
					end_date: battle.endDate,
					max_people_count: battle.currentPeopleCount || 0,
					live_apply_user_count: 0,
					category: battle.category,
					image_uri: battle.imageUri || "",
					live_uri: battle.roomId,
					status: selectedStatus,
				}));
				setFilteredCards(cards);
			} catch (error) {
				console.error("Failed to fetch live battles:", error);
			} finally {
				setIsLoading(false);
			}
		};

		fetchLiveBattles();
	}, [selectedCategory, selectedStatus]);

	return (
		<div>
			<Header />
			<div className="p-10 pt-20">
				<BoardHeader
					boardName="불구경"
					categories={categories}
					selectedCategory={selectedCategory}
					onCategorySelect={handleCategorySelect}
					selectedStatus={selectedStatus}
					onStatusSelect={handleStatusSelect}
				/>
				{isLoading ? (
					<div>Loading...</div>
				) : (
					<div className="mt-4 grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-6">
						{filteredCards.map((card) => (
							<LiveCard
								key={card.id}
								id={card.id}
								title={card.title}
								regist_user_id={card.regist_user_id}
								opposite_user_id={card.opposite_user_id}
								start_date={card.start_date}
								end_date={card.end_date}
								max_people_count={card.max_people_count}
								live_apply_user_count={card.live_apply_user_count}
								category={card.category}
								image_uri={card.image_uri}
								live_uri={card.live_uri}
								status={card.status}
							/>
						))}
					</div>
				)}
			</div>
		</div>
	);
}

export default LiveBoardPage;
