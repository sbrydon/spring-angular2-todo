package brydon.simon.todo.rest.controller;

import brydon.simon.todo.service.dto.RegistrationDto;
import brydon.simon.todo.service.security.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    private final RegistrationService regService;

    @Autowired
    public UserController(RegistrationService regService) {
        this.regService = regService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public void post(@Valid @RequestBody RegistrationDto reg) {
        regService.register(reg.getUsername(), reg.getPassword());
    }
}
