import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class Pair<T,U> {
  private final T key;
  private final U value;

  public Pair(T key, U value) {
      this.key = key;
      this.value = value;
  }

  public T getKey() {
      return this.key;
  }

  public U getValue() {
      return this.value;
  }
}

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
    JScrollPane sPane = new JScrollPane(reportDetails);
    sPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
        // loop through orderdetails and increment an array index
        menuItems = db.executeQuery("SELECT * FROM menu");
        menuItems.first();
        for (Integer i = 0; i < totMenuItems; i++) {
          menuItems.absolute(i + 1);
          myFile.write(menuItems.getString("menuitem") + " sold " + salesNumbers.get(i) + " times for $"
              + (df.format((menuItems.getDouble("price")) * salesNumbers.get(i))) + " in revenue\n");
          reportString += menuItems.getString("menuitem") + " sold " + salesNumbers.get(i) + " times for $"
          + (df.format((menuItems.getDouble("price")) * salesNumbers.get(i))) + " in revenue\n";
        }

      }

      else if (report == "excess") {
        // Use same logic with salesNumbers array and run that through the switch
        // statement in database
        reportString += "Items that sold less than 10% of their inventory\nfrom " + start + " to " + end + "\n\n";
        myFile.write("Items that sold less than 10% of their inventory\nfrom " + start + " to " + end + "\n\n");
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
        ResultSet inv = db.executeQuery("SELECT * FROM inventory ORDER BY item_id");
        int invSize = 0;
        inv.last();
        invSize = inv.getInt("item_id");
        ArrayList<Integer> invNumbers = new ArrayList<Integer>(invSize);
        for (Integer i = 0; i < invSize; i++) {
          invNumbers.add(0);
        }

        for (int i = 0; i < salesNumbers.size(); ++i) {
          int numSales = salesNumbers.get(i);
          menuItems.absolute(i+1);
          String ings = menuItems.getString("ingredients");
          for(int j = 0; j < numSales; j++){
            db.getInvNums(ings, invNumbers);
          }
        }
        for(Integer i = 0; i < invSize; i++){
          if(invNumbers.get(i) == 0){
            inv.absolute(i+1);
            reportString += inv.getString("itemname") + " was not sold at all.\n";
            myFile.write(inv.getString("itemname") + " was not sold at all.\n");
          }
          else if(invNumbers.get(i) < 350){
            inv.absolute(i+1);
            reportString += inv.getString("itemname") + " was only sold " + invNumbers.get(i) + " times.\n";
            myFile.write(inv.getString("itemname") + " was only sold " + invNumbers.get(i) + " times.\n");
          }
        }

        // salesNUMBERS has the number of times each item was ordered in that items
        // index
        // salesNumbers.get(0) will return the number of times the bacon cheeseburger
        // was ordered

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
    else if(report == "combo"){
      reportString += "Pairs of items that sold together and their frequency\nfrom " + start + " to " + end + "\n\n";
      myFile.write("Pairs of items that sold together and their frequency\nfrom " + start + " to " + end + "\n\n");
      HashMap<String, Pair<String, Integer>> map = new HashMap<>();

      ResultSet ohItems = db.executeQuery("SELECT * FROM orderhistory WHERE time_stamp >= '" + start + "' AND time_stamp <= '" + end + "'");

      menuItems = db.executeQuery("SELECT * FROM menu ORDER BY food_id");
      while(ohItems.next()){
        int id = ohItems.getInt("order_id");
        ResultSet odItems = db.executeQuery("SELECT * FROM orderdetails WHERE order_id = " + id);
        ArrayList<Integer> ids = new ArrayList<>();
        while(odItems.next()){
          int foodId = odItems.getInt("food_id");
          ids.add(foodId);
        }
        Collections.sort(ids);
        for(int i=0; i<ids.size(); ++i){
          for(int j=i+1; j<ids.size(); ++j){
            String key = ids.get(i) + " " + ids.get(j);
            if(map.containsKey(key)){
              Pair<String, Integer> curr = map.get(key);
              Pair<String, Integer> updated = new Pair<>(curr.getKey(), curr.getValue()+1);
              map.put(key, updated);
            }else{
              String item1 = "";
              String item2 = "";
              menuItems.absolute(ids.get(i));
              item1 = menuItems.getString("menuitem");
              menuItems.absolute(ids.get(j));
              item2 = menuItems.getString("menuitem");
              String idk = item1 + " and " + item2;
              Pair<String, Integer> newPair = new Pair<>(idk, 1);
              map.put(key, newPair);
            }
          }
        }
      }

      ArrayList<Pair<String, Integer>> p = new ArrayList<>();
      for(Pair<String, Integer> pr : map.values()){
        p.add(pr);
      }
      
      Collections.sort(p, Comparator.comparing(x -> -x.getValue()));

      for(Pair<String, Integer> pa : p){
        //System.out.println("Pair: " + pa.getKey() + " " + pa.getValue());
        reportString += pa.getKey() + " " + pa.getValue() + "\n";
        myFile.write(pa.getKey() + " " + pa.getValue() + "\n");
      }
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
    sPane.setBounds(200, 0, 500, 850);
    reportDetails.setText(reportString);
    frame.add(sPane);
    frame.setVisible(true);
  }
}
