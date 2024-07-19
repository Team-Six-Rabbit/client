import classNames from "classnames";

interface AuthSubmitBtnProps {
	text: string;
	className?: string;
}

interface AuthInputProps {
	label: string;
	name: string;
	type: string;
	placeholder: string;
	className?: string;
}

export function AuthInput({
	label,
	name,
	type,
	placeholder,
	className,
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
					className={classNames(
						"w-full p-2 border-4 border-black rounded-xl focus:outline-none focus:border-[#F66C23] focus:shadow-[0_0_0_3px_rgba(246,108,35,0.3)]",
						className,
					)}
				/>
			</label>
		</div>
	);
}

export function AuthSubmitBtn({ text, className }: AuthSubmitBtnProps) {
	return (
		<button
			type="button"
			className={classNames(
				"w-full h-13 p-2 bg-black text-white text-2xl rounded-2xl mt-4 hover:bg-[#F66C23]",
				className,
			)}
		>
			{text}
		</button>
	);
}

AuthInput.defaultProps = {
	className: "",
};

AuthSubmitBtn.defaultProps = {
	className: "",
};
