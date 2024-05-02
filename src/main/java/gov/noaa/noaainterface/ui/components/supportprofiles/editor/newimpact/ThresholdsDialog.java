package gov.noaa.noaainterface.ui.components.supportprofiles.editor.newimpact;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;

import gov.noaa.noaainterface.ui.components.supportprofiles.editor.dtos.Impact;
import gov.noaa.noaainterface.ui.components.supportprofiles.editor.newevent.ImpactSummary;

public class ThresholdsDialog extends Dialog {
    private final H1 title = new H1();
    private final TextField searchRecentImpacts = new TextField("Search Recent Impacts");
    private final ComboBox<String> recentLevel = new ComboBox<>("Level");
    private final ComboBox<String> recentRisk = new ComboBox<>("Risk");
    private final ComboBox<String> level = new ComboBox<>("Level");
    private final ComboBox<String> risk = new ComboBox<>("Risk");
    private final TextField customLabel = new TextField("Custom Label");
    private final TextArea impactStatement = new TextArea("Impact Statement");
    private final TextArea actions = new TextArea("Actions");
    private final TabManager tabManager = new TabManager();
    private final Button saveImpactButton = new Button("Save Impact");
    private final Button discardImpactButton = new Button("Discard Impact");
    private final VerticalLayout filteredImpactsLayout = new VerticalLayout();
    private final VerticalLayout recentlyUsedImpacts = new VerticalLayout();
    private final VerticalLayout mainLayout = new VerticalLayout();
    private final Binder<Impact> impactBinder = new Binder<>(Impact.class);
    private final Div pageContainer = new Div();
    private final Set<Impact> impacts;
    private Consumer<Impact> saveImpactListener;
    private Impact impact;

    public ThresholdsDialog(Set<Impact> impacts) {
        this.impacts = impacts;
        initializeComponents();
        configureBinding();
        layoutComponents();
        updateFilteredImpacts(impacts);
    }

    private void initializeComponents() {
        title.setText("Impact Details");

        configureComboBox(recentLevel, "Select Level", true, "Level 1", "Level 2", "Level 3", "Level 4", "Level 5");
        configureComboBox(recentRisk, "Select Risk", true, "Little to none", "Minor", "Moderate", "Significant",
                "Extreme");
        configureComboBox(level, "Select Level", true, "Level 1", "Level 2", "Level 3", "Level 4", "Level 5");
        configureComboBox(risk, "Select Risk", true, "Little to none", "Minor", "Moderate", "Significant", "Extreme",
                "Custom");

        searchRecentImpacts.setClearButtonVisible(true);
        customLabel.setVisible(false);
        customLabel.addValueChangeListener(event -> handleCustomLabelChange(event.getValue()));

        impactStatement.setWidthFull();
        actions.setWidthFull();

        saveImpactButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveImpactButton.addClickListener(e -> saveImpact());
        discardImpactButton.addClickListener(e -> resetAndClose());

        searchRecentImpacts.addValueChangeListener(e -> filterImpacts());
        recentLevel.addValueChangeListener(e -> filterImpacts());
        recentRisk.addValueChangeListener(e -> filterImpacts());
        risk.addValueChangeListener(e -> customLabel.setVisible("Custom".equals(risk.getValue())));
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
        impactBinder.readBean(impact);

        title.setText("Impact for " + impact.getWeatherHazard());

    }

    private void configureComboBox(ComboBox<String> comboBox, String placeholder, boolean clearButtonVisible,
            String... items) {
        comboBox.setItems(items);
        comboBox.setPlaceholder(placeholder);
        comboBox.setClearButtonVisible(clearButtonVisible);
    }

    private void configureBinding() {
        impactBinder.forField(level).asRequired().bind(Impact::getLevel, Impact::setLevel);
        impactBinder.forField(risk).asRequired().bind(Impact::getRisk, Impact::setRisk);
        impactBinder.forField(impactStatement).asRequired().bind(Impact::getImpactStatement,
                Impact::setImpactStatement);
        impactBinder.forField(actions).asRequired().bind(Impact::getActions, Impact::setActions);
    }

    private void layoutComponents() {
        HorizontalLayout levelRiskLayout = new HorizontalLayout(level, risk, customLabel);
        levelRiskLayout.setWidthFull();

        HorizontalLayout buttonBar = new HorizontalLayout(discardImpactButton, saveImpactButton);
        buttonBar.setWidthFull();
        buttonBar.setJustifyContentMode(JustifyContentMode.END);

        recentlyUsedImpacts.add(searchRecentImpacts, new HorizontalLayout(recentLevel, recentRisk),
                filteredImpactsLayout);
        recentlyUsedImpacts.addClassName(LumoUtility.Background.CONTRAST_5);
        filteredImpactsLayout.setHeight("50vh");
        filteredImpactsLayout.getStyle().set("overflow-y", "auto");

        mainLayout.add(levelRiskLayout, impactStatement, actions, tabManager, pageContainer);
        mainLayout.setWidth("100%");
        pageContainer.setWidthFull();

        add(title, new HorizontalLayout(recentlyUsedImpacts, mainLayout), buttonBar);
    }

    private void updateFilteredImpacts(Set<Impact> filteredImpacts) {
        filteredImpactsLayout.removeAll();
        filteredImpacts.forEach(impact -> {
            ImpactSummary impactSummary = new ImpactSummary(impact.getLevel(), impact.getRisk(),
                    impact.getImpactStatement());
            impactSummary.setMaxWidth("30rem");
            impactSummary.getSelectButton().addClickListener(e -> impactBinder.setBean(impact));
            filteredImpactsLayout.add(impactSummary, new Hr());
        });
    }

    private void filterImpacts() {
        String searchText = searchRecentImpacts.getValue().toLowerCase();
        String selectedLevel = recentLevel.getValue();
        String selectedRisk = recentRisk.getValue();

        Set<Impact> filtered = impacts.stream()
                .filter(impact -> impact.toString().toLowerCase().contains(searchText) &&
                        (selectedLevel == null || impact.getLevel().equals(selectedLevel)) &&
                        (selectedRisk == null || impact.getRisk().equals(selectedRisk)))
                .collect(Collectors.toSet());
        updateFilteredImpacts(filtered);
    }

    private void handleCustomLabelChange(String value) {
        if ("Custom".equals(risk.getValue())) {
            risk.setItems("Little to none", "Minor", "Moderate", "Significant", "Extreme", value);
            risk.setValue(value);
        }
    }

    private void saveImpact() {
        if (impactBinder.writeBeanIfValid(impact)) {
            saveImpactListener.accept(impact);
            close();
        } else {
            Notification errorNotification = new Notification("Please fill out all required fields", 3000);
            errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorNotification.open();
        }
    }

    private void resetAndClose() {
        impactBinder.readBean(new Impact(this.impact.getWeatherHazard()));
        close();
    }

    public void setSaveImpactListener(Consumer<Impact> listener) {
        this.saveImpactListener = listener;
    }
}
