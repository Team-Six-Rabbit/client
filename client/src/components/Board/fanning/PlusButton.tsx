import styled from "styled-components";

interface ButtonProps {
	strokeColor?: string;
	fillColor?: string;
}

const Button = styled.button<ButtonProps>`
	position: fixed;
	bottom: 40px;
	right: 30px;
	background: none;
	border: none;
	cursor: pointer;
	outline: none;

	.plus-button-svg {
		width: 50px;
		height: 50px;
		transition:
			transform 0.3s,
			fill 0.3s,
			stroke 0.3s;
	}

	&:hover .plus-button-svg {
		transform: rotate(90deg);
		stroke: ${(props) => props.strokeColor};
		fill: ${(props) => props.fillColor};
	}

	&:active .plus-button-svg {
		stroke: ${(props) => props.strokeColor};
		fill: ${(props) => props.fillColor};
		transition-duration: 0s;
	}
`;

function PlusButton({ strokeColor, fillColor }: ButtonProps) {
	return (
		<Button
			title="새로운 토론 주제 작성"
			className="plus-button"
			strokeColor={strokeColor}
			fillColor={fillColor}
		>
			<svg
				xmlns="http://www.w3.org/2000/svg"
				viewBox="0 0 24 24"
				className="plus-button-svg stroke-zinc-400 fill-none"
			>
				<path
					d="M12 22C17.5 22 22 17.5 22 12C22 6.5 17.5 2 12 2C6.5 2 2 6.5 2 12C2 17.5 6.5 22 12 22Z"
					strokeWidth="1.5"
				/>
				<path d="M8 12H16" strokeWidth="1.5" />
				<path d="M12 16V8" strokeWidth="1.5" />
			</svg>
		</Button>
	);
}

// Default Props Declaration
PlusButton.defaultProps = {
	strokeColor: "#1d3d6b",
	fillColor: "#fbca27",
};

export default PlusButton;
