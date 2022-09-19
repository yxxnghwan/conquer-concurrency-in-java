package com.techcoursetalk.concurrency;

import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

public class AtomicTest {

    @Test
    void atomicRef() {
        final AtomicReference<User> atomicUser = new AtomicReference<>();

    }

    static class User {
        private Long id;
        private String name;

        
    }
}
