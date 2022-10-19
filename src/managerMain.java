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

import javax.swing.table.*;

public class managerMain implements ActionListener {
  Database db;
  ArrayList<ArrayList<String>> inventoryData;
  JTable inventoryTable;
  JTable menuTable;
  JTable critTable;
  JButton inventoryButton;
  JButton MenuButton;
  JButton addRowButton;
  JButton invaddRowButton;

  // REPORT BUTTONS
  JButton salesReport;
  JButton restockReport;
  JButton excessReport;
  JButton comboReport;

  /**
   * @exception Exception if result set has an error
   */
  managerMain() {
    // DEFINING MAIN J OBJECTS USED
    Color primary = new Color(0x2A2A72);
    Font guiFont = new Font("Impact", Font.PLAIN, 20);
    inventoryButton = new JButton("Make Changes");
    salesReport = new JButton("Sales Report");
    restockReport = new JButton("Restock Report");
    excessReport = new JButton("Excess Report");
    comboReport = new JButton("Combo Report");
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
    JLabel itemRangeTitle = new JLabel("Report Generator");
    MenuButton = new JButton("Change Menu");
    addRowButton = new JButton("Add Row");
    invaddRowButton = new JButton("Add Row");
    // Create current inventory
    JPanel currentInventory = new JPanel();
    JLabel currentInventoryTitle = new JLabel("Current Inventory");

    String[] columns = new String[] { "item_id", "itemname", "itemcount", "itemfcount" };
    String[][] sinv = new String[30][4];
    ResultSet inventoryItems = db.executeQuery("SELECT * FROM inventory ORDER BY item_id");
    Integer inventorySize = 0;
    try {
      inventoryItems.last();
      inventorySize = inventoryItems.getInt("item_id");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // Display inventory
    ResultSet invItems = db.executeQuery("SELECT * FROM inventory ORDER BY itemcount");
    inventoryTable = new JTable(new DefaultTableModel(columns, 0));
    for (Integer i = 0; i < inventorySize; i++) {
      try {
        invItems.absolute(i + 1);
        sinv[i][0] = invItems.getString("item_id");
        sinv[i][1] = invItems.getString("itemname");
        sinv[i][2] = invItems.getString("itemcount");
        sinv[i][3] = invItems.getString("itemfcount");
        DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
        model.addRow(sinv[i]);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    String[] cicolumns = new String[] { "item_id", "itemname", "itemcount", "itemfcount" };
    String[][] cisinv = new String[30][4];
    ResultSet critItems = db.executeQuery("SELECT * FROM inventory ORDER BY item_id");
    Integer ciSize = 0;
    try {
      critItems.last();
      ciSize = inventoryItems.getInt("item_id");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // Critically Low inventory
    // ResultSet critLowItems = db.executeQuery("SELECT * FROM inventory ORDER BY
    // priority_id");
    // critTable = new JTable(new DefaultTableModel(cicolumns, 0));
    // for (Integer i = 0; i < ciSize; i++) {
    // try {
    // invItems.absolute(i + 1);
    // sinv[i][0] = critLowItems.getString("priority_id");
    // sinv[i][1] = critLowItems.getString("item_id");
    // DefaultTableModel model = (DefaultTableModel) critTable.getModel();
    // model.addRow(cisinv[i]);
    // } catch (Exception e) {
    // System.out.println(e.getMessage());
    // }
    // }

    JScrollPane sPane = new JScrollPane(inventoryTable);
    sPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    inventoryTable.setFont(new Font("Impact", Font.PLAIN, 15));
    sPane.setBounds(200, 550, 600, 300);
    inventoryTable.setBounds(200, 550, 600, 300);
    inventoryTable.setBackground(primary);
    inventoryTable.setForeground(Color.white);

    ResultSet MenuItems = db.executeQuery("SELECT * FROM menu ORDER BY food_id");

    // Display Menu
    JPanel currentMenu = new JPanel();
    JLabel currentMenuTitle = new JLabel("Menu");

    Integer menuSize = 0;
    try {
      MenuItems.last();
      menuSize = MenuItems.getInt("food_id");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    String[] columns_menu = new String[] { "food_id", "menuitem", "price", "ingredients" };
    String[][] sinv_menu = new String[menuSize][4];
    menuTable = new JTable(new DefaultTableModel(columns_menu, 0));

    for (Integer i = 0; i < menuSize; i++) {
      try {
        MenuItems.absolute(i + 1);
        sinv_menu[i][0] = MenuItems.getString("food_id");
        sinv_menu[i][1] = MenuItems.getString("menuitem");
        sinv_menu[i][2] = MenuItems.getString("price");
        sinv_menu[i][3] = MenuItems.getString("ingredients");
        DefaultTableModel model = (DefaultTableModel) menuTable.getModel();
        model.addRow(sinv_menu[i]);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    // menuTable = new JTable(sinv_menu, columns_menu);
    // menuTable = updateTable(MenuItems);
    JScrollPane sPane_menu = new JScrollPane(menuTable);
    sPane_menu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    menuTable.setFont(new Font("Impact", Font.PLAIN, 15));
    sPane_menu.setBounds(20, 20, 450, 400);
    menuTable.setBounds(20, 20, 450, 400);
    menuTable.setBackground(primary);
    menuTable.setForeground(Color.white);
    menuRangeTextBox.add(MenuButton);
    menuRangeTextBox.add(addRowButton);
    addRowButton.addActionListener(this);
    invaddRowButton.addActionListener(this);
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

    restockReport.addActionListener(this);
    itemRange.add(itemRangeTitle);
    itemRange.add(salesReport);
    itemRange.add(restockReport);
    itemRange.add(excessReport);
    itemRange.add(comboReport);

    // Sets up current Inventory
    currentInventory.setBounds(0, 505, 1000, 400);
    currentInventory.setBackground(primary);
    currentInventoryTitle.setForeground(new Color(255, 255, 255));
    currentInventoryTitle.setFont(guiFont);

    inventoryRangeTextBox.setBounds(0, 905, 1000, 55);
    inventoryRangeTextBox.setBackground(primary);
    inventoryRangeTextBox.add(inventoryButton);
    inventoryRangeTextBox.add(invaddRowButton);
    inventoryButton.addActionListener(this);
    MenuButton.addActionListener(this);
  }

  /**
   * @param e action event, the button that got pressed
   * @exception Exception if result set has an error
   */
  public void actionPerformed(ActionEvent e) {
    System.out.println("Click");
    if (e.getSource() == inventoryButton) {
      Object[] col0 = new Object[inventoryTable.getRowCount()];
      Object[] col1 = new Object[inventoryTable.getRowCount()];
      Object[] col2 = new Object[inventoryTable.getRowCount()];
      Object[] col3 = new Object[inventoryTable.getRowCount()];

      for (int i = 0; i < inventoryTable.getRowCount(); i++) { // Loop through the rows
        col0[i] = inventoryTable.getValueAt(i, 0);
        col1[i] = inventoryTable.getValueAt(i, 1);
        col2[i] = inventoryTable.getValueAt(i, 2);
        col3[i] = inventoryTable.getValueAt(i, 3);
        String updateItem = "UPDATE inventory SET itemname=" + "\'" + col1[i] + "\'" + " WHERE item_id="
            + col0[i].toString();
        String updatePrice = "UPDATE inventory SET itemCount=" + col2[i] + " WHERE item_id=" + col0[i].toString();
        String updateING = "UPDATE inventory SET itemfcount=" + col3[i] + " WHERE item_id=" + col0[i].toString();
        System.out.println("Inventory CMD1 " + updateItem + "\n");
        db.executeUpdate(updateItem);
        db.executeUpdate(updatePrice);
        db.executeUpdate(updateING);

        // Needs ability to add a new item from a new row
      }
    }

    if (e.getSource() == MenuButton) {
      Object[] col0 = new Object[menuTable.getRowCount()];
      Object[] col1 = new Object[menuTable.getRowCount()];
      Object[] col2 = new Object[menuTable.getRowCount()];
      Object[] col3 = new Object[menuTable.getRowCount()];

      for (int i = 0; i < menuTable.getRowCount(); i++) { // Loop through the rows
        col0[i] = menuTable.getValueAt(i, 0);
        col1[i] = menuTable.getValueAt(i, 1);
        col2[i] = menuTable.getValueAt(i, 2);
        col3[i] = menuTable.getValueAt(i, 3);
        String updateItem = "UPDATE menu SET menuItem=" + "\'" + col1[i] + "\'" + " WHERE food_id="
            + col0[i].toString();
        String updatePrice = "UPDATE menu SET price=" + col2[i] + " WHERE food_id=" + col0[i].toString();
        String updateING = "UPDATE menu SET ingredients=" + "\'" + col3[i] + "\'" + " WHERE food_id="
            + col0[i].toString();
        System.out.println("CMD1 " + updateItem + "\n");
        db.executeUpdate(updateItem);
        db.executeUpdate(updatePrice);
        db.executeUpdate(updateING);

        // Needs ability to add a new item from a new row
      }
    }
    if (e.getSource() == addRowButton) {
      ResultSet MenuItems = db.executeQuery("SELECT * FROM menu ORDER BY food_id");
      Integer menuSize = 0;
      try {
        MenuItems.last();
        menuSize = MenuItems.getInt("food_id");
        MenuItems.close();
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }

      String cmd = "INSERT INTO menu(food_id, menuitem, price, ingredients) VALUES("
          + (menuSize + 1) + ", 'enter item name', 0.00, 'add ingredients')";
      db.executeUpdate(cmd);
      String[] newRow = new String[4];
      MenuItems = db.executeQuery("SELECT * FROM menu ORDER BY food_id");
      try {
        MenuItems.last();
        newRow[0] = MenuItems.getString("food_id");
        newRow[1] = MenuItems.getString("menuitem");
        newRow[2] = MenuItems.getString("price");
        newRow[3] = MenuItems.getString("ingredients");
        DefaultTableModel model = (DefaultTableModel) menuTable.getModel();
        model.addRow(newRow);
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }

    if (e.getSource() == invaddRowButton) {
      ResultSet MenuItems = db.executeQuery("SELECT * FROM inventory ORDER BY item_id");
      Integer menuSize = 0;
      try {
        MenuItems.last();
        menuSize = MenuItems.getInt("item_id");
        MenuItems.close();
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }

      String cmd = "INSERT INTO inventory(item_id, itemname, itemcount, itemfcount) VALUES("
          + (menuSize + 1) + ", 'enter item name', 0, 3000)";
      db.executeUpdate(cmd);
      String[] newRow = new String[4];
      MenuItems = db.executeQuery("SELECT * FROM inventory ORDER BY item_id");
      try {
        MenuItems.last();
        newRow[0] = MenuItems.getString("item_id");
        newRow[1] = MenuItems.getString("itemname");
        newRow[2] = MenuItems.getString("itemcount");
        newRow[3] = MenuItems.getString("itemfcount");
        DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
        model.addRow(newRow);
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }
    if (e.getSource() == restockReport) {
      new reportGen("0", "0", "restock");
    }
  }
}