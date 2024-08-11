import { useEffect, useState } from "react";
import styled from "styled-components";
import "@/assets/styles/scrollbar.css";
import { authService } from "@/services/userAuthService";
import { convertToTimeZone } from "@/utils/dateUtils";
import formatTime from "@/utils/formatTime";

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

interface CreatedLive {
	id: string;
	title: string;
	date: string;
	statusColor: string;
}

function CreatedLives() {
	const [lives, setLives] = useState<CreatedLive[]>([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState<string | null>(null);

	useEffect(() => {
		const fetchLives = async () => {
			try {
				const data = await authService.getUserCreatedLives();
				const formattedLives = data.map((live, index) => {
					// 특정 시간대로 변환 (예: 'Asia/Seoul')
					const formattedDate = convertToTimeZone(
						live.registDate,
						"Asia/Seoul",
					);

					// 시간 부분을 초로 변환한 후 포맷팅
					const dateTimeParts = formattedDate.split(" ");
					const timeParts = dateTimeParts[1].split(":");
					const totalSeconds =
						parseInt(timeParts[0], 10) * 3600 + parseInt(timeParts[1], 10) * 60;
					const formattedTime = formatTime(totalSeconds);

					return {
						id: String(index + 1),
						title: live.title,
						date: `${dateTimeParts[0]} ${formattedTime}`, // 'YYYY-MM-DD HH:MM'
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
					<CreatedLivesListItem key={live.id}>
						<span>{live.title}</span>
						<Date>{live.date}</Date>
						<StatusCircle statusColor={live.statusColor} />
					</CreatedLivesListItem>
				))}
			</CreatedLivesList>
		</CreatedLivesContainer>
	);
}

export default CreatedLives;
