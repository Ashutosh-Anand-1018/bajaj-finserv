package com.example.bajaj_finserv_health;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class BajajFinservHealthApplication implements ApplicationRunner {

	private final RestTemplate restTemplate;

	public BajajFinservHealthApplication(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(BajajFinservHealthApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		try {
			String generateWebhookUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

			Map<String, String> requestBody = new HashMap<>();
			requestBody.put("name", "Ashutosh Anand");
			requestBody.put("regNo", "22BCE0205");
			requestBody.put("email", "ashutoshanand120704@gmail.com");

			HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, createJsonHeaders());

			ResponseEntity<Map> response = restTemplate.postForEntity(generateWebhookUrl, requestEntity, Map.class);
			Map<String, Object> responseBody = response.getBody();

			System.out.println("Full API Response: " + responseBody);

			String webhookUrl = (String) responseBody.get("webhook");
			String accessToken = (String) responseBody.get("accessToken");

			System.out.println("Webhook URL: " + webhookUrl);
			System.out.println("Access Token: " + accessToken);

			if (webhookUrl == null || webhookUrl.isEmpty()) {
				System.err.println("Error: Webhook URL not received. Cannot submit the query.");
				return;
			}

			String finalQuery = "SELECT p.AMOUNT AS SALARY, CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, " +
					"TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE, " +
					"d.DEPARTMENT_NAME AS DEPARTMENT_NAME " +
					"FROM PAYMENTS p " +
					"JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID " +
					"JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
					"WHERE DAY(p.PAYMENT_TIME) <> 1 " +
					"ORDER BY p.AMOUNT DESC " +
					"LIMIT 1;";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(accessToken);

			Map<String, String> submissionBody = new HashMap<>();
			submissionBody.put("finalQuery", finalQuery);

			HttpEntity<Map<String, String>> submissionEntity = new HttpEntity<>(submissionBody, headers);

			ResponseEntity<String> submissionResponse = restTemplate.postForEntity(webhookUrl, submissionEntity, String.class);

			System.out.println("Submission Status Code: " + submissionResponse.getStatusCode());
			System.out.println("Submission Response: " + submissionResponse.getBody());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Helper method to create headers for the initial request
	private HttpHeaders createJsonHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}