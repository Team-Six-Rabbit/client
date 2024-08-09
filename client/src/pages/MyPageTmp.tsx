/* eslint-disable jsx-a11y/no-noninteractive-element-to-interactive-role */
/* eslint-disable react/button-has-type */
/* eslint-disable jsx-a11y/label-has-associated-control */
import { useState, useEffect, ChangeEvent, useRef } from "react";
import { Outlet, useNavigate, useLocation } from "react-router-dom";
import "@/assets/styles/mypage.css";
import Header from "@/components/header";
import { authService } from "@/services/userAuthService";
import { DetailUserInfo } from "@/types/user";
import editIcon from "@/assets/images/edit.png";
import profileImagePlaceholder from "@/assets/images/test.png";
import "@/assets/styles/shake.css";
import { useAuthStore } from "@/stores/userAuthStore";
import MyPageContent from "@/components/MyPageContent";

function MyPage() {
	const navigate = useNavigate();
	const location = useLocation();
	const [userInfo, setUserInfo] = useState<DetailUserInfo | null>(null);
	const [originalUserInfo, setOriginalUserInfo] =
		useState<DetailUserInfo | null>(null);
	const [isEditing, setIsEditing] = useState(false);
	const [errors, setErrors] = useState({ nickname: "" });
	const [doShake, setDoShake] = useState(false);
	const fileInputRef = useRef<HTMLInputElement>(null);
	const [profileImage, setProfileImage] = useState<string>(
		profileImagePlaceholder,
	);

	const { user } = useAuthStore();

	useEffect(() => {
		if (location.pathname === "/my-page") {
			navigate("/my-page/win-rate");
		}
	}, [location.pathname, navigate]);

	useEffect(() => {
		const fetchProfile = async () => {
			if (user) {
				console.log(user);
				setUserInfo(user);
				const imageUrl = await authService.getProfileImage(user.imgUrl || "");
				setProfileImage(imageUrl || profileImagePlaceholder);
				setOriginalUserInfo(user);
			}
		};
		fetchProfile();
	}, [user]);

	const handleEditClick = () => {
		if (isEditing) {
			// Cancel을 눌렀을 때 원래 상태로 복원
			setUserInfo(originalUserInfo);
			setProfileImage(originalUserInfo?.imgUrl || profileImagePlaceholder);
			setErrors({ nickname: "" });
		}
		setIsEditing(!isEditing);
	};

	const handleSaveClick = async () => {
		if (errors.nickname) {
			setDoShake(true);
			setTimeout(() => setDoShake(false), 500);
			return;
		}

		try {
			const formData = new FormData();
			formData.append("nickname", userInfo!.nickname);

			if (fileInputRef.current && fileInputRef.current.files?.length) {
				formData.append("profileImage", fileInputRef.current.files[0]);
			}

			const isNicknameAvailable = await authService.checkNicknameAvailability(
				userInfo!.nickname,
			);
			if (!isNicknameAvailable) {
				setErrors({ nickname: "이미 사용 중인 닉네임입니다." });
				setDoShake(true);
				setTimeout(() => setDoShake(false), 500);
				return;
			}

			const uploadResponse = await authService.uploadProfileImage(formData);
			if (uploadResponse.code === "success" && uploadResponse.data) {
				const imageUrl = await authService.getProfileImage(uploadResponse.data);
				setProfileImage(imageUrl || profileImagePlaceholder);
				setUserInfo((prevState) => ({
					...prevState!,
					imgUrl: uploadResponse.data || "",
				}));
			}

			await authService.updateUserProfile(userInfo!);
			setIsEditing(false);
			setOriginalUserInfo(userInfo);
		} catch (error) {
			setErrors({ nickname: "닉네임 중복 확인 중 오류가 발생했습니다." });
			setDoShake(true);
			setTimeout(() => setDoShake(false), 500);
		}
	};

	const handleIconClick = () => {
		if (isEditing && fileInputRef.current) {
			fileInputRef.current.click();
		}
	};

	const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
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

	const handleInputChange = async (event: ChangeEvent<HTMLInputElement>) => {
		const { name, value } = event.target;

		setUserInfo((prevState) => ({
			...prevState!,
			[name]: value,
		}));

		if (name === "nickname") {
			if (value === originalUserInfo?.nickname) {
				setErrors({ nickname: "" });
				return;
			}

			try {
				const isNicknameAvailable =
					await authService.checkNicknameAvailability(value);
				if (!isNicknameAvailable) {
					setErrors({ nickname: "이미 사용 중인 닉네임입니다." });
				} else {
					setErrors({ nickname: "" });
				}
			} catch (error) {
				setErrors({ nickname: "닉네임 중복 확인 중 오류가 발생했습니다." });
			}
		}
	};

	if (!userInfo) {
		return <div>Loading...</div>;
	}

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
									src={profileImage || profileImagePlaceholder}
									alt="프로필 이미지"
									onClick={handleIconClick}
									onKeyDown={(e) => {
										if (e.key === "Enter" || e.key === " ") {
											handleIconClick();
										}
									}}
									role="button"
									tabIndex={isEditing ? 0 : -1}
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
										role="button"
										tabIndex={0}
									/>
								)}
							</div>
							<input
								type="file"
								ref={fileInputRef}
								style={{ display: "none" }}
								onChange={handleFileChange}
								accept="image/*"
							/>
							<div className="tier-section">
								<span className="tier-label">Tier</span>
								<div className="tier-bar">
									<div
										className="tier-progress"
										style={{ width: `${userInfo.rating}%` }}
									/>
								</div>
							</div>
						</div>
					</div>
					<div className="right-section">
						<div className="profile-info">
							<label>Email:</label>
							<input type="text" value={userInfo.email} readOnly />
							<label>Nickname:</label>
							<div className="input-with-error">
								<input
									type="text"
									name="nickname"
									value={userInfo.nickname}
									readOnly={!isEditing}
									onChange={handleInputChange}
								/>
								{errors.nickname && (
									<div className="error-message">{errors.nickname}</div>
								)}
							</div>
						</div>
						<div className="button-group">
							<button className="edit-btn" onClick={handleEditClick}>
								{isEditing ? "Cancel" : "Edit"}
							</button>
							{isEditing && (
								<button
									className={`save-btn ${doShake ? "shake" : ""}`}
									onClick={handleSaveClick}
								>
									Save
								</button>
							)}
						</div>
					</div>
				</div>
				<MyPageContent />
				<div className="mypage-content">
					<Outlet />
				</div>
			</div>
		</div>
	);
}

export default MyPage;
