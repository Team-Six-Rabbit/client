import * as faceapi from "face-api.js";
import { RefObject, useEffect, useRef, useState } from "react";

const useFaceApi = (
	isPublisher: boolean,
	video: RefObject<HTMLVideoElement>,
	canvas: RefObject<HTMLCanvasElement>,
) => {
	const [isReady, setIsReady] = useState<boolean>(false);
	const interval = useRef<unknown>();
	const stream = useRef<MediaStream>();

	const SSD_MOBILENETV1 = "ssd_mobilenetv1";
	const TINY_FACE_DETECTOR = "tiny_face_detector";

	// ssd_mobilenetv1 options
	const minConfidence = 0.5;

	// tiny_face_detector options
	const inputSize = 512;
	const scoreThreshold = 0.5;

	let selectedFaceDetector = TINY_FACE_DETECTOR;
	selectedFaceDetector = TINY_FACE_DETECTOR;

	const clear = () => {
		if (interval.current) clearInterval(interval.current as number);
		interval.current = undefined;
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
	) => {
		const ctx = canvas.current!.getContext("2d")!;
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

		// Simulate eye blink
		if (Math.random() > 0.95) {
			ctx.fillStyle = "blue";
			ctx.fillRect(
				leftEye.x - faceWidth * 0.1,
				leftEye.y - faceWidth * 0.1,
				faceWidth * 0.2,
				faceWidth * 0.2,
			);
			ctx.fillRect(
				rightEye.x - faceWidth * 0.1,
				rightEye.y - faceWidth * 0.1,
				faceWidth * 0.2,
				faceWidth * 0.2,
			);
		}

		// Simulate mouth movement
		if (Math.random() > 0.95) {
			ctx.clearRect(
				mouthLeft.x,
				mouthTop.y,
				mouthRight.x - mouthLeft.x,
				mouthBottom.y - mouthTop.y,
			);

			// Upper lip movement
			ctx.fillStyle = "red";
			ctx.fillRect(
				mouthLeft.x,
				mouthTop.y - faceHeight * 0.03,
				mouthRight.x - mouthLeft.x,
				(mouthBottom.y - mouthTop.y) / 2,
			);

			// Lower lip movement
			ctx.fillStyle = "white";
			ctx.fillRect(
				mouthLeft.x,
				mouthTop.y + (mouthBottom.y - mouthTop.y) / 2 - faceHeight * 0.03,
				mouthRight.x - mouthLeft.x,
				(mouthBottom.y - mouthTop.y) / 2,
			);
		}
	};

	const onPlay = () => {
		interval.current = setInterval(async () => {
			if (
				!video.current ||
				// video.current?.paused ||
				// video.current?.ended ||
				!isFaceDetectionModelLoaded()
			)
				return;

			const options = getFaceDetectorOptions();
			const result = await faceapi
				.detectSingleFace(video.current!, options)
				.withFaceLandmarks();

			if (result) {
				const dims = faceapi.matchDimensions(
					canvas.current!,
					{ width: 940, height: 650 },
					true,
				);
				const resizedResult = faceapi.resizeResults(result, dims);
				render2DCharacter(resizedResult);
			}
		}, 100);
	};

	const loadModel = () => {
		return Promise.all([
			faceapi.nets.tinyFaceDetector.loadFromUri("/weights"),
			faceapi.nets.faceLandmark68Net.loadFromUri("/weights"),
			faceapi.nets.faceRecognitionNet.loadFromUri("/weights"),
			faceapi.nets.faceExpressionNet.loadFromUri("/weights"),
		]).then(() => {
			setIsReady(true);
		});
	};

	const startVideo = async () => {
		stream.current = await navigator.mediaDevices.getUserMedia({
			video: true,
			audio: true,
		});
		// eslint-disable-next-line no-param-reassign
		video.current!.srcObject = stream.current;
		return stream.current;
	};

	useEffect(() => {
		if (!isPublisher || isFaceDetectionModelLoaded()) return;

		startVideo()
			.then(() => loadModel())
			.then(() => onPlay());
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [isPublisher]);

	return { isReady };
};

export default useFaceApi;
