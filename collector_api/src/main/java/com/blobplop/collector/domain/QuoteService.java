package com.blobplop.collector.domain;

import com.blobplop.collector.entities.Quote;
import com.blobplop.collector.repositories.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public List<Quote> findAll() {
        return quoteRepository.findAll();
    }

    public Quote findById(long quoteId) {

        return quoteRepository.findById(quoteId).orElse(null);
    }

    public Result<Quote> add(Quote quote) {
        Result<Quote> result = validate(quote);
        if (!result.isSuccess()) {
            return result;
        }
        if(quote.getQuoteId() > 0) {
            result.addMessage(ActionStatus.INVALID, "'quoteId' should not be " +
                    "set for 'add'.");
        }
        if (result.isSuccess()) {
            quote = quoteRepository.save(quote);
            result.setPayload(quote);
        }
        return result;

    }

    public Result<Quote> update(Quote quote) {
        Result<Quote> result = validate(quote);
        if (!result.isSuccess()) {
            return result;
        }
        if (quote.getQuoteId() < 1) {
            result.addMessage(ActionStatus.INVALID, "'quoteId' should be set " +
                    "for 'update'.");
        }
        if (result.isSuccess() && !quoteRepository.existsById(quote.getQuoteId())) {
            result.addMessage(ActionStatus.NOT_FOUND,
                    notFoundMessage(quote.getQuoteId()));
        }
        if (result.isSuccess()) {
            quote = quoteRepository.save(quote);
            result.setPayload(quote);
        }
        return result;

    }

    public Result<Quote> deleteById(long quoteId) {
        Result<Quote> result = new Result<>();
        if (!quoteRepository.existsById(quoteId)) {
            result.addMessage(ActionStatus.NOT_FOUND, notFoundMessage(quoteId));
        } else {
            quoteRepository.deleteById(quoteId);
        }
        return result;
    }

    Result<Quote> validate(Quote quote) {
        Result<Quote> result = new Result<>();
        if (quote == null) {
            result.addMessage(ActionStatus.INVALID, "Quote cannot be null.");
            return result;
        }
        if (quote.getQuote() == null || quote.getQuote().isBlank()) {
            result.addMessage(ActionStatus.INVALID, "'quote' should be set.");
        }
        if (quote.getSource() != null && quote.getSource().isEmpty()) {
            result.addMessage(ActionStatus.INVALID, "'author' should be set.");
        }
        if (quote.getSource() != null && quote.getSource().length() > 255) {
            result.addMessage(ActionStatus.INVALID, "'author' should be less " +
                    "than 255 characters.");
        }
        if (quote.getQuoteId() > 0 && !quoteRepository.existsById(quote.getQuoteId())) {
            result.addMessage(ActionStatus.NOT_FOUND, notFoundMessage(quote.getQuoteId()));
        }
        if (isDuplicate(quote)) {
            result.addMessage(ActionStatus.INVALID, "Quote already exists for" +
                    " this user" +
                    ".");
        }
        return result;
    }

    private boolean isDuplicate(Quote quote) {
        List<Quote> quotes = quoteRepository.findAll();
        for (Quote q : quotes) {
            if (q.getQuote().equals(quote.getQuote()) &&
                    (q.getSource() == null && quote.getSource() == null ||
                            q.getSource() != null && q.getSource().equals(quote.getSource()))) {
                return true;
            }
        }
        return false;
    }

    private String notFoundMessage(long quoteId) {
        return String.format("Quote with id: '%s' was not found.", quoteId);
    }

}
