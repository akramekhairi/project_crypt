package com.gmail.projectCrypt.ui.settings;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class subscriptionInfo extends VerticalLayout {

    public subscriptionInfo() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.STRETCH);
        setWidth("70%");
        setHeightFull();
        //Initializing the user's subscription information to be added to the view (same for every user for now :D)
        Paragraph subscriptionInformation = new Paragraph("You are currently on our free plan, dont worry though, we currently only support the free plan :)");
        Button changeSubscription = new Button("Update Subscription");
        changeSubscription.setEnabled(false);
        FormLayout subscription = new FormLayout();
        subscription.setWidthFull();
        subscription.addFormItem(subscriptionInformation, "SUBSCRIPTION");
        subscription.add(changeSubscription);
        add(subscription);

    }
}
