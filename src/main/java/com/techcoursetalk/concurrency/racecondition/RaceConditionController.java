package com.techcoursetalk.concurrency.racecondition;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/race-condition")
public class RaceConditionController {

    public static Integer soDangerousCount = 0;

    @PostMapping("/1/increase")
    public ResponseEntity<Void> increaseCount() {
        soDangerousCount++;
        return ResponseEntity.ok().build();
    }

    @GetMapping("/1/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(soDangerousCount);
    }

    @PostMapping("/2/check-then-act")
    public ResponseEntity<Void> printOdd() throws InterruptedException {
        soDangerousCount++;
        if (soDangerousCount % 2 == 1) {
            Thread.sleep(1);
            System.out.println("soDangerousCount = " + soDangerousCount);
        }
        return ResponseEntity.ok().build();
    }
}
