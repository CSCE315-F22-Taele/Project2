import java.sql.*;

/*
CSCE 331
Group 65
 */
public class Runner {
    //Commands to run this script
    //javac *.java
    //Mac/Linux: java -cp ".:postgresql-42.2.8.jar" jdbcpostgreSQL

    //MAKE SURE YOU ARE ON VPN or TAMU WIFI TO ACCESS DATABASE
    public static void main(String args[]) {
        //Building the connection with your credentials
        Connection conn = null;
        String teamNumber = "65";
        String sectionNumber = "905";
        String dbName = "csce331_" + sectionNumber + "_" + teamNumber;
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbsetup myCredentials = new dbsetup(); 

        //Connecting to the database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbsetup.user, dbsetup.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");

        try{
            //create a statement object
            Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
            Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE);

            GenerateData data = new GenerateData(21);

            data.generate(stmt1, stmt2, 2022, 9, 1);

            //closing the connection
            try {
                conn.close();
                System.out.println("Connection Closed.");
            } catch(Exception e) {
                System.out.println("Connection NOT Closed.");
            }
        }catch(Exception e){}
    }
}
