//shows stored states, and you can select anyone of the mentioned states
package JavaSwing;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class StateListPanel extends JFrame implements ActionListener
{
    JFrame frame;
    JList<String> state_list;
    DefaultListModel<String> stateListModel;

    JPanel list_panel;
    JButton  okay_button;
    public String selectedState;
    public StateListPanel(String[] states_for_view)
    {
        frame = new JFrame("Available States");
        frame.setBounds(300,300,400,300);

        state_list = new JList<>();
        state_list.setBorder(new EmptyBorder(10,20, 10, 10));
        stateListModel = new DefaultListModel<>();


        list_panel = new JPanel();

        okay_button = new JButton("Select");
        okay_button.addActionListener(this);


        state_list.setModel(stateListModel);
        if(states_for_view!=null)
        {
            for (int i = 0; i< states_for_view.length; i++) {
                stateListModel.addElement(states_for_view[i]);
            }
        }

        state_list.getSelectionModel().addListSelectionListener(e ->
        {
            //do nothing

        });

        state_list.setBackground(Color.darkGray);
        state_list.setForeground(Color.white);

        okay_button.setBackground(Color.BLACK);
        okay_button.setBackground(Color.white);


        list_panel.setBackground(Color.darkGray);
        list_panel.setForeground(Color.blue);

        frame.setBackground(Color.BLACK);
        frame.setForeground(Color.ORANGE);


        list_panel.add(new JScrollPane(state_list));
        list_panel.setPreferredSize(new Dimension(450,200));

        frame.add(list_panel,BorderLayout.NORTH);
        frame.add(okay_button,BorderLayout.SOUTH);


        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == okay_button)
        {
            if(state_list.getSelectedValue() == null)   //no state has been selected
            {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                return;
            }

            selectedState = new String(state_list.getSelectedValue());
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }
    public String getSelectedState()
    {
        return selectedState;
    }
}