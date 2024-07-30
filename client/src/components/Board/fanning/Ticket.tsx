import { useState } from "react";
import styled, { css } from "styled-components";
import barcodeYellow from "@/assets/images/BarcodeYellow.png";
import barcodeNavy from "@/assets/images/BarcodeNavy.png";
import PreVotingModal from "@/components/Modal/PreVotingModal";

const themes = {
	navy: {
		backgroundColor: "#1D3D6B",
		borderColor: "#FBCA27",
		textColor: "#FBCA27",
		buttonColor: "#1e3a8a",
		buttonBackgroundColor: "#ffffff",
		titleColor: "#ffffff",
		attendeeCountColor: "#FFFFFF",
		barcodeImage: barcodeYellow,
	},
	yellow: {
		backgroundColor: "#FBCA27",
		borderColor: "#1D3D6B",
		textColor: "#1D3D6B",
		buttonColor: "#FFFFFF",
		buttonBackgroundColor: "#1D3D6B",
		titleColor: "#1D3D6B",
		attendeeCountColor: "#1D3D6B",
		barcodeImage: barcodeNavy,
	},
};

const TicketContainer = styled.div`
	display: flex;
	align-items: center;
	padding: 16px;
	margin: 8px;
	width: 600px;
	position: relative;
	${({ theme }) => css`
		background-color: ${theme.backgroundColor};
		color: white;
		box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
	`}
`;
const TicketContent = styled.div`
	flex: 1;
	padding-right: 16px;
`;

const TicketTitle = styled.div`
	font-size: 14px;
	margin-bottom: 8px;
	${({ theme }) => css`
		color: ${theme.titleColor};
	`}
`;

const EventDetails = styled.div`
	padding: 16px;
	margin-bottom: 8px;
	${({ theme }) => css`
		border: 3px solid ${theme.borderColor};
		background-color: ${theme.backgroundColor};
	`}
`;

const EventTitle = styled.div`
	font-size: 24px;
	${({ theme }) => css`
		color: ${theme.textColor};
	`}
	margin-bottom: 8px;
`;

const EventSubtitle = styled.div`
	font-size: 18px;
	${({ theme }) => css`
		color: ${theme.textColor};
	`}
	margin-bottom: 8px;
`;

const EventTime = styled.div`
	font-size: 14px;
	${({ theme }) => css`
		color: ${theme.textColor};
	`}
`;

const BarcodeContainer = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	margin-left: 16px;
	margin-top: 18px;
`;

const BarcodeImage = styled.img`
	height: 40px;
	width: 64px;
	margin-bottom: 20px;
`;

const AttendButton = styled.div`
	border-radius: 9999px;
	padding: 4px 16px;
	margin-bottom: 8px;
	${({ theme }) => css`
		background-color: ${theme.buttonBackgroundColor};
		color: ${theme.buttonColor};
		cursor: pointer;
	`}
`;

const AttendCount = styled.div`
	font-size: 14px;
	${({ theme }) => css`
		color: ${theme.attendeeCountColor};
	`}
`;

const Divider = styled.div`
	position: absolute;
	top: 0;
	bottom: 0;
	left: calc(100% - 100px);
	width: 2px;
	background: repeating-linear-gradient(
		to bottom,
		transparent,
		transparent 4px,
		#fff 4px,
		#fff 8px
	);
`;

interface TicketProps {
	title: string;
	opinion1: string;
	opinion2: string;
	date: string;
	attendees: number;
	maxAttendees: number;
	theme: "navy" | "yellow";
}

function Ticket({
	title,
	opinion1,
	opinion2,
	date,
	attendees,
	maxAttendees,
	theme = "navy",
}: TicketProps) {
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
				<EventDetails theme={themeData}>
					<EventTitle theme={themeData}>{title}</EventTitle>
					<EventSubtitle
						theme={themeData}
					>{`${opinion1} VS ${opinion2}`}</EventSubtitle>
					<EventTime theme={themeData}>{date}</EventTime>
				</EventDetails>
			</TicketContent>
			<Divider />
			<BarcodeContainer>
				<BarcodeImage src={themeData.barcodeImage} alt="Barcode" />
				<AttendButton theme={themeData} onClick={() => setShowModal(true)}>
					참석
				</AttendButton>
				<AttendCount
					theme={themeData}
				>{`${attendees}/${maxAttendees}`}</AttendCount>
			</BarcodeContainer>
			<PreVotingModal
				showModal={showModal}
				setShowModal={setShowModal}
				title={title}
				opinion1={opinion1}
				opinion2={opinion2}
				onVote={handleVote}
			/>
		</TicketContainer>
	);
}

export default Ticket;
