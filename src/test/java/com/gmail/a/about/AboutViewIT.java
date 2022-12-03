package com.gmail.a.about;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.flow.component.html.testbench.SpanElement;
import com.vaadin.flow.component.orderedlayout.testbench.HorizontalLayoutElement;
import com.gmail.a.AbstractViewTest;
import com.gmail.a.MainLayoutElement;
import com.gmail.a.authentication.LoginFormElement;

public class AboutViewIT extends AbstractViewTest {

    @Test
    public void openAboutView_showsFlowVersion() {

        // given authenticated as a regular user
        $(LoginFormElement.class).first().login("user", "user");

        // when selecting "About" from the sidebar menu
        final MainLayoutElement mainElem = $(MainLayoutElement.class).first();
        mainElem.clickMenuLink("About");

        // then the view contents a span with Flow version information
        final SpanElement aboutSpan = mainElem.$(HorizontalLayoutElement.class)
                .last().$(SpanElement.class).last();

        Assert.assertTrue("Expected version text",
                aboutSpan.getText().contains("Vaadin"));
    }
}
