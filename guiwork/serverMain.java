import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class serverMain implements ActionListener{
  JButton menuButtons[] = new JButton[20];

  serverMain() {
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
    Integer j = 10; //counter for second page (starts at index 10)
    for(Integer i = 0; i < 10; i++){ // counter for first page (starts at index 0)
      Integer n1 = i + 1; //starts button at 1 instead of 0
      Integer n2 = j + 1; //starts button at 11 instead of 10
      String c = n1.toString(); //converts number to string so it can be passed into JButton()
      String c2 = n2.toString();
      menuButtons[i] = new JButton(c);
      menuButtons[j] = new JButton(c2);
      menuButtons[i].setBackground(buttonColor);
      menuButtons[i].setFont(guiFont);
      menuButtons[i].addActionListener(this);

      menuButtons[j].setBackground(buttonColor);
      menuButtons[j].setFont(guiFont);
      menuButtons[j].addActionListener(this);
      menu.add(menuButtons[i]);
      menu2.add(menuButtons[j]);

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

  // This will be used to open new window for customizations
  @Override
  public void actionPerformed(ActionEvent e) {
      for(Integer i = 0; i < 20; i++){
        if(e.getSource()==menuButtons[i]){
          //the i variable will also be passed into the constructor
          // this will allow for a specialized customize screen depending on the menu item
          new serverCustomize(i); 
        }
      }
  }
}
