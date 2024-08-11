import styled from "styled-components";
import "@/assets/styles/scrollbar.css";

const VotesContainer = styled.div`
	width: 100%;
	height: 300px;
	padding: 20px;
	overflow-y: auto;
`;

const VotesList = styled.ul`
	list-style: none;
	padding: 0;
	margin: 0;
`;

const VotesListItem = styled.li`
	display: flex;
	align-items: center;
	justify-content: space-between;
	border-radius: 30px;
	border: 3px solid #e0e0e0;
	padding: 5px 20px;
	margin-bottom: 10px;
	font-size: 16px;
`;

const VoteDate = styled.span`
	font-size: 14px;
	color: #333;
`;

const VoteStatusIndicator = styled.span<{ statusColor: string }>`
	width: 20px;
	height: 20px;
	border-radius: 50%;
	background-color: ${({ statusColor }) => statusColor};
`;

interface Vote {
	id: string;
	title: string;
	date: string;
	statusColor: string;
}

function ParticipatedVotesList() {
	const sampleVotes: Vote[] = [
		{
			id: "1",
			title: "참여한 토론 제목 1",
			date: "2024.07.12",
			statusColor: "#BDE3FF",
		},
		{
			id: "2",
			title: "참여한 토론 제목 2",
			date: "2024.07.13",
			statusColor: "#FFC7C2",
		},
		{
			id: "3",
			title: "참여한 토론 제목 3",
			date: "2024.07.14",
			statusColor: "#FFC7C2",
		},
		{
			id: "4",
			title: "참여한 토론 제목 4",
			date: "2024.07.15",
			statusColor: "#FFC7C2",
		},
		{
			id: "5",
			title: "참여한 토론 제목 5",
			date: "2024.07.16",
			statusColor: "#BDE3FF",
		},
		{
			id: "6",
			title: "참여한 토론 제목 6",
			date: "2024.07.17",
			statusColor: "#FFC7C2",
		},
		{
			id: "7",
			title: "참여한 토론 제목 7",
			date: "2024.07.18",
			statusColor: "#FFC7C2",
		},
		{
			id: "8",
			title: "참여한 토론 제목 8",
			date: "2024.07.19",
			statusColor: "#BDE3FF",
		},
	];

	return (
		<VotesContainer className="custom-scrollbar">
			<VotesList>
				{sampleVotes.map((vote) => (
					<VotesListItem key={vote.id}>
						<span>{vote.title}</span>
						<VoteDate>{vote.date}</VoteDate>
						<VoteStatusIndicator statusColor={vote.statusColor} />
					</VotesListItem>
				))}
			</VotesList>
		</VotesContainer>
	);
}

export default ParticipatedVotesList;
