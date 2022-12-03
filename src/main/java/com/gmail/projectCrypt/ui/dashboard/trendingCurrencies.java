package com.gmail.projectCrypt.ui.dashboard;

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;


public class trendingCurrencies extends HorizontalLayout implements HasSize {

    public trendingCurrencies() {

        //Adding the 3 cards
        Icon ico1 = VaadinIcon.COIN_PILES.create();
        Icon ico2 = VaadinIcon.COIN_PILES.create();
        Icon ico3 = VaadinIcon.COIN_PILES.create();
        card c1 = new card(ico1,"BTC","Bitcoin is a digital currency created in January 2009." +
                " It follows the ideas set out in a whitepaper by the mysterious and pseudonymous developer Satoshi Nakamoto, whose true identity has yet to be verified. "); //Pull the title and the description from the database
        card c2 = new card(ico2, "ETH", "Ethereum is a decentralized, open source, " +
                "and distributed computing platform that enables the creation of smart contracts and decentralized applications, also known as dapps.");
        card c3 = new card(ico3, "XRP", "XRP is the native cryptocurrency of the XRP Ledger (XRPL). " +
                "XRP is a deflationary currency because each time you make a transaction - a small portion of the XRP is destroyed (0.00001 XRP).");
        //resizing before adding and centering content
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();

        //adding all components in respective order
        add(c1);
        add(c2);
        add(c3);
    }

}
