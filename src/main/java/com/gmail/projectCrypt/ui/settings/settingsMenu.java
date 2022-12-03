package com.gmail.projectCrypt.ui.settings;

import com.gmail.projectCrypt.authentication.AccessControlFactory;
import com.gmail.projectCrypt.authentication.CurrentUser;
import com.gmail.projectCrypt.backend.cryptData.SQLConnector;
import com.gmail.projectCrypt.backend.cryptData.cryptCurrencyPriceData;
import com.gmail.projectCrypt.newsPage.news;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.crud.CrudI18n;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.lumo.Lumo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class settingsMenu extends VerticalLayout {

    public settingsMenu() throws SQLException {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.STRETCH);
        setWidth("70%");
        setHeightFull();

        //Below button changes the theme from dark to light mode throughout the entire program :)

        Button toggleButton = new Button("Toggle dark theme", click -> changeTheme() );

        

        add(toggleButton);

        //here we want to add the delete account option

        H4 dangerZone = new H4("DANGER ZONE");
        add(dangerZone);
        //Creating the pop up after clicking the delete account button
        FormLayout deleteAccount = new FormLayout();
        Paragraph warningInformation = new Paragraph("Once you delete your account there is no going back! Please be careful!");
        Paragraph finalWarningInformation = new Paragraph("Once you delete your account there is no going back! Please be careful!");
        deleteAccount.add(warningInformation);

        Button deleteAccountButton = new Button("Delete Account!");
        Button finalConfirmationButton = new Button("Delete Account!!!???");
        Dialog confirmation = new Dialog(finalWarningInformation, finalConfirmationButton);

        deleteAccountButton.addClickListener(e ->
                confirmation.open());
        //If the confirmation button is clicked then the user's account is deleted
        finalConfirmationButton.addClickListener(e -> {
                    try {
                        deleteUser();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    getUI().ifPresent(ui -> ui.navigate("Login"));
                }
                );

        deleteAccount.add(deleteAccountButton);

        add(deleteAccount);










    }

    private void logout() {
        AccessControlFactory.getInstance().createAccessControl().signOut();
    }

    private void deleteUser() throws SQLException {

        //code to delete account from the database
        SQLConnector connect = new SQLConnector();
        Statement stmt = connect.SQLConnection();
        String delete = "DELETE FROM users WHERE username = '"+CurrentUser.get()+"'";
        stmt.execute(delete);
        logout();
    }
    private void changeTheme(){
        ThemeList themeList = UI.getCurrent().getElement().getThemeList(); // (1)

        if (themeList.contains(Lumo.DARK)) { // (2)
            themeList.remove(Lumo.DARK);
        } else {
            themeList.add(Lumo.DARK);
        }
    }
}
