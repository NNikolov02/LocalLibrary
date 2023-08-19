package com.example.locallibrary1.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailTemplate {

    @Autowired
    private TemplateEngine templateEngine;

    public String processTemplate(String templateName, Context context) {
        return templateEngine.process(templateName, context);
    }
}
