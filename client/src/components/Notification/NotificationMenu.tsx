interface NotificationMenuProps {
	selectedMenu: string;
	onSelectMenu: (menu: string) => void;
}

function NotificationMenu({
	selectedMenu,
	onSelectMenu,
}: NotificationMenuProps) {
	return (
		<div className="border-solid border-royalBlue border-4 rounded-md text-white">
			<button
				type="button"
				className={`block w-full text-left py-2 px-4 pr-12 ${selectedMenu === "Notify" ? "bg-royalBlue" : "bg-white text-royalBlue"}`}
				onClick={() => onSelectMenu("Notify")}
			>
				Notify
			</button>
			<button
				type="button"
				className={`block w-full text-left py-2 px-4 pr-12 ${selectedMenu === "Live" ? "bg-royalBlue" : "bg-white text-royalBlue"}`}
				onClick={() => onSelectMenu("Live")}
			>
				Live
			</button>
			<button
				type="button"
				className={`block w-full text-left py-2 px-4 pr-12 ${selectedMenu === "Invite" ? "bg-royalBlue" : "bg-white text-royalBlue"}`}
				onClick={() => onSelectMenu("Invite")}
			>
				Invite
			</button>
			<button
				type="button"
				className={`block w-full text-left py-2 px-4 pr-12 ${selectedMenu === "Punishment" ? "bg-royalBlue" : "bg-white text-royalBlue"}`}
				onClick={() => onSelectMenu("Punishment")}
			>
				Punishment
			</button>
		</div>
	);
}

export default NotificationMenu;
