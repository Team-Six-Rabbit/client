<<<<<<< HEAD
import { ChangeEvent, useState, KeyboardEvent } from "react";
=======
import React, { ChangeEvent, useState } from "react";
>>>>>>> ddc781d (Feat: 로그인 페이지 오류 메시지 생성)
import { Link, useNavigate } from "react-router-dom";
import { AuthInput, AuthSubmitBtn } from "@/components/auth/AuthFormComponent";
import GoogleLoginButton from "@/components/auth/googleLoginBtn";
import { LoginRequest } from "@/types/api";
<<<<<<< HEAD
import { authService } from "@/services/userAuthService";
=======
import { login } from "@/services/userAuthService";
>>>>>>> 5246c8b (Feat: 로그인 페이지 오류 메시지 생성)
import { createLiveStateBorder } from "@/utils/textBorder"; // textBorder import
=======
import { login } from "@/services/userAuthService";
import { createLiveStateBorder } from "@/utils/textBorder"; // textBorder import

interface CustomCSSProperties extends React.CSSProperties {
	textShadow?: string;
}

const headingTagStyles: CustomCSSProperties = {
	...createLiveStateBorder("black", 4),
};
>>>>>>> ddc781d (Feat: 로그인 페이지 오류 메시지 생성)

function LoginPage() {
	const navigator = useNavigate();
	const [formValues, setFormValues] = useState<LoginRequest>({
		email: "",
		password: "",
	});

	const [errors, setErrors] = useState<{
		email?: string;
		password?: string;
		general?: string;
	}>({}); // errors 상태 추가
	const [doShake, setDoShake] = useState<boolean>(false);

	const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {
		const { name, value } = event.target;
		setFormValues({
			...formValues,
			[name]: value,
		});
		setErrors((prevErrors) => ({ ...prevErrors, [name]: "", general: "" })); // 입력 변경 시 에러 초기화
<<<<<<< HEAD
	};

	const handleKeyDown = (event: KeyboardEvent<HTMLInputElement>) => {
		if (event.key === "Enter") {
			// eslint-disable-next-line @typescript-eslint/no-use-before-define
			doLogin();
		}
=======
>>>>>>> ddc781d (Feat: 로그인 페이지 오류 메시지 생성)
	};

	const doLogin = async () => {
		const newErrors: { email?: string; password?: string; general?: string } =
			{};

		if (!formValues.email) {
			newErrors.email = "이메일을 입력해주세요."; // 이메일 입력 요구 메시지
		}
		if (!formValues.password) {
			newErrors.password = "비밀번호를 입력해주세요."; // 비밀번호 입력 요구 메시지
		}

		if (Object.keys(newErrors).length > 0) {
			setErrors(newErrors);
			setDoShake(true);
			setTimeout(() => {
				setDoShake(false);
			}, 500);
			return;
		}

		try {
			await login(formValues);
			navigator("/");
		} catch (err) {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 3516697 (Feat: 로그인 페이지 오류 메시지 생성)
=======
>>>>>>> b795bd7 (Chore: 로그인 회원가입 push)
=======
			// console.error("로그인 실패");
			// // TODO: 로그인 실패 시 처리
			// setDoShake(true);
			// setTimeout(() => {
			// 	setDoShake(false);
			// }, 500);
<<<<<<< HEAD
>>>>>>> ddc781d (Feat: 로그인 페이지 오류 메시지 생성)
=======
>>>>>>> 7ec1267 (Chore: 로그인 회원가입 push)
=======
>>>>>>> 5246c8b (Feat: 로그인 페이지 오류 메시지 생성)
<<<<<<< HEAD
>>>>>>> 3516697 (Feat: 로그인 페이지 오류 메시지 생성)
=======
=======
>>>>>>> 0e5627c (Chore: 로그인 회원가입 push)
>>>>>>> b795bd7 (Chore: 로그인 회원가입 push)
			setErrors({ general: "로그인에 실패했습니다." }); // 로그인 실패 메시지
			setDoShake(true);
			setTimeout(() => {
				setDoShake(false);
			}, 500);
		}
	};

	return (
		<div className="flex justify-center items-center h-screen bg-white">
			<div className="bg-white p-8 rounded-3xl shadow-lg scale-100 border-4 border-solid border-black">
				<h1
					className="text-center text-white text-5xl mb-4"
					style={createLiveStateBorder("black", 4)}
				>
					로그인
				</h1>
				<div className="mb-4 relative">
					<AuthInput
						label="이메일"
						type="email"
						name="email"
						value={formValues.email}
						onChange={handleInputChange}
<<<<<<< HEAD
						onKeyDown={handleKeyDown}
=======
>>>>>>> ddc781d (Feat: 로그인 페이지 오류 메시지 생성)
						placeholder="이메일"
					/>
					{errors.email && (
						<div className="text-red-500 text-xs absolute right-0 top-0 mt-1 mr-2">
							{errors.email} {/* 이메일 에러 메시지 */}
						</div>
					)}
				</div>
				<div className="mb-4 relative">
					<AuthInput
						label="비밀번호"
						type="password"
						name="password"
						value={formValues.password}
						onChange={handleInputChange}
<<<<<<< HEAD
						onKeyDown={handleKeyDown}
=======
>>>>>>> ddc781d (Feat: 로그인 페이지 오류 메시지 생성)
						placeholder="비밀번호"
					/>
					{errors.password && (
						<div className="text-red-500 text-xs absolute right-0 top-0 mt-1 mr-2">
							{errors.password} {/* 비밀번호 에러 메시지 */}
						</div>
					)}
				</div>
				<div id="submit-btn" className="relative">
					<AuthSubmitBtn
						text="로그인"
						onClick={doLogin}
						className={doShake ? "shake" : ""}
					/>
					{errors.general && (
						<div className="text-red-500 text-xs absolute right-0 -top-1 mt-1 mr-2">
							{errors.general} {/* 일반 에러 메시지 */}
						</div>
					)}
				</div>
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
