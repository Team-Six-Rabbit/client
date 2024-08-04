import React from "react";

function Interests() {
	return (
		<div className="interests">
			<h2>관심사</h2>
			<p>무엇을 좋아하세요? 맞춤 컨텐츠를 보여드립니다!</p>
			<div className="interests-buttons">
				<button type="button">사랑</button>
				<button type="button">일상</button>
				<button type="button">음식</button>
				<button type="button">연애</button>
				<button type="button">게임</button>
				<button type="button">스포츠</button>
				<button type="button">기타</button>
			</div>
		</div>
	);
}

export default Interests;
