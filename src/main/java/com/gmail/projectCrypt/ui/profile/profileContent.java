package com.gmail.projectCrypt.ui.profile;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.sql.SQLException;

public class profileContent extends HorizontalLayout {

    public profileContent() throws SQLException {
        //Adding the image and the user's information in a horizontal layout to be added to the view
        Image avatarImage = new Image("img/avatar.png", "");
        add(avatarImage);

        try {
            profileInformation information = new profileInformation();
            add(information);
        }

        catch(Exception e)
        {

        }



        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setMargin(true);
        setPadding(true);
        setHeight("70%");
        setWidth("70%");
        setMaxHeight("100%");
        setMaxWidth("100%");
    }


}
