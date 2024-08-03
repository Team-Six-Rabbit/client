import { useState } from "react";
import Slider from "react-slick";
import styled, { keyframes, css } from "styled-components";
import { CardType } from "@/types/Board/liveBoardCard";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

const SliderContainer = styled.div`
	width: 100%;
	margin-top: 120px;
	position: relative;
`;

const StyledSlider = styled(Slider)`
	.slick-prev,
	.slick-next {
		z-index: 1;
		width: 40px;
		height: 40px;
		color: orange;
		position: absolute;
		top: 50%;
		transform: translateY(-50%);

		&:before {
			font-size: 40px;
			color: orange;
		}
	}

	.slick-prev {
		left: 130px;
	}

	.slick-next {
		right: 130px;
	}

	.slick-slide > div {
		margin: 0 15px;
		background-color: #f0f0f0;
		height: 400px;
		display: flex;
		align-items: center;
		justify-content: center;
		position: relative; /* 텍스트 위치 설정을 위해 */
		overflow: hidden; /* 넘치는 텍스트 자르기 */
	}
`;

const ImageContainer = styled.div`
	width: 100%;
	height: 400px; // 고정 높이 설정
	position: relative;
	display: flex;
	align-items: center;
	justify-content: center;
	overflow: hidden;
	border-radius: 19px;
	border: 6px solid black;
`;

const StyledImage = styled.img`
	width: 100%;
	height: 100%;
	object-fit: cover;
	position: absolute;
	top: 0;
	left: 0;
	z-index: 1;
`;

const slideUp = keyframes`
	from {
    transform: translateY(20px);
    opacity: 0;
	}
	to {
    transform: translateY(0);
    opacity: 1;
	}
`;

const TextOverlay = styled.div<{ animate: boolean }>`
	position: absolute;
	bottom: 20px;
	left: 20px;
	z-index: 2;
	color: white;
	padding: 10px;
	border-radius: 5px;
	max-width: 80%;
	opacity: 0; /* 기본적으로 투명하게 설정 */

	${({ animate }) =>
		animate &&
		css`
			animation: ${slideUp} 0.9s ease-out;
			opacity: 1; /* 애니메이션 시 보이도록 설정 */
		`}
`;

const Title = styled.h3`
	margin: 0;
	font-size: 2em;
	color: white;
`;

const UserInfo = styled.p`
	margin: 5px 0 0;
	font-size: 1.5em;
	line-height: 1.2;
	color: white;
`;

function CenterMode({ cards }: { cards: CardType[] }) {
	const [currentSlide, setCurrentSlide] = useState(0);

	const settings = {
		className: "center",
		centerMode: true,
		infinite: true,
		centerPadding: "200px",
		slidesToShow: 1,
		speed: 1500,
		autoplay: true,
		autoplaySpeed: 4000,
		afterChange: (index: number) => setCurrentSlide(index),
		responsive: [
			{
				breakpoint: 1500,
				settings: {
					slidesToShow: 3,
				},
			},
			{
				breakpoint: 1500,
				settings: {
					slidesToShow: 2,
				},
			},
			{
				breakpoint: 1500,
				settings: {
					slidesToShow: 1,
				},
			},
		],
	};

	return (
		<SliderContainer>
			<StyledSlider
				className={settings.className}
				centerMode={settings.centerMode}
				infinite={settings.infinite}
				centerPadding={settings.centerPadding}
				slidesToShow={settings.slidesToShow}
				speed={settings.speed}
				autoplay={settings.autoplay}
				autoplaySpeed={settings.autoplaySpeed}
				afterChange={settings.afterChange}
				responsive={settings.responsive}
			>
				{cards.map((card, index) => (
					<div key={card.id}>
						<ImageContainer>
							{card.image_uri && (
								<StyledImage src={card.image_uri} alt={card.title} />
							)}
							<TextOverlay animate={currentSlide === index}>
								<Title>{card.title}</Title>
								<UserInfo>
									{card.regist_user_id} vs {card.opposite_user_id}
								</UserInfo>
							</TextOverlay>
						</ImageContainer>
					</div>
				))}
			</StyledSlider>
		</SliderContainer>
	);
}

export default CenterMode;
