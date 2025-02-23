package com.blobplop.collector.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.blobplop.collector.controllers.QuoteController;
import com.blobplop.collector.domain.QuoteService;
import com.blobplop.collector.domain.Result;
import com.blobplop.collector.entities.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(QuoteController.class)
public class QuoteControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private QuoteService quoteService;
//
//    @InjectMocks
//    private QuoteController quoteController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testFindById() throws Exception {
//        Quote quote = new Quote();
//        quote.setQuoteId(1L);
//        when(quoteService.findById(1L)).thenReturn(quote);
//
//        mockMvc.perform(get("/api/quote/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.quoteId").value(1L));
//    }
//
//    @Test
//    public void testAddQuote() throws Exception {
//        Quote quote = new Quote();
//        quote.setQuote("Test Quote");
//        when(quoteService.add(any(Quote.class))).thenReturn(new Result<>(quote));
//
//        mockMvc.perform(post("/api/quote")
//                        .contentType("application/json")
//                        .content("{\"quote\":\"Test Quote\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.quote").value("Test Quote"));
//    }

    // Add more tests for other methods
}