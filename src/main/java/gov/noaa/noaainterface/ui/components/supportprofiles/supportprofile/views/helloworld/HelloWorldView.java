package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.helloworld;

import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.WorkFlow;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.WorkflowPage;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.RequestingPartnerInformation;

@PageTitle("Hello World")
@Route(value = "hello-world")
@RouteAlias(value = "")
public class HelloWorldView extends HorizontalLayout {

    public HelloWorldView() {
        List<Partner> partners = List.of();
        WorkflowPage eventPage1 = new WorkflowPage("Requesting Partner Information",
                new RequestingPartnerInformation(partners));
        eventPage1.add();

        WorkflowPage eventPage2 = new WorkflowPage("Event Information", new VerticalLayout(new Span("Event Info")));
        WorkflowSection eventWorflowSection = new WorkflowSection("Event", eventPage1, eventPage2);

        WorkflowPage impactPage1 = new WorkflowPage("Impact Information", new VerticalLayout(new Span("Impact Info")));
        WorkflowPage impactPage2 = new WorkflowPage("Impact Information", new VerticalLayout(new Span("Impact Info")));

        WorkflowSection impactWorkflowSection = new WorkflowSection("Impact", impactPage1, impactPage2);

        WorkFlow workFlow = new WorkFlow(eventWorflowSection, impactWorkflowSection);

        add(workFlow);
    }

}
