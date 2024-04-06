package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import java.util.List;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.helloworld.Partner;

public class RequestingPartnerInformation extends VerticalLayout {

    public RequestingPartnerInformation(List<Partner> partners) {

        Span partnerRequestInstructionsSpan = new Span(
                "Contact information for the partner who put in the request for IDSS for an event.");
        Span contactsPageInfoSpan = new Span(
                "If you do not see the partner listed, or if you need to update their information, go to the Contacts page.");

        add(partnerRequestInstructionsSpan, contactsPageInfoSpan);

        ComboBox<Partner> partnerSearchComboBox = new ComboBox<>();
        partnerSearchComboBox.setItems(partners);

        partnerSearchComboBox.setWidth("45%");
        add(partnerSearchComboBox);

        setHorizontalComponentAlignment(Alignment.CENTER, partnerSearchComboBox);

    }
}
