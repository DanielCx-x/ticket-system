package com.ticket.controller;

import com.ticket.exception.BaseException;
import com.ticket.result.Result;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/ping")
    public Result<Map<String, String>> ping() {
        return Result.success(Map.of("status", "ok", "service", "ticket-server"));
    }

    @GetMapping("/error")
    public Result<Void> error(@RequestParam(defaultValue = "manual error") String message) {
        throw new BaseException(message);
    }
}
