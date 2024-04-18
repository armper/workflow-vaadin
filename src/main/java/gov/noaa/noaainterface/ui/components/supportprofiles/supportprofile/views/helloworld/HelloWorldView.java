package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.helloworld;

import java.util.Collection;
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
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.interfaces.ValidatableForm;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.AreaOfConcern;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.AreaOfConcernLayout;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.Partner;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.RequestingPartnerInformationLayout;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.SupportSchedule;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.SupportScheduleLayout;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.eventinformation.EventInformation;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.eventinformation.EventInformationLayout;

@PageTitle("Hello World")
@Route(value = "hello-world")
@RouteAlias(value = "")
public class HelloWorldView extends HorizontalLayout {

        private Collection<String> states = List.of("CA", "NY", "FL", "TX", "WA", "OR", "NV", "AZ", "CO", "UT",
                        "NM", "ID", "MT", "WY", "ND", "SD", "NE", "KS", "OK", "MN", "IA", "MO", "AR", "LA", "MS", "AL",
                        "TN", "KY", "IN", "OH", "MI", "WI", "IL", "PA", "WV", "VA", "NC", "SC", "GA", "VT", "NH", "ME",
                        "MA", "RI", "CT", "NJ", "DE", "MD", "DC");
        private Collection<String> counties = List.of("San Mateo", "Santa Clara", "Alameda", "Contra Costa",
                        "San Francisco", "Marin", "Sonoma", "Napa", "Solano", "Sacramento", "Placer", "El Dorado",
                        "Yolo", "San Joaquin", "Stanislaus", "Merced", "Madera", "Fresno", "Kings", "Tulare", "Kern",
                        "San Luis Obispo", "Santa Barbara", "Ventura", "Los Angeles", "Orange", "Riverside",
                        "San Bernardino",
                        "San Diego", "Imperial");

        public HelloWorldView() {

                Partner partner1 = new Partner("fred@flintstone.com", "Fred", "Flintstone", "Rock Breaker", "Bedrock",
                                "111-111-1123");
                Partner partner2 = new Partner("barney@rubble.com", "Barney", "Rubble", "Rock Breaker", "Bedrock",
                                "111-111-1123");
                Partner partner3 = new Partner("john@doe.com", "John", "Doe", "Emergency Manager", "Bedrock",
                                "111-111-1123");

                Partner partner4 = new Partner("jane@doe.com", "Jane", "Doe", "Director of Fire Dept", "Bedrock",
                                "111-111-1123");

                Partner partner5 = new Partner("tom@brady.com", "Tom", "Brady", "Admin", "San Mateo", "111-111-1123");

                List<Partner> partners = List.of(partner1, partner2, partner3, partner4, partner5);

                WorkflowPage<Partner> requestingPartnerInformationPage = new WorkflowPage<>(
                                "Requesting Partner Information",
                                new RequestingPartnerInformationLayout(partners));

                requestingPartnerInformationPage.addValueChangeListener(listener -> {
                        System.out.println("Value changed" + listener);
                });

                AreaOfConcernLayout areaOfConcernLayout = new AreaOfConcernLayout(states, counties);
                WorkflowPage<AreaOfConcern> areaOfConcernPage = new WorkflowPage<>("Area of Concern",
                                areaOfConcernLayout);

                ValidatableForm<EventInformation> eventInformationLayout = new EventInformationLayout(states);
                WorkflowPage<EventInformation> eventInformationPage = new WorkflowPage<>("Event Information",
                                eventInformationLayout);

                eventInformationPage.addValueChangeListener(listener -> {
                        System.out.println("Value changed" + listener);
                        if (listener.getAddress() != null)
                                areaOfConcernLayout.setEventAddress(listener.getAddress().getAddress1());
                });

                ValidatableForm<SupportSchedule> supportScheduleLayout = new SupportScheduleLayout();
                WorkflowPage<SupportSchedule> supportSchedulePage = new WorkflowPage<>("Support Schedule",
                                supportScheduleLayout);

                WorkflowSection eventWorflowSection = new WorkflowSection("Event", areaOfConcernPage,
                                requestingPartnerInformationPage,
                                eventInformationPage, areaOfConcernPage, supportSchedulePage);

                WorkflowSection impactWorkflowSection = new WorkflowSection("Impact");

                WorkFlow workFlow = new WorkFlow(eventWorflowSection);

                add(workFlow);
        }

}
