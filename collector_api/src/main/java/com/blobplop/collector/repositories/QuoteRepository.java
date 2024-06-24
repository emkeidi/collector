package com.blobplop.collector.repositories;

import com.blobplop.collector.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
