import java.io.FileWriter;
import java.io.IOException; 
import java.sql.*;

public class reportGen {
  // Takes in a start and end time as well as the type of report to be generated
  // SELECT * FROM orderhistory WHERE time_stamp >= '<start>' AND time_stamp <= '<end>';
  // Example:
  // SELECT * FROM orderhistory WHERE time_stamp >= '2022-09-14 13:00:01' AND time_stamp <= '2022-09-17 16:34:00';
  reportGen(String start, String end, String report){
    // CHECKING WHAT TYPE OF REPORT IS TO BE GENERATED
    if(report == "sales"){
      try{
        FileWriter myFile = new FileWriter(report + "Report.txt");
        myFile.write("Sales Report from " + start + " to " + end);
        //loop through orderdetails and increment an array index
        

      }
      catch (IOException e){
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

    }

    else if(report == "excess"){

    }

    else if(report == "restock"){

    }
    
    // The "combo" report details what items sell together often
    else if(report == "combo"){

    }
  }
}
