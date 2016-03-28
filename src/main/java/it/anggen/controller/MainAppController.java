
package it.anggen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codahale.metrics.annotation.Timed;

@Controller
@RequestMapping("//")
public class MainAppController {


    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "template";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @Timed
    public String home() {
        return "home";
    }

}
