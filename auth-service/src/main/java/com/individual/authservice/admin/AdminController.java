package com.individual.authservice.admin;

import com.individual.authservice.security.Department;
import com.individual.authservice.security.Roles;
import com.google.firebase.auth.FirebaseAuthException;
import com.individual.authservice.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/firebase/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserManagementService userManagementService;

    //@Secured("ROLE_ANONYMOUS")
    @PostMapping(path = "/user-claims/{uid}")
    public void setUserClaims(
            @PathVariable String uid,
            @RequestBody List<Roles> requestedClaims,
            @RequestBody List<Department> requestedSubClaims
    ) throws FirebaseAuthException {
        userManagementService.setUserClaims(uid, requestedClaims, requestedSubClaims);
    }

}
