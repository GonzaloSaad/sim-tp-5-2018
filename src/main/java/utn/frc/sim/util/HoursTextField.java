package utn.frc.sim.util;

import javafx.scene.control.TextField;

public class HoursTextField extends TextField {

    public HoursTextField() {
        this.setPromptText("0-24");
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[0-9]") || text.isEmpty()) {
            super.replaceText(start,end,text);
        }
    }
}
