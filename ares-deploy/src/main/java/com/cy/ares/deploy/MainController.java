package com.cy.ares.deploy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author sunju.sj
 * @date 2020/3/25
 * @since v1.0.0
 */
@Controller
public class MainController {

    @GetMapping({"/page/*", "/", "/nameSpace", "/appConfig", "/environmentalSpace", "/configDetail"})
    public String index() {
        return "/index.html";
    }

}
