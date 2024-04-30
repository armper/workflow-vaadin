package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import java.util.ArrayList;
import java.util.List;

public class WeatherThreshold {

    private List<Impact> impacts = new ArrayList<>();

    public List<Impact> getImpacts() {
        return impacts;
    }

    public void setImpacts(List<Impact> impacts) {
        this.impacts = impacts;
    }
}
