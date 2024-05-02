package gov.noaa.noaainterface.ui.components.supportprofiles.editor.dtos;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WeatherThreshold {

    private List<Impact> impacts = new ArrayList<>();

    public List<Impact> getImpacts() {
        return impacts;
    }

    public void setImpacts(List<Impact> impacts) {
        this.impacts = impacts;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
