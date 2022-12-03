package com.gmail.projectCrypt.ui.profile;

import com.gmail.projectCrypt.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.sql.SQLException;

@Route(value = "Profile", layout = MainLayout.class)
@PageTitle("Profile")
public class Profile extends VerticalLayout {

    public Profile() throws SQLException {
        profileContent content = new profileContent();
        add(content);

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setWidth("100%");
        setHeight("100%");

    }

}
