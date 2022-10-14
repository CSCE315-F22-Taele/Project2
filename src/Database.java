import java.sql.*;

public class Database {
    private Connection conn;
    private String teamNumber;
    private String sectionNumber;
    private String dbName;
    private String dbConnectionString;

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
            System.out.println(e.getMessage());
        }
        return ans;
    }

    public void executeUpdate(String s){
        Statement stmt = createStatement();
        try {
            stmt.executeUpdate(s);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        int[] subAmt = new int[ings.length];
        String query = "UPDATE inventory SET itemcount = ";
        String query2 = " WHERE item_id=";
        int nextCount;
        for(String s:ings){
            try{
                switch(s){
                    case "bun":
                        subAmt[0]++;;
                        break;
                    case "txt":
                        subAmt[1]++;
                        break;
                    case "pty":
                        subAmt[2]++;
                        break;
                    case "bbp":
                        subAmt[3]++;
                        break;
                    case "bcn":
                        subAmt[4]++;
                        break;
                    case "ctr":
                        subAmt[5]++;
                        break;
                    case "sfr":
                        subAmt[6]++;
                        break;
                    case "ltc":
                        subAmt[7]++;
                        break;
                    case "pkl":
                        subAmt[8]++;
                        break;
                    case "tmt":
                        subAmt[9]++;
                        break;
                    case "onn":
                        subAmt[10]++;
                        break;
                    case "acs":
                        subAmt[11]++;
                        break;
                    case "frs":
                        subAmt[12]++;
                        break;
                    case "tts":
                        subAmt[13]++;
                        break;
                    case "onr":
                        subAmt[14]++;
                        break;
                    case "ktc":
                        subAmt[15]++;
                        break;
                    case "ges":
                        subAmt[16]++;
                        break;
                    case "srs":
                        subAmt[17]++;
                        break;
                    case "gvy":
                        subAmt[18]++;
                        break;
                    case "bfs":
                        subAmt[19]++;
                        break;
                    case "hnm":
                        subAmt[20]++;
                        break;
                    case "rnc":
                        subAmt[21]++;
                        break;
                    case "bqs":
                        subAmt[22]++;
                        break;
                    case "rfd":
                        subAmt[23]++;
                        break;
                    case "lfd":
                        subAmt[24]++;
                        break;
                    case "wts":
                        subAmt[25]++;
                        break;
                    case "wtb":
                        subAmt[26]++;
                        break;
                    case "dbi":
                        subAmt[27]++;
                        break;
                    case "ash":
                        subAmt[28]++;
                        break;
                    case "snd":
                        subAmt[29]++;
                        break;
                }
            }catch(Exception e){}
        }

        for(int i=0; i<subAmt.length; ++i){
            try{
                if(subAmt[i] != 0){
                    inv.absolute(i+1);
                    nextCount = inv.getInt("itemcount") - subAmt[i];
                    executeUpdate(query + nextCount + query2 + (i+1));
                }
            }catch(Exception e){}
        }
    }

}
