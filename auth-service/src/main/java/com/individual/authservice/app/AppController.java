package com.individual.authservice.app;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/firebase")
public class AppController {

    @GetMapping(path = "/test")
    @PreAuthorize("hasAuthority('MANAGER') && hasAuthority('FOOD_PRODUCT')")
    public String test(Principal principal) {
        return principal.getName();
    }
}
