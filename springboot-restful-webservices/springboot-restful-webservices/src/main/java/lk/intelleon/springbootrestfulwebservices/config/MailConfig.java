package lk.intelleon.springbootrestfulwebservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Configure the mail sender properties (e.g., host, port, username, password)
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587); // Example port
        mailSender.setUsername("ashen13516@gmail.com");
        mailSender.setPassword("iutouaafrugwofru");
        return mailSender;
    }
}
