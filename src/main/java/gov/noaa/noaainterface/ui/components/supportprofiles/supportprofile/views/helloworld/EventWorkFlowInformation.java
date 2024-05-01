package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.helloworld;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.dtos.AreaOfConcern;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.dtos.Partner;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.dtos.Recipients;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.dtos.WeatherThreshold;

public class EventWorkFlowInformation {

    private Recipients recipients;

    private Partner requestingPartnerInformation;

    private AreaOfConcern areaOfConcern;

    private WeatherThreshold weatherThreshold;

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

    public WeatherThreshold getWeatherThreshold() {
        return weatherThreshold;
    }

    public void setWeatherThreshold(WeatherThreshold weatherThreshold) {
        this.weatherThreshold = weatherThreshold;
    }

}
