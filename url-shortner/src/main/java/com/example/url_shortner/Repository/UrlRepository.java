package com.example.url_shortner.Repository;

import com.example.url_shortner.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Integer> {

    Optional<Url> findByOriginalUrl(String url);
}
