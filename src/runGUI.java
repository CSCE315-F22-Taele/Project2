public class runGUI {

  /**
   * @param args command line arguments
   */
  public static void main(String[] args) {
    // new managerMain();
    // new serverMain();
    //new startPage();

    // SALES REPORT TEST
    new reportGen("2022-09-18 20:12:51", "2022-09-18 20:14:12", "combo");

    // start and end time not necessary for RESTOCK REPORT
    // new reportGen("0", "0", "restock");
  }
}

// MUST BE CONNECTED TO VPN OR SCHOOL WIFI
// Windows
// java -cp ".;postgresql-42.2.8.jar" runGUI

// Mac
// java -cp ".:postgresql-42.2.8.jar" runGUI