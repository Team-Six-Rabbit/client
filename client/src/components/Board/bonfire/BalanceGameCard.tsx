import { useState } from "react";
import BalanceGameModal from "@/components/Modal/BalanceGameModal";
import { BalanceGameCardType } from "@/types/Board/balancegameCard";
import ActiveBalanceGameCard from "@/components/Board/bonfire/ActiveBalanceGameCard";
import EndedBalanceGameCard from "@/components/Board/bonfire/EndedBalanceGameCard";
import {
	BalanceGameCardWrapper,
	Question,
} from "@/assets/styles/balanceGameStyle";

interface BalanceGameCardProps {
	data: BalanceGameCardType;
	onVote: (id: number, option: number) => void;
	disabled: boolean;
}

function BalanceGameCard({ data, onVote, disabled }: BalanceGameCardProps) {
	const [hasVoted, setHasVoted] = useState(data.userVote !== null);
	const [isModalOpen, setIsModalOpen] = useState(false);

	const handleVote = (option: number) => {
		if (hasVoted || disabled) return;

		setHasVoted(true);
		onVote(data.id, option);
	};

	const handleCardClick = () => {
		setIsModalOpen(true);
	};

	const handleCloseModal = () => {
		setIsModalOpen(false);
	};

	return (
		<>
			<BalanceGameCardWrapper>
				<Question onClick={handleCardClick}>{data.title}</Question>
				{hasVoted || disabled ? (
					<EndedBalanceGameCard opinions={data.opinions} />
				) : (
					<ActiveBalanceGameCard opinions={data.opinions} onVote={handleVote} />
				)}
			</BalanceGameCardWrapper>
			{isModalOpen && (
				<BalanceGameModal data={data} onClose={handleCloseModal} />
			)}
		</>
	);
}

export default BalanceGameCard;
