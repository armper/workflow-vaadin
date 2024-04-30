package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class ImpactSummary extends VerticalLayout {

    private final Span level = new Span();
    private final Span risk = new Span();
    private final Span impactStatement = new Span();
    private Button selectButton = new Button("Select");

    public ImpactSummary(String level, String risk, String impactStatement) {
        // bold this.level
        this.level.getStyle().set("font-weight", "bold");
        this.level.setText(level + ":");
        this.risk.getStyle().set("font-weight", "bold");
        this.risk.setText(risk);
        this.impactStatement.setText(impactStatement);

        HorizontalLayout horizontalLayout = new HorizontalLayout(this.level, this.risk);
        add(horizontalLayout, this.impactStatement, selectButton);
    }

    public Button getSelectButton() {
        return selectButton;
    }

    public String toString() {
        return level.getText() + " " + risk.getText() + " " + impactStatement.getText();
    }
}
