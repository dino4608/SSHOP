package com.dino.backend.shared.test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/test/cache")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestCacheController {

    TestCacheRepo testCacheRepo;

    @GetMapping("/able")
    public ResponseEntity<Object> test1() {
        return ResponseEntity.ok(this.testCacheRepo.getResult());
    }
}
