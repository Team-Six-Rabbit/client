import React, { useState } from "react";
import styled from "styled-components";

const CarouselContainer = styled.div`
	display: flex;
	align-items: center;
	justify-content: center;
	position: relative;
	width: 650px;
	height: 300px;
	background-color: black;
	border-radius: 25px;
	border: 10px solid black;
	outline: none;
`;

const CarouselNintendo = styled.div`
	display: flex;
	align-items: center;
	justify-content: space-between;
	width: 100%;
	height: 100%;
	overflow-y: hidden;
`;

const CarouselButtonLeft = styled.div`
	width: 100px;
	height: 100%;
	display: flex;
	align-items: flex-start;
	justify-content: center;
	background-color: #04b9ce;
	border-radius: 15px 0 0 15px;
	padding-top: 30px;
`;

const CarouselButtonRight = styled.div`
	width: 100px;
	height: 100%;
	display: flex;
	align-items: flex-end;
	justify-content: center;
	background-color: #eb5545;
	border-radius: 0 15px 15px 0;
	padding-bottom: 30px;
`;

const ControllerButton = styled.button`
	cursor: pointer;
	transition: all 0.15s;
	background-color: #303030;
	color: white;
	padding: 0.5rem 1rem;
	border-radius: 100%;
	border-bottom-width: 4px;
	border-color: #000000;
	border-style: solid;
	font-weight: bold;
	font-size: 1.5rem;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);

	&:hover {
		filter: brightness(110%);
		transform: translateY(-1px);
		border-bottom-width: 6px;
	}

	&:active {
		filter: brightness(90%);
		transform: translateY(2px);
		border-bottom-width: 2px;
	}
`;

const CarouselArrow = styled.button`
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
	width: 0;
	height: 0;
	border-style: solid;
	cursor: pointer;
	outline: none;
	z-index: 10;
`;

const CarouselArrowLeft = styled(CarouselArrow)`
	left: -90px;
	border-width: 30px 45px 30px 0;
	border-color: transparent #000000 transparent transparent;
`;

const CarouselArrowRight = styled(CarouselArrow)`
	right: -90px;
	border-width: 30px 0 30px 45px;
	border-color: transparent transparent transparent #000000;
`;

const CarouselContent = styled.div`
	flex: 1;
	display: flex;
	justify-content: center;
	align-items: center;
	color: white;
	font-size: 1.5rem;
	font-weight: bold;
`;

const CarouselBorder = styled.div`
	width: 8px;
	height: 100%;
	background-color: black;
`;

const images = [
	"https://via.placeholder.com/600x300?text=Slide+1",
	"https://via.placeholder.com/600x300?text=Slide+2",
	"https://via.placeholder.com/600x300?text=Slide+3",
];

function Carousel() {
	const [currentIndex, setCurrentIndex] = useState(0);

	const nextSlide = () => {
		setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
	};

	const prevSlide = () => {
		setCurrentIndex(
			(prevIndex) => (prevIndex - 1 + images.length) % images.length,
		);
	};

	const handleKeyDown = (event: React.KeyboardEvent<HTMLButtonElement>) => {
		if (event.key === "ArrowLeft") {
			prevSlide();
		} else if (event.key === "ArrowRight") {
			nextSlide();
		}
	};

	return (
		<CarouselContainer tabIndex={0} role="region" aria-label="Image Carousel">
			<CarouselArrowLeft
				onClick={prevSlide}
				onKeyDown={handleKeyDown}
				aria-label="Previous Slide"
				type="button"
			/>
			<CarouselNintendo>
				<CarouselButtonLeft>
					<ControllerButton>
						<span>M</span>
					</ControllerButton>
				</CarouselButtonLeft>
				<CarouselBorder />
				<CarouselContent>
					<img
						src={images[currentIndex]}
						alt={`Slide ${currentIndex + 1}`}
						style={{ width: "600px", height: "300px", objectFit: "cover" }}
					/>
				</CarouselContent>
				<CarouselBorder />
				<CarouselButtonRight>
					<ControllerButton>
						<span>A</span>
					</ControllerButton>
				</CarouselButtonRight>
			</CarouselNintendo>
			<CarouselArrowRight
				onClick={nextSlide}
				onKeyDown={handleKeyDown}
				aria-label="Next Slide"
				type="button"
			/>
		</CarouselContainer>
	);
}

export default Carousel;
