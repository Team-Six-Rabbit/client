import styled from "styled-components";
import Dropdown from "@/components/Board/regist/Dropdown";
import { categories } from "@/constant/boardCategory";

const Form = styled.form`
	margin-top: 16px;
	max-width: 800px;
	margin: 0 auto;
`;

const FormGroup = styled.div`
	margin-top: 13px;
	display: flex;
	flex-direction: column;
`;

const FlexFormGroup = styled.div`
	margin-top: 13px;
	display: flex;
	align-items: center;
	justify-content: space-between;
	gap: 15px; /* Space between flex items */
`;

const Label = styled.label`
	color: #000000;
	margin-bottom: 8px;
	font-size: 17px;
	display: block;
`;

const Input = styled.input`
	border-radius: 15px;
	border: 3.5px solid #000000;
	padding: 12px 16px;
	color: #000000;
	font-size: 14px;
	outline: none;
	flex: 1;
	box-sizing: border-box;

	&:focus {
		border-color: #f66c23;
	}
`;

const TextArea = styled.textarea`
	width: 100%;
	border-radius: 15px;
	border: 3.5px solid #000000;
	color: #000000;
	padding: 12px 16px;
	font-size: 14px;
	outline: none;
	box-sizing: border-box;

	&:focus {
		border-color: #f66c23;
	}
`;

const ButtonGroup = styled.div`
	display: flex;
	justify-content: flex-end;
`;

const Button = styled.button`
	margin-top: 16px;
	background-color: #000000;
	color: white;
	border-radius: 15px;
	padding: 8px 16px;
	cursor: pointer;
	border: none;

	&:hover {
		background-color: #f66c23;
	}
`;

// const UserIcon = styled.div`
// 	width: 40px;
// 	height: 40px;
// 	border-radius: 50%;
// 	background-color: #ddd;
// 	display: inline-block;
// 	margin-right: 10px;
// 	background-image: url("https://via.placeholder.com/40");
// 	background-size: cover;
// `;

function LiveDebateRegistForm() {
	const categoryNames = categories.map((category) => category.name);

	const handleCategorySelect = (category: string) => {
		console.log("Selected category:", category);
	};

	return (
		<Form>
			<FormGroup>
				<FlexFormGroup>
					<FormGroup style={{ flex: 2 }}>
						<Label htmlFor="title">제목</Label>
						<Input id="title" type="text" placeholder="제목을 입력하세요" />
					</FormGroup>
					<FormGroup style={{ flex: 1 }}>
						<Label htmlFor="category">카테고리</Label>
						<Dropdown
							options={categoryNames}
							defaultOption="카테고리 선택"
							onSelect={handleCategorySelect}
						/>
					</FormGroup>
				</FlexFormGroup>
			</FormGroup>
			<FlexFormGroup>
				<FormGroup style={{ flex: 1 }}>
					<Label htmlFor="author">작성자 닉네임</Label>
					<Input id="author" type="text" placeholder="닉네임을 입력하세요" />
				</FormGroup>
				<FormGroup style={{ flex: 1 }}>
					<Label htmlFor="opponent">상대방 닉네임</Label>
					<Input
						id="opponent"
						type="text"
						placeholder="상대방 닉네임을 입력하세요"
					/>
				</FormGroup>
			</FlexFormGroup>
			<FlexFormGroup>
				<FormGroup style={{ flex: 1 }}>
					<Label htmlFor="authorChoice">작성자 선택지</Label>
					<Input
						id="authorChoice"
						type="text"
						placeholder="작성자 선택지를 입력하세요"
					/>
				</FormGroup>
				<FormGroup style={{ flex: 1 }}>
					<Label htmlFor="opponentChoice">상대방 선택지</Label>
					<Input
						id="opponentChoice"
						type="text"
						placeholder="상대방 선택지를 입력하세요"
					/>
				</FormGroup>
			</FlexFormGroup>
			<FlexFormGroup>
				<FormGroup style={{ flex: 2 }}>
					<Label htmlFor="startTime">라이브 시작 시간</Label>
					<Input
						id="startTime"
						type="datetime-local"
						placeholder="라이브 시작 시간을 입력하세요"
					/>
				</FormGroup>
				<FormGroup style={{ flex: 1 }}>
					<Label htmlFor="duration">분</Label>
					<Dropdown
						options={["10분", "20분", "30분", "40분", "50분", "60분"]}
						defaultOption="선택"
						onSelect={(value) => console.log(`Selected duration: ${value}`)}
					/>
				</FormGroup>
				<FormGroup style={{ flex: 1 }}>
					<Label htmlFor="maxParticipants">최대 인원 수</Label>
					<Input
						id="maxParticipants"
						type="number"
						placeholder="최대 인원 수"
					/>
				</FormGroup>
			</FlexFormGroup>
			<FormGroup>
				<Label htmlFor="details">토론 상세 정보 작성</Label>
				<TextArea id="details" placeholder="상세 정보를 입력하세요" />
			</FormGroup>
			<ButtonGroup>
				<Button type="submit">등록</Button>
			</ButtonGroup>
		</Form>
	);
}

export default LiveDebateRegistForm;
