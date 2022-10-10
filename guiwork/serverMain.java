import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class serverMain{
  serverMain(){
    // DEFINING MAIN J OBJECTS USED
    JFrame frame = new JFrame(); 
    JPanel menu = new JPanel(); //FIRST TAB
    JPanel menu2 = new JPanel(); //SECOND TAB
    JPanel total = new JPanel(); //RIGHT SIDE OF GUI WHERE RUNNING TOTAL IS KEPT
    JTabbedPane tabbedPane = new JTabbedPane(); //ALLOWS FOR TABBED MENU
    JLabel totalTitle = new JLabel("Order Total");
    Color buttonColor = new Color(0xEF3054);
    Color primary = new Color(0x2A2A72);
    Font guiFont = new Font("Impact",Font.PLAIN,20);
    


    // CONFIG AND LAYOUT
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000,1000);
    frame.setLayout(null);
    frame.setResizable(false);

    tabbedPane.setBounds(0,0,500,1000);

    total.setBounds(500,0,500,1000);
    total.setBackground(primary);
    totalTitle.setForeground(Color.white);
    totalTitle.setFont(guiFont);

    menu.setLayout(new GridLayout(5,4,10,10)); // args is rows, columns
    menu2.setLayout(new GridLayout(5,4,10,10)); // args is rows, columns

    // POPULATING BOTH MENUS WITH BUTTONS
    Integer j = 11;
    for(Integer i = 1; i <= 10; i++){
      String c = i.toString();
      String c2 = j.toString();
      JButton button = new JButton(c);
      JButton button2 = new JButton(c2);
      button.setBackground(buttonColor);
      button.setFont(guiFont);
      button2.setBackground(buttonColor);
      button2.setFont(guiFont);
      menu.add(button);
      menu2.add(button2);
      j++;
    }

    // POPULATING TOTAL SIDE
    JButton checkout = new JButton("CHECKOUT");
    checkout.setBounds(100,0,300,50);
    checkout.setBackground(buttonColor);
    // checkout.setHorizontalAlignment(JButton.RIGHT);
    // checkout.setVerticalAlignment(JButton.BOTTOM);


    // ADDING ITEMS TO PROPER CONTAINER
    tabbedPane.add("Menu1", menu);
    tabbedPane.add("Menu2", menu2);
    total.add(totalTitle);
    total.add(checkout);
    frame.add(tabbedPane);
    frame.add(total);
    frame.setVisible(true);
  }
}
