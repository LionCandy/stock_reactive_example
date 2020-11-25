package io.spring.workshop.tradingservice;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

import org.springframework.http.MediaType;

@Controller
public class QuotesController {

    private final QuotesClient quotesClient;

    private final TradingCompanyClient tradingCompanyClient;

    public QuotesController(QuotesClient quotesClient,TradingCompanyClient tradingCompanyClient) {
        this.quotesClient = quotesClient;
        this.tradingCompanyClient = tradingCompanyClient;
    }

    @GetMapping(path = "/quotes/feed", produces = TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Quote> quotesFeed() {
        return this.quotesClient.quotesFeed();
    }

    /*
    @GetMapping(path = "/quotes/{ticker}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Quote> getLatestQuote(String ticker) {
        return this.quotesClient.getLatestQuote(ticker);
    }
    */

    @GetMapping(path = "/quotes/summary/{ticker}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<TradingCompanySummary> getQuotesSummary(@PathVariable String ticker) {
        return this.tradingCompanyClient.getTradingCompany(ticker)
                 .zipWith(this.quotesClient.getLatestQuote(ticker),
                     TradingCompanySummary::new);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(TickerNotFoundException.class)
	public void onTickerNotFound() {
	}



    

}