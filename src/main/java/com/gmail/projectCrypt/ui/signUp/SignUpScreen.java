package com.gmail.projectCrypt.ui.signUp;

import com.gmail.projectCrypt.authentication.AccessControl;
import com.gmail.projectCrypt.authentication.Encryption;
import com.gmail.projectCrypt.authentication.User;
import com.gmail.projectCrypt.backend.cryptData.SQLConnector;
import com.gmail.projectCrypt.backend.cryptData.Validation;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedList;

@Route("Sign-Up")
@PageTitle("Sign Up")
@CssImport("./styles/shared-styles.css")



//Use of OOP

public class SignUpScreen extends VerticalLayout {

    private AccessControl accessControl;

    public SignUpScreen() {

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setAlignSelf(Alignment.CENTER);
        add(buildUI());

    }

    private VerticalLayout buildUI() {

        VerticalLayout layout = new VerticalLayout();

        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        layout.setAlignItems(Alignment.STRETCH);
        layout.setMaxHeight("50%");
        layout.setMaxWidth("50%");

        H1 title = new H1("Sign Up");
        H3 subtitle = new H3("To sign up, please fill out the form below :)");
        layout.add(title);
        title.getElement().getStyle().set("color", "rgb(18, 202, 214)");
        layout.add(subtitle);
        FormLayout form = form();
        layout.add(form);
        layout.setAlignSelf(Alignment.CENTER);

        return layout;
    }

    public FormLayout form() {

        FormLayout signUpForm = new FormLayout();

        signUpForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("60em", 1),
                new FormLayout.ResponsiveStep("60em", 2),
                new FormLayout.ResponsiveStep("60em", 3));

        //name fields
        TextField firstName = new TextField();
        firstName.setPlaceholder("John");
        firstName.setValueChangeMode(ValueChangeMode.EAGER);
        firstName.setRequired(true);
        TextField lastName = new TextField();
        lastName.setValueChangeMode(ValueChangeMode.EAGER);
        lastName.setPlaceholder("Doe");
        TextField username = new TextField();
        username.setPlaceholder("Username");
        username.setRequired(true);
        username.setValueChangeMode(ValueChangeMode.EAGER);

        //email and bday fields
        TextField email = new TextField();
        email.setPlaceholder("JohnDoe@mail.com");
        email.setValueChangeMode(ValueChangeMode.EAGER);

        //password field
        PasswordField password = new PasswordField();
        password.setRequired(true);
        password.setPlaceholder("Make it Strong :)");

        //adding buttons for confirm and cancel here as well
        userControl USERCONTROL = new userControl();

        Button confirmButton = new Button("Sign up!");
        confirmButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        Button cancelButton = new Button("Back to Login");
        cancelButton.addClickListener(e ->
                getUI().ifPresent(ui ->
                        ui.navigate("Login")));
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);


        //adding them to the form layout
        signUpForm.addFormItem(firstName, "First Name");
        signUpForm.addFormItem(lastName, "Last Name");
        signUpForm.addFormItem(username, "Username");
        signUpForm.addFormItem(email, "Email");
        signUpForm.addFormItem(password, "Password");
        signUpForm.add(cancelButton);
        signUpForm.add(confirmButton);

        signUpForm.setColspan(email, 2);
        signUpForm.setColspan(username, 3);
        signUpForm.setColspan(confirmButton, 2);
        signUpForm.setColspan(cancelButton, 1);
        signUpForm.setColspan(password, 3);

        Encryption encrypt = new Encryption();
        confirmButton.addClickListener(e ->
        {
            try {
                Validation valid = new Validation();
                //Check if the first name, last name, and email are all valid before creating a user and if not display appropriate notifications
                if(valid.firstNamecheck(firstName.getValue()) && !(username.getValue() == "") && !(password.getValue() == "")  ) {
                    if (valid.lastNamecheck(lastName.getValue())) {
                        if(valid.emailcheck(email.getValue())) {
                            //Create the user and add it to the DB
                            USERCONTROL.createUser(firstName.getValue(), lastName.getValue(), username.getValue(), email.getValue(), encrypt.encryptmessage(password.getValue()),encrypt.hashingalgorithm(username.getValue(),password.getValue()));
                        }
                        //Appropriate notifications if some fields are invalid
                        else{
                            Notification emailnotif = new Notification();
                            emailnotif.addThemeVariants(NotificationVariant.LUMO_ERROR);
                            Label label = new Label("Check if your email is valid");
                            emailnotif.add(label);
                            emailnotif.open();
                        }
                    }
                    else{
                        Notification lastNamenotif = new Notification();
                        lastNamenotif.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        Label label = new Label("Check if your last name is valid(Within 16 characters and only letters. No special characters or numbers");
                        lastNamenotif.add(label);
                        lastNamenotif.open();
                    }
                }
                else{
                    Notification firstNamenotif = new Notification();
                    firstNamenotif.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    Label label = new Label("Check if your first name is valid(Within 16 characters and only letters. No special characters or numbers " +
                            "and your username and password field aren't empty or invalid");
                    firstNamenotif.add(label);
                    firstNamenotif.open();
                }
            } catch (SQLException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException ex) {
                ex.printStackTrace();
            }
        });

        //returning a form
        return signUpForm;
    }





}

class userControl {

    public void createUser(String firstName, String lastName, String username, String email, String password, double hashvalue ) throws SQLException {

        Dialog success = new Dialog();
        Div content = new Div();
        Text message = new Text("You have successfully created an Account!");

        //loading the user details in here so they can be accessed by Settings view
        User user = new User(firstName, lastName, username, email, password);
        SQLConnector connector = new SQLConnector();
        Statement statement = connector.SQLConnection();
        int day;
        ZoneId zone = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now(zone);
        day = localDate.getDayOfMonth();


        //encrypting day value using mathematical process (Using complex mathematical processes from NEA criteria)
        double l = Math.pow(Math.E, 44) * Math.log10(day);
        DecimalFormat df = new DecimalFormat("###.###");
        String encryptedday = df.format(l);



        //Using DDL INSERT statement to enter the user's details into the database.
        String sql = "INSERT INTO users" +
                "(username, password, lastName, email, firstName,encryptedDay,hashvalue)" + "VALUES(" +
                "'" + username + "'" + "," +
                "'" + password + "'" + "," +
                "'" + lastName + "'" + "," +
                "'" + email + "'" + "," +
                "'" + firstName + "'" + "," +
                "'" + encryptedday + "'," +
                hashvalue +  ")";

        statement.executeUpdate(sql);

        content.add(message);
        success.add(content);
        success.open();
        message.getUI().ifPresent(ui -> ui.navigate("Login"));

    }

}
