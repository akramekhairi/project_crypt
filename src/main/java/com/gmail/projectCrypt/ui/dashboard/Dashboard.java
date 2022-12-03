package com.gmail.projectCrypt.ui.dashboard;

import com.gmail.projectCrypt.ui.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

@Route(value = "Dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Dashboard")
public class Dashboard extends VerticalLayout {

    public Dashboard() throws SQLException {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        //adding dashboard content
        dashboardContent content = new dashboardContent();
        add(content);
    }
}

class dashboardContent extends VerticalLayout{

    public dashboardContent() throws SQLException {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setPadding(true);
        setMargin(true);
        setSpacing(true);

        //add a search bar here so the user picks anything they want
        TextField searchBar = new TextField();
        searchBar.setPlaceholder("Search for Cryptocurrencies to view their prices...");
        //change this for the current cryptocurrency being shown
        searchBar.setAutoselect(true);
        searchBar.setAutofocus(true);
        searchBar.setSizeFull();
        AtomicReference<String> symbol = new AtomicReference<>("BTC");

        //adding dashboard title
        H1 title = new H1();
        title.setText("Welcome to your Dashboard.");
        title.getElement().getStyle().set("color", "rgb(18, 202, 214)");

        //adding the title and searchbar to the dashboard view.
        add(title);
        add(searchBar);

        //Add chart and if the value of the searchBar changes then add a new chart with that coin's historical data
        final chart[] chart = {new chart(symbol)};
        searchBar.addValueChangeListener(event -> symbol.set(event.getValue()));

        searchBar.addValueChangeListener(event -> {
            try {
                remove(chart[0]);
                chart[0] = new chart(symbol);
                addComponentAtIndex(2,chart[0]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        add(chart[0]);

        //Adding an H1 here
        H1 trendingH1 = new H1();
        trendingH1.setText("Trending Cryptocurrencies");
        trendingH1.getElement().getStyle().set("color", "rgb(18, 202, 214)");
        add(trendingH1);

        //adding the horizontal layout of 3 cards into the view
        trendingCurrencies currencyCards = new trendingCurrencies();
        add(currencyCards);
    }
}






















