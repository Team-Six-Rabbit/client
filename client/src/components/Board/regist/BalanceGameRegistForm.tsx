import styled from "styled-components";
import Dropdown from "@/components/Board/regist/Dropdown"; // Dropdown component import
import { categories } from "@/constant/boardCategory"; // Categories import

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
	gap: 15px; /* Remove extra gap */
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

	&:hover {
		background-color: #f66c23;
	}
`;

function BalanceGameRegistForm() {
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
			<FormGroup>
				<Label htmlFor="author">작성자 닉네임</Label>
				<Input id="author" type="text" placeholder="닉네임을 입력하세요" />
			</FormGroup>
			<FlexFormGroup>
				<FormGroup style={{ flex: 1 }}>
					<Label htmlFor="optionA">선택지 A</Label>
					<Input id="optionA" type="text" placeholder="선택지 A를 입력하세요" />
				</FormGroup>
				<FormGroup style={{ flex: 1 }}>
					<Label htmlFor="optionB">선택지 B</Label>
					<Input id="optionB" type="text" placeholder="선택지 B를 입력하세요" />
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

export default BalanceGameRegistForm;
