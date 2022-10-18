public class runGUI {
  public static void main(String[] args) {
     //managerMain();
    // new serverMain();
    new startPage();

    // SALES REPORT TEST
    // new reportGen("2022-09-14 13:00:01", "2022-09-17 16:34:00", "sales");

    //start and end time not necessary for RESTOCK REPORT
    // new reportGen("0", "0", "restock"); 
  }
}

// MUST BE CONNECTED TO VPN OR SCHOOL WIFI
// Windows
// java -cp ".;postgresql-42.2.8.jar" runGUI

// Mac
// java -cp ".:postgresql-42.2.8.jar" runGUI