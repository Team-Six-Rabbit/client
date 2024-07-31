import React, { ChangeEvent, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AuthInput, AuthSubmitBtn } from "@/components/auth/AuthFormComponent";
import GoogleLoginButton from "@/components/auth/googleLoginBtn";
import { LoginRequest } from "@/types/api";
import { authService } from "@/services/userAuthService";

interface CustomCSSProperties extends React.CSSProperties {
	textShadow?: string;
}

const headingTagStyles: CustomCSSProperties = {
	textShadow:
		"-4px -4px 0 black, 4px -4px 0 black, -4px 4px 0 black, 4px 4px 0 black",
};

function LoginPage() {
	const navigator = useNavigate();
	const [formValues, setFormValues] = useState<LoginRequest>({
		email: "",
		password: "",
	});

	const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {
		const { name, value } = event.target;
		setFormValues({
			...formValues,
			[name]: value,
		});
	};

	const doLogin = async () => {
		try {
			await authService.login(formValues);
			navigator("/");
		} catch (err) {
			console.error("로그인 실패"); // TODO: 로그인 실패 시 처리
		}
	};

	return (
		<div className="flex justify-center items-center h-screen bg-white">
			<div className="bg-white p-8 rounded-3xl shadow-lg scale-100 border-4 border-solid border-black">
				<h1
					className="text-center text-white text-5xl mb-4"
					style={headingTagStyles}
				>
					로그인
				</h1>
				<AuthInput
					label="이메일"
					type="email"
					name="email"
					value={formValues.email}
					onChange={handleInputChange}
					placeholder="이메일"
				/>
				<AuthInput
					label="비밀번호"
					type="password"
					name="password"
					value={formValues.password}
					onChange={handleInputChange}
					placeholder="비밀번호"
				/>
				<AuthSubmitBtn text="로그인" onClick={doLogin} />
				<GoogleLoginButton />
				<div className="flex justify-between mt-4">
					<Link
						to="/join"
						className="text-black hover:color hover:text-[#F66C23]"
					>
						#회원가입
					</Link>
					<Link to="/" className="text-black hover:text-[#F66C23]">
						#홈으로 이동
					</Link>
				</div>
			</div>
		</div>
	);
}

export default LoginPage;
