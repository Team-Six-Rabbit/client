import { useEffect, useState } from "react";
import styled from "styled-components";
import "@/assets/styles/scrollbar.css";
import { authService } from "@/services/userAuthService";
import { convertToTimeZone } from "@/utils/dateUtils";
import EndedLivePreviewModal from "@/components/Modal/EndedLivePreviewModal";

const CreatedLivesContainer = styled.div`
	width: 100%;
	height: 300px;
	padding: 20px;
	overflow-y: auto;
`;

const CreatedLivesList = styled.ul`
	list-style: none;
	padding: 0;
	margin: 0;
`;

const CreatedLivesListItem = styled.li`
	display: flex;
	align-items: center;
	justify-content: space-between;
	border-radius: 30px;
	border: 3px solid #e0e0e0;
	padding: 5px 20px;
	margin-bottom: 10px;
	font-size: 16px;
	cursor: pointer; /* 클릭 가능하게 설정 */
`;

const Date = styled.span`
	font-size: 14px;
	color: #333;
`;

const StatusCircle = styled.span<{ statusColor: string }>`
	width: 20px;
	height: 20px;
	border-radius: 50%;
	background-color: ${({ statusColor }) => statusColor};
`;

const TitleContainer = styled.div`
	flex-grow: 1;
	text-align: left;
`;

const DateStatusContainer = styled.div`
	display: flex;
	align-items: center;
	justify-content: flex-end;
	gap: 10px; /* 날짜와 상태 아이콘 사이의 간격 */
`;

interface CreatedLive {
	battleBoardId: number;
	title: string;
	date: string;
	statusColor: string;
}

function CreatedLives() {
	const [lives, setLives] = useState<CreatedLive[]>([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState<string | null>(null);
	const [selectedBattleId, setSelectedBattleId] = useState<number | null>(null);

	useEffect(() => {
		const fetchLives = async () => {
			try {
				const data = await authService.getUserCreatedLives();
				const formattedLives = data.map((live) => {
					const formattedDate = convertToTimeZone(
						live.registDate,
						"Asia/Seoul",
					);

					const dateOnly = formattedDate.split(" ")[0]; // 'YYYY-MM-DD'

					return {
						battleBoardId: live.battleBoardId,
						title: live.title,
						date: `${dateOnly}`, // 'YYYY-MM-DD HH:MM'
						statusColor: live.isWin ? "#BDE3FF" : "#FFC7C2",
					};
				});
				setLives(formattedLives);
			} catch (err) {
				setError("Failed to fetch created lives");
			} finally {
				setLoading(false);
			}
		};

		fetchLives();
	}, []);

	const handleTitleClick = (battleId: number) => {
		setSelectedBattleId(battleId);
	};

	const closeModal = () => {
		setSelectedBattleId(null);
	};

	if (loading) {
		return <div>Loading...</div>;
	}

	if (error) {
		return <div>{error}</div>;
	}

	return (
		<CreatedLivesContainer className="custom-scrollbar">
			<CreatedLivesList>
				{lives.map((live) => (
					<CreatedLivesListItem
						key={live.battleBoardId}
						onClick={() => handleTitleClick(live.battleBoardId)}
					>
						<TitleContainer>{live.title}</TitleContainer>
						<DateStatusContainer>
							<Date>{live.date}</Date>
							<StatusCircle statusColor={live.statusColor} />
						</DateStatusContainer>
					</CreatedLivesListItem>
				))}
			</CreatedLivesList>

			{selectedBattleId !== null && (
				<EndedLivePreviewModal
					battleId={selectedBattleId}
					onClose={closeModal}
				/>
			)}
		</CreatedLivesContainer>
	);
}

export default CreatedLives;
