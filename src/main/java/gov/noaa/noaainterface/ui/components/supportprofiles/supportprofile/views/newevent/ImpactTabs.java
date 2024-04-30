package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class ImpactTabs extends VerticalLayout {
    private Tabs tabs = new Tabs();
    private Map<Tab, VerticalLayout> tabContents = new HashMap<>();
    private VerticalLayout contentContainer = new VerticalLayout();

    public ImpactTabs() {
        add(tabs, contentContainer);
        contentContainer.setSizeFull();

        tabs.addSelectedChangeListener(event -> {
            contentContainer.removeAll();
            VerticalLayout selectedContent = tabContents.get(tabs.getSelectedTab());
            if (selectedContent != null) {
                contentContainer.add(selectedContent);
            }
        });
    }

    public void add(String tabName, ImpactCard impactCard) {
        Optional<Tab> existingTab = findTabByName(tabName);
        VerticalLayout contentContainer;

        if (existingTab.isPresent()) {
            contentContainer = tabContents.get(existingTab.get());
        } else {
            Tab newTab = new Tab(tabName);
            contentContainer = new VerticalLayout();
            tabContents.put(newTab, contentContainer);
            tabs.add(newTab);
        }

        contentContainer.add(impactCard);
        styleImpactCard(contentContainer, impactCard);
        attachEventHandlers(impactCard, contentContainer, existingTab.orElse(null));

        if (tabs.getSelectedTab() == null || tabs.getSelectedTab() == existingTab.orElse(null)) {
            this.contentContainer.removeAll();
            this.contentContainer.add(contentContainer);
            tabs.setSelectedTab(existingTab.orElseGet(() -> new Tab(tabName)));
        }
    }

    public Registration addImpactCardEditListener(ComponentEventListener<ImpactCardEditEvent> listener) {
        return addListener(ImpactCardEditEvent.class, listener);
    }

    private void styleImpactCard(VerticalLayout container, ImpactCard card) {
        if (container.getComponentCount() % 2 != 0) { // Check if the count is odd
            card.addClassName(LumoUtility.Background.CONTRAST_10); // Apply darker background
        } else {
            card.removeClassName(LumoUtility.Background.CONTRAST_10); // Ensure normal background
        }
    }

    private void attachEventHandlers(ImpactCard impactCard, VerticalLayout contentContainer, Tab tab) {
        // Handle delete button
        impactCard.getDeleteButton().addClickListener(event -> {
            contentContainer.remove(impactCard);
            if (contentContainer.getComponentCount() == 0) {
                removeTab(tab);
            }
        });

        // Handle edit button
        impactCard.getEditButton().addClickListener(event -> {
            fireEvent(new ImpactCardEditEvent(this, impactCard));
        });
    }

    private void removeTab(Tab tab) {
        tabs.remove(tab);

        if (tabs.getSelectedTab() == null && !tabs.getChildren().findFirst().isEmpty()) {
            tabs.getChildren()
                    .filter(Tab.class::isInstance)
                    .map(Tab.class::cast)
                    .findFirst()
                    .ifPresent(nextTab -> {
                        tabs.setSelectedTab(nextTab);
                        contentContainer.removeAll();
                        contentContainer.add(tabContents.get(nextTab));
                    });
        }
    }

    private Optional<Tab> findTabByName(String tabName) {
        return tabs.getChildren()
                .filter(Tab.class::isInstance)
                .map(Tab.class::cast)
                .filter(tab -> tab.getLabel().equals(tabName))
                .findFirst();
    }
}
