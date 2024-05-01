package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.newevent;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ImpactSummary extends VerticalLayout {

    private final Span levelSpan = new Span();
    private final Span riskSpan = new Span();
    private final Span impactStatementSpan = new Span();
    private final Button selectButton = new Button("Select");

    public ImpactSummary(String level, String risk, String impactStatement) {
        setupSpan(levelSpan, level + ":");
        setupSpan(riskSpan, risk);
        setupImpactStatementSpan(impactStatementSpan, impactStatement);

        HorizontalLayout horizontalLayout = new HorizontalLayout(levelSpan, riskSpan);
        horizontalLayout.setAlignItems(Alignment.BASELINE);

        add(horizontalLayout, impactStatementSpan, selectButton);
        configureLayout();
    }

    private void setupSpan(Span span, String text) {
        span.getStyle().set("font-weight", "bold");
        span.setText(text);
    }

    private void setupImpactStatementSpan(Span span, String impactStatement) {
        String trimmedStatement = impactStatement.length() > 125 ? impactStatement.substring(0, 122) + "..."
                : impactStatement;
        span.setText(trimmedStatement);
    }

    private void configureLayout() {
        setWidthFull();
        setDefaultHorizontalComponentAlignment(Alignment.START);
    }

    public Button getSelectButton() {
        return selectButton;
    }

    @Override
    public String toString() {
        return levelSpan.getText() + " " + riskSpan.getText() + " " + impactStatementSpan.getText();
    }
}
