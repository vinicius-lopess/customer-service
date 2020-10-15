package com.unbox.customer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @GetMapping(value = "/")
    public String index(){
        logger.info("Redirecionando para a página Rest API Swagger");
        return "redirect:swagger-ui.html";
	}
}