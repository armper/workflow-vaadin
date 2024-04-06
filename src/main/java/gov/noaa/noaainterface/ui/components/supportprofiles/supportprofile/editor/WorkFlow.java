package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class WorkFlow extends VerticalLayout {

    private final WorkflowSection[] sections;
    private int currentSectionIndex = 0;
    private final Div sectionContainer; // Container for the current section
    private final ProgressBar[] progressBars; // Array to hold a ProgressBar for each section
    private final Span[] progressLabels; // Array to hold a label for each section

    public WorkFlow(WorkflowSection... sections) {
        this.sections = sections;
        this.sectionContainer = new Div();
        this.progressBars = new ProgressBar[sections.length];
        this.progressLabels = new Span[sections.length];

        createProgressComponents();
        initializeWorkflow();
    }

    private void createProgressComponents() {
        for (int i = 0; i < sections.length; i++) {
            WorkflowSection section = sections[i];

            ProgressBar progressBar = new ProgressBar(0, section.getTotalPages(), 1);
            progressBar.setWidthFull();
            progressBars[i] = progressBar;

            Span progressLabel = new Span(getProgressText(section, 1));
            progressLabels[i] = progressLabel;
            progressLabel.getStyle().set("white-space", "nowrap");

            section.addPageChangeListener(event -> updateProgress(section, progressBar, progressLabel));
            section.addSubmitListener(submitListener -> submitCurrentSection());
        }
    }

    private String getProgressText(WorkflowSection section, int currentPageIndex) {
        return String.format("%s Information %d of %d",
                section.getItemName(),
                currentPageIndex,
                section.getTotalPages());
    }

    private void updateProgress(WorkflowSection section, ProgressBar progressBar, Span progressLabel) {
        int currentPageIndex = section.getCurrentPageIndex() + 1;
        progressBar.setValue(currentPageIndex);
        progressLabel.setText(getProgressText(section, currentPageIndex));
        updateSubmitButtonVisibility();
    }

    private void initializeWorkflow() {
        sectionContainer.setWidthFull();
        HorizontalLayout progressBarLayout = new HorizontalLayout();
        progressBarLayout.setWidthFull();

        for (int i = 0; i < sections.length; i++) {
            HorizontalLayout layout = new HorizontalLayout(progressLabels[i], progressBars[i]);
            layout.setWidthFull();
            progressBarLayout.add(layout);
        }

        if (sections.length > 0) {
            addSectionToView(0);
        }

        add(sectionContainer, progressBarLayout);
    }

    private void addSectionToView(int sectionIndex) {
        sectionContainer.removeAll();
        if (sectionIndex < sections.length) {
            WorkflowSection section = sections[sectionIndex];
            sectionContainer.add(section);
            section.setWidthFull();
            section.setVisible(true); // Make sure the section is visible when added
        }
        updateProgressIndicators(sectionIndex);
    }

    private void updateProgressIndicators(int sectionIndex) {
        for (int i = 0; i < sections.length; i++) {
            String text = (i == sectionIndex)
                    ? getProgressText(sections[i], sections[i].getCurrentPageIndex() + 1)
                    : sections[i].getItemName() + "s";
            progressLabels[i].setText(text);
        }
    }

    private void submitCurrentSection() {
        WorkflowSection currentSection = sections[currentSectionIndex];
        if (!currentSection.isLastPage()) {
            currentSection.nextPage();
        } else if (currentSectionIndex < sections.length - 1) {
            currentSectionIndex++;
            addSectionToView(currentSectionIndex);
        } else {
            // Final section's last page submitted
            currentSection.getSubmitButton().setEnabled(false);
        }
        updateSubmitButtonVisibility();
    }

    private void updateSubmitButtonVisibility() {
        boolean isLastPageInSection = sections[currentSectionIndex].isLastPage();
        boolean isLastSection = currentSectionIndex == sections.length - 1;

        sections[currentSectionIndex].getSubmitButton().setVisible(isLastPageInSection);
        if (isLastPageInSection) {
            sections[currentSectionIndex].hideNextButton();
        } else {
            sections[currentSectionIndex].showNextButton();
        }

        // Additional logic for the last section's last page
    }
}
