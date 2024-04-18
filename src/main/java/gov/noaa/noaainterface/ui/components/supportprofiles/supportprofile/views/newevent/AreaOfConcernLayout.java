package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import elemental.json.JsonArray;
import elemental.json.JsonObject;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.interfaces.ValidatableForm;

public class AreaOfConcernLayout extends VerticalLayout implements ValidatableForm<AreaOfConcern> {

    private Binder<AreaOfConcern> binder = new Binder<>(AreaOfConcern.class);
    private Consumer<AreaOfConcern> valueChangeListener;

    private RadioButtonGroup<String> type = new RadioButtonGroup<>("Type");

    private Span eventAddressLabel = new Span("Event Address ");
    private Span eventAddress = new Span();

    private TextField latitude = new TextField("Latitude");
    private TextField longitude = new TextField("Longitude");

    private ComboBox<String> radius = new ComboBox<>("Radius");
    private ComboBox<String> countyComboBox = new ComboBox<>("County/Parish/Borough");

    private Icon editAddressIcon = new Icon(VaadinIcon.EDIT);

    private AddressDialog eventAddressDialog;
    private final Collection<String> counties;

    private OpenLayersMap openLayersMap = new OpenLayersMap();
    private Div openLayersMapDiv = new Div();

    Button clearArea = new Button("Clear Area");

    public AreaOfConcernLayout(Collection<String> states, Collection<String> counties) {
        this.counties = counties;

        eventAddressDialog = new AddressDialog(states);

        binder.setBean(new AreaOfConcern());

        FormLayout formLayout = createFormLayout();
        add(formLayout);

        setResponsiveSteps(formLayout);
        addlisteners();
        configureComponents();
        bindFields();

        // Set default value
        addAttachListener(listener -> {
            type.setValue("Point");
            radius.setValue("20 mi");
            updateVisibilityBasedOnType("Point");

        });
    }

    public void setEventAddress(String address) {
        eventAddress.setText(address);
    }

    private FormLayout createFormLayout() {

        openLayersMapDiv.add(openLayersMap);
        openLayersMapDiv.setWidth("100%");
        openLayersMapDiv.setHeight("75vh");

        FormLayout formLayout = new FormLayout();
        formLayout.add(type, 2);
        formLayout.add(eventAddressLabel, 2);
        formLayout.add(eventAddress, 2);
        formLayout.add(latitude, 1);
        formLayout.add(longitude, 1);
        formLayout.add(countyComboBox, 2);
        formLayout.add(radius, 2);
        formLayout.add(clearArea, 2);
        formLayout.add(openLayersMapDiv, 2);
        return formLayout;
    }

    private void setResponsiveSteps(FormLayout formLayout) {
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2));
    }

    private void addlisteners() {
        editAddressIcon.addClickListener(event -> {
            eventAddressDialog.open();
        });

        eventAddressDialog.setOnSave(address -> {
            eventAddress.setText(address.getAddress1() + ", " + address.getCity() + ", " + address.getState() + " "
                    + address.getZipCode());
        });

        type.addValueChangeListener(event -> {
            updateVisibilityBasedOnType(event.getValue());

            if (event.getValue().equals("Custom Area")) {
                openLayersMap.toggleMode("polygon");
            } else {
                openLayersMap.toggleMode("point");
            }
        });

        radius.addValueChangeListener(event -> {
            openLayersMap.setRadius(Double.parseDouble(event.getValue().split(" ")[0]));
        });

        // Use Java lambda expressions to handle custom events
        openLayersMap.getElement().addEventListener("point-click", event -> {
            double x = event.getEventData().getNumber("event.detail.coordinate[0]");
            double y = event.getEventData().getNumber("event.detail.coordinate[1]");
            this.latitude.setValue(String.valueOf(x));
            this.longitude.setValue(String.valueOf(y));
        }).addEventData("event.detail.coordinate[0]").addEventData("event.detail.coordinate[1]");

        openLayersMap.getElement().addEventListener("polygon-complete", event -> {
            JsonObject eventData = event.getEventData();
            JsonArray outerRing = eventData.getArray("event.detail.coords[0]");
            handlePolygonComplete(outerRing);
        }).addEventData("event.detail.coords[0]");

    }

    private void handlePolygonComplete(JsonArray outerRing) {
        StringBuilder coordsBuilder = new StringBuilder("Polygon Coordinates: ");
        for (int i = 0; i < outerRing.length(); i++) {
            JsonArray point = outerRing.getArray(i);
            double x = point.getNumber(0);
            double y = point.getNumber(1);
            coordsBuilder.append(String.format("[%f, %f], ", x, y));
        }
        System.out.println(coordsBuilder.toString());
    }

    private void configureComponents() {

        configureDialog();

        eventAddressLabel.add(editAddressIcon);

        type.setItems("Point", "County/Parish/Borough", "Custom Area");

        radius.setItems("1 mi", "2 mi", "5 mi", "10 mi", "20 mi", "50 mi");
        countyComboBox.setItems(counties);

        binder.addValueChangeListener(event -> {
            if (valueChangeListener != null && binder.getBean() != null && binder.isValid()) {
                valueChangeListener.accept(binder.getBean());
            }
        });

    }

    private void updateVisibilityBasedOnType(String typeValue) {
        boolean isPoint = "Point".equals(typeValue);
        boolean isCounty = "County/Parish/Borough".equals(typeValue);

        latitude.setVisible(isPoint);
        longitude.setVisible(isPoint);
        countyComboBox.setVisible(isCounty);

        radius.setVisible(isPoint);

        clearArea.setVisible("Custom Area".equals(typeValue));
    }

    private void configureDialog() {

    }

    private void bindFields() {
        binder.forField(type)
                .asRequired("Type is required")
                .bind(AreaOfConcern::getType, AreaOfConcern::setType);

        binder.bindInstanceFields(this);
    }

    @Override
    public boolean isValid() {
        return binder.isValid();
    }

    @Override
    public String getErrorMessage() {
        if (isValid()) {
            return "";
        } else {
            return binder.validate().getBeanValidationErrors().stream()
                    .map(error -> error.getErrorMessage())
                    .collect(Collectors.joining(", "));
        }
    }

    @Override
    public void showErrors() {
        binder.validate().notifyBindingValidationStatusHandlers();
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public AreaOfConcern getData() {
        return binder.getBean();
    }

    @Override
    public void addValueChangeListener(Consumer<AreaOfConcern> valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

}
