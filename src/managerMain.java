import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
    JLabel itemRangeTitle = new JLabel("Item History");
    JLabel startTime = new JLabel("Start: ");
    JLabel endTime = new JLabel("End: ");
    JLabel itemSelection = new JLabel("Item: ");

    
    //Create current inventory
    JPanel currentInventory = new JPanel();
    JLabel currentInventoryTitle = new JLabel("Current Inventory");
    
    //Sets up the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000,1000);
    frame.setLayout(null);
    frame.setResizable(false);
    frame.add(criticallyLow);
    frame.add(itemRange);
    frame.add(currentInventory);
    frame.setVisible(true);
    
    //Sets up critically low
    criticallyLow.setBounds(0,0,500,500);
    criticallyLow.setBackground(primary);
    criticallyLowTitle.setForeground(Color.white);
    criticallyLowTitle.setFont(guiFont);
    criticallyLow.add(criticallyLowTitle);

    //Sets up item range
    itemRange.setBounds(505,0,500,500);
    itemRange.setBackground(primary);
    itemRangeTitle.setForeground(new Color(0, 0, 0));
    itemRangeTitle.setFont(guiFont);
    itemRange.add(itemRangeTitle);

    startTime.setBounds(500,0,100,100);
    startTime.setForeground(Color.white);
    itemRange.add(startTime);

    endTime.setBounds(600,250,20,20);
    endTime.setForeground(Color.white);
    itemRange.add(endTime);
    
    itemRange.add(itemSelection);
    itemSelection.setBounds(510,0,20,20);
    itemSelection.setForeground(Color.white);
    
    //Sets up current Inventory
    currentInventory.setBounds(0,505,1000,500);
    currentInventory.setBackground(primary);
    currentInventoryTitle.setForeground(Color.white);
    currentInventoryTitle.setFont(guiFont);
    currentInventory.add(currentInventoryTitle);
  }
}
