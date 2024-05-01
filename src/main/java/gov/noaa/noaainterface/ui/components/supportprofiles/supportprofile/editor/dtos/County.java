package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.dtos;

public class County {

    private String name;
    private String polygonCoordinates;

    public County(String name, String polygonCoordinates) {
        this.name = name;
        this.polygonCoordinates = polygonCoordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPolygonCoordinates() {
        return polygonCoordinates;
    }

    public void setPolygonCoordinates(String polygonCoordinates) {
        this.polygonCoordinates = polygonCoordinates;
    }

}
