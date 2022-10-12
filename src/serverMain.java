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
import java.sql.*;
import java.text.DecimalFormat;

public class serverMain implements ActionListener{
  String names[] = {"Bacon Cheeseburger", "Black Bean Burger", "BYO Burger", "Cheeseburger", "Double Stack Cheeseburger", "Classic Hamburger", "Chicken Caesar Salad", "Gig Em Patty Melt", "Grilled Chicken Sandwich", "Double Scoop Ice Cream", "Aggie Shake", "Cookie Ice Cream Sundae", "French Fries", "Tater Tots", "Onion Rings", "Kettle Chips", "Aquafina 16 oz", "Aquafina 20 oz", "Fountain Drink", "Chicken Tender Basket"};

  Double prices[] = {7.89,7.29,6.49,6.99,9.69,6.49,8.29,7.09,7.49,3.29,4.49,4.49,2.69,2.69,2.69,2.69,1.79,2.19,2.45,6.79,7.29};

  JButton menuButtons[] = new JButton[20];
  Double runTot = 0.0; //Total price of order that is displayed to total side of screen
  DecimalFormat df = new DecimalFormat("0.00");
  
  // Database object to communicate with the server
  Database db;

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
    db = new Database();

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
    ResultSet menuItems = db.executeQuery("SELECT * FROM menu ORDER BY food_id");

    Integer j = 10; //counter for second page (starts at index 10)
    for(Integer i = 0; i < 10; i++){ // counter for first page (starts at index 0)
      try{
        //menuButtons[i] = new JButton(names[i]);
        //menuButtons[j] = new JButton(names[j]);
        menuItems.absolute(i+1);
        String name = menuItems.getString("menuitem");
        menuButtons[i] = new JButton(name);
        menuItems.absolute(i+j+1);
        name = menuItems.getString("menuitem");
        menuButtons[i+j] = new JButton(name);

        menuButtons[i].setBackground(buttonColor);
        menuButtons[i].setFont(guiFont);
        menuButtons[i].addActionListener(this);

        menuButtons[i+j].setBackground(buttonColor);
        menuButtons[i+j].setFont(guiFont);
        menuButtons[i+j].addActionListener(this);
        menu.add(menuButtons[i]);
        menu2.add(menuButtons[i+j]);

        //j++;
      }catch(Exception e){
        System.out.println(e.getMessage());
      }
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
    ResultSet menuItems = db.executeQuery("SELECT * FROM menu ORDER BY food_id");
      for(Integer i = 0; i < 20; i++){
        if(e.getSource()==menuButtons[i]){
          //the i variable will also be passed into the constructor
          // this will allow for a specialized customize screen depending on the menu item
          //runTot += names[i] + "  " + prices[i] + "\n";

          // TODO move this to when the server clicks the 'finish order' button
          try{
            menuItems.absolute(i+1);
            String name = menuItems.getString("menuitem");
            double price = menuItems.getDouble("price");
            String ings = menuItems.getString("ingredients");

            db.updateInventory(ings);

            System.out.println(name + "  " + price);
            runTot += price;
            new serverCustomize(i);
          }catch(Exception ex){
            System.out.println(ex.getMessage());
          }
        }
      }
      System.out.println("Total Price: " + df.format(runTot));
  }
}