const NotifyCode = new Map<number, string>([
	[2, "Notify"], // 서버에서 주지 않지만 메뉴를 위해 할당한 값
	[1, "Live"],
	[0, "Invite"],
	// [3, "Punishment"],
]);

export default NotifyCode;
