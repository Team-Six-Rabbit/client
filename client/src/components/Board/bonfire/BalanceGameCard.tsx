import { useState } from "react";
import BalanceGameModal from "@/components/Modal/BalanceGameModal";
import { BalanceGameCardType } from "@/types/Board/balancegameCard";
import ActiveBalanceGameCard from "@/components/Board/bonfire/ActiveBalanceGameCard";
import EndedBalanceGameCard from "@/components/Board/bonfire/EndedBalanceGameCard";
import {
	BalanceGameCardWrapper,
	Question,
} from "@/assets/styles/balanceGameStyle";
import { getBalanceGameById } from "@/services/balanceGameService";

interface BalanceGameCardProps {
	data: BalanceGameCardType;
	onVote: (id: number, option: number) => void;
	disabled: boolean;
}

function BalanceGameCard({ data, onVote, disabled }: BalanceGameCardProps) {
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
			const response = await getBalanceGameById(data.id.toString());
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
				<Question onClick={handleCardClick}>{data.title}</Question>
				{hasVoted || disabled ? (
					<EndedBalanceGameCard opinions={data.opinions} />
				) : (
					<ActiveBalanceGameCard opinions={data.opinions} onVote={handleVote} />
				)}
			</BalanceGameCardWrapper>
			{isLoading && <div>Loading...</div>}
			{isModalOpen && modalData && (
				<BalanceGameModal data={modalData} onClose={handleCloseModal} />
			)}
		</>
	);
}

export default BalanceGameCard;
