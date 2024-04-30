package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import com.vaadin.flow.component.ComponentEvent;

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
