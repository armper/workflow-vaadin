package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class PartnerComponentUtil {
    public static FlexLayout createPartnerComponent(Partner person) {
        FlexLayout wrapper = new FlexLayout();
        wrapper.setAlignItems(Alignment.CENTER);
        wrapper.setFlexDirection(FlexDirection.ROW);
        wrapper.setWidthFull();

        Div nameDiv = new Div(new Span(person.firstName() + " " + person.lastName()));
        nameDiv.getStyle().set("flex", "1").set("margin-right", "auto");

        Div jobTitleDiv = new Div(new Span(person.jobTitle()));
        jobTitleDiv.addClassNames(LumoUtility.TextColor.SECONDARY, LumoUtility.FontSize.SMALL);
        jobTitleDiv.getStyle().set("flex", "1").set("margin-right", "auto");

        Div cityDiv = new Div(new Span(person.city()));
        cityDiv.addClassNames(LumoUtility.TextColor.TERTIARY, LumoUtility.FontSize.SMALL);
        cityDiv.getStyle().set("flex", "1");

        wrapper.add(nameDiv, jobTitleDiv, cityDiv);
        return wrapper;
    }
}
