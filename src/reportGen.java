import java.io.FileWriter;
import java.io.IOException; 
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class reportGen {
  // Takes in a start and end time as well as the type of report to be generated
  // SELECT * FROM orderhistory WHERE time_stamp >= '<start>' AND time_stamp <= '<end>';
  // Example:
  // SELECT * FROM orderhistory WHERE time_stamp >= '2022-09-14 13:00:01' AND time_stamp <= '2022-09-17 16:34:00';

    // Database object to communicate with the server
    Database db;
    // How many times each menu item sold:
    ArrayList<Integer> salesNumbers; 
    DecimalFormat df = new DecimalFormat("0.00");
    int totMenuItems = 0;
    ResultSet menuItems;
  reportGen(String start, String end, String report){
    db = new Database();
    // CHECKING WHAT TYPE OF REPORT IS TO BE GENERATED
    try{
    FileWriter myFile = new FileWriter(report + "Report.txt");
    if(report == "sales"){
    // display how much each menu item was sold in a timeframe

      ResultSet ohItems = db.executeQuery("SELECT * FROM orderhistory WHERE time_stamp >= '" + start + "' AND time_stamp <= '" + end + "'");
      ohItems.first();
      int firstID = ohItems.getInt("order_id"); //first order no. in time frame
      ohItems.last();
      int lastID = ohItems.getInt("order_id"); //last order no. in time frame

      ResultSet odItems = db.executeQuery("SELECT * FROM orderdetails WHERE order_id >= " + firstID + " AND order_id <= " + lastID);
      menuItems = db.executeQuery("SELECT * FROM menu");
      menuItems.last();
      totMenuItems = menuItems.getInt("food_id");
      ArrayList<Integer> salesNumbers = new ArrayList<Integer>(totMenuItems);
      for(Integer i = 0; i < totMenuItems; i++){
        salesNumbers.add(0);
      }

      while(odItems.next()){
        int menuItem = odItems.getInt("food_id");
        // Get current value at this index
        Integer curvalue = salesNumbers.get(menuItem - 1);
        // increment the value by 1
        salesNumbers.set((menuItem - 1), curvalue + 1);
      }
      myFile.write("Sales Report from " + start + " to " + end + "\n");
      myFile.write("Item\t\t\tSales\t\t\tTotal Revenue\n");
      //loop through orderdetails and increment an array index
      menuItems = db.executeQuery("SELECT * FROM menu");
      menuItems.first();
      for(Integer i = 0; i < totMenuItems; i++){
        menuItems.absolute(i+1);
        myFile.write(menuItems.getString("menuitem") + "\t" + salesNumbers.get(i) + "\t$" + (df.format((menuItems.getDouble("price")) * salesNumbers.get(i))) + "\n");
      }

    }

    else if(report == "excess"){

    }

    else if(report == "restock"){
        ResultSet lowInv = db.executeQuery("SELECT * FROM lowinventory");
        while(lowInv.next()){
          int itemId = lowInv.getInt("item_id");
        }
    }
    
    // The "combo" report details what items sell together often
    else if(report == "combo"){

    }

    myFile.close();
    
    } //end of try block
    catch (IOException e){ //exception for file IO
      System.out.println("An error occurred.");
      e.printStackTrace();
    } 
    catch(Exception ex){ //exception for database stuff
      System.out.println(ex.getMessage());
    }
  }
}
