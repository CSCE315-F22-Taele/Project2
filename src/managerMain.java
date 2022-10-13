import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class managerMain implements ActionListener {
  Database db;
  ArrayList<ArrayList<String>> inventoryData;
  JTable inventoryTable;
  JTable menuTable;
  JButton inventoryButton;
  JButton MenuButton;
  JButton addRow;
  
  

  managerMain() {
    // DEFINING MAIN J OBJECTS USED
    Color primary = new Color(0x2A2A72);
    Font guiFont = new Font("Impact", Font.PLAIN, 20);
    inventoryButton = new JButton("Make Changes");
    
    // Create frame
    JFrame frame = new JFrame();
    db = new Database();

    // Create critically low pane
    JPanel criticallyLow = new JPanel();

    // Create item range page
    JPanel itemRange = new JPanel();
    JPanel itemRangeTextBox = new JPanel();
    JPanel inventoryRangeTextBox = new JPanel();
    JPanel menuRangeTextBox = new JPanel();
    JLabel itemRangeTitle = new JLabel("Item History");
    MenuButton = new JButton("Change Menu");
    addRow = new JButton("Add Row");
    // Create current inventory
    JPanel currentInventory = new JPanel();
    JLabel currentInventoryTitle = new JLabel("Current Inventory");

    String[] columns = new String[] { "item_id", "itemname", "itemcount", "itemfcount" };
    String[][] sinv = new String[30][4];

    // Display inventory
    ResultSet invItems = db.executeQuery("SELECT * FROM inventory ORDER BY itemcount");
    for (Integer i = 0; i < 30; i++) {
      try {
        invItems.absolute(i + 1);
        sinv[i][0] = invItems.getString("item_id");
        sinv[i][1] = invItems.getString("itemname");
        sinv[i][2] = invItems.getString("itemcount");
        sinv[i][3] = invItems.getString("itemfcount");
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
    inventoryTable = new JTable(sinv, columns);

    JScrollPane sPane = new JScrollPane(inventoryTable);
    sPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    inventoryTable.setFont(new Font("Impact", Font.PLAIN, 15));
    sPane.setBounds(200, 550, 600, 300);
    inventoryTable.setBounds(200, 550, 600, 300);
    inventoryTable.setBackground(primary);
    inventoryTable.setForeground(Color.white);

    // Display Menu
    JPanel currentMenu = new JPanel();
    JLabel currentMenuTitle = new JLabel("Menu");
    Integer menuSize = 20;
    ResultSet menuSize2 = db.executeQuery("SELECT COUNT (food_id) FROM menu;");
    System.out.println(menuSize2.toString());

    String[] columns_menu = new String[] { "food_id", "menuitem", "price", "ingredients" };
    String[][] sinv_menu = new String[menuSize][4];
    ResultSet MenuItems = db.executeQuery("SELECT * FROM menu ORDER BY food_id");
    for (Integer i = 0; i < menuSize; i++) {
      try {
        MenuItems.absolute(i + 1);
        sinv_menu[i][0] = MenuItems.getString("food_id");
        sinv_menu[i][1] = MenuItems.getString("menuitem");
        sinv_menu[i][2] = MenuItems.getString("price");
        sinv_menu[i][3] = MenuItems.getString("ingredients");
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
    menuTable = new JTable(sinv_menu, columns_menu);
    JScrollPane sPane_menu = new JScrollPane(menuTable);
    sPane_menu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    menuTable.setFont(new Font("Impact", Font.PLAIN, 15));
    sPane_menu.setBounds(20, 20, 450, 400);
    menuTable.setBounds(20, 20, 450, 400);
    menuTable.setBackground(primary);
    menuTable.setForeground(Color.white);
    menuRangeTextBox.add(MenuButton);
    menuRangeTextBox.add(addRow);
    addRow.addActionListener(this);
    menuRangeTextBox.setBounds(100, 420, 300, 300);

    // Sets up the frame
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 1000);
    frame.setLayout(null);
    frame.setResizable(false);
    frame.add(sPane);
    frame.add(sPane_menu);
    frame.add(criticallyLow);
    frame.add(menuRangeTextBox);
    frame.add(itemRange);
    frame.add(itemRangeTextBox);
    frame.add(inventoryRangeTextBox);
    frame.setVisible(true);

    // Sets up item range
    itemRange.setBounds(505, 0, 500, 450); // Box
    itemRange.setBackground(primary);
    itemRangeTextBox.setBounds(505, 450, 500, 50);
    itemRangeTextBox.setBackground(primary);
    itemRangeTitle.setForeground(new Color(255, 255, 255));
    itemRangeTitle.setFont(guiFont);

    itemRange.add(itemRangeTitle);

    // Sets up current Inventory
    currentInventory.setBounds(0, 505, 1000, 400);
    currentInventory.setBackground(primary);
    currentInventoryTitle.setForeground(new Color(255, 255, 255));
    currentInventoryTitle.setFont(guiFont);

    inventoryRangeTextBox.setBounds(0, 905, 1000, 55);
    inventoryRangeTextBox.setBackground(primary);
    inventoryRangeTextBox.add(inventoryButton);
    inventoryButton.addActionListener(this);
    MenuButton.addActionListener(this);


    // Sets up critically low
    // criticallyLow.setBounds(0, 0, 500, 500);
    // criticallyLow.setBackground(primary);
    // criticallyLowTitle.setForeground(new Color(255, 255, 255));
    // criticallyLowTitle.setFont(guiFont);
    // criticallyLow.add(criticallyLowTitle);
    
  }

  public void actionPerformed(ActionEvent e) {
    System.out.println("Click");
    if (e.getSource() == inventoryButton) {
      Object[] itemData = new Object [inventoryTable.getRowCount()];
      Object[] itemidData = new Object [inventoryTable.getRowCount()];

      for (int i = 0; i < inventoryTable.getRowCount(); i++) {  // Loop through the rows
        itemData[i] = inventoryTable.getValueAt(i, 2);
        itemidData[i] = inventoryTable.getValueAt(i, 0);
        String updateSQL= "UPDATE inventory SET itemcount=" + itemData[i].toString() + " WHERE item_id=" + itemidData[i].toString() + ";";
        db.executeQuery(updateSQL);
     }
    } 


    if (e.getSource() == MenuButton) {
      System.out.println("For");
      Object[] col0 = new Object [menuTable.getRowCount()];
      Object[] col1 = new Object [menuTable.getRowCount()];
      Object[] col2 = new Object [menuTable.getRowCount()];
      Object[] col3 = new Object [menuTable.getRowCount()];

      for (int i = 0; i < menuTable.getRowCount(); i++) {  // Loop through the rows
        col0[i] = menuTable.getValueAt(i, 0);
        col1[i] = menuTable.getValueAt(i, 1);
        col2[i] = menuTable.getValueAt(i, 2);
        col3[i] = menuTable.getValueAt(i, 3);
        String updateItem= "UPDATE menu SET menuItem=" +  "\'" + col1[i] + "\'" + " WHERE food_id=" + col0[i].toString() + ";";
        String updatePrice= "UPDATE menu SET price=" + col2[i]  + " WHERE food_id=" + col0[i].toString() + ";";
        String updateING= "UPDATE menu SET ingredients=" + "\'" + col3[i]  +  "\'" + " WHERE food_id=" + col0[i].toString() + ";";
        db.executeQuery(updateItem);
        db.executeQuery(updatePrice);
        db.executeQuery(updateING);
     }
    if (e.getSource() == addRow) {
      
     }
    } 
  }
}