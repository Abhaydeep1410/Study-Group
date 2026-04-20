package com.example.url_shortner.controller;

import com.example.url_shortner.dto.UrlRequestDto;
import com.example.url_shortner.dto.UrlResponseDto;
import com.example.url_shortner.service.UrlShortService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ShortnerController {

    private final UrlShortService urlShortService;

    @PostMapping("/rest/api/v1/shorten")
    public ResponseEntity<UrlResponseDto> createUrlShortner(@RequestBody UrlRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(urlShortService.createShortUrl(dto));
    }

    @GetMapping("/{shortCode:[a-zA-Z0-9]+}")
    ResponseEntity<String> getUrl(@PathVariable String shortCode) {
        return ResponseEntity.
                status(HttpStatus.FOUND)
                .location(URI.create(urlShortService.getUrl(shortCode)))
                .build();
    }

}
