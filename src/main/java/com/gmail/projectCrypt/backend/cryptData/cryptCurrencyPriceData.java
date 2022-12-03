package com.gmail.projectCrypt.backend.cryptData;

/*  Example data structure when pulling from the API --> :)

 //useful in some sense
{
data: [{
  "id": 2679,
  "name": "Decentralized Machine Learning",
  "symbol": "DML",
  "slug": "decentralized-machine-learning",

  //Not needed
  "num_market_pairs": 2,

  //needed
  "date_added": "2018-04-28T00:00:00.000Z",

  //not needed
  "tags": [],
  "max_supply": null,
  "circulating_supply": 144015446.883227,
  "total_supply": 272937006.725531,

  //this is useless
  "platform": {
    "id": 1027,
    "name": "Ethereum",
    "symbol": "ETH",
    "slug": "ethereum",
    "token_address": "0xbcdfe338d55c061c084d81fd793ded00a27f226d"
  },

  //useless
  "cmc_rank": 1417,

  //useful
  "last_updated": "2020-02-03T16:16:09.000Z",

  //very important
  "quote": {
    "USD": {
      "price": 0.000909184214847,
      "volume_24h": 23400.2984521546,
      "percent_change_1h": -0.303527,
      "percent_change_24h": -1.30921,
      "percent_change_7d": -32.3736,
      "market_cap": 130936.57100036656,
      "last_updated": "2020-02-03T16:16:09.000Z"
    }
  }
}
] }
 */

import java.io.Serializable;

public class cryptCurrencyPriceData implements Serializable {

    private String symbol;
    //basic data
    private String name;
    private String slug;
    private String dateAdded;

    //price data
    private double price;
    private double volume_24h;
    private double percent_change_1h;
    private double percentChange_24h;
    private double percent_change_7d;
    private double market_cap;

    //seperate constructor overloading for the names and symbols and such.
    public cryptCurrencyPriceData(String name, String symbol, String slug, String dateAdded) {
        this.name = name;
        this.symbol = symbol;
        this.slug = slug;
        this.dateAdded = dateAdded;
    }

    //seperate constructor overloading for the price data, change the prices without changing names and such.
    public cryptCurrencyPriceData(int id, double price, double volume_24h, double percent_change_1h, double percent_change_24h, double percent_change_7d, double market_cap) {
        this.price = price;
        this.volume_24h = volume_24h;
        this.percent_change_1h = percent_change_1h;
        this.percentChange_24h = percent_change_24h;
        this.percent_change_7d = percent_change_7d;
        this.market_cap = market_cap;
    }



    public cryptCurrencyPriceData(String name, double price, String symbol, String slug, String date_added, double volume_24h, double percent_change_1h, double percent_change_24h, double percent_change_7d, double market_cap) {
        this.name = name;
        this.symbol = symbol;
        this.slug = slug;
        this.dateAdded = date_added;
        this.price = price;
        this.volume_24h = volume_24h;
        this.percent_change_1h = percent_change_1h;
        this.percentChange_24h = percent_change_24h;
        this.percent_change_7d = percent_change_7d;
        this.market_cap = market_cap;
    }

    //empty constructor
    public cryptCurrencyPriceData() {

    }
    //Getters and setters for all the different attributes
    public double getPercent_change_1h() {
        return percent_change_1h;
    }

    public void setPercent_change_1h( double percent_change_1h) {
        this.percent_change_1h = percent_change_1h;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVolume_24h() {
        return volume_24h;
    }

    public void setVolume_24h(double volume_24h) {
        this.volume_24h = volume_24h;
    }

    public double getPercentChange_24h() {
        return percentChange_24h;
    }

    public void setPercentChange_24h(double percentChange_24h) {
        this.percentChange_24h = percentChange_24h;
    }

    public double getPercent_change_7d() {
        return percent_change_7d;
    }

    public void setPercent_change_7d(double percent_change_7d) {
        this.percent_change_7d = percent_change_7d;
    }

    public double getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(double market_cap) {
        this.market_cap = market_cap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}




























