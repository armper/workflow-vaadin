package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

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
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;

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
    private TabManager tabManager = new TabManager();

    private final Button saveImpactButton = new Button("Save Impact");
    private final Button discardImpactButton = new Button("Discard Impact");

    private final VerticalLayout filteredImpactsLayout = new VerticalLayout();
    private final VerticalLayout recentlyUsedImpacts;
    private final VerticalLayout mainLayout = new VerticalLayout();

    private final Binder<Impact> impactBinder = new Binder<>(Impact.class);
    private Consumer<Impact> saveImpactListener;
    private Impact impact;
    private final Set<Impact> impacts;
    private final Div pageContainer = new Div();

    public void setSaveImpactListener(Consumer<Impact> saveImpactListener) {
        this.saveImpactListener = saveImpactListener;
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
        impactBinder.readBean(impact);

        title.setText("Impact for " + impact.getWeatherHazard());

    }

    public ThresholdsDialog(Set<Impact> impacts) {
        this.impacts = impacts;
        impactBinder.setBean(impact);
        setupBinder();

        recentlyUsedImpacts = new VerticalLayout(searchRecentImpacts, new HorizontalLayout(recentLevel, recentRisk),
                filteredImpactsLayout);
        recentlyUsedImpacts.addClassName(LumoUtility.Background.CONTRAST_5);
        filteredImpactsLayout.setHeight("50vh");
        filteredImpactsLayout.getStyle().set("overflow-y", "auto");

        updateFilteredImpacts(impacts); // Initially display all impacts

        // Filter logic
        searchRecentImpacts.addValueChangeListener(e -> filterImpacts());
        recentLevel.addValueChangeListener(e -> filterImpacts());
        recentLevel.setPlaceholder("Select");
        recentLevel.setClearButtonVisible(true);
        recentRisk.addValueChangeListener(e -> filterImpacts());
        recentRisk.setPlaceholder("Select");
        recentRisk.setClearButtonVisible(true);

        configureComponents();
        layoutComponents();

    }

    private void filterImpacts() {
        String searchText = searchRecentImpacts.getValue().toLowerCase();
        String selectedLevel = recentLevel.getValue();
        String selectedRisk = recentRisk.getValue();

        Set<Impact> filtered = impacts.stream()
                .filter(impact -> (impact.toString().toLowerCase().contains(searchText)) &&
                        (selectedLevel == null || impact.getLevel().equals(selectedLevel)) &&
                        (selectedRisk == null || impact.getRisk().equals(selectedRisk)))
                .collect(Collectors.toSet());
        updateFilteredImpacts(filtered);
    }

    private void updateFilteredImpacts(Set<Impact> filteredImpacts) {
        filteredImpactsLayout.removeAll();
        filteredImpacts.forEach(impact -> {
            ImpactSummary impactSummary = new ImpactSummary(impact.getLevel(), impact.getRisk(),
                    impact.getImpactStatement());
            impactSummary.getSelectButton().addClickListener(e -> {
                impactBinder.setBean(impact);
            });
            filteredImpactsLayout.add(impactSummary);
            filteredImpactsLayout.add(new Hr());
        });
    }

    private void setupBinder() {
        impactBinder.forField(level).asRequired().bind(Impact::getLevel, Impact::setLevel);
        impactBinder.forField(risk).asRequired().bind(Impact::getRisk, Impact::setRisk);
        impactBinder.forField(impactStatement).asRequired().bind(Impact::getImpactStatement,
                Impact::setImpactStatement);
        impactBinder.forField(actions).asRequired().bind(Impact::getActions, Impact::setActions);

        saveImpactButton.addClickListener(e -> {
            if (impactBinder.writeBeanIfValid(impact)) {
                saveImpactListener.accept(impact);
                close();
            }
        });

        discardImpactButton.addClickListener(e -> {
            this.impact = new Impact(this.impact.getWeatherHazard());
            impactBinder.readBean(impact);
            close();
        });
    }

    private void configureComponents() {
        searchRecentImpacts.setClearButtonVisible(true);

        level.setPlaceholder("Select");
        level.setClearButtonVisible(true);
        risk.setPlaceholder("Select");
        risk.setClearButtonVisible(true);

        impactStatement.setWidthFull();
        actions.setWidthFull();

        recentLevel.setItems("Level 1", "Level 2", "Level 3", "Level 4", "Level 5");
        recentRisk.setItems("Little to none", "Minor", "Moderate", "Significant", "Extreme");

        level.setItems("Level 1", "Level 2", "Level 3", "Level 4", "Level 5");
        risk.setItems("Little to none", "Minor", "Moderate", "Significant", "Extreme", "Custom");

        risk.addValueChangeListener(event -> customLabel.setVisible("Custom".equals(risk.getValue())));
        customLabel.setVisible(false);
        customLabel.addValueChangeListener(event -> {
            String value = event.getValue();
            if ("Custom".equals(risk.getValue())) {
                risk.setItems("Little to none", "Minor", "Moderate", "Significant", "Extreme", value);
                risk.setValue(value);
            }
        });

        saveImpactButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    }

    private void layoutComponents() {
        HorizontalLayout levelRiskLayout = new HorizontalLayout(level, risk, customLabel);
        levelRiskLayout.setWidthFull();
        HorizontalLayout buttonBar = new HorizontalLayout(discardImpactButton, saveImpactButton);
        buttonBar.setWidthFull();
        buttonBar.setJustifyContentMode(JustifyContentMode.END); // Flush right

        // Add the pageContainer below the tabs within the main layout
        mainLayout.add(levelRiskLayout, impactStatement, actions, tabManager, pageContainer);
        mainLayout.setWidth("100%");

        // Set the width of pageContainer to match the main layout if necessary
        pageContainer.setWidthFull();

        // Add the main layout to the Dialog
        add(title, new HorizontalLayout(recentlyUsedImpacts, mainLayout), buttonBar);
    }

}
