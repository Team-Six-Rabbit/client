import profileIcon from "@/assets/images/profile.png";
import searchIcon from "@/assets/images/search.png";
import notificationIcon from "@/assets/images/notification.png";
import brandIcon from "@/assets/images/Logo.png";
import { Link, useNavigate } from "react-router-dom";
import { Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/react";
import { useAuthStore } from "@/stores/userAuthStore";
import { MouseEventHandler } from "react";
import { logout } from "@/services/userAuthService";

interface DropDownMenuItem {
	link: string;
	text: string;
	onClick?: MouseEventHandler;
}

export function ProfileBtn() {
	const { isLogin } = useAuthStore();
	const navigator = useNavigate();

	const doLogout = async () => {
		try {
			await logout();
		} catch (err) {
			console.error("로그아웃 실패");
		} finally {
			navigator("/");
		}
	};

	const dropdownBeforeLogin: DropDownMenuItem[] = [
		{ link: "/login", text: "로그인" },
		{ link: "/join", text: "회원가입" },
	];

	const dropdownAfterLogin: DropDownMenuItem[] = [
		{ link: "/my-page", text: "마이페이지" },
		{ link: "/logout", text: "로그아웃", onClick: doLogout },
	];

	const menuItems = isLogin ? dropdownAfterLogin : dropdownBeforeLogin;

	return (
		<Menu as="div" className="size-8 relative inline-block text-left">
			<MenuButton className="size-8 inline-flex justify-center text-sm font-semibold text-gray-900 shadow-sm hover:scale-105">
				<img className="size-8" src={profileIcon} alt="프로필 이미지" />
			</MenuButton>

			<MenuItems
				anchor="bottom end"
				transition
				className="absolute right-0 z-50 mt-2 w-40 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 transition focus:outline-none data-[closed]:scale-95 data-[closed]:transform data-[closed]:opacity-0 data-[enter]:duration-100 data-[leave]:duration-75 data-[enter]:ease-out data-[leave]:ease-in"
			>
				<div className="py-1">
					{menuItems.map((item) => (
						<MenuItem key={item.link}>
							<Link
								to={item.link}
								onClick={item.onClick}
								className="block px-4 py-2 text-sm text-gray-700 data-[focus]:bg-gray-100 data-[focus]:text-gray-900"
							>
								{item.text}
							</Link>
						</MenuItem>
					))}
				</div>
			</MenuItems>
		</Menu>
	);
}

function Logo() {
	return (
		<Link
			to="/"
			className="flex items-center no-underline hover:text-white text-white text-2xl font-[BMHANNA\_11yrs] space-x-2 mr-8 lg:mr-16"
		>
			<img className="h-[35px]" src={brandIcon} alt="로고" />
			<span>배틀의 민족</span>
		</Link>
	);
}

function LeftHeader() {
	return (
		<>
			<Logo />
			<div className="flex space-x-4 lg:space-x-8 text-white text-lg">
				<Link className="text-white hover:text-gray-400" to="/firework">
					불구경
				</Link>
				<Link className="text-white hover:text-gray-400" to="/fanning">
					부채질
				</Link>
				<Link className="text-white hover:text-gray-400" to="/bonfire">
					모닥불
				</Link>
			</div>
		</>
	);
}

function SearchBar() {
	return (
		<div className="text-base relative w-full max-w-[300px] lg:max-w-96 h-[35px] flex items-center bg-[#fff] border-[2px] border-solid border-[#fff] rounded-[10px]">
			<input
				type="text"
				className="w-full h-full pl-4 pr-10 bg-transparent border-none outline-none"
				placeholder="검색"
			/>
			<img
				className="absolute right-2 size-5"
				src={searchIcon}
				alt="검색 아이콘"
			/>
		</div>
	);
}

function RightHeader() {
	return (
		<div className="flex items-center justify-end space-x-2 lg:space-x-4 w-full max-w-screen-sm ml-auto">
			<SearchBar />
			<button
				type="button"
				className="size-8 btn hover:scale-105"
				onClick={() => {
					alert("Notification clicked"); // TODO: 알림 버튼 클릭시 수행 기능
				}}
			>
				<img className="size-8" src={notificationIcon} alt="알림 버튼" />
			</button>
			<ProfileBtn />
		</div>
	);
}

function Header() {
	return (
		<div className="fixed top-0 left-0 w-full h-[68px] bg-[#000] overflow-hidden z-50">
			<div className="flex items-center h-full px-4 lg:px-8 hover:none">
				<LeftHeader />
				<RightHeader />
			</div>
		</div>
	);
}

export default Header;
