package br.com.zup.ecommerce.shared.services;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public interface MailerService {

    void send(EmailMessage message);
}
