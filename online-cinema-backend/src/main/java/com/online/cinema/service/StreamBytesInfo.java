package com.online.cinema.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Data
@AllArgsConstructor
public class StreamBytesInfo {

    private final StreamingResponseBody responseBody;

    private final long fileSize;

    private final long rangeStart;

    private final long rangeEnd;

    private final String contentType;

}

