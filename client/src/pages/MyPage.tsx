/* eslint-disable react/button-has-type */
/* eslint-disable jsx-a11y/label-has-associated-control */
import React, { useState, useEffect, useRef, ChangeEvent } from "react"; // , ChangeEvent
import { Outlet, useNavigate, useLocation } from "react-router-dom";
import "@/assets/styles/mypage.css";
import Header from "@/components/header";
import Content from "@/components/MyPageContent";
import profileIcon from "@/assets/images/test.png";
import editIcon from "@/assets/images/edit.png";
import { authService } from "@/services/userAuthService";
import "@/assets/styles/shake.css";

interface NickNameAndPassword {
	nickname: string;
	password: string;
}

function MyPage() {
	// 마이페이지 첫 화면 설정
	const navigate = useNavigate();
	const location = useLocation();
	useEffect(() => {
		if (location.pathname === "/profile") {
			navigate("/profile/win-rate");
		}
	}, [location.pathname, navigate]);

	const [isEditing, setIsEditing] = useState(false);
	const [profileImage, setProfileImage] = useState(profileIcon);
	const [originalProfileImage, setOriginalProfileImage] = useState(profileIcon);
	const email = "asdf@asdf.com";
	const [nickname, setNickname] = useState("우아한 레빗츠");
	const [password, setPassword] = useState("**********");
	const [originalNickname, setOriginalNickname] = useState(nickname);
	const [originalPassword, setOriginalPassword] = useState(password);
	const fileInputRef = useRef<HTMLInputElement>(null);

	const [formValues, setFormValues] = useState<NickNameAndPassword>({
		nickname: "",
		password: "",
	});

	const [errors, setErrors] = useState({
		nickname: "",
		password: "",
	});
	const [doShake, setDoShake] = useState<boolean>(false);

	const handleEditClick = () => {
		if (isEditing) {
			// If editing is already enabled, reset fields to original values
			setProfileImage(originalProfileImage);
			setNickname(originalNickname);
			setPassword(originalPassword);
			// setFormValues({
			// 	nickname: originalNickname,
			// 	password: originalPassword,
			// });
		} else {
			// Save current values as original before enabling editing
			setOriginalProfileImage(profileImage);
			setOriginalNickname(nickname);
			setOriginalPassword(password);
		}
		setIsEditing(!isEditing);
	};

	const handleSaveClick = async () => {
		const hasErrors = Object.values(errors).some((error) => error !== "");
		const hasEmptyFields = Object.values(formValues).some(
			(value) => value === "",
		);

		if (hasErrors || hasEmptyFields) {
			setErrors({
				nickname:
					formValues.nickname === ""
						? "닉네임을 입력해주세요."
						: errors.nickname,
				password:
					formValues.password === ""
						? "비밀번호를 입력해주세요."
						: errors.password,
			});
			setDoShake(true);
			setTimeout(() => {
				setDoShake(false);
			}, 500);
			return;
		}

		try {
			// Perform the save action (e.g., API request to save user info)
			console.log("Saving user information", formValues);
		} catch (err) {
			console.error("유저 수정 실패");
			setDoShake(true);
			setTimeout(() => {
				setDoShake(false);
			}, 500);
		}
		setIsEditing(false);
	};

	const handleIconClick = () => {
		if (isEditing && fileInputRef.current) {
			fileInputRef.current.click(); // trigger file input click
		}
	};

	const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		const file = event.target.files?.[0];
		if (file) {
			const reader = new FileReader();
			reader.onload = () => {
				if (reader.result) {
					setProfileImage(reader.result as string);
				}
			};
			reader.readAsDataURL(file);
		}

		if (fileInputRef.current) {
			fileInputRef.current.value = "";
		}
	};

	const validatePassword = (password: string) => {
		const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,16}$/;
		return passwordRegex.test(password);
	};

	const handleInputChange = async (event: ChangeEvent<HTMLInputElement>) => {
		const { name, value } = event.target;
		setFormValues({
			...formValues,
			[name]: value,
		});

		let errorMsg = "";

		switch (name) {
			case "nickname":
				if (value) {
					try {
						const isNicknameAvailable =
							await authService.checkNicknameAvailability(value);
						if (!isNicknameAvailable) {
							errorMsg = "이미 사용 중인 닉네임입니다.";
						}
					} catch (error) {
						errorMsg = "닉네임 중복 확인 중 오류가 발생했습니다.";
					}
				}
				break;
			case "password":
				if (!validatePassword(value)) {
					errorMsg = "8~16자 영문 대소문자, 숫자를 포함해야 합니다.";
				}
				break;
			default:
				break;
		}

		setErrors((prevErrors) => ({
			...prevErrors,
			[name]: errorMsg,
		}));
	};

	return (
		<div>
			<Header />
			<div className="mypage-container">
				<div className="mypage-header">
					<div className="left-section">
						<div className="profile-section">
							<div className="profile-img-container">
								<img
									className={`profile-img ${isEditing ? "darken" : ""}`}
									src={profileImage}
									alt="프로필 이미지"
									onClick={handleIconClick}
									onKeyDown={(e) => {
										if (e.key === "Enter" || e.key === " ") {
											handleIconClick();
										}
									}}
									// eslint-disable-next-line jsx-a11y/no-noninteractive-element-to-interactive-role
									role="button"
									tabIndex={isEditing ? 0 : -1} // only focusable when editing
								/>
								{isEditing && (
									<img
										className="edit-icon"
										src={editIcon}
										alt="편집 아이콘"
										onClick={handleIconClick}
										onKeyDown={(e) => {
											if (e.key === "Enter" || e.key === " ") {
												handleIconClick();
											}
										}}
										// eslint-disable-next-line jsx-a11y/no-noninteractive-element-to-interactive-role
										role="button"
										tabIndex={0}
									/>
								)}
							</div>
							<input
								type="file"
								ref={fileInputRef} // reference to the file input
								style={{ display: "none" }}
								onChange={handleFileChange} // handle file change
								accept="image/*"
							/>
							<div className="tier-section">
								<span className="tier-label">Tier</span>
								<div className="tier-bar">
									<div className="tier-progress" style={{ width: "70%" }} />
								</div>
							</div>
						</div>
					</div>
					<div className="right-section">
						<div className="profile-info">
							<label>Email:</label>
							<input type="text" value={email} readOnly />
							<label>Nickname:</label>
							<input
								type="text"
								name="nickname"
								value={formValues.nickname}
								readOnly={!isEditing}
								onChange={handleInputChange}
							/>
							{errors.nickname && (
								<div className="error-message">{errors.nickname}</div>
							)}
							<label>Password:</label>
							<input
								type="password"
								name="password"
								value={formValues.password}
								readOnly={!isEditing}
								onChange={handleInputChange}
							/>
							{errors.password && (
								<div className="error-message">{errors.password}</div>
							)}
							<div className="button-group">
								<button className="edit-btn" onClick={handleEditClick}>
									{isEditing ? "Cancel" : "Edit"}
								</button>
								<button
									className={doShake ? "shake save-btn" : "save-btn"}
									onClick={handleSaveClick}
								>
									Save
								</button>
							</div>
						</div>
					</div>
				</div>
				<Content />

				<div className="mypage-content">
					<Outlet />
				</div>
			</div>
		</div>
	);
}

export default MyPage;
