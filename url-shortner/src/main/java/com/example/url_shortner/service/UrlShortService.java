package com.example.url_shortner.service;

import com.example.url_shortner.Repository.UrlRepository;
import com.example.url_shortner.dto.UrlRequestDto;
import com.example.url_shortner.dto.UrlResponseDto;
import com.example.url_shortner.entity.Url;
import com.example.url_shortner.utility.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlShortService {

    private final UrlRepository urlRepository;

    public UrlResponseDto createShortUrl(UrlRequestDto urlRequestDto) {
        if (urlRequestDto.url() == null || urlRequestDto.url().isBlank()) {
            throw new IllegalArgumentException("url is empty");
        }
        String normalizedUrl = normalizeUrl(urlRequestDto.url());
        return urlRepository.findByOriginalUrl(normalizedUrl)
                .map(existing -> new UrlResponseDto(
                        existing.getOriginalUrl(),
                        Base62Encoder.encode(existing.getId())))
                .orElseGet(() -> {
                            Url url = new Url();
                            url.setOriginalUrl(normalizedUrl);
                            Url saved = urlRepository.save(url);
                            return new UrlResponseDto(saved.getOriginalUrl(), Base62Encoder.encode(saved.getId()));
                        }
                );

    }

    public String getUrl(String shortCode) {
        if (shortCode == null || shortCode.isBlank()) {
            throw new IllegalArgumentException("short code is empty");
        }
        int id = Base62Encoder.decode(shortCode);
        System.out.println("Decoded ID: " + id);
        Url url = urlRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("URL not found"));
        return url.getOriginalUrl();

    }

    private String normalizeUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }
        return url;
    }
}
