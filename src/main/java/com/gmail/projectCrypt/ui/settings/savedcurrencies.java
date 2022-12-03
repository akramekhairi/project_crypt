package com.gmail.projectCrypt.ui.settings;

public class savedcurrencies {
    private String coin;

    public savedcurrencies(String coin){
        this.coin = coin;
    }

    public String getCoin(){ return coin; }

    public void setCoin(String coin) {
        this.coin = coin;
    }
}
