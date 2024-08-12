import * as faceapi from "face-api.js";
import { RefObject, useEffect, useRef, useState } from "react";

const useFaceApi = (
	isPublisher: boolean,
	video: RefObject<HTMLVideoElement>,
	canvas: RefObject<HTMLCanvasElement>,
) => {
	const [isReady, setIsReady] = useState<boolean>(false);
	const shouldRenderVideo = useRef<boolean>(true);
	const shouldRenderMask = useRef<boolean>(false);
	const timeout = useRef<unknown>();
	const stream = useRef<MediaStream>();

	const SSD_MOBILENETV1 = "ssd_mobilenetv1";
	const TINY_FACE_DETECTOR = "tiny_face_detector";

	// ssd_mobilenetv1 options
	const minConfidence = 0.5;

	// tiny_face_detector options
	const inputSize = 224;
	const scoreThreshold = 0.4;

	let selectedFaceDetector = TINY_FACE_DETECTOR;
	selectedFaceDetector = TINY_FACE_DETECTOR;

	const clear = () => {
		if (timeout.current) clearTimeout(timeout.current as number);
		timeout.current = undefined;
		if (stream.current) {
			stream.current.getTracks().forEach((track) => {
				track.stop();
				stream.current!.removeTrack(track);
			});
			stream.current = undefined;
		}
		setIsReady(false);
	};

	useEffect(() => {
		return clear;
	}, []);

	const getCurrentFaceDetectionNet = () => {
		if (selectedFaceDetector === SSD_MOBILENETV1) {
			return faceapi.nets.ssdMobilenetv1;
		}
		if (selectedFaceDetector === TINY_FACE_DETECTOR) {
			return faceapi.nets.tinyFaceDetector;
		}
		throw new Error("Invalid face detector model");
	};

	const isFaceDetectionModelLoaded = () => {
		return !!getCurrentFaceDetectionNet().params;
	};

	const getFaceDetectorOptions = () => {
		return selectedFaceDetector === SSD_MOBILENETV1
			? new faceapi.SsdMobilenetv1Options({ minConfidence })
			: new faceapi.TinyFaceDetectorOptions({ inputSize, scoreThreshold });
	};

	const render2DCharacter = (
		detection: faceapi.WithFaceLandmarks<{
			detection: faceapi.FaceDetection;
		}>,
		ctx: CanvasRenderingContext2D,
	) => {
		const { positions } = detection.landmarks;

		const leftEye = positions[36];
		const rightEye = positions[45];
		const mouthLeft = positions[48];
		const mouthRight = positions[54];
		const mouthTop = positions[51];
		const mouthBottom = positions[57];
		// const nose = positions[30];

		const faceWidth = detection.alignedRect.box.width;
		const faceHeight = detection.alignedRect.box.height;

		const faceX = detection.alignedRect.box.x;
		const faceY = detection.alignedRect.box.y;

		ctx.fillStyle = "blue";
		ctx.fillRect(faceX, faceY, faceWidth, faceHeight);

		// Draw eyes
		ctx.fillStyle = "white";
		ctx.beginPath();
		ctx.arc(leftEye.x, leftEye.y, faceWidth * 0.1, 0, Math.PI * 2);
		ctx.arc(rightEye.x, rightEye.y, faceWidth * 0.1, 0, Math.PI * 2);
		ctx.fill();

		// Draw pupils
		ctx.fillStyle = "black";
		ctx.beginPath();
		ctx.arc(leftEye.x, leftEye.y, faceWidth * 0.05, 0, Math.PI * 2);
		ctx.arc(rightEye.x, rightEye.y, faceWidth * 0.05, 0, Math.PI * 2);
		ctx.fill();

		// Draw upper lip
		ctx.fillStyle = "red";
		ctx.fillRect(
			mouthLeft.x,
			mouthTop.y,
			mouthRight.x - mouthLeft.x,
			(mouthBottom.y - mouthTop.y) / 2,
		);

		// Draw lower lip
		ctx.fillStyle = "white";
		ctx.fillRect(
			mouthLeft.x,
			mouthTop.y + (mouthBottom.y - mouthTop.y) / 2,
			mouthRight.x - mouthLeft.x,
			(mouthBottom.y - mouthTop.y) / 2,
		);
	};

	const renderVideoToCanvas = (ctx: CanvasRenderingContext2D) => {
		if (!video.current!.paused && !video.current!.ended) {
			ctx.drawImage(
				video.current!,
				0,
				0,
				canvas.current!.width,
				canvas.current!.height,
			);
		}
	};

	const onPlay = async () => {
		if (
			!video.current ||
			(shouldRenderMask.current && !isFaceDetectionModelLoaded())
		) {
			console.error(
				video.current,
				video.current?.paused,
				video.current?.ended,
				shouldRenderMask.current,
				isFaceDetectionModelLoaded(),
			);
			timeout.current = setTimeout(() => onPlay(), 1000);
			return timeout.current;
		}

		let resizedResult;
		if (shouldRenderMask.current) {
			const options = getFaceDetectorOptions();
			console.time("onPlay Execution Time");
			const result = await faceapi
				.detectSingleFace(video.current!, options)
				.withFaceLandmarks(true);
			console.timeEnd("onPlay Execution Time");

			if (result) {
				const dims = faceapi.matchDimensions(
					canvas.current!,
					{ width: 640, height: 480 },
					true,
				);
				resizedResult = faceapi.resizeResults(result, dims);
			}
		}

		const ctx = canvas.current!.getContext("2d")!;
		if (shouldRenderVideo.current) renderVideoToCanvas(ctx);
		if (resizedResult) render2DCharacter(resizedResult, ctx);

		timeout.current = setTimeout(() => onPlay(), 0);
		return timeout.current;
	};

	const loadModel = () => {
		return Promise.all([
			faceapi.nets.tinyFaceDetector.loadFromUri("/weights"),
			faceapi.nets.faceLandmark68TinyNet.loadFromUri("/weights"),
		]);
	};

	const startVideo = async () => {
		stream.current = await navigator.mediaDevices.getUserMedia({
			video: true,
		});
		// eslint-disable-next-line no-param-reassign
		video.current!.srcObject = stream.current;
		return stream.current;
	};

	const drawMask = async () => {
		if (!isFaceDetectionModelLoaded()) await loadModel();
		setIsReady(true);
		onPlay();
	};

	useEffect(() => {
		if (!isPublisher) return;

		startVideo();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [isPublisher]);

	return { drawMask, isReady, shouldRenderVideo, shouldRenderMask };
};

export default useFaceApi;
