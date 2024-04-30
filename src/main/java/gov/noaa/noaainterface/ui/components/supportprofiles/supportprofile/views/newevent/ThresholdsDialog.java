package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
    private final Tabs thresholdsTabs = new Tabs();
    private final ComboBox<String> addThresholds = new ComboBox<>("Add Thresholds");
    private final Button saveImpactButton = new Button("Save Impact");
    private final Button discardImpactButton = new Button("Discard Impact");
    private final VerticalLayout filteredImpactsLayout = new VerticalLayout();
    private final VerticalLayout recentlyUsedImpacts;
    private final VerticalLayout mainLayout = new VerticalLayout();
    private Paragraph addThresholdsInstructions = new Paragraph(
            "Search by element ('wind', 'dewpoint', 'probability'...) or by category ('winter weather, 'fire weather'...)");
    private Button addTab;
    private final Binder<Impact> impactBinder = new Binder<>(Impact.class);
    private Consumer<Impact> saveImpactListener;
    private Impact impact = new Impact();
    private final List<Impact> impacts;

    public void setSaveImpactListener(Consumer<Impact> saveImpactListener) {
        this.saveImpactListener = saveImpactListener;
    }

    public void setTitle(String title) {
        this.title.setText("Impact for " + title);
    }

    public ThresholdsDialog(List<Impact> impacts) {
        this.impacts = impacts;
        impactBinder.setBean(impact);
        setupBinder();

        recentlyUsedImpacts = new VerticalLayout(searchRecentImpacts, new HorizontalLayout(recentLevel, recentRisk),
                filteredImpactsLayout);
        filteredImpactsLayout.setHeight("50vh");
        filteredImpactsLayout.getStyle().set("overflow-y", "auto");

        updateFilteredImpacts(impacts); // Initially display all impacts

        // Filter logic
        searchRecentImpacts.addValueChangeListener(e -> filterImpacts());
        recentLevel.addValueChangeListener(e -> filterImpacts());
        recentRisk.addValueChangeListener(e -> filterImpacts());

        configureComponents();
        layoutComponents();
    }

    private void filterImpacts() {
        String searchText = searchRecentImpacts.getValue().toLowerCase();
        String selectedLevel = recentLevel.getValue();
        String selectedRisk = recentRisk.getValue();

        List<Impact> filtered = impacts.stream()
                .filter(impact -> (impact.toString().toLowerCase().contains(searchText)) &&
                        (selectedLevel == null || impact.getLevel().equals(selectedLevel)) &&
                        (selectedRisk == null || impact.getRisk().equals(selectedRisk)))
                .collect(Collectors.toList());
        updateFilteredImpacts(filtered);
    }

    private void updateFilteredImpacts(List<Impact> filteredImpacts) {
        filteredImpactsLayout.removeAll();
        filteredImpacts.forEach(impact -> {
            ImpactSummary impactSummary = new ImpactSummary(impact.getLevel(), impact.getRisk(),
                    impact.getImpactStatement());
            impactSummary.getSelectButton().addClickListener(e -> {
                impactBinder.setBean(impact);
            });
            filteredImpactsLayout.add(impactSummary);
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
    }

    private void configureComponents() {
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

        addTab = new Button(new Icon(VaadinIcon.PLUS));
        addTab.addClickListener(e -> addNewTab());
        thresholdsTabs.add(new Tab("Set 1"));
    }

    private void layoutComponents() {
        HorizontalLayout levelRiskLayout = new HorizontalLayout(level, risk, customLabel);
        HorizontalLayout tabsWithAddButtonLayout = new HorizontalLayout(thresholdsTabs, addTab);
        HorizontalLayout buttonBar = new HorizontalLayout(discardImpactButton, saveImpactButton);
        buttonBar.setJustifyContentMode(JustifyContentMode.END); // Flush right

        mainLayout.add(levelRiskLayout, impactStatement, actions, tabsWithAddButtonLayout, addThresholds,
                addThresholdsInstructions);
        add(title, new HorizontalLayout(recentlyUsedImpacts, mainLayout), buttonBar);
    }

    private void addNewTab() {
        int numTabs = thresholdsTabs.getComponentCount();
        thresholdsTabs.add(new Tab("Set " + (numTabs + 1)));
    }
}
