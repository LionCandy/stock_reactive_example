package io.spring.workshop.tradingservice;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;;

@Controller
public class TradingCompanyController {

    private final TradingCompanyClient tradingCompanyClient;

    public TradingCompanyController(TradingCompanyClient tradingCompanyClient) {
        this.tradingCompanyClient = tradingCompanyClient;
    }

    @GetMapping(path = "/details", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Flux<TradingCompany> getAllTradingCompanies() {
        return this.tradingCompanyClient.fidnAllCompanies();
    }

    @GetMapping(path = "/details/{ticker}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<TradingCompany> getTradingCompany(@PathVariable String ticker) {
        return this.tradingCompanyClient.getTradingCompany(ticker);
    }
}