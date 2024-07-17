function SignUpPage() {
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
        `}
				</style>
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
					<div className="mb-4">
						<label htmlFor="email" className="block text-black mb-2">
							이메일
							<input
								id="email"
								type="email"
								placeholder="이메일을 입력해주세요."
								className="w-full p-2 border-4 border-black rounded-xl"
							/>
						</label>
					</div>
					<div className="mb-4">
						<label htmlFor="nickname" className="block text-black mb-2">
							닉네임
							<input
								id="nickname"
								type="text"
								placeholder="닉네임을 입력해주세요."
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
								placeholder="6~20자/ 영문 소문자, 숫자, 특수문자 중 2개"
								className="w-full p-2 border-4 border-black rounded-xl"
							/>
						</label>
					</div>
					<div className="mb-4">
						<label htmlFor="confirm-password" className="block text-black mb-2">
							비밀번호 확인
							<input
								id="confirm-password"
								type="password"
								placeholder="비밀번호 확인"
								className="w-full p-2 border-4 border-black rounded-xl"
							/>
						</label>
					</div>
					<button
						type="submit"
						className="w-full h-16 p-2 bg-black text-white text-2xl rounded-2xl mt-4"
					>
						확인
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

export default SignUpPage;
