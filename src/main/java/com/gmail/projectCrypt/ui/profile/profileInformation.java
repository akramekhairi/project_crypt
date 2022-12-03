package com.gmail.projectCrypt.ui.profile;

import com.gmail.projectCrypt.authentication.User;
import com.gmail.projectCrypt.backend.cryptData.SQLConnector;
import com.gmail.projectCrypt.ui.settings.savedcurrencies;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TemplateRenderer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class profileInformation extends VerticalLayout implements HasSize {

    public profileInformation() throws SQLException {
        H2 title = new H2();
        title.getElement().getStyle().set("color", "rgb(18, 202, 214)");

        Label nameLabel = new Label();
        TextField name = new TextField();
        Label lastNameLabel = new Label();
        TextField lastName = new TextField();
        Label usernameLabel = new Label();
        TextField username = new TextField();
        Label emailLabel = new Label();
        TextField email = new TextField();

        //user details extracted from database
        User user = new User();
        user.loadData();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.STRETCH);
        setWidth("100%");

        title.setText("Profile");
        //Setting the different fields to the user's details
        nameLabel.setText("First Name: ");
        name.setPlaceholder("name...");
        name.setValue(user.getFirstName());

        lastNameLabel.setText("Last Name:");
        lastName.setPlaceholder("last name...");
        lastName.setValue(user.getLastName());

        usernameLabel.setText("Username: ");
        username.setPlaceholder("username...");
        username.setValue(user.getUsername());

        emailLabel.setText("Email: ");
        email.setPlaceholder("email...");
        email.setValue(user.getEmail());

        //Creating a new list with objects of coins extracted from the savedcurrencies in the database and adding it to the grid
        List<savedcurrencies> coinList = new ArrayList<>();
        SQLConnector connect = new SQLConnector();
        Statement stmt = connect.SQLConnection();
        String extract = "SELECT * FROM savedcurrencies";
        ResultSet rs = stmt.executeQuery(extract);
        while(rs.next()) {
            coinList.add(new savedcurrencies(rs.getString("symbol")));
        }

        Grid<savedcurrencies> coinGrid = new Grid<>(savedcurrencies.class);
        coinGrid.setItems(coinList);


        //Adding a column for the grid for the remove buttons that updates the database and the grid.
        coinGrid.addColumn(TemplateRenderer.<savedcurrencies>of(
                "<vaadin-button theme = \"error\" on-click='handleClick'>Remove</vaadin-button>"
        )
                .withEventHandler("handleClick", savedcurrencies -> {
                            String sql = "DELETE FROM savedcurrencies WHERE symbol = " + "'" + savedcurrencies.getCoin() + "'";
                            try {
                                stmt.executeUpdate(sql);
                                List<savedcurrencies> coinList2 = new ArrayList<>();
                                String extract2 = "SELECT * FROM savedcurrencies";
                                ResultSet rs2 = stmt.executeQuery(extract2);
                                while(rs2.next()) {
                                    coinList2.add(new savedcurrencies(rs2.getString("symbol")));
                                }
                                coinGrid.setItems(coinList2);


                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            Notification.show("You have removed "+savedcurrencies.getCoin());

                        }

                ));

        coinGrid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS, GridVariant.LUMO_NO_BORDER);
        coinGrid.setMaxHeight("10em");




        //Adding all components in the correct order
        add(title);
        add(nameLabel);
        add(name);
        add(lastNameLabel);
        add(lastName);
        add(usernameLabel);
        add(username);
        add(emailLabel);
        add(email);
        add(coinGrid);



    }

}
