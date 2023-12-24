package com.mohamednasser.sadaqa.dto;

import com.mohamednasser.sadaqa.model.Cause;

import java.time.Instant;


public record CauseOut(
        String owner,
        String title,
        String body,
        Instant publishDate,
        float credibility) {
    public static CauseOut createInstanceFrom(Cause aCause) {
        return new CauseOut(
                aCause.getOwner().getEmail(),
                aCause.getTitle(),
                aCause.getBody(),
                aCause.getPublishDate(),
                aCause.getCredibility()
        );
    }
}
