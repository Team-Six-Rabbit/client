import { useState, useEffect } from "react";
import Header from "@/components/header";
import BoardHeader from "@/components/Board/BoardHeader";
import LiveCard from "@/components/Board/firework/LiveCard";
import { CardType } from "@/types/Board/liveBoardCard";
import { categories, sampleCards } from "@/constant/boardCategory";

function LiveBoardPage() {
	const [selectedCategory, setSelectedCategory] = useState<string>("전체");
	const [selectedStatus, setSelectedStatus] = useState<
		"live" | "upcoming" | "ended"
	>("live");
	const [filteredCards, setFilteredCards] = useState<CardType[]>([]);

	const handleCategorySelect = (category: string) => {
		setSelectedCategory(category);
	};

	const handleStatusSelect = (status: "live" | "upcoming" | "ended") => {
		setSelectedStatus(status);
	};

	useEffect(() => {
		setFilteredCards(
			sampleCards.filter((card) => card.status === selectedStatus),
		);
	}, [selectedStatus]);

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
							index={card.index}
						/>
					))}
				</div>
			</div>
		</div>
	);
}

export default LiveBoardPage;
