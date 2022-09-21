package com.techcoursetalk.concurrency.synchronizedinjava;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/no-synchronized")
public class NoSynchronizeController {

    private Integer soDangerousCount = 0;

    @PostMapping("/increase")
    public ResponseEntity<Void> increaseNoSynchronizedCount() {
        soDangerousCount++;
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(soDangerousCount);
    }

}
