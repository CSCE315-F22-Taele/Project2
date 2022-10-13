import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class managerMain {
  Database db;
  ArrayList<ArrayList<String>> inventoryData;

  managerMain() {
    // DEFINING MAIN J OBJECTS USED
    Color primary = new Color(0x2A2A72);
    Font guiFont = new Font("Impact", Font.PLAIN, 20);

    // Create frame
    JFrame frame = new JFrame();
    db = new Database();

    // Create critically low pane
    JPanel criticallyLow = new JPanel();
    JLabel criticallyLowTitle = new JLabel("Critically Low Inventory");

    // Create item range page
    JPanel itemRange = new JPanel();
    JPanel itemRangeTextBox = new JPanel();
    JPanel inventoryRangeTextBox = new JPanel();
    JLabel itemRangeTitle = new JLabel("Item History");
    JTextField startTime = new JTextField("Start: ");
    JTextField endTime = new JTextField("End: ");
    JTextField itemSelection = new JTextField("Item: ");
    JTextField inventoryItem = new JTextField("Item: ");
    JTextField inventoryAmount = new JTextField("Amount: ");
    JButton inventoryButton = new JButton("Make Selected Change");

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
    JTable inventoryTable = new JTable(sinv, columns);
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

    String[] columns_menu = new String[] { "food_id", "menuitem", "price", "ingredients" };
    String[][] sinv_menu = new String[21][4];
    ResultSet MenuItems = db.executeQuery("SELECT * FROM inventory ORDER BY food_id");
    for (Integer i = 0; i < 30; i++) {
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
    JTable menuTable = new JTable(sinv_menu, columns_menu);
    JScrollPane sPane_menu = new JScrollPane(menuTable);
    sPane_menu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    menuTable.setFont(new Font("Impact", Font.PLAIN, 15));
    sPane_menu.setBounds(200, 550, 600, 300);
    menuTable.setBounds(200, 550, 600, 300);
    menuTable.setBackground(primary);
    menuTable.setForeground(Color.white);


    // Sets up the frame
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 1000);
    frame.setLayout(null);
    frame.setResizable(false);
    frame.add(sPane);

    frame.add(criticallyLow);
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
    itemRangeTextBox.add(startTime);
    itemRangeTextBox.add(endTime);
    itemRangeTextBox.add(itemSelection);

    // Sets up current Inventory
    currentInventory.setBounds(0, 505, 1000, 400);
    currentInventory.setBackground(primary);
    currentInventoryTitle.setForeground(new Color(255, 255, 255));
    currentInventoryTitle.setFont(guiFont);

    inventoryRangeTextBox.setBounds(0, 905, 1000, 55);
    inventoryRangeTextBox.setBackground(primary);
    inventoryRangeTextBox.add(inventoryItem);
    inventoryRangeTextBox.add(inventoryAmount);
    inventoryRangeTextBox.add(inventoryButton);
    currentInventory.add(currentInventoryTitle);


    // Sets up critically low
    criticallyLow.setBounds(0, 0, 500, 500);
    criticallyLow.setBackground(primary);
    criticallyLowTitle.setForeground(new Color(255, 255, 255));
    criticallyLowTitle.setFont(guiFont);
    criticallyLow.add(criticallyLowTitle);
  }
}
