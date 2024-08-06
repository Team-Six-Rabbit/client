import { useState } from "react";
import BalanceGameModal from "@/components/Modal/BalanceGameModal";
import { BalanceGameCardType } from "@/types/Board/balancegameCard";
import ActiveBalanceGameCard from "@/components/Board/bonfire/ActiveBalanceGameCard";
import EndedBalanceGameCard from "@/components/Board/bonfire/EndedBalanceGameCard";
import {
	BalanceGameCardWrapper,
	Question,
} from "@/assets/styles/balanceGameStyle";
import { balanceGameService } from "@/services/balanceGameService";
import fireExtinguisher from "@/assets/images/fireExtinguisher.gif";
import fire from "@/assets/images/fire.gif";
import styled from "styled-components";

interface BalanceGameCardProps {
	data: BalanceGameCardType;
	onVote: (id: number, option: number) => void;
	disabled: boolean;
	isEnded: boolean; // Add a prop to indicate if the game is ended
}

// Styled component for the like GIF
const LikeImage = styled.img`
	width: 50px; // Adjust size as needed
	height: 50px; // Adjust size as needed
	margin-right: 8px; // Space between the GIF and the title
`;

function BalanceGameCard({
	data,
	onVote,
	disabled,
	isEnded,
}: BalanceGameCardProps) {
	const [hasVoted, setHasVoted] = useState(
		data.userVote !== null && data.userVote !== undefined,
	);
	const [isModalOpen, setIsModalOpen] = useState(false);
	const [modalData, setModalData] = useState<BalanceGameCardType | null>(null);
	const [isLoading, setIsLoading] = useState(false);

	const handleVote = (option: number) => {
		if (hasVoted || disabled) return;

		setHasVoted(true);
		onVote(data.id, option);
	};

	const handleCardClick = async () => {
		setIsLoading(true);
		try {
			const response = await balanceGameService.getBalanceGameById(
				data.id.toString(),
			);
			setModalData(response.data as unknown as BalanceGameCardType);
			setIsModalOpen(true);
		} catch (error) {
			console.error("Failed to fetch balance game details:", error);
		} finally {
			setIsLoading(false);
		}
	};

	const handleCloseModal = () => {
		setIsModalOpen(false);
		setModalData(null);
	};

	return (
		<>
			<BalanceGameCardWrapper>
				<Question onClick={handleCardClick}>
					{isEnded && <LikeImage src={fireExtinguisher} alt="Ended" />}
					{!isEnded && <LikeImage src={fire} alt="Ing" />}
					{data.title}
				</Question>
				{hasVoted || disabled ? (
					<EndedBalanceGameCard
						opinions={data.opinions}
						userVote={data.userVote}
					/>
				) : (
					<ActiveBalanceGameCard opinions={data.opinions} onVote={handleVote} />
				)}
			</BalanceGameCardWrapper>
			{isLoading && <div>Loading...</div>}
			{isModalOpen && modalData && (
				<BalanceGameModal
					data={{
						...modalData,
						detail: modalData.detail ?? "No details available",
					}}
					onClose={handleCloseModal}
				/>
			)}
		</>
	);
}

export default BalanceGameCard;
