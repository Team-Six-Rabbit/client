import { useState } from "react";
import PreVotingModal from "@/components/Modal/PreVotingModal";
import { TicketType } from "@/types/Board/ticket";
import { themes } from "@/assets/styles/themes";
import {
	TicketContainer,
	TicketContent,
	TicketTitle,
	Divider,
} from "@/assets/styles/ticketStyle";
import TicketDetails from "@/components/Board/fanning/TicketDetails";
import TicketBarcode from "@/components/Board/fanning/TicketBarcode";

interface TicketProps {
	ticket: TicketType;
	theme: "navy" | "yellow";
}

function Ticket({ ticket, theme = "navy" }: TicketProps) {
	const themeData = themes[theme] || themes.navy;
	const [showModal, setShowModal] = useState(false);

	const handleVote = (opinion: string) => {
		console.log(`Voted for: ${opinion}`);
		setShowModal(false);
	};

	return (
		<TicketContainer theme={themeData}>
			<TicketContent>
				<TicketTitle theme={themeData}>Ticket To Live</TicketTitle>
				<TicketDetails
					themeData={themeData}
					title={ticket.title}
					opinions={ticket.opinionDtos}
					startDate={ticket.startDate}
				/>
			</TicketContent>
			<Divider />
			<TicketBarcode
				themeData={themeData}
				barcodeImage={themeData.barcodeImage}
				isVoted={ticket.isVoted}
				currentPeopleCount={ticket.currentPeopleCount}
				maxPeopleCount={ticket.maxPeopleCount}
				onAttend={() => setShowModal(true)}
			/>
			<PreVotingModal
				showModal={showModal}
				setShowModal={setShowModal}
				title={ticket.title}
				opinion1={ticket.opinionDtos[0].opinion}
				opinion2={ticket.opinionDtos[1].opinion}
				onVote={handleVote}
			/>
		</TicketContainer>
	);
}

export default Ticket;
