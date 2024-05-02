package gov.noaa.noaainterface.ui.components.supportprofiles.editor.newevent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;

import gov.noaa.noaainterface.ui.components.supportprofiles.editor.dtos.Impact;
import gov.noaa.noaainterface.ui.components.supportprofiles.editor.events.ImpactCardEditEvent;

public class ImpactTabs extends VerticalLayout {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(ImpactTabs.class);

    private Tabs tabs = new Tabs();
    private Map<Tab, VerticalLayout> tabContents = new HashMap<>();
    private VerticalLayout contentContainer = new VerticalLayout();
    private Map<UUID, ImpactCard> impactCardMap = new HashMap<>();

    public ImpactTabs() {
        add(tabs, contentContainer);
        contentContainer.setSizeFull();
        tabs.addSelectedChangeListener(event -> switchTab());
    }

    private void switchTab() {
        contentContainer.removeAll();
        Optional.ofNullable(tabContents.get(tabs.getSelectedTab()))
                .ifPresent(contentContainer::add);
    }

    public void addOrUpdateImpact(Impact impact) {
        ImpactCard existingCard = impactCardMap.get(impact.getId());
        if (existingCard != null) {
            existingCard.setImpact(impact);
        } else {
            manageNewImpact(impact);
        }
    }

    private void manageNewImpact(Impact impact) {
        String tabName = impact.getWeatherHazard();
        Optional<Tab> existingTab = findTabByName(tabName);
        VerticalLayout content = existingTab.map(tabContents::get).orElseGet(VerticalLayout::new);

        if (!existingTab.isPresent()) {
            Tab newTab = new Tab(tabName);
            tabs.add(newTab);
            tabContents.put(newTab, content);
            existingTab = Optional.of(newTab);
        }

        ImpactCard newCard = new ImpactCard(impact);
        content.add(newCard);
        impactCardMap.put(impact.getId(), newCard);
        styleImpactCard(content, newCard);
        attachEventHandlers(newCard, content, existingTab.get());

        if (tabs.getSelectedTab() == null || tabs.getSelectedTab() == existingTab.get()) {
            contentContainer.removeAll();
            contentContainer.add(content);
            tabs.setSelectedTab(existingTab.get());
        }
    }

    private void styleImpactCard(VerticalLayout container, ImpactCard card) {
        if (container.getComponentCount() % 2 != 0) {
            card.addClassName(LumoUtility.Background.CONTRAST_10);
        } else {
            card.removeClassName(LumoUtility.Background.CONTRAST_10);
        }
    }

    private void attachEventHandlers(ImpactCard card, VerticalLayout container, Tab tab) {
        card.getDeleteButton().addClickListener(event -> removeCard(card, container, tab));
        card.getEditButton().addClickListener(event -> {
            if (tabExists(tab)) {
                fireEvent(new ImpactCardEditEvent(this, card));
            } else {
                logger.debug("Tab does not exist anymore when trying to edit.");
            }
        });
    }

    private void removeCard(ImpactCard card, VerticalLayout container, Tab tab) {
        container.remove(card);
        impactCardMap.remove(card.getImpactId());
        if (container.getComponentCount() == 0) {
            removeTab(tab);
        }
    }

    private void removeTab(Tab tab) {
        if (tabExists(tab)) {
            logger.debug("Removing tab: " + tab.getLabel());
            tabs.remove(tab);
            Optional.ofNullable(tabContents.remove(tab)).ifPresent(VerticalLayout::removeAll);
            selectNextAvailableTab();
        } else {
            logger.debug("Attempted to remove a non-existent or null tab.");
        }
    }

    private void selectNextAvailableTab() {
        tabs.getChildren().filter(Tab.class::isInstance).findFirst().map(Tab.class::cast)
                .ifPresentOrElse(this::switchToTab, contentContainer::removeAll);
    }

    private void switchToTab(Tab tab) {
        tabs.setSelectedTab(tab);
        contentContainer.removeAll();
        Optional.ofNullable(tabContents.get(tab)).ifPresent(contentContainer::add);
    }

    private boolean tabExists(Tab tab) {
        return tabs.getChildren().filter(Tab.class::isInstance).map(Tab.class::cast).collect(Collectors.toList())
                .contains(tab);
    }

    private Optional<Tab> findTabByName(String tabName) {
        return tabs.getChildren().filter(Tab.class::isInstance).map(Tab.class::cast)
                .filter(tab -> tab.getLabel().equals(tabName)).findFirst();
    }

    public Registration addImpactCardEditListener(ComponentEventListener<ImpactCardEditEvent> listener) {
        return addListener(ImpactCardEditEvent.class, listener);
    }

}
