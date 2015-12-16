package ac.tec.buses.engine.controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RequestMapping('/health')
    @ResponseBody
    String index() { 'buses engine API is up and running' }
}
