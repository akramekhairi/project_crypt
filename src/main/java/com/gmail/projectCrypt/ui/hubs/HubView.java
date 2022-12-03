package com.gmail.projectCrypt.ui.hubs;

import com.gmail.projectCrypt.authentication.CurrentUser;
import com.gmail.projectCrypt.backend.cryptData.SQLConnector;
import com.gmail.projectCrypt.backend.cryptData.cryptCurrencyPriceData;
import com.gmail.projectCrypt.ui.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Route(value = "Hubs", layout = MainLayout.class)
@PageTitle("Hubs")
public class HubView extends VerticalLayout {
    private final  SQLConnector connect = new SQLConnector();
    private final Statement stmt = connect.SQLConnection();

    public HubView() throws SQLException {

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.STRETCH);

        //making a grid list where there are objects of coins and there data is extracted from the database

        //now for the grid list below
        List<cryptCurrencyPriceData> coinList = new ArrayList<>();
        extractfromDB(coinList);

        Grid<cryptCurrencyPriceData> coinGrid = new Grid<>(cryptCurrencyPriceData.class);
        coinGrid.setItems(coinList);
        //Removing 2 columns and setting the names for the rest
        coinGrid.removeColumnByKey("slug");
        coinGrid.removeColumnByKey("dateAdded");
        coinGrid.setColumns("name", "symbol", "price", "volume_24h", "percent_change_1h", "percentChange_24h", "percent_change_7d", "market_cap");
        //Creating a new column with the save buttons for each coin's object and if it is clicked, the username of the current user and the coin's symbol are added to the database
        coinGrid.addColumn(TemplateRenderer.<cryptCurrencyPriceData>of(
                "<vaadin-button on-click='handleClick'>Save</vaadin-button>"
        )
                .withEventHandler("handleClick", cryptCurrencyPriceData -> {
                    String sql = "INSERT INTO savedcurrencies(username,symbol) VALUES(" + "'" + CurrentUser.get() + "'" + "," + "'" + cryptCurrencyPriceData.getSymbol() + "')";
                    try {
                        stmt.executeUpdate(sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Notification.show("You have saved "+cryptCurrencyPriceData.getName());
                        }

                ));





        //configuring the grid's properties and adding it to the view
        coinGrid.setSizeFull();
        coinGrid.setHeightFull();
        
        coinGrid.setHeightByRows(true);

        coinGrid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS, GridVariant.LUMO_NO_BORDER);

        add(coinGrid);

    }

    private List<cryptCurrencyPriceData> extractfromDB(List<cryptCurrencyPriceData> coinList) throws SQLException {
        String extract = "SELECT * FROM coins";
        ResultSet rs = stmt.executeQuery(extract);
        while(rs.next()) {
            coinList.add(new cryptCurrencyPriceData(rs.getString("name"), rs.getDouble("price"),
                    rs.getString("symbol"), rs.getString("slug"), rs.getString("date_added"),
                    rs.getDouble("volume_24h"), rs.getDouble("percent_change_1h"),
                    rs.getDouble("percent_change_24h"), rs.getDouble("percent_change_7d"), rs.getDouble("market_cap")));
        }
        return coinList;
    }

}
