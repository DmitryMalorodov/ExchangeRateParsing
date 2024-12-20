package main.model.currency;

import main.model.Connector;
import main.model.Helper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс реализации парсинга
 */
public class CurrencyParser extends Connector {
    private static final String bids = "//div[contains(@class, 'bid innerContainer')]";
    private static final String asks = "//div[contains(@class, 'ask innerContainer')]";
    private static final String dayDif = "//div[@class='change genToolTip']/span[1]";
    private static final String dayDifPercents = "//span[contains(@class, 'pcp')]";
    private static final String exchangeNames = "//div[@class='topBox']/a";
    private static final String difTime = "//div[@class='topBox']/span";

    private List<String> getBids() {
        Elements elements = document.selectXpath(bids);
        return elements.stream().map(Element::text).collect(Collectors.toList());
    }

    private List<String> getAsks() {
        Elements elements = document.selectXpath(asks);
        return elements.stream().map(Element::text).collect(Collectors.toList());
    }

    private List<String> getDayDiffs() {
        Elements elements = document.selectXpath(dayDif);
        return elements.stream().map(Element::text).collect(Collectors.toList());
    }

    private List<String> getDayDifPercents() {
        Elements elements = document.selectXpath(dayDifPercents);
        return elements.stream().map(Element::text).collect(Collectors.toList());
    }

    private List<String> getExchangeNames() {
        Elements elements = document.selectXpath(exchangeNames);
        return elements.stream().map(Element::text).collect(Collectors.toList());
    }

    private List<String> getDifTime() {
        Elements elements = document.selectXpath(difTime);
        return elements.stream().map(Element::text).collect(Collectors.toList());
    }

    public List<Currency> createListOfCurrencies() {
        List<String> bids = getBids();
        List<String> asks = getAsks();
        List<String> exchangeNames = getExchangeNames();
        List<String> dayDiffs = getDayDiffs();
        List<String> dayDifPercents = getDayDifPercents();
        List<String> difTime = getDifTime();
        String currentData = Helper.getCurrentData();

        List<Currency> currencies = new ArrayList<>();

        for (int i = 0; i < bids.size(); i++) {
            currencies.add(new Currency(bids.get(i), asks.get(i), exchangeNames.get(i),
                    dayDiffs.get(i), dayDifPercents.get(i), difTime.get(i),
                    currentData));
        }
        return currencies;
    }
}
