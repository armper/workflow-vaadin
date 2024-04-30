package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

public class Impact {
    private String level;
    private String risk;
    private String impactStatement;
    private String actions;

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

    public String toString() {
        return level + " " + risk + " " + impactStatement + " " + actions;
    }

}
