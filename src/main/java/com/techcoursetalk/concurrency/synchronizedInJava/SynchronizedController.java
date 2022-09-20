package com.techcoursetalk.concurrency.synchronizedInJava;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/synchronized")
public class SynchronizedController {

    private Integer synchronizedCount = 0;

    @PostMapping("/increase")
    public synchronized ResponseEntity<Void> increaseSynchronizedCount() {
        synchronizedCount++;
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public synchronized ResponseEntity<Integer> getSynchronizedCount() {
        return ResponseEntity.ok(synchronizedCount);
    }

}
