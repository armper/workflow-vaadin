package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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

    List<Impact> impacts = new ArrayList<>();

    private String selectedWeatherHazard;

    public WeatherThresholdLayout(List<String> initialWeatherHazards) {
        thresholdsDialog = new ThresholdsDialog(impacts);

        thresholdsDialog.setSaveImpactListener(impact -> {
            onSaveImpactListener(impact);
        });

        this.weatherHazards.addAll(initialWeatherHazards);
        customHazardLayout.setVisible(false);
        weatherHazardComboBox.setItems(weatherHazards);
        weatherHazardComboBox.setPlaceholder("Select");

        add(instructions, new HorizontalLayout(weatherHazardComboBox, customHazardLayout), impactTabs);
        setupEventHandlers();
    }

    private void onSaveImpactListener(Impact impact) {
        impacts.add(impact);
        impactTabs.add(weatherHazardComboBox.getValue(), new ImpactCard(impact));

        // Reset the ComboBox after adding an impact. But also store what was selected.
        selectedWeatherHazard = weatherHazardComboBox.getValue();
        weatherHazardComboBox.clear();

        thresholdsDialog = new ThresholdsDialog(impacts);
        thresholdsDialog.setSaveImpactListener(newImpact -> {
            onSaveImpactListener(newImpact);
        });
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
                weatherHazards.add(customHazardName.getValue());
                weatherHazardComboBox.setItems(weatherHazards);
                weatherHazardComboBox.setValue(customHazardName.getValue());
            }
            showThresholdsModal();

        });

        impactTabs.addImpactCardEditListener(listener -> {
            thresholdsDialog = new ThresholdsDialog(impacts);
            thresholdsDialog.setImpact(listener.getImpactCard().getImpact());
            thresholdsDialog.setSaveImpactListener(newImpact -> {
                onSaveImpactListener(newImpact);
            });

            thresholdsDialog.setTitle(selectedWeatherHazard);
            thresholdsDialog.open();
        });

    }

    private void showThresholdsModal() {
        thresholdsDialog.setTitle(weatherHazardComboBox.getValue());
        thresholdsDialog.setImpact(new Impact());
        thresholdsDialog.open();
    }

    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException("Unimplemented method 'isValid'");
    }

    @Override
    public String getErrorMessage() {
        throw new UnsupportedOperationException("Unimplemented method 'getErrorMessage'");
    }

    @Override
    public void showErrors() {
        throw new UnsupportedOperationException("Unimplemented method 'showErrors'");
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public WeatherThreshold getData() {
        throw new UnsupportedOperationException("Unimplemented method 'getData'");
    }

    @Override
    public void addValueChangeListener(Consumer<WeatherThreshold> listener) {
        throw new UnsupportedOperationException("Unimplemented method 'addValueChangeListener'");
    }
}
