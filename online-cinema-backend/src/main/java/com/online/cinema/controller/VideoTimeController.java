package com.online.cinema.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class VideoTimeController {


    @PostMapping("/savetime")
    public void saveCurrentTime(@RequestParam("currentTime") String currentTime, HttpSession session) {
        session.setAttribute("currentTime", currentTime);
    }

    @GetMapping("/currenttime")
    public String getCurrentTime(HttpSession session) {
        return (String) session.getAttribute("currentTime");
    }

    @PostMapping("/savestate")
    public void sendEvent(@RequestParam("state") String state, HttpSession session) {
        session.setAttribute("state", state);
    }

    @GetMapping("/currentstate")
    public String getCurrentState(HttpSession session) {
        return (String) session.getAttribute("state");
    }
}

