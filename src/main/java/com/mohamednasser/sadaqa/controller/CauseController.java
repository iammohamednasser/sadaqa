package com.mohamednasser.sadaqa.controller;

import com.mohamednasser.sadaqa.dto.CauseIn;
import com.mohamednasser.sadaqa.dto.CauseOut;
import com.mohamednasser.sadaqa.exception.CauseNotFoundException;
import com.mohamednasser.sadaqa.model.Cause;
import com.mohamednasser.sadaqa.service.CauseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/causes", produces = "application/json")
public class CauseController {

    private final CauseService causeService;


    public CauseController(CauseService causeService) {
        this.causeService = causeService;
    }

    @PostMapping
    public CauseOut createCause(@RequestBody CauseIn cause) throws Exception {
        return CauseOut.createInstanceFrom(this.causeService.saveCause(cause));
    }

    @GetMapping("/{causeId}")
    public CauseOut getCause(@PathVariable Long causeId) throws CauseNotFoundException {
        Cause cause = this.causeService.getCause(causeId);
        return CauseOut.createInstanceFrom(cause);
    }

    /* TODO - Results should be sorted by datetime */
    @GetMapping
    public List<CauseOut> getAllCauses(@RequestParam String handle, @RequestParam int page, @RequestParam int size) {
        return this.causeService.getAllCauses(handle, page, size).stream()
                .map(CauseOut::createInstanceFrom)
                .toList();
    }

    @GetMapping("/recent")
    public List<CauseOut> getRecentCauses() {
        return null;
    }

    @PatchMapping("/close/{causeId}")
    public void close(@PathVariable int causeId) throws CauseNotFoundException {
        this.causeService.closeCause(causeId);
    }

    @DeleteMapping("/{causeId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCause(@PathVariable Long causeId) {
        // delete
    }
    @PatchMapping(value = "/vote/{causeId}", consumes = "application/json")
    public void rateCause(@PathVariable Long causeId, @RequestBody boolean positive) throws CauseNotFoundException {
        this.causeService.updateRate(causeId, positive);

    }
}
