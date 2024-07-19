import axios, { AxiosError, InternalAxiosRequestConfig } from "axios";

interface CustomAxiosRequestConfig extends InternalAxiosRequestConfig {
	retry?: boolean;
}
const baseURL = import.meta.env.VITE_API_BASE_URL;

const axiosInstance = axios.create({
	baseURL,
	timeout: 3000,
	headers: {
		"Content-Type": "application/json",
	},
	withCredentials: true,
});

const onRequest = (
	config: CustomAxiosRequestConfig,
): CustomAxiosRequestConfig => {
	return config;
};

axiosInstance.interceptors.request.use(onRequest);

const onResponseError = (error: AxiosError | Error) => {
	if (axios.isAxiosError(error)) {
		const originalRequest = error.config! as CustomAxiosRequestConfig;

		if (error.response?.status === 401 && !originalRequest.retry) {
			originalRequest.retry = true;

			return new Promise((resolve, reject) => {
				axiosInstance
					.post("/auth/refresh")
					.then(() => {
						resolve(axiosInstance(originalRequest));
					})
					.catch((err) => {
						reject(err);
					});
			});
		}
	}
	console.log(`🚨 [API] | Error ${error.message}`);
	return Promise.reject(error);
};

axiosInstance.interceptors.response.use(
	(response) => response,
	onResponseError,
);

// Axios 인스턴스 익스포트
export default axiosInstance;
