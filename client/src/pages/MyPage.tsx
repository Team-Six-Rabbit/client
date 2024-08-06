/* eslint-disable react/button-has-type */
/* eslint-disable jsx-a11y/label-has-associated-control */
import { NavLink, Outlet } from "react-router-dom";
import "@/assets/styles/mypage.css";
import Header from "@/components/header";
import profileIcon from "@/assets/images/test.png";

function MyPage() {
	return (
		<div>
			<Header />
			<div className="mypage-container">
				<div className="mypage-header">
					<div className="left-section">
						<div className="profile-section">
							<img
								className="profile-img"
								src={profileIcon}
								alt="프로필 이미지"
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
							<input type="text" value="rabbit@battle-people.com" readOnly />
							<label>Nickname:</label>
							<input type="text" value="우아한 레빗츠" readOnly />
							<label>Password:</label>
							<input type="password" value="**********" readOnly />
							<button className="save-btn">Save</button>
						</div>
					</div>
				</div>
				<ul className="mypage-nav">
					<li>
						<NavLink
							to="/my-page/win-rate"
							className={({ isActive }) =>
								isActive ? "nav-link active" : "nav-link"
							}
						>
							Win Rate
						</NavLink>
					</li>
					<li>
						<NavLink
							to="/my-page/created-lives"
							className={({ isActive }) =>
								isActive ? "nav-link active" : "nav-link"
							}
						>
							Created Lives
						</NavLink>
					</li>
					<li>
						<NavLink
							to="/my-page/participated-votes"
							className={({ isActive }) =>
								isActive ? "nav-link active" : "nav-link"
							}
						>
							Participated Votes
						</NavLink>
					</li>
					<li>
						<NavLink
							to="/my-page/interests"
							className={({ isActive }) =>
								isActive ? "nav-link active" : "nav-link"
							}
						>
							Interests
						</NavLink>
					</li>
				</ul>
				<div className="mypage-content">
					<Outlet />
				</div>
			</div>
		</div>
	);
}

export default MyPage;
