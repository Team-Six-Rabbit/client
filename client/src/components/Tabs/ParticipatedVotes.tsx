import { useEffect, useState } from "react";
import styled from "styled-components";
import "@/assets/styles/scrollbar.css";
import { authService } from "@/services/userAuthService";
import { convertToTimeZone } from "@/utils/dateUtils";
import ParticipatedVotesModal from "@/components/Modal/ParticipatedVotesModal";

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
	cursor: pointer;
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

interface Vote {
	id: number;
	title: string;
	date: string;
	statusColor: string;
}

function ParticipatedVotesList() {
	const [votes, setVotes] = useState<Vote[]>([]);
	const [selectedVoteId, setSelectedVoteId] = useState<number | null>(null);
	const [, setLoading] = useState(true);
	const [, setError] = useState<string | null>(null);

	useEffect(() => {
		const fetchVotes = async () => {
			try {
				const data = await authService.getUserVotes(); // API 호출
				const formattedVotes = data.map((vote) => {
					const formattedDate = convertToTimeZone(
						vote.registDate,
						"Asia/Seoul",
					);

					const dateOnly = formattedDate.split(" ")[0]; // 'YYYY-MM-DD'

					return {
						id: vote.id,
						title: vote.title,
						date: `${dateOnly}`, // 날짜를 로컬 형식으로 변환
						statusColor: vote.isWin ? "#BDE3FF" : "#FFC7C2", // 승리 여부에 따른 색상 설정
					};
				});
				setVotes(formattedVotes);
			} catch (err) {
				setError("Failed to fetch user votes.");
			} finally {
				setLoading(false);
			}
		};

		fetchVotes();
	}, []);

	return (
		<VotesContainer className="custom-scrollbar">
			<VotesList>
				{votes.map((vote) => (
					<VotesListItem
						key={vote.id}
						onClick={() => setSelectedVoteId(Number(vote.id))}
					>
						<TitleContainer>{vote.title}</TitleContainer>
						<DateStatusContainer>
							<VoteDate>{vote.date}</VoteDate>
							<VoteStatusIndicator statusColor={vote.statusColor} />
						</DateStatusContainer>
					</VotesListItem>
				))}
			</VotesList>
			{selectedVoteId && (
				<ParticipatedVotesModal
					voteId={selectedVoteId}
					onClose={() => setSelectedVoteId(null)} // 모달 닫기
				/>
			)}
		</VotesContainer>
	);
}

export default ParticipatedVotesList;
