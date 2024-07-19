import React from "react";
import { AuthInput, AuthSubmitBtn } from "@/components/auth/AuthFormComponent";
import GoogleLoginButton from "@/components/auth/googleLoginBtn";
import { Link } from "react-router-dom";

interface CustomCSSProperties extends React.CSSProperties {
	textShadow?: string;
}

const headingTagStyles: CustomCSSProperties = {
	textShadow:
		"-4px -4px 0 black, 4px -4px 0 black, -4px 4px 0 black, 4px 4px 0 black",
};

function LoginPage() {
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
					placeholder="이메일"
				/>
				<AuthInput
					label="비밀번호"
					type="password"
					name="password"
					placeholder="비밀번호"
				/>
				<AuthSubmitBtn text="로그인" />
				<GoogleLoginButton />
				<div className="flex justify-between mt-4">
					<Link to="/" className="text-black hover:color hover:text-[#F66C23]">
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
