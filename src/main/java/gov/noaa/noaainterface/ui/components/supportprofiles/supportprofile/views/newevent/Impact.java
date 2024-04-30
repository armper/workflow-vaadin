package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Impact {
    private UUID id;
    private String weatherHazard;
    private String level;
    private String risk;
    private String impactStatement;
    private String actions;

    public Impact(String weatherHazard) {
        this.id = UUID.randomUUID();
        this.weatherHazard = weatherHazard;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getWeatherHazard() {
        return weatherHazard;
    }

    public void setWeatherHazard(String weatherHazard) {
        this.weatherHazard = weatherHazard;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getImpactStatement() {
        return impactStatement;
    }

    public void setImpactStatement(String impactStatement) {
        this.impactStatement = impactStatement;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Impact other = (Impact) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
