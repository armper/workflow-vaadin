package gov.noaa.noaainterface.ui.components.supportprofiles.editor.events;

import com.vaadin.flow.component.ComponentEvent;

import gov.noaa.noaainterface.ui.components.supportprofiles.editor.newevent.ImpactCard;
import gov.noaa.noaainterface.ui.components.supportprofiles.editor.newevent.ImpactTabs;

public class ImpactCardEditEvent extends ComponentEvent<ImpactTabs> {
    private ImpactCard impactCard;

    public ImpactCardEditEvent(ImpactTabs source, ImpactCard impactCard) {
        super(source, false);
        this.impactCard = impactCard;
    }

    public ImpactCard getImpactCard() {
        return impactCard;
    }
}
