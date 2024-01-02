package com.mohamednasser.sadaqa.controller;

import com.mohamednasser.sadaqa.dto.CauseIn;
import com.mohamednasser.sadaqa.dto.CauseOut;
import com.mohamednasser.sadaqa.exception.CauseNotFoundException;
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
        return this.causeService.saveCause(cause);
    }

    @GetMapping("/{causeId}")
    public CauseOut getCause(@PathVariable Long causeId) throws CauseNotFoundException {
        return this.causeService.getCause(causeId);
    }

    /* TODO - Results should be sorted by datetime */
    @GetMapping
    public List<CauseOut> getAllCauses(@RequestParam String handle, @RequestParam int page, @RequestParam int size) {
        return this.causeService.getAllCauses(handle, page, size);
    }


    @DeleteMapping("/{causeId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCause(@PathVariable Long causeId) {
        // delete
    }
    @PatchMapping(value = "/votes/{causeId}", consumes = "application/json")
    public void rateCause(@PathVariable Long causeId, @RequestBody boolean positive) throws CauseNotFoundException {
        this.causeService.updateRate(causeId, positive);

    }
}
