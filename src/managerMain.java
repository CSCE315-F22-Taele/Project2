import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class managerMain{
  managerMain() {
    // DEFINING MAIN J OBJECTS USED
    Color primary = new Color(0x2A2A72);
    Font guiFont = new Font("Impact",Font.PLAIN,20);

    //Create frame
    JFrame frame = new JFrame();  

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
    
    //Sets up the frame
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000,1000);
    frame.setLayout(null);
    frame.setResizable(false);
    frame.add(criticallyLow);
    frame.add(itemRange);
    frame.add(currentInventory);
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
