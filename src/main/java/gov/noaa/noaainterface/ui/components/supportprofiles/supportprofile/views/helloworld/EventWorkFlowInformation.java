package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.helloworld;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.AreaOfConcern;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.Partner;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.Recipients;

public class EventWorkFlowInformation {

    private Recipients recipients;

    private Partner requestingPartnerInformation;

    private AreaOfConcern areaOfConcern;

    public Recipients getRecipients() {
        return recipients;
    }

    public void setRecipients(Recipients recipients) {
        this.recipients = recipients;
    }

    public Partner getRequestingPartnerInformation() {
        return requestingPartnerInformation;
    }

    public void setRequestingPartnerInformation(Partner requestingPartnerInformation) {
        this.requestingPartnerInformation = requestingPartnerInformation;
    }

    public AreaOfConcern getAreaOfConcern() {
        return areaOfConcern;
    }

    public void setAreaOfConcern(AreaOfConcern areaOfConcern) {
        this.areaOfConcern = areaOfConcern;
    }

}
