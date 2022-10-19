import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

public class serverMain implements ActionListener {
  // THESE ARRAYS ARE NO LONGER BEING USED AS ALL INFO IS EXTRACTED FROM DB NOW
  // String names[] = {"Bacon Cheeseburger", "Black Bean Burger", "BYO Burger",
  // "Cheeseburger", "Double Stack Cheeseburger", "Classic Hamburger", "Chicken
  // Caesar Salad", "Gig Em Patty Melt", "Grilled Chicken Sandwich", "Double Scoop
  // Ice Cream", "Aggie Shake", "Cookie Ice Cream Sundae", "French Fries", "Tater
  // Tots", "Onion Rings", "Kettle Chips", "Aquafina 16 oz", "Aquafina 20 oz",
  // "Fountain Drink", "Chicken Tender Basket"};

  // Double prices[] =
  // {7.89,7.29,6.49,6.99,9.69,6.49,8.29,7.09,7.49,3.29,4.49,4.49,2.69,2.69,2.69,2.69,1.79,2.19,2.45,6.79,7.29};

  JButton menuButtons[] = new JButton[30];
  Double runTot = 0.0; // Total price of order that is displayed to total side of screen
  DecimalFormat df = new DecimalFormat("0.00");
  JButton checkout = new JButton("CHECKOUT");
  JButton clear = new JButton("CLEAR ORDER");
  ArrayList<Integer> currOrder; // This will be the array for the items within the current order
  JTextArea ongoingOrder = new JTextArea("   Current Order:\n", 35, 35);
  String order = "";
  JLabel totalTitle = new JLabel("Order Total:     " + df.format(runTot), JLabel.CENTER);
  Integer menuRowsCount = 20; // Number to keep track of rows in menu
  // Database object to communicate with the server
  Database db;
  // "item 1, item 2"

  // 0 1 4 5
  HashMap<String, Integer> pair;

  /**
   * @exception Exception if result set has an error
   */
  serverMain() {
    // DEFINING MAIN J OBJECTS USED
    JFrame frame = new JFrame();
    JPanel menu = new JPanel(); // FIRST TAB
    JPanel menu2 = new JPanel(); // SECOND TAB
    JPanel menu3 = new JPanel(); // SECOND TAB
    JPanel total = new JPanel(); // RIGHT SIDE OF GUI WHERE RUNNING TOTAL IS KEPT

    JTabbedPane tabbedPane = new JTabbedPane(); // ALLOWS FOR TABBED MENU

    Color buttonColor = new Color(0xEF3054);
    Color primary = new Color(0x2A2A72);
    Font guiFont = new Font("Impact", Font.PLAIN, 20);
    db = new Database();
    currOrder = new ArrayList<>();

    // CONFIG AND LAYOUT
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 1000);
    frame.setLayout(null);
    frame.setResizable(false);

    tabbedPane.setBounds(0, 0, 500, 1000);

    total.setBounds(500, 0, 500, 1000);
    total.setBackground(primary);
    totalTitle.setForeground(Color.white);
    totalTitle.setPreferredSize(new Dimension(250, 100));
    totalTitle.setFont(guiFont);
    totalTitle.setVerticalAlignment(JLabel.BOTTOM);
    ongoingOrder.setBounds(600, 300, 400, 1000);
    ongoingOrder.setFont(new Font("Impact", Font.PLAIN, 17));

    menu.setLayout(new GridLayout(5, 4, 10, 10)); // args is rows, columns
    menu2.setLayout(new GridLayout(5, 4, 10, 10)); // args is rows, columns
    menu3.setLayout(new GridLayout(5, 4, 10, 10)); // args is rows, columns

    // POPULATING BOTH MENUS WITH BUTTONS
    ResultSet menuItems = db.executeQuery("SELECT * FROM menu ORDER BY food_id");

