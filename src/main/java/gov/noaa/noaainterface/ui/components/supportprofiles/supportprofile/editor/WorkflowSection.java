
package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.events.PageChangeEvent;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.events.SubmitEvent;

public class WorkflowSection extends Div {

    private final String itemName;
    private final WorkflowPage[] pages;
    private final Span progressLabel;
    private final Button nextButton, previousButton;
    private int currentPageIndex = 0;
    private Button submitButton;

    public WorkflowSection(String itemName, WorkflowPage... pages) {
        this.itemName = itemName;
        this.pages = pages;
        this.progressLabel = new Span();
        this.nextButton = new Button("Next", event -> nextPage());
        this.previousButton = new Button("Previous", event -> previousPage());
        this.submitButton = new Button("Submit", event -> fireEvent(new SubmitEvent(this)));
        submitButton.setVisible(false);

        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        previousButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        setupSectionPages();
        add(progressLabel, createNavigationLayout());

        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    }

    public Registration addSubmitListener(ComponentEventListener<SubmitEvent> listener) {
        return addListener(SubmitEvent.class, listener);
    }

    private HorizontalLayout createNavigationLayout() {
        // Spacer to push buttons to the right
        Span spacer = new Span();
        spacer.setWidthFull();

        // Layout to hold navigation buttons
        HorizontalLayout navigationLayout = new HorizontalLayout(previousButton, spacer, nextButton, submitButton);
        navigationLayout.setWidthFull();
        navigationLayout.expand(spacer); // Make the spacer take up all extra space

        // Ensure buttons are aligned correctly
        navigationLayout.setVerticalComponentAlignment(FlexComponent.Alignment.END, nextButton, submitButton);
        navigationLayout.setVerticalComponentAlignment(FlexComponent.Alignment.START, previousButton);

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
            WorkflowPage<?> workflowPage = pages[currentPageIndex];

            currentPageIndex--;
            updateSectionView();
            fireEvent(new PageChangeEvent<>(this, currentPageIndex, pages.length, workflowPage.getData()));
        }
    }

    public boolean isLastPage() {
        return currentPageIndex >= pages.length - 1;
    }

    public void nextPage() {

        // check if page is valid
        WorkflowPage<?> workflowPage = pages[currentPageIndex];
        if (workflowPage.isValid()) {
            if (currentPageIndex < pages.length - 1) {
                currentPageIndex++;
                updateSectionView();
                fireEvent(new PageChangeEvent<>(this, currentPageIndex, pages.length, workflowPage.getData()));
            }
        } else {
            workflowPage.showErrors();

        }

    }

    private void updateSectionView() {
        removeAll();
        add(pages[currentPageIndex], progressLabel, createNavigationLayout());
    }

    public Registration addPageChangeListener(ComponentEventListener<PageChangeEvent<?>> listener) {
        @SuppressWarnings("unchecked")
        Registration registration = addListener((Class<PageChangeEvent<?>>) (Class<?>) PageChangeEvent.class, listener);
        return registration;
    }

    public int getTotalPages() {
        return pages.length;
    }

    public String getItemName() {
        return itemName;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void hideNextButton() {
        nextButton.setVisible(false);
    }

    public void showNextButton() {
        nextButton.setVisible(true);
    }

    public Button getSubmitButton() {
        return this.submitButton;
    }
}