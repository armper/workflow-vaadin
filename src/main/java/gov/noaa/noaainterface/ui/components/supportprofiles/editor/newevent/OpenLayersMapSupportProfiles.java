package gov.noaa.noaainterface.ui.components.supportprofiles.editor.newevent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.dom.Element;

import elemental.json.JsonArray;
import elemental.json.JsonObject;

@Tag("ol-map-support-profiles")
@JsModule("./ol-map-support-profiles.js")
@NpmPackage(value = "ol", version = "7.5.1")
@NpmPackage(value = "ol-contextmenu", version = "5.3.0")
public class OpenLayersMapSupportProfiles extends LitTemplate {
    private static final Logger logger = LoggerFactory.getLogger(OpenLayersMapSupportProfiles.class);

    @Id("map")
    private Element mapElement;

    public OpenLayersMapSupportProfiles() {
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
        logger.debug("Point clicked at x: " + x + ", y: " + y);
    }

    private void handlePolygonComplete(JsonArray coords) {
        logger.debug("Polygon drawing complete with coordinates: " + coords.toJson());
    }

    public void toggleMode(String mode) {
        getElement().callJsFunction("toggleMode", mode);
    }

    public void setRadius(double radiusInMiles) {
        getElement().callJsFunction("setRadius", radiusInMiles);
    }

    public void clear() {
        getElement().callJsFunction("clear");
    }

    @ClientCallable
    public void drawingStarted() {
        logger.debug("Drawing started on the map");
    }

    @ClientCallable
    public void drawingFinished() {
        logger.debug("Drawing finished on the map");
    }

    public void displayPolygon(String polygonCoordinates) {
        getElement().callJsFunction("displayPolygon", polygonCoordinates);
    }

}
