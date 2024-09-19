package com.mavericksstube.maverickshub.services;

import com.mavericksstube.maverickshub.config.MailConfig;
import com.mavericksstube.maverickshub.dtos.requests.BrevoMailRequest;
import com.mavericksstube.maverickshub.dtos.requests.Recipient;
import com.mavericksstube.maverickshub.dtos.requests.SendMailRequest;
import com.mavericksstube.maverickshub.dtos.requests.Sender;
import com.mavericksstube.maverickshub.dtos.response.BrevoMailResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@AllArgsConstructor
public class MavericsHubMailService implements MailService{

    private final MailConfig mailConfig;

    @Override
    public String sendMail(SendMailRequest mailRequest) {

        RestTemplate restTemplate = new RestTemplate();
        String url = mailConfig.getMailApiUrl();

        BrevoMailRequest request = new BrevoMailRequest();
        request.setSubject(mailRequest.getSubject());
        request.setSender(new Sender());
        request.setRecipients(List.of(new Recipient(mailRequest.getRecipientName(), mailRequest.getRecipientEmail())));
        request.setContent(mailRequest.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.set("api-key", mailConfig.getMailApiKey());
        headers.set("accept", APPLICATION_JSON.toString());
        RequestEntity<?> httpRequest = new RequestEntity<>(request, headers, HttpMethod.POST, URI.create(url));
        ResponseEntity<BrevoMailResponse> response = restTemplate.postForEntity(url, httpRequest, BrevoMailResponse.class);
        if (response.getBody()!=null && response.getStatusCode() == HttpStatusCode.valueOf(201)) return "mail sent successfully";
        throw new RuntimeException("email sending failed");
    }
}
