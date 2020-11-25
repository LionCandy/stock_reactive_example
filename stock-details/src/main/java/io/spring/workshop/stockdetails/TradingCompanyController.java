package io.spring.workshop.stockdetails;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TradingCompanyController {

    private final TradingCompanyRepository repository;

    public TradingCompanyController(TradingCompanyRepository tradingCompanyRepository) {
        this.repository = tradingCompanyRepository;
    }

    @GetMapping(path = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<TradingCompany> listTradingCompanies() {
        return this.repository.findAll();
    }

    @GetMapping(path = "/details/{ticker}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TradingCompany> getTradingCompany(@PathVariable String ticker) {
        return this.repository.findByTicker(ticker).delayElement(Duration.ofMillis(400));
    }
}