import { useState, useEffect, useMemo } from "react";
import styled from "styled-components";
import LiveSlickCarousel from "@/components/Main/LiveSlickCarousel";
import Header from "@/components/header";
import { CardType } from "@/types/Board/liveBoardCard";
import { categories } from "@/constant/boardCategory";
import { liveBattleService } from "@/services/liveBattleService";

const PageContainer = styled.div`
	padding: 40px;
`;

const CategorySection = styled.div`
	margin-top: 40px;
`;

const SectionHeader = styled.div`
	margin-top: 60px;
	margin-bottom: 20px;
	font-size: 2em;
	text-align: left;
`;

const CategoryTitle = styled.div`
	font-size: 1.8em;
	padding-left: 20px;
`;

const LoadingMessage = styled.div`
	font-size: 1.2em;
	text-align: center;
	padding: 20px;
`;

function MainPage() {
	const [interestedCards, setInterestedCards] = useState<
		Record<number, CardType[]>
	>({});
	const [otherCards, setOtherCards] = useState<Record<number, CardType[]>>({});
	const [isLoading, setIsLoading] = useState<boolean>(true);

	// useMemo를 사용하여 userInterestedCategories를 메모이제이션합니다.
	const userInterestedCategories = useMemo(() => [1, 2, 6], []);

	useEffect(() => {
		const fetchLiveData = async () => {
			try {
				setIsLoading(true);

				// 각 카테고리의 데이터를 가져오기 위한 비동기 요청 배열을 생성합니다.
				const promises = categories
					.filter((category) => category.id !== 7) // id가 7인 카테고리를 제외합니다.
					.map(async (category) => {
						const response = await liveBattleService.getActiveList(category.id);
						const liveBattles = response.data || [];

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
							status: "live",
						}));

						return { categoryId: category.id, cards };
					});

				// 모든 비동기 요청을 동시에 처리합니다.
				const results = await Promise.all(promises);

				const interested: Record<number, CardType[]> = {};
				const others: Record<number, CardType[]> = {};

				results.forEach(({ categoryId, cards }) => {
					if (userInterestedCategories.includes(categoryId)) {
						interested[categoryId] = cards;
					} else {
						others[categoryId] = cards;
					}
				});

				setInterestedCards(interested);
				setOtherCards(others);
			} catch (error) {
				console.error("Failed to fetch live battles:", error);
			} finally {
				setIsLoading(false);
			}
		};

		fetchLiveData();
	}, [userInterestedCategories]);

	return (
		<>
			<Header />
			<PageContainer>
				{isLoading ? (
					<LoadingMessage>Loading...</LoadingMessage>
				) : (
					<>
						<SectionHeader>💘 회원님의 관심사에 맞춘 라이브 방송</SectionHeader>
						{Object.keys(interestedCards).map((categoryId) => (
							<CategorySection key={categoryId}>
								<CategoryTitle>
									#
									{
										categories.find(
											(cat) => cat.id === parseInt(categoryId, 10),
										)?.name
									}
								</CategoryTitle>
								<LiveSlickCarousel
									cards={interestedCards[parseInt(categoryId, 10)]}
								/>
							</CategorySection>
						))}
						<SectionHeader>🔍 다른 카테고리 탐색하기</SectionHeader>
						{Object.keys(otherCards).map((categoryId) => (
							<CategorySection key={categoryId}>
								<CategoryTitle>
									#
									{
										categories.find(
											(cat) => cat.id === parseInt(categoryId, 10),
										)?.name
									}
								</CategoryTitle>
								<LiveSlickCarousel
									cards={otherCards[parseInt(categoryId, 10)]}
								/>
							</CategorySection>
						))}
					</>
				)}
			</PageContainer>
		</>
	);
}

export default MainPage;
