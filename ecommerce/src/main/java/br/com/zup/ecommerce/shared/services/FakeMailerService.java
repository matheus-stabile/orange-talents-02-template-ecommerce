package br.com.zup.ecommerce.shared.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FakeMailerService implements MailerService {
    @Override
    public void send(EmailMessage message) {
        System.out.println(message.build());
    }
}
