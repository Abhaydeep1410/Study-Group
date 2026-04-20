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

        return urlRepository.findByOriginalUrl(urlRequestDto.url())
                .map(existing -> new UrlResponseDto(
                        existing.getOriginalUrl(),
                        Base62Encoder.encode(existing.getId())))
                .orElseGet(() -> {
                            Url url = new Url();
                            url.setOriginalUrl(urlRequestDto.url());
                            Url saved = urlRepository.save(url);
                            return new UrlResponseDto(url.getOriginalUrl(), Base62Encoder.encode(saved.getId()));
                        }
                );

    }

    public String getUrl(String shortCode) {
        if (shortCode == null || shortCode.isBlank()) {
            throw new IllegalArgumentException("short code is empty");
        }
        int id = Base62Encoder.decode(shortCode);
        Url url = urlRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("URL not found"));
        return url.getOriginalUrl();

    }
}
