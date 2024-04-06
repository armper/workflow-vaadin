
package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.helloworld;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.PageChangeEvent;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.WorkflowPage;

public class WorkflowSection extends Div {

    private final String itemName;
    private final WorkflowPage[] pages;
    private final Span progressLabel;
    private final Button nextButton, previousButton;
    private int currentPageIndex = 0;

    public WorkflowSection(String itemName, WorkflowPage... pages) {
        this.itemName = itemName;
        this.pages = pages;
        this.progressLabel = new Span();
        this.nextButton = new Button("Next", event -> nextPage());
        this.previousButton = new Button("Previous", event -> previousPage());

        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        previousButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        setupSectionPages();
        add(progressLabel, createNavigationLayout());
    }

    private HorizontalLayout createNavigationLayout() {
        // Layout to hold navigation buttons
        HorizontalLayout navigationLayout = new HorizontalLayout(previousButton, nextButton);
        navigationLayout.setWidthFull();
        navigationLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        updateButtonStates(); // Update button states based on current page
        return navigationLayout;
    }

    private void setupSectionPages() {
        removeAll();
        if (pages.length > 0) {
            add(pages[currentPageIndex]);
            updateButtonStates();
        }
    }

    private void updateButtonStates() {
        previousButton.setEnabled(currentPageIndex > 0);
        nextButton.setEnabled(currentPageIndex < pages.length - 1);
    }

    public void previousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            updateSectionView();
            fireEvent(new PageChangeEvent(this, currentPageIndex, pages.length)); // Include progress info
        }
    }

    public boolean isLastPage() {
        return currentPageIndex >= pages.length - 1;
    }

    public void nextPage() {
        if (currentPageIndex < pages.length - 1) {
            currentPageIndex++;
            updateSectionView();
            fireEvent(new PageChangeEvent(this, currentPageIndex, pages.length)); // Include progress info
        }
    }

    private void updateSectionView() {
        removeAll();
        add(pages[currentPageIndex], progressLabel, createNavigationLayout());
    }

    public Registration addPageChangeListener(ComponentEventListener<PageChangeEvent> listener) {
        return addListener(PageChangeEvent.class, listener);
    }

    public int getTotalPages() {
        return pages.length;
    }

    public String getItemName() {
        return itemName;
    }
}