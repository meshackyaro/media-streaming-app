package com.mavericksstube.maverickshub;

import com.mavericksstube.maverickshub.dtos.requests.SendMailRequest;
import com.mavericksstube.maverickshub.services.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendEmail(){

        String email = "miishaqhyaro@gmail.com";
        SendMailRequest sendMailRequest = new SendMailRequest();
        sendMailRequest.setRecipientEmail("miishaqhyaro@gmail.com");
        sendMailRequest.setRecipientName("Mesh");
        sendMailRequest.setContent("<p>Hello World!</p>");
        sendMailRequest.setSubject("Hello");

        var response = mailService.sendMail(sendMailRequest);

        assertThat(response).isNotNull();
        assertThat(response).containsIgnoringCase("success");
    }
}
