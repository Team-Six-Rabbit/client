import { NavLink } from "react-router-dom";
import "@/assets/styles/mypage.css";

function MyPageContent() {
	return (
		<ul className="mypage-nav">
			<li>
				<NavLink
					to="/profile/win-rate"
					className={({ isActive }) =>
						isActive ? "nav-link active" : "nav-link"
					}
				>
					Win Rate
				</NavLink>
			</li>
			<li>
				<NavLink
					to="/profile/created-lives"
					className={({ isActive }) =>
						isActive ? "nav-link active" : "nav-link"
					}
				>
					Created Lives
				</NavLink>
			</li>
			<li>
				<NavLink
					to="/profile/participated-votes"
					className={({ isActive }) =>
						isActive ? "nav-link active" : "nav-link"
					}
				>
					Participated Votes
				</NavLink>
			</li>
			<li>
				<NavLink
					to="/profile/interests"
					className={({ isActive }) =>
						isActive ? "nav-link active" : "nav-link"
					}
				>
					Interests
				</NavLink>
			</li>
		</ul>
	);
}

export default MyPageContent;
