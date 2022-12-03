package com.gmail.projectCrypt.ui;

import com.gmail.projectCrypt.authentication.AccessControlFactory;
import com.gmail.projectCrypt.backend.cryptData.API;
import com.gmail.projectCrypt.backend.cryptData.SQLConnector;
import com.gmail.projectCrypt.newsPage.newsView;
import com.gmail.projectCrypt.ui.about.AboutView;
import com.gmail.projectCrypt.ui.dashboard.Dashboard;
import com.gmail.projectCrypt.ui.hubs.HubView;
import com.gmail.projectCrypt.ui.settings.Settings;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.sql.SQLException;
import java.sql.Statement;


/**
 * The main layout. Contains the navigation menu.
 */

@Theme(value = Lumo.class, variant = Lumo.DARK)
@PWA(name = "Project Crypt", shortName = "Project Crypt")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/menu-buttons.css", themeFor = "vaadin-button")
public class MainLayout extends AppLayout implements RouterLayout {
    //CREATE TABLE STATEMENTS AND OOP USAGE
    private final Button logoutButton;

    public MainLayout() throws SQLException {

        //running the API on a backend thread so that it updates prices while the program is running.
        Runnable r = new Runnable() {
            @Override
            public void run() {
                API.main();
            }
        };

        new Thread(r).start();


        SQLConnector connect = new SQLConnector();
        Statement stmt = connect.SQLConnection();

/*
        String users = "CREATE TABLE `users` (" +
                "  `username` varchar(16) NOT NULL," +
                "  `email` varchar(255)," +
                "  `password` varchar(1000) NOT NULL," +
                "  `binaryDay` varchar(45)," +
                "  `firstName` varchar(45)," +
                "  `lastName` varchar(45)" +
                "   PRIMARY KEY(‘username’)" +
                ")";
        stmt.execute(users);
        String savedcurrencies = "CREATE TABLE ‘savedcurrencies’ (" +
                "`symbol` varchar(10)," +
                "‘username’ varchar(16)," +
                "PRIMARY KEY(‘symbol,’username’)";
        stmt.execute(savedcurrencies);
        String historical_data = "  CREATE TABLE `prices` (" +
                "  `symbol` varchar(10)," +
                "  `date` varchar(100)," +
                "  `price` double," +
                "   PRIMARY KEY (‘symbol’, ‘date’, ‘price’)" +
                ")";
        stmt.execute(historical_data);
        String coins = "CREATE TABLE `coins` (" +
                "  `name` varchar(100) NOT NULL," +
                "  `symbol` varchar(45)," +
                "  `slug` varchar(45)," +
                "  `price` decimal(10,2)," +
                "  `date_added` varchar(100)," +
                "  `volume_24h` decimal(10,2)," +
                "  `percent_change_1h` decimal(10,2)," +
                "  `percent_change_24h` decimal(10,2)," +
                "  `percent_change_7d` decimal(10,2)," +
                "  `market_cap` decimal(10,2)," +
                "  PRIMARY KEY (`symbol`)" +
                ")";
        stmt.execute(coins);


*/












        // Header of the menu (the navbar)

        // menu toggle
        final DrawerToggle drawerToggle = new DrawerToggle();
        drawerToggle.addClassName("menu-toggle");
        addToNavbar(drawerToggle);

        // image, logo
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        top.setClassName("menu-header");


        final String resolvedImage = VaadinService.getCurrent().resolveResource(
                "http://i.imgur.com/q7YE4Ob.png", VaadinSession.getCurrent().getBrowser());

        final Image image = new Image(resolvedImage, "");
        //Styling the image so it doesnt take up the whole screen, check css for the rest.
        image.setMaxWidth("30px");
        image.setMaxHeight("30px");

        //adding the images into the navbar
        final Label title = new Label("Project Crypt");
        top.add(image, title);
        top.add(title);
        addToNavbar(top);

        //create the LUMO ICONS to use in the drawer navigation system
        Icon dashboardIcon = new Icon("lumo", "bar-chart");
        Icon hubsIcon = new Icon("lumo", "menu");
        Icon settingsIcon = new Icon("lumo", "cog");
        Icon newsIcon = new Icon(VaadinIcon.NEWSPAPER);

        // Navigation items
        addToDrawer(createMenuLink(Dashboard.class, "Dashboard",
                dashboardIcon));

        addToDrawer(createMenuLink(HubView.class, "Hubs",
                hubsIcon));

        addToDrawer(createMenuLink(newsView.class, "News",
                newsIcon));

        addToDrawer(createMenuLink(Settings.class, "Settings",
                settingsIcon));

        addToDrawer(createMenuLink(AboutView.class, AboutView.VIEW_NAME,
                VaadinIcon.INFO_CIRCLE.create()));

        // Create logout button but don't add it yet; admin view might be added
        // in between (see #onAttach())
        logoutButton = createMenuButton("Logout", VaadinIcon.SIGN_OUT.create());
        logoutButton.addClickListener(e -> logout());
        logoutButton.getElement().setAttribute("title", "Logout (Ctrl+L)");

    }

    private void logout() {
        AccessControlFactory.getInstance().createAccessControl().signOut();
    }
    //Method in order to redirect to different views
    private RouterLink createMenuLink(Class<? extends Component> viewClass,
            String caption, Icon icon) {
        final RouterLink routerLink = new RouterLink(null, viewClass);
        routerLink.setClassName("menu-link");
        routerLink.add(icon);
        routerLink.add(new Span(caption));
        icon.setSize("24px");
        return routerLink;
    }
    //Method in order to create a button on the navigation bar
    private Button createMenuButton(String caption, Icon icon) {
        final Button routerButton = new Button(caption);
        routerButton.setClassName("menu-button");
        routerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        routerButton.setIcon(icon);
        routerButton.getElement().getStyle().set("margin", "5px");
        icon.setSize("20px");
        return routerButton;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // User can quickly activate logout with Ctrl+L
        attachEvent.getUI().addShortcutListener(() -> logout(), Key.KEY_L,
                KeyModifier.CONTROL);


        // Finally, add logout button for all users
        addToDrawer(logoutButton);
    }

}
