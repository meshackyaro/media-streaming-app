package com.mavericksstube.maverickshub.services;

import com.mavericksstube.maverickshub.dtos.requests.SendMailRequest;
import org.springframework.stereotype.Service;


public interface MailService {
//    String sendMail(String email);

    String sendMail(SendMailRequest mailRequest);
}
