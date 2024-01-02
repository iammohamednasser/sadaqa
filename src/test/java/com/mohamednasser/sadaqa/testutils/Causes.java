package com.mohamednasser.sadaqa.testutils;

import com.mohamednasser.sadaqa.dto.CauseIn;
import com.mohamednasser.sadaqa.dto.CauseOut;
import com.mohamednasser.sadaqa.model.Cause;

import java.time.Instant;

public class Causes {

    public static class FirstCause {

        public static final String TITLE = "Cause Title";

        public static final String BODY = "Detailed description of the cause.";

        public static final Cause CAUSE;

        public static final CauseIn CAUSE_IN;

        public static final CauseOut CAUSE_OUT;

        static {

            CAUSE = Cause.builder()
                    .id(1L)
                    .owner(Users.FirstUser.USER_ENTITY)
                    .title(TITLE)
                    .body(BODY)
                    .credibility(50)
                    .publishDate(Instant.now())
                    .build();

            CAUSE_IN = new CauseIn(TITLE, BODY);

            CAUSE_OUT = CauseOut.createInstanceFrom(CAUSE);
        }
    }
}
