/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable jsx-a11y/no-noninteractive-element-to-interactive-role */
/* eslint-disable jsx-a11y/label-has-associated-control */
/* eslint-disable react/button-has-type */
import { useState, useEffect, ChangeEvent, useRef } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "@/assets/styles/mypage.css";
import Header from "@/components/header";
import { authService } from "@/services/userAuthService";
import { DetailUserInfo } from "@/types/user";
import editIcon from "@/assets/images/edit.png";
import profileImagePlaceholder from "@/assets/images/test.png";
import "@/assets/styles/shake.css";
import MyPageContent from "@/components/MyPageContent";

function MyPage() {
	const navigate = useNavigate();
	const location = useLocation();
	const [isEditing, setIsEditing] = useState(false);
	const [errors, setErrors] = useState({ nickname: "" });
	const [doShake, setDoShake] = useState(false);
	const fileInputRef = useRef<HTMLInputElement>(null);
	const formDataRef = useRef<FormData>(new FormData());
	const userInfoRef = useRef<DetailUserInfo | null>(null);
	const [profileImage, setProfileImage] = useState<string>(
		profileImagePlaceholder,
	);

	useEffect(() => {
		if (location.pathname === "/my-page") {
			navigate("/my-page/win-rate");
		}
	}, [location.pathname, navigate]);

	useEffect(() => {
		const fetchProfile = async () => {
			const response = await authService.getUserInfo();
			if (response.code === "success" && response.data) {
				userInfoRef.current = response.data;
				const imageUrl = await authService.getProfileImage(
					response.data.imgUrl || "",
				);
				setProfileImage(imageUrl || profileImagePlaceholder);
			}
		};

		if (!userInfoRef.current) {
			fetchProfile();
		}
	}, []);

	const handleEditClick = () => {
		if (isEditing) {
			// Cancel을 눌렀을 때 원래 상태로 복원
			const originalUserInfo = userInfoRef.current;
			if (originalUserInfo) {
				const resetImage = async () => {
					const imageUrl = await authService.getProfileImage(
						originalUserInfo.imgUrl || "",
					);
					setProfileImage(imageUrl || profileImagePlaceholder);
				};
				resetImage();
			}
			setErrors({ nickname: "" });
		}
		setIsEditing(!isEditing);
	};

	const handleSaveClick = async () => {
		try {
			const uploadResponse = await authService.uploadProfileImage(
				formDataRef.current,
			);
			if (uploadResponse.code === "success" && uploadResponse.data) {
				const imageUrl = await authService.getProfileImage(uploadResponse.data);
				setProfileImage(imageUrl || profileImagePlaceholder);
				if (userInfoRef.current) {
					userInfoRef.current.imgUrl = uploadResponse.data;
				}
			}

			if (userInfoRef.current) {
				await authService.updateUserProfile(userInfoRef.current);
			}
			setIsEditing(false);
		} catch (error) {
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
			formDataRef.current.set("file", file); // formData에 파일 설정
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

	if (!userInfoRef.current) {
		return <div>Loading...</div>;
	}

	const { email, nickname, rating } = userInfoRef.current;

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
					<div className="right-section">
						<div className="profile-info">
							<label>Email:</label>
							<input type="text" value={email} readOnly />
							<label>Nickname:</label>
							<div className="input-with-error">
								<input
									type="text"
									name="nickname"
									value={nickname}
									readOnly={!isEditing}
									// 주석 처리된 handleInputChange 부분
									// onChange={handleInputChange}
								/>
								{errors.nickname && (
									<div className="error-message">{errors.nickname}</div>
								)}
							</div>
							<div className="tier-section">
								<span className="tier-label">Tier</span>
								<div className="tier-bar">
									<div
										className="tier-progress"
										style={{ width: `${rating}%` }}
									/>
								</div>
							</div>
						</div>
					</div>
				</div>
				<MyPageContent />
			</div>
		</div>
	);
}

export default MyPage;
