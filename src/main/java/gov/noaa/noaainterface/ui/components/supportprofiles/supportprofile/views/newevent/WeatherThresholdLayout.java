package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.interfaces.ValidatableForm;

public class WeatherThresholdLayout extends VerticalLayout implements ValidatableForm<WeatherThreshold> {
    private final Paragraph instructions = new Paragraph(
            "Set weather condition thresholds for the forecaster to be aware of. You must add at least one impact.");
    private final ComboBox<String> weatherHazardComboBox = new ComboBox<>("Weather Hazard");
    private final TextField customHazardName = new TextField("Custom Hazard Name");
    private final Button addHazardButton = new Button("Add Hazard", new Icon(VaadinIcon.PLUS));
    private final HorizontalLayout customHazardLayout = new HorizontalLayout(customHazardName, addHazardButton);
    private ThresholdsDialog thresholdsDialog;
    private final List<String> weatherHazards = new ArrayList<>();
    private final ImpactTabs impactTabs = new ImpactTabs();

    private Set<Impact> impacts = new HashSet<>();

    public WeatherThresholdLayout(List<String> initialWeatherHazards) {
        super();
        initializeComponents(initialWeatherHazards);
        setupEventHandlers();
    }

    private void initializeComponents(List<String> initialWeatherHazards) {
        weatherHazards.addAll(initialWeatherHazards);
        customHazardLayout.setVisible(false);
        weatherHazardComboBox.setItems(weatherHazards);
        weatherHazardComboBox.setPlaceholder("Select");

        thresholdsDialog = new ThresholdsDialog(impacts);
        thresholdsDialog.setSaveImpactListener(this::onSaveImpactListener);

        add(instructions, new HorizontalLayout(weatherHazardComboBox, customHazardLayout), impactTabs);
    }

    private void onSaveImpactListener(Impact impact) {
        impacts.add(impact);
        impactTabs.addOrUpdateImpact(impact);
        refreshDialog();
    }

    private void refreshDialog() {
        weatherHazardComboBox.clear();
        thresholdsDialog = new ThresholdsDialog(impacts);
        thresholdsDialog.setSaveImpactListener(this::onSaveImpactListener);
    }

    private void setupEventHandlers() {
        weatherHazardComboBox.addValueChangeListener(event -> {
            boolean isCustomHazard = "Custom Hazard".equals(event.getValue());
            customHazardLayout.setVisible(isCustomHazard);
            if (!isCustomHazard) {
                showThresholdsModal();
            }
        });

        addHazardButton.addClickListener(event -> {
            if ("Custom Hazard".equals(weatherHazardComboBox.getValue())) {
                String customName = customHazardName.getValue();
                weatherHazards.add(customName);
                weatherHazardComboBox.setItems(weatherHazards);
                weatherHazardComboBox.setValue(customName);
            }
            showThresholdsModal();
        });

        impactTabs.addImpactCardEditListener(event -> {
            Impact impact = event.getImpactCard().getImpact();
            thresholdsDialog.setImpact(impact);
            thresholdsDialog.open();
        });
    }

    private void showThresholdsModal() {
        Impact newImpact = new Impact(weatherHazardComboBox.getValue());
        thresholdsDialog.setImpact(newImpact);
        thresholdsDialog.open();
    }

    @Override
    public boolean isValid() {
        return !impacts.isEmpty();
    }

    @Override
    public String getErrorMessage() {
        if (isValid()) {
            return ""; // No error if the form is valid
        } else {
            return "At least one impact must be added.";
        }
    }

    @Override
    public void showErrors() {
        if (!isValid()) {
            Notification errorNotification = new Notification(getErrorMessage(), 3000);
            errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorNotification.open();
        }
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public WeatherThreshold getData() {
        WeatherThreshold threshold = new WeatherThreshold();
        threshold.setImpacts(new ArrayList<>(impacts));
        return threshold;
    }

    @Override
    public void addValueChangeListener(Consumer<WeatherThreshold> listener) {
        throw new UnsupportedOperationException("Unimplemented method 'addValueChangeListener'");
    }
}
