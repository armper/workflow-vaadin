package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ImpactSummary extends VerticalLayout {

    private final Span levelSpan = new Span();
    private final Span riskSpan = new Span();
    private final Span impactStatementSpan = new Span();
    private Button selectButton = new Button("Select");

    public ImpactSummary(String level, String risk, String impactStatement) {
        levelSpan.getStyle().set("font-weight", "bold");
        levelSpan.setText(level + ":");
        riskSpan.getStyle().set("font-weight", "bold");
        riskSpan.setText(risk);
        impactStatementSpan.setText(impactStatement);

        HorizontalLayout horizontalLayout = new HorizontalLayout(this.levelSpan, this.riskSpan);
        add(horizontalLayout, this.impactStatementSpan, selectButton);
    }

    public Button getSelectButton() {
        return selectButton;
    }

    public String toString() {
        return levelSpan.getText() + " " + riskSpan.getText() + " " + impactStatementSpan.getText();
    }
}
