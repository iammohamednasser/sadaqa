package com.mohamednasser.sadaqa.service;

import com.mohamednasser.sadaqa.dto.CauseIn;
import com.mohamednasser.sadaqa.dto.CauseOut;
import com.mohamednasser.sadaqa.exception.CauseNotFoundException;
import com.mohamednasser.sadaqa.model.Cause;
import com.mohamednasser.sadaqa.model.User;
import com.mohamednasser.sadaqa.repository.CauseRepository;
import com.mohamednasser.sadaqa.security.AuthenticationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CauseService {

    private final CauseRepository causeRepository;

    private final AuthenticationService authenticationService;

    public CauseService(CauseRepository CauseRepository, AuthenticationService authenticationService) {
        this.causeRepository = CauseRepository;
        this.authenticationService = authenticationService;
    }

    public CauseOut getCause(Long causeId) throws CauseNotFoundException {
        Optional<Cause> cause = causeRepository.findById(causeId);
        if (cause.isEmpty()) throw new CauseNotFoundException(causeId);
        return CauseOut.createInstanceFrom(cause.get());
    }

    public CauseOut saveCause(CauseIn dto) throws Exception {
        User user = this.authenticationService.getAuthenticatedUser();

        Cause aCause = Cause.builder()
                .owner(user)
                .body(dto.body())
                .title(dto.title())
                .publishDate(Instant.now())
                .credibility(0)
                .build();

        Cause cause = this.causeRepository.save(aCause);
        return CauseOut.createInstanceFrom(cause);
    }
    public List<CauseOut> getAllCauses(String handle, int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return this.causeRepository.findAllByHandle(handle, pr)
                .stream()
                .map(CauseOut::createInstanceFrom)
                .toList();
    }


    public void deleteCause(Long id) {
        causeRepository.deleteById(id);
    }

    public void closeCause(long causeId) throws CauseNotFoundException {
        Optional<Cause> optional = this.causeRepository.findById(causeId);
        if (optional.isEmpty()) throw new CauseNotFoundException(causeId);
        Cause aCause = optional.get();
        aCause.setClosed(true);
        this.causeRepository.save(aCause);
    }

    public void updateRate(Long causeId, boolean positive) throws CauseNotFoundException {
        Optional<Cause> optional = this.causeRepository.findById(causeId);
        if (optional.isEmpty()) throw new CauseNotFoundException(causeId);
        Cause cause = optional.get();
        int value = (positive)? 1: 0;
        int n = cause.getNumberOfVotes();
        float rate = cause.getCredibility();
        rate = ((n * rate) + value)/(n + 1);
        cause.setNumberOfVotes(n + 1);
        cause.setCredibility(rate);
    }
}