package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import java.util.List;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.helloworld.Partner;

public class RequestingPartnerInformationLayout extends VerticalLayout {
    private Partner selectedPartner;

    public RequestingPartnerInformationLayout(List<Partner> partners) {
        // Instructions and contacts information
        Span partnerRequestInstructionsSpan = new Span(
                "Contact information for the partner who put in the request for IDSS for an event.");
        Span contactsPageInfoSpan = new Span(
                "If you do not see the partner listed, or if you need to update their information, go to the Contacts page.");

        ComboBox<Partner> partnerSearchComboBox = new ComboBox<>();
        partnerSearchComboBox.setWidth("65%");
        partnerSearchComboBox.setMaxWidth("65%");
        partnerSearchComboBox.setItems(partners);
        partnerSearchComboBox.setItemLabelGenerator(partner -> partner.firstName() + " " + partner.lastName());
        partnerSearchComboBox.setRenderer(new ComponentRenderer<>(this::createPartnerComponent));
        partnerSearchComboBox.setPlaceholder("Search by name or email address");

        // Component to display selected partner details
        VerticalLayout partnerDetailsLayout = new VerticalLayout();
        partnerDetailsLayout.setVisible(false); // Initially hidden
        partnerDetailsLayout.setWidth(partnerSearchComboBox.getWidth());
        partnerDetailsLayout.setMaxWidth(partnerSearchComboBox.getMaxWidth());

        partnerSearchComboBox
                .addValueChangeListener(event -> {
                    updatePartnerDetails(event.getValue(), partnerDetailsLayout);
                    selectedPartner = event.getValue();
                });

        Div comboBoxWrapper = new Div(partnerSearchComboBox, partnerDetailsLayout);
        comboBoxWrapper.setWidthFull();
        comboBoxWrapper.getStyle().set("display", "flex");
        comboBoxWrapper.getStyle().set("flex-direction", "column");
        comboBoxWrapper.getStyle().set("align-items", "center");

        add(partnerRequestInstructionsSpan, contactsPageInfoSpan, comboBoxWrapper);
        setAlignItems(Alignment.CENTER);
    }

    private FlexLayout createPartnerComponent(Partner person) {
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

    private void updatePartnerDetails(Partner selectedPartner, VerticalLayout detailsLayout) {
        detailsLayout.removeAll();
        if (selectedPartner != null) {
            detailsLayout.setVisible(true);

            Icon checkMark = VaadinIcon.CHECK.create();
            checkMark.addClassName(LumoUtility.TextColor.PRIMARY);
            checkMark.setSize("20px");

            VerticalLayout textLayout = new VerticalLayout(
                    new Span(selectedPartner.firstName() + " " + selectedPartner.lastName()),
                    new Span(selectedPartner.jobTitle()),
                    new Span(selectedPartner.city()),
                    new Span(selectedPartner.phone()),
                    new Span(selectedPartner.email()));
            textLayout.setPadding(false);
            textLayout.setSpacing(false);

            HorizontalLayout detailLayout = new HorizontalLayout(checkMark, textLayout);
            detailLayout.setAlignItems(Alignment.START);
            detailLayout.setFlexGrow(0, checkMark);
            detailLayout.setFlexGrow(1, textLayout);

            detailsLayout.add(detailLayout);
        } else {
            detailsLayout.setVisible(false);
        }
    }

    public Partner getSelectedPartner() {
        return selectedPartner;
    }
}
