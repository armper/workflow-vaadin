package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.helloworld.WorkflowSection;

public class WorkFlow extends VerticalLayout {

    private final WorkflowSection[] sections;
    private int currentSectionIndex = 0;
    private final Div sectionContainer; // Container for the current section
    private final Button submitButton; // Button to submit each section and move to the next
    private final ProgressBar[] progressBars; // Array to hold a ProgressBar for each section
    private final Span[] progressLabels; // Array to hold a label for each section

    public WorkFlow(WorkflowSection... sections) {
        this.sections = sections;
        this.sectionContainer = new Div();
        this.progressBars = new ProgressBar[sections.length]; // Initialize the array
        this.progressLabels = new Span[sections.length]; // Initialize the label array

        this.submitButton = new Button("Submit", event -> submitCurrentSection());
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        for (int i = 0; i < sections.length; i++) {
            // WorkflowSection section = sections[i]; // Original
            WorkflowSection section = sections[i];
        
            // Initialize progress bars and labels
            ProgressBar progressBar = new ProgressBar();
            progressBar.setWidthFull();
            progressBars[i] = progressBar;
            progressBar.setValue(1.0 / section.getTotalPages());
        
            Span progressLabel = new Span(
                    String.format("%s Information %d of %d", section.getItemName(), 1, section.getTotalPages()));
            progressLabels[i] = progressLabel;
        
            section.addPageChangeListener(event -> {
                double progress = (event.getCurrentPageIndex() + 1) / (double) event.getTotalPages();
                progressBar.setValue(progress);
                progressLabel.setText(String.format("%s Information %d of %d", section.getItemName(),
                        event.getCurrentPageIndex() + 1, event.getTotalPages()));
                // After updating progress, also check if it's time to show the submit button.
                updateSubmitButtonVisibility();
            });
            
        }
        
        initializeWorkflow();
    }

    private void initializeWorkflow() {
        if (sections.length > 0) {
            addSectionToView(0);
        }
        VerticalLayout progressBarLayout = new VerticalLayout();
        for (int i = 0; i < sections.length; i++) {
            // For each section, add a HorizontalLayout containing the label and the
            // progress bar
            HorizontalLayout layout = new HorizontalLayout(progressLabels[i], progressBars[i]);
            layout.setWidthFull();
            progressBarLayout.add(layout);
        }
        add(sectionContainer, progressBarLayout, submitButton);
        updateSubmitButtonVisibility();
    }

    private void addSectionToView(int sectionIndex) {
        sectionContainer.removeAll();
        if (sectionIndex < sections.length) {
            WorkflowSection section = sections[sectionIndex];
            sectionContainer.add(section);
            // Since progress bar is part of the WorkflowSection, it's automatically
            // included
        }
    }

    private void submitCurrentSection() {
        WorkflowSection currentSection = sections[currentSectionIndex];
        if (!currentSection.isLastPage()) {
            // If not on the last page of the current section, go to the next page
            currentSection.nextPage();
            updateSubmitButtonVisibility(); // Ensure this is updated after navigating pages
        } else if (currentSectionIndex < sections.length - 1) {
            // If on the last page of the current section and more sections remain, move to
            // the next section
            currentSectionIndex++;
            addSectionToView(currentSectionIndex);
            updateSubmitButtonVisibility(); // Update visibility when moving to a new section
        } else {
            // Final section's last page submitted
            submitButton.setEnabled(false); // Or other finalization logic
        }
    }

    private void updateSubmitButtonVisibility() {
        // The submit button should be visible if the current section is on its last
        // page.
        // It does not depend on whether there are more sections left.
        boolean isLastPageInSection = sections[currentSectionIndex].isLastPage();
        submitButton.setVisible(isLastPageInSection); // Show the button when on the last page of any section

        // Additionally, adjust here if you want the button to be disabled instead of
        // invisible
        // after the final section is submitted.
        // For example, to disable the button after all sections are submitted, you
        // could:
        // submitButton.setEnabled(currentSectionIndex < sections.length - 1 ||
        // !isLastPageInSection);
    }
}