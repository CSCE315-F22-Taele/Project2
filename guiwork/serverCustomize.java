import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

// THIS IS THE NEW WINDOW THAT OPENS TO CUSTOMIZE AN ITEM
// WILL USE CHECKBOXES FOR INGREDIENTS
public class serverCustomize {
  JLabel bacon;
  serverCustomize(Integer i){
    Integer food_id = i + 1; //Match to database number

    // FRAME SETUP AND CONFIG
    JFrame frame = new JFrame();
    frame.setSize(500,500);
    frame.setLayout(new FlowLayout());
    frame.setResizable(false);

    // if its a burger
    if(food_id < 6){
      JCheckBox cheese = new JCheckBox("CHEESE");
      cheese.setSelected(true);
      JCheckBox lettuce = new JCheckBox("LETTUCE");
      lettuce.setSelected(true);
      JCheckBox bacon = new JCheckBox("BACON");
      bacon.setSelected(true);

      frame.add(lettuce);
      frame.add(cheese);
      frame.add(bacon);
    }


    frame.setVisible(true);
  }
}
