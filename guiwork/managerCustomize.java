import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

// THIS IS THE NEW WINDOW THAT OPENS TO CUSTOMIZE AN ITEM
// WILL USE CHECKBOXES FOR INGREDIENTS
// ONLY SOME ITEMS NEED TO HAVE CUSTOMIZATION

public class managerCustomize {
  managerCustomize(Integer i){
    Integer food_id = i + 1; //Match to database number

    // FRAME SETUP AND CONFIG
    JFrame frame = new JFrame();
    frame.setSize(200,200);
    frame.setLayout(new FlowLayout());
    frame.setResizable(false);

    // Create checkboxes for all possible ingredients
    JCheckBox ingredients[] = new JCheckBox[8];
    ingredients[0] = new JCheckBox("LETTUCE");
    ingredients[1] = new JCheckBox("TOMATO");
    ingredients[2] = new JCheckBox("ONION");
    ingredients[3] = new JCheckBox("PICKLES");
    ingredients[4] = new JCheckBox("BACON");
    ingredients[5] = new JCheckBox("GIG EM SAUCE");
    ingredients[6] = new JCheckBox("AMERICAN CHEESE");
    ingredients[7] = new JCheckBox("GRAVY");
    for(Integer j = 0; j < 8; j++){
      // Sets all checkboxes to selected by default
      ingredients[j].setSelected(true); 
      ingredients[j].setFont(new Font("Impact",Font.PLAIN,20));
    }

    if(food_id == 1){
      frame.add(ingredients[4]);
      frame.add(ingredients[6]);
    }

    if(food_id == 2 || food_id == 5 || food_id == 6){
      frame.add(ingredients[0]);
      frame.add(ingredients[1]);
      frame.add(ingredients[3]);
      frame.add(ingredients[2]);
    }

    if(food_id == 4){
      frame.add(ingredients[6]);
      frame.add(ingredients[1]);
      frame.add(ingredients[3]);
      frame.add(ingredients[2]);
    }

    if(food_id == 8){
      frame.add(ingredients[6]);
      frame.add(ingredients[2]);
      frame.add(ingredients[5]);
    }

    if(food_id == 9){
      frame.add(ingredients[6]);
      frame.add(ingredients[2]);
      frame.add(ingredients[1]);
      frame.add(ingredients[0]);
    }

    frame.setVisible(true);
  }
}
