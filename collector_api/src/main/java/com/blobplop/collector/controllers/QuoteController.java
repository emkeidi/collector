package com.blobplop.collector.controllers;

import com.blobplop.collector.domain.ActionStatus;
import com.blobplop.collector.domain.QuoteService;
import com.blobplop.collector.domain.Result;
import com.blobplop.collector.entities.Quote;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quote")
public class QuoteController {

    private final QuoteService service;

    public QuoteController(QuoteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Quote> findAll() {
        return service.findAll();
    }

    @GetMapping("/{quoteId}")
    public ResponseEntity<Quote> findById(@PathVariable long quoteId) {
        Quote quote = service.findById(quoteId);
        if (quote != null) {
            return ResponseEntity.ok(quote);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Quote quote) {
        Result  result = service.add(quote);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getPayload());
        }
        return ResponseEntity.badRequest().body(result.getMessages());
    }

    @PutMapping("/{quoteId}")
    public ResponseEntity<Object> update(@PathVariable long quoteId,
                                         @RequestBody Quote quote) {
        if (quoteId != quote.getQuoteId()) {
            return ResponseEntity.status(409).build();
        }

        Result result = service.update(quote);
        if (result.getStatus() == ActionStatus.NOT_FOUND) {
            return ResponseEntity.notFound().build();
        }
        if (result.getStatus() == ActionStatus.INVALID) {
            return ResponseEntity.badRequest().body(result.getMessages());
        }
        return ResponseEntity.ok(result.getPayload());
    }

    @DeleteMapping("/{quoteId}")
    public ResponseEntity<Object> deleteById(@PathVariable long quoteId) {
        Result result = service.deleteById(quoteId);
        if (result.isSuccess()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }



}