    Integer j = 10; // counter for second page (starts at index 10)
    Integer k = 20; // counter for possible third page
    for (Integer i = 0; i < 10; i++) { // counter for first page (starts at index 0)
      try {

        menuItems.absolute(i + 1);
        String name = menuItems.getString("menuitem");
        menuButtons[i] = new JButton(name);
        menuItems.absolute(i + j + 1);
        name = menuItems.getString("menuitem");
        menuButtons[i + j] = new JButton(name);

        menuButtons[i].setBackground(buttonColor);
        menuButtons[i].setFont(guiFont);
        menuButtons[i].addActionListener(this);

        menuButtons[i + j].setBackground(buttonColor);
        menuButtons[i + j].setFont(guiFont);
        menuButtons[i + j].addActionListener(this);
        menu.add(menuButtons[i]);
        menu2.add(menuButtons[i + j]);

        if (menuItems.absolute(k + i + 1)) { // false if row does not exist
          menuItems.absolute(k + i + 1);
          name = menuItems.getString("menuitem");
          menuButtons[k + i] = new JButton(name);
          menuButtons[k + i].setBackground(buttonColor);
          menuButtons[k + i].setFont(guiFont);
          menuButtons[k + i].addActionListener(this);
          menu3.add(menuButtons[k + i]);
          menuRowsCount += 1; // increment number of rows
        }

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    // POPULATING TOTAL SIDE
    checkout.addActionListener(this);
    checkout.setBackground(buttonColor);
    clear.addActionListener(this);
    clear.setBackground(buttonColor);

    // ADDING ITEMS TO PROPER CONTAINER
    tabbedPane.add("Menu1", menu);
    tabbedPane.add("Menu2", menu2);
    tabbedPane.add("Menu3", menu3);
    total.add(totalTitle); // The order that these are added matters
    total.add(ongoingOrder);
    total.add(checkout);
    total.add(clear);
    frame.add(tabbedPane);
    frame.add(total);
    frame.setVisible(true);
  }

  // This will be used to open new window for customizations
  /**
   * @param e the action event, the button being pressed
   * @exception Exception if result set has an error
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    ResultSet menuItems;

    for (Integer i = 0; i < menuRowsCount; i++) {
      if (e.getSource() == menuButtons[i]) {
        menuItems = db.executeQuery("SELECT * FROM menu ORDER BY food_id");
        // the i variable will also be passed into the constructor
        // this will allow for a specialized customize screen depending on the menu item
        // runTot += names[i] + " " + prices[i] + "\n";

        try {
          menuItems.absolute(i + 1);
          String name = menuItems.getString("menuitem");
          double price = menuItems.getDouble("price");
          int id = menuItems.getInt("food_id");
          currOrder.add(id);
          order += "   " + name + "   $" + price + "\n";
          runTot += price;
          totalTitle.setText("Order Total:     $" + df.format(runTot));
          ongoingOrder.setText(order);

          new serverCustomize(i);
        } catch (Exception ex) {
          System.out.println(ex.getMessage());
        }
      }
    }

    if (e.getSource() == checkout) {
      // MOVE STUFF FROM ABOVE TO ONLY DECREMENT ONCE THIS BUTTON IS PUSHED

      Collections.sort(currOrder);
      menuItems = db.executeQuery("SELECT * FROM menu ORDER BY food_id");
      try {
        ResultSet orderDetails = db.executeQuery("SELECT * FROM orderdetails ORDER BY order_id");
        int currOrderId;
        if (!orderDetails.isBeforeFirst()) {
          currOrderId = 1;
        } else {
          orderDetails.last();
          currOrderId = orderDetails.getInt("order_id") + 1;
        }

        orderDetails.close();

        Calendar date = Calendar.getInstance();
        String cmd = "INSERT INTO orderhistory(order_id, time_stamp, pricetotal) VALUES(";
        String timestamp = "'" + date.get(Calendar.YEAR) + "-"
            + date.get(Calendar.MONTH) + "-"
            + date.get(Calendar.DAY_OF_MONTH) + " "
            + date.get(Calendar.HOUR_OF_DAY) + ":"
            + date.get(Calendar.MINUTE) + ":"
            + date.get(Calendar.SECOND) + "'";
        db.executeUpdate(cmd + currOrderId + ", " + timestamp + ", " + df.format(runTot) + ")");

        cmd = "INSERT INTO orderdetails(order_id, food_id) VALUES(";
        for (int i = 0; i < currOrder.size(); ++i) {
          int id = currOrder.get(i);
          menuItems.absolute(id);
          String ings = menuItems.getString("ingredients");
          db.updateInventory(ings);
          db.executeUpdate(cmd + currOrderId + ", " + id + ")");
        }
        // CLEAR OUT ORDER THAT HAS BEEN PLACED
        ongoingOrder.setText("   Order Placed!\n");
        order = "";
        runTot = 0.0;
        totalTitle.setText("Order Total:     $" + df.format(runTot));
        currOrder.removeAll(currOrder);
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }

    if (e.getSource() == clear) {
      ongoingOrder.setText("");
      order = "";
      runTot = 0.0;
      totalTitle.setText("Order Total:     $" + df.format(runTot));
      currOrder.removeAll(currOrder);
    }
  }
}
