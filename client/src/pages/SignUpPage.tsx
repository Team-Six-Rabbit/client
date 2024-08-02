import { AuthInput, AuthSubmitBtn } from "@/components/auth/AuthFormComponent";
import { authService } from "@/services/userAuthService";
import { JoinRequest } from "@/types/api";
import { ChangeEvent, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

function SignUpPage() {
	const navigator = useNavigate();
	const [formValues, setFormValues] = useState<JoinRequest>({
		email: "",
		password: "",
		nickname: "",
	});
	const [passwordConfirm, setPasswordConfirm] = useState<string>("");

	const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {
		const { name, value } = event.target;
		setFormValues({
			...formValues,
			[name]: value,
		});
	};

	const handlePasswordConfirmChange = (
		event: ChangeEvent<HTMLInputElement>,
	) => {
		const { value } = event.target;
		setPasswordConfirm(value);

		// TODO: 비밀번호와 일치 여부 표시하기
	};

	const doJoin = async () => {
		try {
			// TODO: 비밀번호 규칙 검사
			await authService.join(formValues);
			navigator("/");
		} catch (err) {
			console.error("회원가입 실패"); // TODO: 회원가입 실패 시 처리
		}
	};

	return (
		<div className="flex justify-center items-center h-screen bg-white">
			<div
				className="bg-white p-8 rounded-3xl shadow-lg scale-100"
				style={{ border: "4px solid black" }}
			>
				<h1
					className="text-center text-white text-5xl mb-4"
					style={{
						textShadow:
							"-4px -4px 0 black, 4px -4px 0 black, -4px 4px 0 black, 4px 4px 0 black",
					}}
				>
					회원가입
				</h1>
				<form>
					<AuthInput
						label="이메일"
						type="email"
						name="email"
						value={formValues.email}
						onChange={handleInputChange}
						placeholder="이메일을 입력해주세요."
					/>
					<AuthInput
						label="닉네임"
						type="text"
						name="nickname"
						value={formValues.nickname}
						onChange={handleInputChange}
						placeholder="닉네임을 입력해주세요."
					/>
					<AuthInput
						label="비밀번호"
						type="password"
						name="password"
						value={formValues.password}
						onChange={handleInputChange}
						placeholder="6~20자/ 영문 소문자, 숫자, 특수문자 중 2개"
					/>
					<AuthInput
						label="비밀번호 확인"
						type="password"
						name="confirm-password"
						value={passwordConfirm}
						onChange={handlePasswordConfirmChange}
						placeholder="비밀번호 확인"
					/>
					<AuthSubmitBtn text="확인" onClick={doJoin} />
				</form>
				<div className="flex justify-between mt-4">
					<Link
						to="/login"
						className="text-black hover:color hover:text-[#F66C23]"
					>
						#로그인
					</Link>
					<Link to="/" className="text-black hover:color hover:text-[#F66C23]">
						#홈으로 이동
					</Link>
				</div>
			</div>
		</div>
	);
}

export default SignUpPage;
