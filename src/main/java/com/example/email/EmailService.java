package com.example.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration freemarkerConfig;

    public void sendDemoEmail(String name, String phone, String sendTo) {

        try {
            freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/");
            // 1. Model data for template
            Map<String, Object> model = new HashMap<>();
            model.put("name", name);
            model.put("phone", phone);

            // 2. Load template
            Template template = freemarkerConfig.getTemplate("emailDemo.ftl");

            // 3. Merge template + data
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            // 4. Create mail
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("sbokade181@gmail.com");
            helper.setTo(sendTo);
            helper.setSubject("Demo Email");
            helper.setText(html, true);

            // 5. Send
            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}