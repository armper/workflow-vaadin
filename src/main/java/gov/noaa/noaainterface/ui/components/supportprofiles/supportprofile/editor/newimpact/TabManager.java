package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.newimpact;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

public class TabManager extends VerticalLayout {
    private Tabs tabs = new Tabs();
    private VerticalLayout contentContainer = new VerticalLayout();
    private Map<Tab, Component> tabContents = new HashMap<>();
    private int tabCounter = 0;

    public TabManager() {
        Button addTabButton = new Button(new Icon(VaadinIcon.PLUS), click -> addNewTab());
        add(new HorizontalLayout(tabs, addTabButton), contentContainer);
        tabs.setWidthFull();
        contentContainer.setWidthFull();
        setWidthFull();

        // Add initial tab
        addNewTab();
    }

    public void addNewTab() {
        tabCounter++;
        String tabName = "Set " + tabCounter;
        Tab newTab = new Tab(tabName);
        VerticalLayout newTabContent = createNewTabContent();
        tabContents.put(newTab, newTabContent);

        tabs.add(newTab);
        tabs.setSelectedTab(newTab);
        updateDisplayedContent(newTabContent);

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = tabs.getSelectedTab();
            updateDisplayedContent(tabContents.get(selectedTab));
        });
    }

    private void updateDisplayedContent(Component content) {
        contentContainer.removeAll();
        if (content != null) {
            contentContainer.add(content);
        }
    }

    private VerticalLayout createNewTabContent() {
        ComboBox<String> thresholdsComboBox = new ComboBox<>("Add thresholds");
        thresholdsComboBox.setItems("wind", "dewpoint", "probability", "winter weather", "fire weather");
        Paragraph descriptionParagraph = new Paragraph(
                "Search by element ('wind', 'dewpoint', 'probability'...) or by category ('winter weather', 'fire weather'...)");
        return new VerticalLayout(thresholdsComboBox, descriptionParagraph);
    }
}
