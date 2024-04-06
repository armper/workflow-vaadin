package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.helloworld;

import java.util.List;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.WorkFlow;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.WorkflowPage;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.WorkflowSection;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.Partner;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.RequestingPartnerInformationLayout;

@PageTitle("Hello World")
@Route(value = "hello-world")
@RouteAlias(value = "")
public class HelloWorldView extends HorizontalLayout {

    public HelloWorldView() {
        /*
         * public record Partner(String email, String firstName, String lastName, String
         * jobDescription, String City,
         * String phone)
         */
        Partner partner1 = new Partner("fred@flintstone.com", "Fred", "Flintstone", "Rock Breaker", "Bedrock",
                "111-111-1123");
        Partner partner2 = new Partner("barney@rubble.com", "Barney", "Rubble", "Rock Breaker", "Bedrock",
                "111-111-1123");
        Partner partner3 = new Partner("john@doe.com", "John", "Doe", "Emergency Manager", "Bedrock", "111-111-1123");

        Partner partner4 = new Partner("jane@doe.com", "Jane", "Doe", "Director of Fire Dept", "Bedrock", "111-111-1123");

        Partner partner5 = new Partner("tom@brady.com", "Tom", "Brady", "Admin", "San Mateo", "111-111-1123");

        List<Partner> partners = List.of(partner1, partner2, partner3, partner4, partner5);

        WorkflowPage requestingPartnerInformationPage = new WorkflowPage("Requesting Partner Information",
                new RequestingPartnerInformationLayout(partners));
        requestingPartnerInformationPage.add();

        WorkflowPage eventPage2 = new WorkflowPage("Event Information", new VerticalLayout(new Span("Event Info 2")));
        WorkflowPage eventPage3 = new WorkflowPage("Event Information", new VerticalLayout(new Span("Event Info 3")));
        WorkflowPage eventPage4 = new WorkflowPage("Event Information", new VerticalLayout(new Span("Event Info 4")));
        WorkflowPage eventPage5 = new WorkflowPage("Event Information", new VerticalLayout(new Span("Event Info 5")));

        WorkflowSection eventWorflowSection = new WorkflowSection("Event", requestingPartnerInformationPage, eventPage2,
                eventPage3, eventPage4,
                eventPage5);

        WorkflowPage impactPage1 = new WorkflowPage("Impact Information", new VerticalLayout(new Span("Impact Info")));
        WorkflowPage impactPage2 = new WorkflowPage("Impact Information",
                new VerticalLayout(new Span("Impact Info 2")));
        WorkflowPage impactPage3 = new WorkflowPage("Impact Information",
                new VerticalLayout(new Span("Impact Info 3")));

        WorkflowSection impactWorkflowSection = new WorkflowSection("Impact", impactPage1, impactPage2, impactPage3);

        WorkFlow workFlow = new WorkFlow(eventWorflowSection, impactWorkflowSection);

        add(workFlow);
    }

}
