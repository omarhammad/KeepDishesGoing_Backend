package com.omarhammad.kdg_backend.orders.adapters.out.emailAdapter;


import com.omarhammad.kdg_backend.orders.domain.Order;
import com.omarhammad.kdg_backend.orders.ports.out.NotificationPort;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class EmailAdapter implements NotificationPort {


    //
    @Value("${sendgrid.api.key}")
    private String apiKey;

    @Value("${sendgrid.email.from}")
    private String fromAddress;

    @Value("${sendgrid.template.id}")
    private String templateId;

    @Override
    public void notify(Order order) {
        Mail mail = getMail(order);
        SendGrid sendGrid = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            log.info("Email response : {}", response.getBody());
        } catch (IOException e) {
            throw new RuntimeException("Failed to send tracking email", e);
        }

    }

    private Mail getMail(Order order) {
        Email from = new Email(fromAddress);
        Email to = new Email(order.getCustomer().getEmail().email());

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setTemplateId(templateId);


        Personalization personalization = new Personalization();

        personalization.addTo(to);


        personalization.addDynamicTemplateData("customerName", "%s".formatted(order.getCustomer().getFirstName()));
        personalization.addDynamicTemplateData("trackingUrl", "https://unintroducible-versatilely-lakia.ngrok-free.dev/tracking/%s".formatted(order.getId().value()));
        personalization.addDynamicTemplateData("year", "2025");
        mail.addPersonalization(personalization);
        return mail;
    }
}
