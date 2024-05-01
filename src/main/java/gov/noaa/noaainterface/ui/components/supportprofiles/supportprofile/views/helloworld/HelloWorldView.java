package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.helloworld;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.WorkFlow;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.WorkflowPage;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.WorkflowSection;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.dtos.AreaOfConcern;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.dtos.County;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.dtos.EventInformation;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.dtos.Partner;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.dtos.Recipients;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.dtos.SupportSchedule;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.dtos.WeatherThreshold;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.interfaces.ValidatableForm;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.newevent.AreaOfConcernLayout;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.newevent.EventInformationLayout;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.newevent.RecipientsLayout;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.newevent.RequestingPartnerInformationLayout;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.newevent.SupportScheduleLayout;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.newimpact.WeatherThresholdLayout;

@PageTitle("Hello World")
@Route(value = "hello-world")
@RouteAlias(value = "")
public class HelloWorldView extends HorizontalLayout {

        private Collection<String> states = List.of("CA", "NY", "FL", "TX", "WA", "OR", "NV", "AZ", "CO", "UT",
                        "NM", "ID", "MT", "WY", "ND", "SD", "NE", "KS", "OK", "MN", "IA", "MO", "AR", "LA", "MS", "AL",
                        "TN", "KY", "IN", "OH", "MI", "WI", "IL", "PA", "WV", "VA", "NC", "SC", "GA", "VT", "NH", "ME",
                        "MA", "RI", "CT", "NJ", "DE", "MD", "DC");
        private Collection<County> counties = List.of(new County("San Mateo",
                        "{\"type\": \"FeatureCollection\", \"features\": [" +
                                        "{\"type\": \"Feature\", \"geometry\": {\"type\": \"Polygon\", \"coordinates\": ["
                                        +
                                        "[[-100.0, 40.0], [-105.0, 45.0], [-110.0, 40.0], [-100.0, 40.0]]]}}]}"));

        private Collection<Partner> recipientsList = List.of();
        private Collection<Partner> partnersList = List.of();
        private List<String> weatherHazards = List.of("Air Quality", "Extreme Heat", "Extreme Cold",
                        "Flooding", "Lightning", "Severe Storm", "Tropical Weather", "Wind", "Custom Hazard");

        public HelloWorldView() {

                EventWorkFlowInformation eventWorkFlowInformation = new EventWorkFlowInformation();

                Partner partner1 = new Partner(UUID.randomUUID(), "fred@flintstone.com", "Fred", "Flintstone",
                                "Rock Breaker", "Bedrock",
                                "111-111-1123", "Bedrock Dinosaur Watch Dept", true);
                Partner partner2 = new Partner(UUID.randomUUID(), "barney@rubble.com", "Barney", "Rubble",
                                "Rock Breaker", "Bedrock",
                                "111-111-1123", "Bedrock Police Dept", false);
                Partner partner3 = new Partner(UUID.randomUUID(), "john@doe.com", "John", "Doe", "Emergency Manager",
                                "Bedrock",
                                "111-111-1123", "FAA", true);

                Partner partner4 = new Partner(UUID.randomUUID(), "jane@doe.com", "Jane", "Doe",
                                "Director of Fire Dept", "Bedrock",
                                "111-111-1123", "Some Fire Dept", false);

                Partner partner5 = new Partner(UUID.randomUUID(), "tom@brady.com", "Tom", "Brady",
                                "Sporting Event Coordinator", "San Mateo",
                                "111-111-1123", "San Mateo Sports Events", true);

                partnersList = List.of(partner1, partner2, partner3, partner4, partner5);

                recipientsList = List.of(partner1, partner2, partner3, partner4, partner5);

                RecipientsLayout recipientsLayout = new RecipientsLayout(recipientsList);
                WorkflowPage<Recipients> recipientsPage = new WorkflowPage<>("Recipients", recipientsLayout);

                recipientsPage.addValueChangeListener(listener -> {
                        eventWorkFlowInformation.setRecipients(listener);
                });

                WorkflowPage<Partner> requestingPartnerInformationPage = new WorkflowPage<>(
                                "Requesting Partner Information",
                                new RequestingPartnerInformationLayout(partnersList));

                requestingPartnerInformationPage.addValueChangeListener(listener -> {
                        eventWorkFlowInformation.setRequestingPartnerInformation(listener);
                        recipientsLayout.setRequestingPartner(listener);
                });

                AreaOfConcernLayout areaOfConcernLayout = new AreaOfConcernLayout(states, counties);
                WorkflowPage<AreaOfConcern> areaOfConcernPage = new WorkflowPage<>("Area of Concern",
                                areaOfConcernLayout);

                areaOfConcernLayout.addValueChangeListener(listener -> {
                        eventWorkFlowInformation.setAreaOfConcern(listener);
                });

                ValidatableForm<EventInformation> eventInformationLayout = new EventInformationLayout(states);
                WorkflowPage<EventInformation> eventInformationPage = new WorkflowPage<>("Event Information",
                                eventInformationLayout);

                eventInformationPage.addValueChangeListener(listener -> {
                        System.out.println("Value changed" + listener);
                        if (listener.getAddress() != null)
                                areaOfConcernLayout.setEventAddress(listener.getAddress());
                });

                ValidatableForm<SupportSchedule> supportScheduleLayout = new SupportScheduleLayout();
                WorkflowPage<SupportSchedule> supportSchedulePage = new WorkflowPage<>("Support Schedule",
                                supportScheduleLayout);

                WorkflowSection eventWorflowSection = new WorkflowSection("Event",
                                requestingPartnerInformationPage,
                                eventInformationPage, supportSchedulePage, areaOfConcernPage, recipientsPage);

                WeatherThresholdLayout weatherThresholdLayout = new WeatherThresholdLayout(weatherHazards);
                WorkflowPage<WeatherThreshold> weatherThresholdPage = new WorkflowPage<>("Weather Threshold",
                                weatherThresholdLayout);

                weatherThresholdPage.addValueChangeListener(listener -> {
                        eventWorkFlowInformation.setWeatherThreshold(listener);
                        System.out.println("Weather Threshold changed" + listener);
                });

                WorkflowSection impactWorkflowSection = new WorkflowSection("Impact", weatherThresholdPage);

                // WorkFlow workFlow = new WorkFlow(eventWorflowSection, impactWorkflowSection);
                WorkFlow workFlow = new WorkFlow(impactWorkflowSection);

                add(workFlow);
        }

}
