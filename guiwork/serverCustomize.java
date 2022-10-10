import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

// THIS IS THE NEW WINDOW THAT OPENS TO CUSTOMIZE AN ITEM
// WILL USE RADIO BUTTONS FOR (NO) OR (YES)... YES WILL BE THE DEFAULT
public class serverCustomize {
  JLabel bacon;
  serverCustomize(Integer i){
    Integer food_id = i + 1; //Match to database number

    // FRAME SETUP AND CONFIG
    JFrame frame = new JFrame();
    frame.setSize(500,500);
    frame.setLayout(new FlowLayout());
    frame.setResizable(false);

    JRadioButton yes = new JRadioButton("YES");
    JRadioButton no = new JRadioButton("NO");
  
    // ButtonGroup makes it so only one button can be selected
    ButtonGroup group = new ButtonGroup(); 
    group.add(yes);
    group.add(no);

    // if its a burger
    // if(food_id < 6){
    //   frame.setLayout(new FlowLayout());
    //   JLabel bacon = new JLabel();
    // }
    
    frame.add(yes);
    frame.add(no);
    frame.setVisible(true);
  }
}
