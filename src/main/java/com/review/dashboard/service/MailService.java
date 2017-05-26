package com.review.dashboard.service;

import com.review.dashboard.domain.Customer;
import com.review.dashboard.domain.User;
import com.review.dashboard.service.util.EmailStatus;

import io.github.jhipster.config.JHipsterProperties;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

import java.util.Locale;

/**
 * Service for sending emails.
 * <p>
 * We use the @Async annotation to send emails asynchronously.
 * </p>
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";
    
    private static final String CUSTOMER ="customer";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
            MessageSource messageSource, SpringTemplateEngine templateEngine) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public EmailStatus sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
      
        
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

            // Prepare message using a Spring helper
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
           
            
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            if(isHtml && isMultipart){
                message.addInline("myLogo", new ClassPathResource("static/img/logo-1.jpg"));
                message.addInline("goodReview", new ClassPathResource("static/img/goodReview.PNG"));
                message.addInline("badReview", new ClassPathResource("static/img/badReview.PNG"));
                //all pictures in templpate

                }
          
            javaMailSender.send(mimeMessage);
            
            log.debug("Sent e-mail to User '{}'", to);
            return new EmailStatus(to, subject, content).success();
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}'", to, e);
            return new EmailStatus(to, subject, content).error(e.getMessage());
        }
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process("activationEmail", context);
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process("creationEmail", context);
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public EmailStatus sendCustomerTemplateMail(User user, Customer cust, String token) {

        log.debug("Sending customer support e-mail to '{}'", cust.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable("badLink", jHipsterProperties.getMail().getBaseUrl() + "/api/review2?id2="+cust.getId()+"&token=" + token);
        context.setVariable("goodLink", jHipsterProperties.getMail().getBaseUrl() + "/api/review1?id2="+cust.getId() + "&token=" + token);
        //context.setVariable(CUSTOMER, cust);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process("newsletter", context);
        String subject ="Customer Satisfaction";
        return sendEmail(cust.getEmail(), subject, content, true, true);
    }
    
    
    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process("passwordResetEmail", context);
        String subject = messageSource.getMessage("email.reset.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendSocialRegistrationValidationEmail(User user, String provider) {
        log.debug("Sending social registration validation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable("provider", StringUtils.capitalize(provider));
        String content = templateEngine.process("socialRegistrationValidationEmail", context);
        String subject = messageSource.getMessage("email.social.registration.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }
}
