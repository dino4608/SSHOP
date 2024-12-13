package dmon.SSHOP_springboot_backend.controller;

import dmon.SSHOP_springboot_backend.dto.request.AccessRequest;
import dmon.SSHOP_springboot_backend.dto.response.AccessResponse;
import dmon.SSHOP_springboot_backend.service.AccessService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/access")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccessController {
    AccessService accessService;

    //SIGN IN//
    @PostMapping("/signin")
    public ResponseEntity<AccessResponse> signIn(@RequestBody AccessRequest request) {
        return ResponseEntity
                .ok()
                .body(this.accessService.authenticate(request));
    }

}
