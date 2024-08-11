import styled from "styled-components";
import "@/assets/styles/scrollbar.css";

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
	const sampleLives: CreatedLive[] = [
		{
			id: "1",
			title: "내가 개최한 토론의 제목 1",
			date: "2024.07.12",
			statusColor: "#BDE3FF",
		},
		{
			id: "2",
			title: "내가 개최한 토론의 제목 2",
			date: "2024.07.13",
			statusColor: "#FFC7C2",
		},
		{
			id: "3",
			title: "내가 개최한 토론의 제목 3",
			date: "2024.07.14",
			statusColor: "#FFC7C2",
		},
		{
			id: "4",
			title: "내가 개최한 토론의 제목 4",
			date: "2024.07.15",
			statusColor: "#FFC7C2",
		},
		{
			id: "5",
			title: "내가 개최한 토론의 제목 5",
			date: "2024.07.16",
			statusColor: "#BDE3FF",
		},
		{
			id: "6",
			title: "내가 개최한 토론의 제목 6",
			date: "2024.07.17",
			statusColor: "#FFC7C2",
		},
		{
			id: "7",
			title: "내가 개최한 토론의 제목 7",
			date: "2024.07.18",
			statusColor: "#FFC7C2",
		},
		{
			id: "8",
			title: "내가 개최한 토론의 제목 8",
			date: "2024.07.19",
			statusColor: "#BDE3FF",
		},
	];

	return (
		<CreatedLivesContainer className="custom-scrollbar">
			<CreatedLivesList>
				{sampleLives.map((live) => (
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
