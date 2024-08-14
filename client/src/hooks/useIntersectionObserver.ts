import { useState, useEffect, useRef } from "react";

const useIntersectionObserver = (options: IntersectionObserverInit) => {
	const [isIntersecting, setIntersecting] = useState<boolean>(false);
	const ref = useRef<HTMLDivElement>(null);

	useEffect(() => {
		const observer = new IntersectionObserver(
			([entry]) => setIntersecting(entry.isIntersecting),
			options,
		);

		if (ref.current) {
			observer.observe(ref.current);
		}

		return () => {
			if (ref.current) {
				// eslint-disable-next-line react-hooks/exhaustive-deps
				observer.unobserve(ref.current);
			}
		};
	}, [options]);

	return { ref, isIntersecting };
};

export default useIntersectionObserver;
