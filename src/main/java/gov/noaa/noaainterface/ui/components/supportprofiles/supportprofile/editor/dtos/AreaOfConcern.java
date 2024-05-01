package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.dtos;

public class AreaOfConcern {

    private String type;

    private Address address = new Address();

    private double latitude;

    private double longitude;

    private String radius;

    private String polygonCoordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getPolygonCoordinates() {
        return polygonCoordinates;
    }

    public void setPolygonCoordinates(String json) {
        this.polygonCoordinates = json;
    }

    @Override
    public String toString() {
        return "AreaOfConcern{" +
                "type='" + type + '\'' +
                ", address=" + address +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", radius='" + radius + '\'' +
                ", polygonCoordinates='" + polygonCoordinates + '\'' +
                '}';
    }
}
