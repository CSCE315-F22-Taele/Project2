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


public class managerMain{
  Database db;
  ArrayList<ArrayList<String>> inventoryData;
  managerMain() {
    // DEFINING MAIN J OBJECTS USED
    Color primary = new Color(0x2A2A72);
    Font guiFont = new Font("Impact",Font.PLAIN,20);

    //Create frame
    JFrame frame = new JFrame();  
    db = new Database();

    //Create critically low pane
    JPanel criticallyLow = new JPanel();
    JLabel criticallyLowTitle = new JLabel("Critically Low Inventory");
    
    //Create item range page
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

    
    //Create current inventory
    JPanel currentInventory = new JPanel();
    JLabel currentInventoryTitle = new JLabel("Current Inventory");


    String[] columns = new String[]{"item_id", "itemname", "itemcount", "itemfcount"};
    String[][] sinv = new String[30][4];

    // Display inventory
    ResultSet invItems = db.executeQuery("SELECT * FROM inventory ORDER BY itemcount");
    for(Integer i = 0; i < 30; i++){
        try{
          invItems.absolute(i+1);
          sinv[i][0] = invItems.getString("item_id");
          sinv[i][1] = invItems.getString("itemname");
          sinv[i][2] = invItems.getString("itemcount");
          sinv[i][3] = invItems.getString("itemfcount");
        }catch(Exception e){
          System.out.println(e.getMessage());
        }
    }
    JTable inventoryTable = new JTable(sinv, columns);
    JScrollPane sPane = new JScrollPane(inventoryTable);
    sPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
    inventoryTable.setFont(new Font("Impact",Font.PLAIN,15));
    sPane.setBounds(200,550,600,300);
    inventoryTable.setBounds(200,550,600,300);
    inventoryTable.setBackground(primary);
    inventoryTable.setForeground(Color.white);

    //Display Order History
    String[] columns_orderhist = new String[]{"order_id", "time_stamp", "pricetotal"};
    String[][] sinv_orderhist = new String[30][3];

    ResultSet OrderhistItems = db.executeQuery("SELECT * FROM inventory ORDER BY order_id");
    for(Integer i = 0; i < 30; i++){
        try{
          invItems.absolute(i+1);
          sinv_orderhist[i][0] = OrderhistItems.getString("order_id");
          sinv_orderhist[i][1] = OrderhistItems.getString("time_stamp");
          sinv_orderhist[i][2] = OrderhistItems.getString("pricetotal");
        }catch(Exception e){
          System.out.println(e.getMessage());
        }
    }
    JTable OrderhistTable = new JTable(sinv_orderhist, columns_orderhist);
    JScrollPane sPane_orderhist = new JScrollPane(OrderhistTable);
    sPane_orderhist.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
    inventoryTable.setFont(new Font("Impact",Font.PLAIN,15));
    sPane_orderhist.setBounds(200,550,600,300);
    inventoryTable.setBounds(200,550,600,300);
    inventoryTable.setBackground(primary);
    inventoryTable.setForeground(Color.white);

    //Sets up the frame
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000,1000);
    frame.setLayout(null);
    frame.setResizable(false);
    frame.add(sPane);

    frame.add(criticallyLow);
    frame.add(itemRange);
    frame.add(itemRangeTextBox);
    frame.add(inventoryRangeTextBox);
    frame.setVisible(true);
    
    
    //Sets up item range
    itemRange.setBounds(505,0,500,450);   //Box
    itemRange.setBackground(primary);
    itemRangeTextBox.setBounds(505,450,500,50);
    itemRangeTextBox.setBackground(primary);
    itemRangeTitle.setForeground(new Color(255, 255, 255));
    itemRangeTitle.setFont(guiFont);

    itemRange.add(itemRangeTitle);
    itemRangeTextBox.add(startTime);
    itemRangeTextBox.add(endTime);
    itemRangeTextBox.add(itemSelection);
    
    //Sets up current Inventory
    currentInventory.setBounds(0,505,1000,400);
    currentInventory.setBackground(primary);
    currentInventoryTitle.setForeground(new Color(255, 255, 255));
    currentInventoryTitle.setFont(guiFont);

    inventoryRangeTextBox.setBounds(0,905,1000,55);
    inventoryRangeTextBox.setBackground(primary);
    inventoryRangeTextBox.add(inventoryItem);
    inventoryRangeTextBox.add(inventoryAmount);
    inventoryRangeTextBox.add(inventoryButton);
    currentInventory.add(currentInventoryTitle);


    //Sets up critically low
    criticallyLow.setBounds(0,0,500,500);
    criticallyLow.setBackground(primary);
    criticallyLowTitle.setForeground(new Color(255, 255, 255));
    criticallyLowTitle.setFont(guiFont);
    criticallyLow.add(criticallyLowTitle);
  }
}
