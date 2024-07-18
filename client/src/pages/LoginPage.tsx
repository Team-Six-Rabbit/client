function LoginPage() {
	return (
		<div className="flex justify-center items-center h-screen bg-white">
			<div
				className="bg-white p-8 rounded-3xl shadow-lg scale-100"
				style={{ border: "4px solid black" }}
			>
				<style>
					{`
          input:focus {
            outline: none;
            border-color: #F66C23;
            box-shadow: 0 0 0 3px rgba(246, 108, 35, 0.3);
          }
					a:hover {
						color: #F66C23;
					}
					button:hover {
						background-color: #F66C23;
					}
					.google-button:hover {
            box-shadow: 0 0 0 3px #F66C23 inset;
					}
        `}
				</style>
				<h1
					className="text-center text-white text-5xl mb-4"
					style={{
						textShadow:
							"-4px -4px 0 black, 4px -4px 0 black, -4px 4px 0 black, 4px 4px 0 black",
					}}
				>
					로그인
				</h1>
				<form>
					<div className="mb-4">
						<label htmlFor="email" className="block text-black mb-2">
							이메일
							<input
								id="email"
								type="email"
								placeholder="이메일"
								className="w-full p-2 border-4 border-black rounded-xl"
							/>
						</label>
					</div>
					<div className="mb-4">
						<label htmlFor="password" className="block text-black mb-2">
							비밀번호
							<input
								id="password"
								type="password"
								placeholder="비밀번호"
								className="w-full p-2 border-4 border-black rounded-xl"
							/>
						</label>
					</div>
					<button
						type="submit"
						className="w-full h-13 p-2 bg-black text-white text-2xl rounded-2xl mt-4"
					>
						로그인
					</button>
					<button
						type="button"
						className="w-full bg-white flex items-center justify-center gap-x-3 text-sm sm:text-base rounded-lg hover:bg-white duration-300 transition-colors border px-8 py-2.5 mt-4 google-button"
					>
						<svg
							className="w-5 h-5 sm:h-6 sm:w-6"
							viewBox="0 0 24 24"
							fill="none"
							xmlns="http://www.w3.org/2000/svg"
						>
							<g clipPath="url(#clip0_3033_94454)">
								<path
									d="M23.766 12.2764C23.766 11.4607 23.6999 10.6406 23.5588 9.83807H12.24V14.4591H18.7217C18.4528 15.9494 17.5885 17.2678 16.323 18.1056V21.1039H20.19C22.4608 19.0139 23.766 15.9274 23.766 12.2764Z"
									fill="#4285F4"
								/>
								<path
									d="M12.2401 24.0008C15.4766 24.0008 18.2059 22.9382 20.1945 21.1039L16.3276 18.1055C15.2517 18.8375 13.8627 19.252 12.2445 19.252C9.11388 19.252 6.45946 17.1399 5.50705 14.3003H1.5166V17.3912C3.55371 21.4434 7.7029 24.0008 12.2401 24.0008Z"
									fill="#34A853"
								/>
								<path
									d="M5.50253 14.3003C4.99987 12.8099 4.99987 11.1961 5.50253 9.70575V6.61481H1.51649C-0.18551 10.0056 -0.18551 14.0004 1.51649 17.3912L5.50253 14.3003Z"
									fill="#FBBC04"
								/>
								<path
									d="M12.2401 4.74966C13.9509 4.7232 15.6044 5.36697 16.8434 6.54867L20.2695 3.12262C18.1001 1.0855 15.2208 -0.034466 12.2401 0.000808666C7.7029 0.000808666 3.55371 2.55822 1.5166 6.61481L5.50264 9.70575C6.45064 6.86173 9.10947 4.74966 12.2401 4.74966Z"
									fill="#EA4335"
								/>
							</g>
							<defs>
								<clipPath id="clip0_3033_94454">
									<rect width="24" height="24" fill="white" />
								</clipPath>
							</defs>
						</svg>
						<span>구글 로그인</span>
					</button>
				</form>
				<div className="flex justify-between mt-4">
					<a href="/#" className="text-black">
						#회원가입
					</a>
					<a href="/#" className="text-black">
						#홈으로 이동
					</a>
				</div>
			</div>
		</div>
	);
}

export default LoginPage;
