package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor;

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
    private final ProgressBar[] progressBars; // Array to hold a ProgressBar for each section
    private final Span[] progressLabels; // Array to hold a label for each section

    public WorkFlow(WorkflowSection... sections) {
        this.sections = sections;
        this.sectionContainer = new Div();
        sectionContainer.setWidthFull(); 
        this.progressBars = new ProgressBar[sections.length]; // Initialize the array
        this.progressLabels = new Span[sections.length]; // Initialize the label array

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
            progressLabel.getStyle().set("white-space", "nowrap");

            section.addPageChangeListener(event -> {
                double progress = (event.getCurrentPageIndex() + 1) / (double) event.getTotalPages();
                progressBar.setValue(progress);
                // After updating progress, also check if it's time to show the submit button.
                updateSubmitButtonVisibility();
            });

            section.addSubmitListener(submitListener -> {
                submitCurrentSection();
            });

        }

        initializeWorkflow();

    }

    private void initializeWorkflow() {
        if (sections.length > 0) {
            addSectionToView(0);
        }
        HorizontalLayout progressBarLayout = new HorizontalLayout();
        progressBarLayout.setWidthFull();
        for (int i = 0; i < sections.length; i++) {
            // For each section, add a HorizontalLayout containing the label and the
            // progress bar
            HorizontalLayout layout = new HorizontalLayout(progressLabels[i], progressBars[i]);
            layout.setWidthFull();
            progressBarLayout.add(layout);
        }
        add(sectionContainer, progressBarLayout);
        updateSubmitButtonVisibility();
    }

    private void addSectionToView(int sectionIndex) {
        sectionContainer.removeAll();
        if (sectionIndex < sections.length) {
            WorkflowSection section = sections[sectionIndex];
            sectionContainer.add(section);
            section.setWidthFull();
        }
        // Update progress labels for all sections
        for (int i = 0; i < sections.length; i++) {
            if (i == sectionIndex) {
                // For the current section, show detailed information
                WorkflowSection currentSection = sections[i];
                progressLabels[i].setText(String.format("%s Information %d of %d",
                        currentSection.getItemName(),
                        currentSection.getCurrentPageIndex() + 1,
                        currentSection.getTotalPages()));
            } else {
                // For other sections, show only the item name
                progressLabels[i].setText(sections[i].getItemName()+"s");
            }
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
            sections[currentSectionIndex].getSubmitButton().setEnabled(false); // Or other finalization logic
        }
    }

    private void updateSubmitButtonVisibility() {
        boolean isLastPageInSection = sections[currentSectionIndex].isLastPage();
        boolean isLastSection = currentSectionIndex == sections.length - 1;

        // If on the last page of the current section, show the submit button and hide
        // the next button.
        // Otherwise, ensure the submit button is hidden and the next button is visible.
        if (isLastPageInSection) {
            sections[currentSectionIndex].getSubmitButton().setVisible(true);
            sections[currentSectionIndex].hideNextButton(); // Hide next button
        } else {
            sections[currentSectionIndex].getSubmitButton().setVisible(false);
            sections[currentSectionIndex].showNextButton(); // Ensure the next button is visible
        }

        // If this is the last section and we're on its last page, you might adjust the
        // visibility or enabled state of the submit button further.
        if (isLastSection && isLastPageInSection) {
            // Additional logic if needed for the last section's last page
        }
    }

}