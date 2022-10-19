import java.sql.*;
import java.util.ArrayList;

public class Database {
    private Connection conn;
    private String teamNumber;
    private String sectionNumber;
    private String dbName;
    private String dbConnectionString;

    /**
     * @exception Exception if database can't connect
     */
    public Database() {
        conn = null;
        teamNumber = "65";
        sectionNumber = "905";
        dbName = "csce331_" + sectionNumber + "_" + teamNumber;
        dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;

        // Connecting to the database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbsetup.user, dbsetup.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    /**
     * @exception Exception if connection is not closed correctly
     */
    public void close() {
        // closing the connection
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param s sequal command
     * @return result set from executing the sequal command
     * @exception Exception if there is sequal error
     */
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

    /**
     * @param s sequal command
     * @exception Exception if there is sequal error
     */
    public void executeUpdate(String s) {
        Statement stmt = createStatement();
        try {
            stmt.executeUpdate(s);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return new created statement
     * @exception Exception if it fails to create new statement
     */
    public Statement createStatement() {
        Statement temp = null;
        try {
            temp = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return temp;
    }

    /**
     * @param ingredients string of the item ingredients that is being updated
     * @exception Exception if result set has an error
     */
    public void updateInventory(String ingredients) {
        ResultSet inv = executeQuery("SELECT * FROM inventory ORDER BY item_id");
        ResultSet lowInv = executeQuery("SELECT * FROM lowinventory");
        Boolean present = false;
        int currInv = 0;
        try {
            inv.last();
            currInv = inv.getInt("item_id");
        } catch (Exception e) {
        }
        String[] ings = ingredients.split(",");
        int[] subAmt = new int[currInv + 1];
        String query = "UPDATE inventory SET itemcount = ";
        String query2 = " WHERE item_id=";
        int nextCount;
        for (String s : ings) {
            try {
                switch (s) {
                    case "bun":
                        subAmt[0]++;
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
            } catch (Exception e) {
            }
        }

        for (int i = 0; i < subAmt.length; ++i) {
            try {
                if (subAmt[i] != 0) {
                    inv = executeQuery("SELECT * FROM inventory ORDER BY item_id");
                    lowInv = executeQuery("SELECT * FROM lowinventory");
                    inv.absolute(i + 1);
                    nextCount = inv.getInt("itemcount") - subAmt[i];
                    executeUpdate(query + nextCount + query2 + (i + 1));

                    // Adding code to populate Low Inventory
                    // if the current count is less than 10 % of its full count and not in low
                    // inventory

                    if (inv.getInt("itemcount") < (.10 * inv.getInt("itemfcount"))) {
                        while (lowInv.next()) {
                            if (lowInv.getInt("item_id") == (i + 1)) {
                                present = true;
                            }
                        }

                        if (!present) {
                            executeUpdate("INSERT INTO lowinventory (item_id) VALUES (" + (i + 1) + ")");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }



    public void getInvNums(String ingredients, ArrayList<Integer> subAmt) {
        String[] ings = ingredients.split(",");
        Integer curvalue = 0;
        for (String s : ings) {
            try {
                switch (s) {
                    case "bun":
                        curvalue = subAmt.get(0);
                        subAmt.set(0, curvalue + 1);
                        break;
                    case "txt":
                    curvalue = subAmt.get(1);
                    subAmt.set(1, curvalue + 1);
                        break;
                    case "pty":
                    curvalue = subAmt.get(2);
                    subAmt.set(2, curvalue + 1);
                        break;
                    case "bbp":
                    curvalue = subAmt.get(3);
                    subAmt.set(3, curvalue + 1);
                        break;
                    case "bcn":
                    curvalue = subAmt.get(4);
                    subAmt.set(4, curvalue + 1);
                        break;
                    case "ctr":
                    curvalue = subAmt.get(5);
                    subAmt.set(5, curvalue + 1);
                        break;
                    case "sfr":
                    curvalue = subAmt.get(6);
                    subAmt.set(6, curvalue + 1);
                        break;
                    case "ltc":
                    curvalue = subAmt.get(7);
                    subAmt.set(7, curvalue + 1);
                        break;
                    case "pkl":
                    curvalue = subAmt.get(8);
                    subAmt.set(8, curvalue + 1);
                        break;
                    case "tmt":
                    curvalue = subAmt.get(9);
                    subAmt.set(9, curvalue + 1);
                        break;
                    case "onn":
                    curvalue = subAmt.get(10);
                    subAmt.set(10, curvalue + 1);
                        break;
                    case "acs":
                    curvalue = subAmt.get(11);
                    subAmt.set(11, curvalue + 1);
                        break;
                    case "frs":
                    curvalue = subAmt.get(12);
                    subAmt.set(12, curvalue + 1);
                        break;
                    case "tts":
                    curvalue = subAmt.get(13);
                    subAmt.set(13, curvalue + 1);
                        break;
                    case "onr":
                    curvalue = subAmt.get(14);
                    subAmt.set(14, curvalue + 1);
                        break;
                    case "ktc":
                    curvalue = subAmt.get(15);
                    subAmt.set(15, curvalue + 1);
                        break;
                    case "ges":
                    curvalue = subAmt.get(16);
                    subAmt.set(16, curvalue + 1);
                        break;
                    case "srs":
                    curvalue = subAmt.get(17);
                    subAmt.set(17, curvalue + 1);
                        break;
                    case "gvy":
                    curvalue = subAmt.get(18);
                    subAmt.set(18, curvalue + 1);
                        break;
                    case "bfs":
                    curvalue = subAmt.get(19);
                    subAmt.set(19, curvalue + 1);
                        break;
                    case "hnm":
                    curvalue = subAmt.get(20);
                    subAmt.set(20, curvalue + 1);
                        break;
                    case "rnc":
                    curvalue = subAmt.get(21);
                    subAmt.set(21, curvalue + 1);
                        break;
                    case "bqs":
                    curvalue = subAmt.get(22);
                    subAmt.set(22, curvalue + 1);
                        break;
                    case "rfd":
                    curvalue = subAmt.get(23);
                    subAmt.set(23, curvalue + 1);
                        break;
                    case "lfd":
                    curvalue = subAmt.get(24);
                    subAmt.set(24, curvalue + 1);
                        break;
                    case "wts":
                    curvalue = subAmt.get(25);
                    subAmt.set(25, curvalue + 1);
                        break;
                    case "wtb":
                    curvalue = subAmt.get(26);
                    subAmt.set(26, curvalue + 1);
                        break;
                    case "dbi":
                    curvalue = subAmt.get(27);
                    subAmt.set(27, curvalue + 1);
                        break;
                    case "ash":
                    curvalue = subAmt.get(28);
                    subAmt.set(28, curvalue + 1);
                        break;
                    case "snd":
                    curvalue = subAmt.get(29);
                    subAmt.set(29, curvalue + 1);
                        break;
                }
            } catch (Exception e) {
            }
        }
        }
}

