package com.example.microservices.notification.service;

import com.example.microservices.order.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("Got message from order-placed topic {}", orderPlacedEvent);

        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springshop@gmail.com");
            messageHelper.setTo(orderPlacedEvent.getEmail().toString());
            messageHelper.setSubject(String.format("Your order with order number %s is placed successfully!", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format("""
                    Hi %s %s,
                    
                    Your order with order number %s is placed successfully!
                    
                    Best Regards,
                    Spring shop
                    """, orderPlacedEvent.getFirstName(),
                    orderPlacedEvent.getLastName(),
                    orderPlacedEvent.getOrderNumber()));
        };

        try{
            javaMailSender.send(mimeMessagePreparator);
            log.info("Order Notification email sent");
        } catch (MailException e){
            log.info("Exception occurred while sending notification",e);
            throw new RuntimeException("Error occurred while sending notification",e);
        }
    }
}
