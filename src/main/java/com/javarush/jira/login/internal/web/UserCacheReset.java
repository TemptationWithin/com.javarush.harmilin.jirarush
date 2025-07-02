package com.javarush.jira.login.internal.web;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCacheReset {
    private final CacheManager cacheManager;

    @EventListener(ApplicationReadyEvent.class)
    public void clearUserCache() {
        cacheManager.getCache("users").clear();
        System.out.println("🧹 Кэш users очищен");
    }
}
