package com.mavericksstube.maverickshub.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SendMailRequest {
    private String recipientEmail;
    private String subject;
    private String recipientName;
    private String content;
}
