package com.blobplop.collector.domain;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.blobplop.collector.domain.QuoteService;
import com.blobplop.collector.domain.Result;
import com.blobplop.collector.entities.Quote;
import com.blobplop.collector.repositories.QuoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class QuoteServiceTest {

    @Mock
    private QuoteRepository quoteRepository;

    @InjectMocks
    private QuoteService quoteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        Quote quote = new Quote();
        quote.setQuoteId(1L);
        when(quoteRepository.findById(1L)).thenReturn(Optional.of(quote));

        Quote found = quoteService.findById(1L);
        assertNotNull(found);
        assertEquals(1L, found.getQuoteId());
    }

    @Test
    public void testFindByIdNotFound() {
        when(quoteRepository.findById(1L)).thenReturn(Optional.empty());

        Quote found = quoteService.findById(1L);
        assertNull(found);
    }

    @Test
    public void testFindAll() {
        when(quoteRepository.findAll()).thenReturn(List.of(new Quote(), new Quote()));

        assertEquals(2, quoteService.findAll().size());
    }

    @Test
    public void testAddQuote() {
        Quote quote = new Quote();
        quote.setQuote("Test Quote");
        when(quoteRepository.save(any(Quote.class))).thenReturn(quote);

        Result<Quote> result = quoteService.add(quote);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("Test Quote", result.getPayload().getQuote());
    }

    @Test
    public void testUpdateQuote() {
        Quote quote = new Quote();
        quote.setQuoteId(1L);
        quote.setQuote("Test Quote");
        when(quoteRepository.existsById(1L)).thenReturn(true);
        when(quoteRepository.save(any(Quote.class))).thenReturn(quote);

        Result<Quote> result = quoteService.update(quote);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("Test Quote", result.getPayload().getQuote());
    }

    @Test
    public void testUpdateQuoteNotFound() {
        Quote quote = new Quote();
        quote.setQuoteId(1L);
        quote.setQuote("Test Quote");
        when(quoteRepository.existsById(1L)).thenReturn(false);

        Result<Quote> result = quoteService.update(quote);
        assertFalse(result.isSuccess());
        assertEquals(ActionStatus.NOT_FOUND, result.getStatus());
    }

    @Test
    public void testUpdateQuoteInvalid() {
        Quote quote = new Quote();
        quote.setQuote("Test Quote");

        Result<Quote> result = quoteService.update(quote);
        assertFalse(result.isSuccess());
        assertEquals(ActionStatus.INVALID, result.getStatus());
    }

    @Test
    public void testUpdateQuoteQuoteIdNotSet() {
        Quote quote = new Quote();
        quote.setQuote("Test Quote");
        quote.setSource("Test Source");

        Result<Quote> result = quoteService.update(quote);
        assertFalse(result.isSuccess());
        assertEquals(ActionStatus.INVALID, result.getStatus());
    }

    @Test
    public void testUpdateQuoteIdNotFound() {
        Quote quote = new Quote();
        quote.setQuoteId(1L);
        quote.setQuote("Test Quote");
        quote.setSource("Test Source");
        when(quoteRepository.existsById(1L)).thenReturn(false);

        Result<Quote> result = quoteService.update(quote);
        assertFalse(result.isSuccess());
        assertEquals(ActionStatus.NOT_FOUND, result.getStatus());
    }

    @Test
    public void testDeleteById() {
        when(quoteRepository.existsById(1L)).thenReturn(true);

        Result<Quote> result = quoteService.deleteById(1L);
        assertTrue(result.isSuccess());
    }

    @Test
    public void testDeleteByIdNotFound() {
        when(quoteRepository.existsById(1L)).thenReturn(false);

        Result<Quote> result = quoteService.deleteById(1L);
        assertFalse(result.isSuccess());
        assertEquals(ActionStatus.NOT_FOUND, result.getStatus());
    }

    @Test
    public void testValidateQuoteNull() {
        Result<Quote> result = quoteService.validate(null);
        assertFalse(result.isSuccess());
        assertEquals(ActionStatus.INVALID, result.getStatus());
    }

    @Test
    public void testValidateQuoteQuoteEmpty() {
        Quote quote = new Quote();
        Result<Quote> result = quoteService.validate(quote);
        assertFalse(result.isSuccess());
        assertEquals(ActionStatus.INVALID, result.getStatus());
    }

    @Test
    public void testValidateQuoteSourceEmpty() {
        Quote quote = new Quote();
        quote.setQuote("Test Quote");
        quote.setSource("");
        Result<Quote> result = quoteService.validate(quote);
        assertFalse(result.isSuccess());
        assertEquals(ActionStatus.INVALID, result.getStatus());
    }

    @Test
    public void testValidateQuoteQuoteIdSet() {
        Quote quote = new Quote();
        quote.setQuoteId(1L);
        quote.setQuote("Test Quote");
        quote.setSource("Test Source");
        Result<Quote> result = quoteService.validate(quote);
        assertFalse(result.isSuccess());
        assertEquals(ActionStatus.NOT_FOUND, result.getStatus());
    }

    @Test
    public void testValidateQuoteQuoteIdNotSet() {
        Quote quote = new Quote();
        quote.setQuote("Test Quote");
        quote.setSource("Test Source");
        Result<Quote> result = quoteService.validate(quote);
        assertTrue(result.isSuccess());
    }
}