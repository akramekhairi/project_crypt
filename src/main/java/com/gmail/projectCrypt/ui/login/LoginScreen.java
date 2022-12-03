package com.gmail.projectCrypt.ui.login;

import com.gmail.projectCrypt.authentication.AccessControl;
import com.gmail.projectCrypt.authentication.AccessControlFactory;
import com.gmail.projectCrypt.authentication.CurrentUser;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * UI content when the user is not logged in yet.
 */
@Route("Login")
@PageTitle("Login")
@CssImport("./styles/shared-styles.css")
public class LoginScreen extends FlexLayout {

    private AccessControl accessControl;
    private Button signUpButton = new Button("Sign Up");
    public LoginScreen() {
        //Create an accessControl object and build the loginview
        accessControl = AccessControlFactory.getInstance().createAccessControl();
        buildUI();
    }

    private void buildUI() {
        setSizeFull();
        setClassName("login-screen");

        // login form, centered in the available part of the screen
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(event -> {
            try {
                login(event);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        });
        loginForm.addForgotPasswordListener(
                event -> Notification.show("USERNAME: root, PASSWORD: toor"));

        // layout to center login form when there is screen space
        FlexLayout centeringLayout = new FlexLayout();
        centeringLayout.setSizeFull();
        centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centeringLayout.setAlignItems(Alignment.CENTER);
        centeringLayout.add(loginForm);

        // information text about logging in
        Component loginInformation = buildLoginInformation();

        add(loginInformation);
        add(centeringLayout);
    }

    private Component buildLoginInformation() {
        VerticalLayout loginInformation = new VerticalLayout();
        loginInformation.setClassName("login-information");
        loginInformation.setAlignItems(Alignment.STRETCH);

        H1 loginInfoHeader = new H1("Project Crypt");
        loginInfoHeader.getElement().getStyle().set("color", "#FFFFFF");
        Image logoHeader = new Image("icons/icon.png", "");

        //Icon here
        Icon signUpIcon = new Icon("lumo", "user");
        signUpIcon.setColor("#FFFFFF");

        //Sign up button here
        signUpButton.setIcon(signUpIcon);
        signUpButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        signUpButton.addClickListener(e-> {
                CurrentUser.set("admin");
                getUI().ifPresent(ui -> ui.navigate("Sign-Up"));
                });


        //Login image styling
        logoHeader.setMaxWidth("50%");
        logoHeader.setMaxHeight("50%");
        logoHeader.getElement().getStyle().set("display", "block");
        logoHeader.getElement().getStyle().set("margin-left", "auto");
        logoHeader.getElement().getStyle().set("margin-right", "auto");
        //Login header styling
        loginInfoHeader.setWidth("100%");
        //Login text
        Span loginInfoText = new Span(
                "Welcome to Project Crypt. We are dedicated to providing you with the best possible experience." +
                        "To begin please login or Sign up.");
        //Login text styling
        loginInfoText.setWidth("100%");
        loginInfoText.getElement().getStyle().set("color", "#FFFFFF");
        loginInformation.add(logoHeader);
        loginInformation.add(loginInfoHeader);
        loginInformation.add(loginInfoText);
        loginInformation.add(signUpButton);

        return loginInformation;
    }
    //Method to log the user in if there username and passwword match what is in the DB.
    private void login(LoginForm.LoginEvent event) throws SQLException, BadPaddingException, UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        if (accessControl.signIn(event.getUsername(), event.getPassword())) {
            getUI().ifPresent(ui -> ui.navigate( "Dashboard"));
        } else {
            event.getSource().setError(true);
        }
    }
}
