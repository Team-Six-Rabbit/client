import classNames from "classnames";
import { ChangeEventHandler, MouseEventHandler, KeyboardEvent } from "react";

interface AuthSubmitBtnProps {
	text: string;
	onClick: MouseEventHandler;
	className?: string;
}

interface AuthInputProps {
	label: string;
	name: string;
	type: string;
	placeholder: string;
	value: string;
	onChange: ChangeEventHandler;
	className?: string;
	error?: string; // 에러 메시지 추가
<<<<<<< HEAD
<<<<<<< HEAD
	onKeyDown?: (event: KeyboardEvent<HTMLInputElement>) => void;
=======
>>>>>>> 1d4af31 (Feat: 회원가입 로직 구현)
=======
	onKeyDown?: (event: KeyboardEvent<HTMLInputElement>) => void;
>>>>>>> c239a7e (Refactor: 로그인 엔터 기능 추가, 불필요한 구문 수정)
}

export function AuthInput({
	label,
	name,
	type,
	placeholder,
	value,
	onChange,
	className,
	error, // 에러 메시지 추가
<<<<<<< HEAD
<<<<<<< HEAD
	onKeyDown,
=======
>>>>>>> 1d4af31 (Feat: 회원가입 로직 구현)
=======
	onKeyDown,
>>>>>>> c239a7e (Refactor: 로그인 엔터 기능 추가, 불필요한 구문 수정)
}: AuthInputProps) {
	return (
		<div className="mb-4">
			<label htmlFor={name} className="block text-black mb-2">
				{label}
				<input
					id={name}
					name={name}
					type={type}
					placeholder={placeholder}
					value={value}
					onChange={onChange}
					onKeyDown={onKeyDown}
					className={classNames(
						"w-full p-2 border-4 border-black rounded-xl focus:outline-none focus:border-[#F66C23] focus:shadow-[0_0_0_3px_rgba(246,108,35,0.3)]",
						className,
					)}
				/>
<<<<<<< HEAD
<<<<<<< HEAD
				{error && <p className="text-red-500 text-sm mt-1">{error}</p>}
=======
				{error && <p className="text-red-500 text-sm mt-1">{error}</p>}{" "}
>>>>>>> 1d4af31 (Feat: 회원가입 로직 구현)
=======
				{error && <p className="text-red-500 text-sm mt-1">{error}</p>}
>>>>>>> c239a7e (Refactor: 로그인 엔터 기능 추가, 불필요한 구문 수정)
				{/* 에러 메시지 표시 */}
			</label>
		</div>
	);
}

export function AuthSubmitBtn({
	text,
	onClick,
	className,
}: AuthSubmitBtnProps) {
	return (
		<button
			type="button"
			onClick={onClick}
			className={classNames(
				"w-full h-13 p-2 bg-black text-white text-2xl rounded-2xl mt-4 hover:bg-[#F66C23]",
				className,
			)}
		>
			{text}
		</button>
	);
}
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
>>>>>>> 0180dae (Feat: 회원가입 로직 구현)

AuthInput.defaultProps = {
	className: "",
	error: "", // 에러 메시지 기본값 추가
};

AuthSubmitBtn.defaultProps = {
	className: "",
};
<<<<<<< HEAD
=======
>>>>>>> 1d4af31 (Feat: 회원가입 로직 구현)
>>>>>>> 0180dae (Feat: 회원가입 로직 구현)
