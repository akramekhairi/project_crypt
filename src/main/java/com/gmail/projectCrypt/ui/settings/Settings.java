package com.gmail.projectCrypt.ui.settings;

import com.gmail.projectCrypt.backend.cryptData.SQLConnector;
import com.gmail.projectCrypt.ui.MainLayout;
import com.gmail.projectCrypt.ui.profile.profileContent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Route(value = "Settings", layout = MainLayout.class)
@PageTitle("Settings")
public class Settings extends VerticalLayout {

    public Settings() throws SQLException {
        //Configuring the settings view as well as adding the various components such as the Menu, user information, buttons, etc.
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setWidthFull();
        setHeight("58em");

        H1 title = new H1("Settings");
        title.getElement().getStyle().set("color", "rgb(18, 202, 214)");
        title.setHeight("8em");
        add(title);

        subscriptionInfo subscriptionInformation = new subscriptionInfo();
        add(subscriptionInformation);

        profileContent profileInformation = new profileContent();
        profileInformation.setWidth("70%");
        add(profileInformation);

        Label space = new Label("");

        space.setHeight("8em");

        add(space);

        settingsMenu buttons = new settingsMenu();
        add(buttons);




    }

}
