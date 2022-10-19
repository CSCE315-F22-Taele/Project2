import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class reportGen {
  // Takes in a start and end time as well as the type of report to be generated
  // SELECT * FROM orderhistory WHERE time_stamp >= '<start>' AND time_stamp <=
  // '<end>';
  // Example:
  // SELECT * FROM orderhistory WHERE time_stamp >= '2022-09-14 13:00:01' AND
  // time_stamp <= '2022-09-17 16:34:00';

  // Database object to communicate with the server
  Database db;
  // How many times each menu item sold:
  ArrayList<Integer> salesNumbers;
  DecimalFormat df = new DecimalFormat("0.00");
  int totMenuItems = 0;
  ResultSet menuItems;
  ArrayList<String> lowInvItems; // Keeps track of items in lowinventory

  /**
   * @param start hold values for start time for orders
   * @param end hold values for end time for orders
   * @param report type of report that needs to be generated
   * @exception Exception if result set has an error
   * @exception Exception if the file output has an error
   */
  reportGen(String start, String end, String report) {
    db = new Database();
    JFrame frame = new JFrame();
    JTextArea reportDetails = new JTextArea(); // area on frame where report is displayed
    String reportString = ""; // this string is added to based on report type

    // CHECKING WHAT TYPE OF REPORT IS TO BE GENERATED
    try {
      FileWriter myFile = new FileWriter(report + "Report.txt");
      if (report == "sales") {
        // display how much each menu item was sold in a timeframe

        ResultSet ohItems = db.executeQuery(
            "SELECT * FROM orderhistory WHERE time_stamp >= '" + start + "' AND time_stamp <= '" + end + "'");
        ohItems.first();
        int firstID = ohItems.getInt("order_id"); // first order no. in time frame
        ohItems.last();
        int lastID = ohItems.getInt("order_id"); // last order no. in time frame

        ResultSet odItems = db
            .executeQuery("SELECT * FROM orderdetails WHERE order_id >= " + firstID + " AND order_id <= " + lastID);
        menuItems = db.executeQuery("SELECT * FROM menu");
        menuItems.last();
        totMenuItems = menuItems.getInt("food_id");
        ArrayList<Integer> salesNumbers = new ArrayList<Integer>(totMenuItems);
        for (Integer i = 0; i < totMenuItems; i++) {
          salesNumbers.add(0);
        }

        while (odItems.next()) {
          int menuItem = odItems.getInt("food_id");
          // Get current value at this index
          Integer curvalue = salesNumbers.get(menuItem - 1);
          // increment the value by 1
          salesNumbers.set((menuItem - 1), curvalue + 1);
        }
        myFile.write("Sales Report from " + start + " to " + end + "\n\n");
        reportString += "Sales Report from " + start + " to " + end + "\n\n";
        myFile.write("Item\t\t\tSales\t\t\tTotal Revenue\n");
        reportString += "Item\tSales\tTotal Revenue\n";
        // loop through orderdetails and increment an array index
        menuItems = db.executeQuery("SELECT * FROM menu");
        menuItems.first();
        for (Integer i = 0; i < totMenuItems; i++) {
          menuItems.absolute(i + 1);
          myFile.write(menuItems.getString("menuitem") + "\t" + salesNumbers.get(i) + "\t$"
              + (df.format((menuItems.getDouble("price")) * salesNumbers.get(i))) + "\n");
          reportString += menuItems.getString("menuitem") + "\t" + salesNumbers.get(i) + "\t$"
              + (df.format((menuItems.getDouble("price")) * salesNumbers.get(i))) + "\n";
        }

      }

      else if (report == "excess") {
        // Use same logic with salesNumbers array and run that through the switch
        // statement in database
        ResultSet ohItems = db.executeQuery(
            "SELECT * FROM orderhistory WHERE time_stamp >= '" + start + "' AND time_stamp <= '" + end + "'");
        ohItems.first();
        int firstID = ohItems.getInt("order_id"); // first order no. in time frame
        ohItems.last();
        int lastID = ohItems.getInt("order_id"); // last order no. in time frame

        ResultSet odItems = db
            .executeQuery("SELECT * FROM orderdetails WHERE order_id >= " + firstID + " AND order_id <= " + lastID);
        menuItems = db.executeQuery("SELECT * FROM menu");
        menuItems.last();
        totMenuItems = menuItems.getInt("food_id");
        ArrayList<Integer> salesNumbers = new ArrayList<Integer>(totMenuItems);
        for (Integer i = 0; i < totMenuItems; i++) {
          salesNumbers.add(0);
        }

        while (odItems.next()) {
          int menuItem = odItems.getInt("food_id");
          // Get current value at this index
          Integer curvalue = salesNumbers.get(menuItem - 1);
          // increment the value by 1
          salesNumbers.set((menuItem - 1), curvalue + 1);
        }
        // salesNUMBERS has the number of times each item was ordered in that items
        // index
        // salesNumbers.get(0) will return the number of times the bacon cheeseburger
        // was ordered
        // salesNumbers.get(totMenuItems - 1) will return the number of times the last
        // item on the menu was ordered

      }

      else if (report == "restock") {
        ResultSet lowInv = db.executeQuery("SELECT * FROM lowinventory");
        ResultSet Inv = db.executeQuery("SELECT * FROM inventory ORDER BY item_id");
        // Initial lines at top of file
        myFile.write("Inventory Items that need to be restocked:\n\n");
        reportString += "Inventory Items that need to be restocked:\n\n";
        while (lowInv.next()) {
          int itemId = lowInv.getInt("item_id");
          Inv.absolute(itemId);
          String itemName = Inv.getString("itemname");
          myFile.write(itemName + " has a current count of: " + Inv.getInt("itemcount") + "\n");
          reportString += itemName + " has a current count of: " + Inv.getInt("itemcount") + "\n";
        }
      }

      // The "combo" report details what items sell together often
      else if (report == "combo") {

      }

      myFile.close();

    } // end of try block
    // ERROR CHECKING
    catch (IOException e) { // exception for file IO
      System.out.println("An error occurred.");
      e.printStackTrace();
    } catch (Exception ex) { // exception for database stuff
      System.out.println(ex.getMessage());
    }

    // SETTING UP FRAME TO DISPLAY REPORT DETAILS
    frame.setSize(1000, 1000);
    frame.setLayout(null);
    frame.setResizable(false);
    reportDetails.setBounds(200, 0, 500, 1000);
    reportDetails.setText(reportString);
    frame.add(reportDetails);
    frame.setVisible(true);
  }
}
