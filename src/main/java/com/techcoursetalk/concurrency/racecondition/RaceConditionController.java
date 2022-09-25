package com.techcoursetalk.concurrency.racecondition;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/race-condition")
public class RaceConditionController {

    public static Integer studentCount = 0;

    @PostMapping("/1/increase")
    public ResponseEntity<Void> increaseCount() {
        studentCount++;
        return ResponseEntity.ok().build();
    }

    @GetMapping("/1/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(studentCount);
    }

    @PostMapping("/2/check-then-act")
    public ResponseEntity<Void> printWarning() throws InterruptedException {
        studentCount++;
        if (studentCount < 30) {
            Thread.sleep(1);
            System.out.println("폐강위험! studentCount = " + studentCount);
        }
        return ResponseEntity.ok().build();
    }
}
