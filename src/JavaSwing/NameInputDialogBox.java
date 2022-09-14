//name input dialog box appears when you want to save state by name
package JavaSwing;

import javax.swing.*;

public class NameInputDialogBox
{
    JFrame frame;
    String shapeName;
    public NameInputDialogBox()
    {
        frame = new JFrame();
        shapeName = JOptionPane.showInputDialog("Start entering your value!","ShapeXYZ");
        if(shapeName == null)
        {
            JOptionPane.showMessageDialog(frame, "You have not entered anything!");
        }
    }
    String get_string()
    {
        return shapeName;
    }
}

