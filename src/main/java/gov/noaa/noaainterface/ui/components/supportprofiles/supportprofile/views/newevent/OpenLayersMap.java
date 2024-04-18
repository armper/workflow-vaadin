package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.dom.Element;
import elemental.json.JsonArray;
import elemental.json.JsonObject;

@Tag("ol-map")
@JsModule("./ol-map.js")
@NpmPackage(value = "ol", version = "7.5.1")
@NpmPackage(value = "ol-contextmenu", version = "5.3.0")
public class OpenLayersMap extends LitTemplate {

    @Id("map")
    private Element mapElement;

    public OpenLayersMap() {
        // Setup to handle custom events from the client-side component
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // Listen to custom events by adding them in the constructor
        getElement().addEventListener("point-click", event -> {
            JsonObject eventData = event.getEventData();
            double x = eventData.getNumber("event.detail.coordinate[0]");
            double y = eventData.getNumber("event.detail.coordinate[1]");
            handlePointClick(x, y);
        }).addEventData("event.detail.coordinate[0]").addEventData("event.detail.coordinate[1]");

        getElement().addEventListener("polygon-complete", event -> {
            JsonObject eventData = event.getEventData();
            JsonArray coords = eventData.getArray("event.detail.coords[0][0]");
            handlePolygonComplete(coords);
        }).addEventData("event.detail.coords[0][0]");
    }

    private void handlePointClick(double x, double y) {
        System.out.println("Point clicked at coordinates: [" + x + ", " + y + "]");
    }

    private void handlePolygonComplete(JsonArray coords) {
        System.out.println("Polygon drawing complete with coordinates: " + coords.toJson());
    }

    public void toggleMode(String mode) {
        getElement().callJsFunction("toggleMode", mode);
    }

    public void setRadius(double radiusInMiles) {
        getElement().callJsFunction("setRadius", radiusInMiles);
    }

    @ClientCallable
    public void drawingStarted() {
        System.out.println("Drawing started on the map");
    }

    @ClientCallable
    public void drawingFinished() {
        System.out.println("Drawing finished on the map");
    }
}
