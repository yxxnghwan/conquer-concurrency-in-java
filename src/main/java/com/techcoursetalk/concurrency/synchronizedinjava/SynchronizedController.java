package com.techcoursetalk.concurrency.synchronizedinjava;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/synchronized")
public class SynchronizedController {

    private Integer studentCount = 0;

    @PostMapping("/increase")
    public synchronized ResponseEntity<Void> increaseSynchronizedCount() {
        studentCount++;
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public synchronized ResponseEntity<Integer> getStudentCount() {
        return ResponseEntity.ok(studentCount);
    }

    @PostMapping("/2/check-then-act")
    public synchronized ResponseEntity<Void> printWarning() throws InterruptedException {
        studentCount++;
        if (studentCount < 30) {
            System.out.println("폐강위험! studentCount = " + studentCount);
        }
        return ResponseEntity.ok().build();
    }
}
