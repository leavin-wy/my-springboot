package com.wy;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MockBusinessRunner implements CommandLineRunner {

    private final MockBusinessService mockBusinessService;

    @Override
    public void run(String... args) throws Exception {
        mockBusinessService.saveOrder();
    }
}

