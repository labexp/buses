package proyecto.controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping('/')
    @ResponseBody
    String index() { 'EnrutaTEC is running.' }
}
