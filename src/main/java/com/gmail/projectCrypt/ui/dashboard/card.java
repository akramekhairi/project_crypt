package com.gmail.projectCrypt.ui.dashboard;

import com.github.appreciated.card.RippleClickableCard;
import com.github.appreciated.card.content.IconItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@CssImport("./styles/shared-styles.css")
public class card extends VerticalLayout {
    public card(Icon icon, String title, String description) {

        //create a dialog here to be opened in the component event
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");
        dialog.setHeight("180px");
        icon.setSize("50px");
        icon.setSize("50px");
        icon.setColor("rgb(18, 202, 214)");
        content cardContent = new content(icon, title, description);
        dialog.add(cardContent);
        RippleClickableCard c = new RippleClickableCard(
                componentEvent -> dialog.open(),
                new IconItem(icon, title, description)
        );
        c.setWidth("100%");
        c.setHeightFull();
        add(c);
    }
}

//content we will use in the card
class content extends VerticalLayout {
    public content(Icon icon, String title, String description) {
        getElement().getStyle().set("color", "#FFFFFF")
                .set("text-align", "center")
                .set("align-items", "center");
        Image logoHeader = new Image("icons/icon.png", "");
        //Login image styling
        logoHeader.setMaxWidth("50%");
        logoHeader.setMaxHeight("50%");
        logoHeader.getElement().getStyle().set("display", "block");
        logoHeader.getElement().getStyle().set("margin-left", "auto");
        logoHeader.getElement().getStyle().set("margin-right", "auto");



        add(description);
    }
}

