import { NavLink } from "react-router-dom";
import "@/assets/styles/mypage.css";

function MyPageContent() {
	return (
		<div>
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
		</div>
	);
}

export default MyPageContent;
