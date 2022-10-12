import java.sql.*;

public class Database {
    private Connection conn;
    private String teamNumber;
    private String sectionNumber;
    private String dbName;
    private String dbConnectionString;
    //private dbsetup myCredentials;

    public Database() {
        conn = null;
        teamNumber = "65";
        sectionNumber = "905";
        dbName = "csce331_" + sectionNumber + "_" + teamNumber;
        dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        //myCredentials = new dbsetup();

        // Connecting to the database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbsetup.user, dbsetup.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");
    }

    public void close() {
        // closing the connection
        try {
            conn.close();
            System.out.println("Connection Closed.");
        } catch (Exception e) {
            System.out.println("Connection NOT Closed.");
        }
    }

    public ResultSet executeQuery(String s) {
        Statement stmt = createStatement();
        ResultSet ans = null;
        try {
            ans = stmt.executeQuery(s);
        } catch (Exception e) {
            System.out.println("Error occured with executing query.");
        }
        return ans;
    }

    public void executeUpdate(String s){
        Statement stmt = createStatement();
        ResultSet ans = null;
        try {
            stmt.executeUpdate(s);
        } catch (Exception e) {
            System.out.println("Error occured with executing query.");
        }
    }

    public Statement createStatement() {
        Statement temp = null;
        try {
            temp = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            System.out.println("Error occured while creating statement.");
        }
        return temp;
    }

    public void updateInventory(String ingredients) {
        ResultSet inv = executeQuery("SELECT * FROM inventory ORDER BY item_id");
        String[] ings = ingredients.split(",");
        String query = "UPDATE inventory SET itemcount = ";
        String query2 = " WHERE item_id=";
        int nextCount;
        for(String s:ings){
            try{
                switch(s){
                    case "bun":
                        inv.absolute(1);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 1);
                        break;
                    case "txt":
                        inv.absolute(2);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 2);
                        break;
                    case "pty":
                        inv.absolute(3);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 3);
                        break;
                    case "bbp":
                        inv.absolute(4);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 4);
                        break;
                    case "bcn":
                        inv.absolute(5);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 5);
                        break;
                    case "ctr":
                        inv.absolute(6);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 6);
                        break;
                    case "sfr":
                        inv.absolute(7);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 7);
                        break;
                    case "ltc":
                        inv.absolute(8);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 8);
                        break;
                    case "pkl":
                        inv.absolute(9);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 9);
                        break;
                    case "tmt":
                        inv.absolute(10);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 10);
                        break;
                    case "onn":
                        inv.absolute(11);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 11);
                        break;
                    case "acs":
                        inv.absolute(12);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 12);
                        break;
                    case "frs":
                        inv.absolute(13);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 13);
                        break;
                    case "tts":
                        inv.absolute(14);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 14);
                        break;
                    case "onr":
                        inv.absolute(15);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 5);
                        break;
                    case "ktc":
                        inv.absolute(16);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 16);
                        break;
                    case "ges":
                        inv.absolute(17);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 17);
                        break;
                    case "srs":
                        inv.absolute(18);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 18);
                        break;
                    case "gvy":
                        inv.absolute(19);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 19);
                        break;
                    case "bfs":
                        inv.absolute(20);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 20);
                        break;
                    case "hnm":
                        inv.absolute(21);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 21);
                        break;
                    case "rnc":
                        inv.absolute(22);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 22);
                        break;
                    case "bqs":
                        inv.absolute(23);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 23);
                        break;
                    case "rfd":
                        inv.absolute(24);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 24);
                        break;
                    case "lfd":
                        inv.absolute(25);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 25);
                        break;
                    case "wts":
                        inv.absolute(26);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 26);
                        break;
                    case "wtb":
                        inv.absolute(27);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 27);
                        break;
                    case "dbi":
                        inv.absolute(28);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 28);
                        break;
                    case "ash":
                        inv.absolute(29);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 29);
                        break;
                    case "snd":
                        inv.absolute(30);
                        nextCount = inv.getInt("itemcount") - 1;
                        executeUpdate(query + nextCount + query2 + 30);
                        break;
                }
            }catch(Exception e){}
        }
    }

}
