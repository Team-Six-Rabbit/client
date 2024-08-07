package com.woowahanrabbits.battle_people.domain.battle.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AiThumbnailService {
	@Value("${openai.api.key}")
	private String apiKey;

	private static final String OPENAI_API_URL = "https://api.openai.com/v1/images/generations";

	public void generateImage(String prompt, String fileName) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(OPENAI_API_URL);

		// Set up headers
		// HttpHeaders headers = new HttpHeaders();
		// headers.set("Authorization", "Bearer " + apiKey);
		// headers.set("Content-Type", "application/json");

		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Authorization", "Bearer " + apiKey);

		String json = "{\"prompt\": \"" + prompt + "\", \"n\": 1, \"size\": \"1024x1024\"}";
		StringEntity entity = new StringEntity(json);
		httpPost.setEntity(entity);

		CloseableHttpResponse response = httpClient.execute(httpPost);
		String responseString = EntityUtils.toString(response.getEntity());

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(responseString);
		String imageBase64 = rootNode.get("data").get(0).get("b64_json").asText();

		byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
		try (FileOutputStream fos = new FileOutputStream(new File(fileName))) {
			fos.write(imageBytes);
		}
	}
}
