import java.sql.*;
import java.util.Random;
import java.util.Calendar;

public class GenerateData {
    private int numberOfDays;

    /**
     * @param numberOfDays The number of days to generate.
     */
    public GenerateData(int numberOfDays){
        this.numberOfDays = numberOfDays;
    }
    
    /**
     * Generates random orders starting from the specified date for numberOfDays
     * 
     * @param stmt1 Statement to be used to access the menu items.
     * @param stmt2 Statement to insert the values into the orderhistory
     * @param year  Used for the current year
     * @param month Used for the current month
     * @param day   Used for the current day
     * @exception Exception if result set has an error
     */
    public void generate(Statement stmt1, Statement stmt2, int year, int month, 
    int day){
        Random rand = new Random();
        Calendar date = Calendar.getInstance();
        ResultSet menu;
        int orderId = 1;

        // Query the menu items from the database
        try{
            menu = stmt1.executeQuery(
                "SELECT * FROM menu ORDER BY food_id");
        }catch(Exception e){
            System.out.println("Failure.");
            return;
        }

        // Generate random orders for the number of days requested
        for(int i = 0; i < numberOfDays; ++i){
            date.set(year, month, day + i);
            double money = 0;
            int moneyCap;

            // Handle game days on Saturday
            int dayNum = date.get(Calendar.DAY_OF_WEEK);
            if(dayNum != Calendar.SATURDAY){
                moneyCap = 2200 + rand.nextInt(1000);
            }else{
                moneyCap = 5000 + rand.nextInt(2000);
            }

            // Keep selling orders until we hit the amount
            // needed per day to hit > $15,000 per week
            while(money < moneyCap){
                int itemsPerOrder = rand.nextInt(4) + 1;
                double orderTotal = 0;

                // Order some random items from the menu
                for(int j = 0 ; j < itemsPerOrder; ++j){
                    //May need to change, not sure how many items there are.
                    int randOrder = rand.nextInt(20) + 1;
                    try{
                        menu.absolute(randOrder);
                        double price = menu.getDouble("price");
                        orderTotal += price;
                        //TODO push item to order linking table
                    }catch (Exception e){
                        System.out.println(e.toString());
                        return;
                    }
                }

                // Simulate a random order time
                int randHr = rand.nextInt(12) + 8;
                int randMin = rand.nextInt(60);
                int randSec = rand.nextInt(60);

                date.set(year, month, day + i, randHr, randMin, randSec);
                
                /*
                This is useful for debugging, but makes the code even slower.

                System.out.println("Order on " + date.get(Calendar.YEAR) + "," 
                + date.get(Calendar.MONTH) 
                + "," + date.get(Calendar.DAY_OF_MONTH) 
                + " At: " + date.get(Calendar.HOUR_OF_DAY) + "::" 
                + date.get(Calendar.MINUTE) + "::" + date.get(Calendar.SECOND) 
                + " total: " + orderTotal);
                */

                // Insert the order into the orderhistory table
                String timestamp = date.get(Calendar.YEAR) + "-" 
                + date.get(Calendar.MONTH) + "-" 
                + date.get(Calendar.DAY_OF_MONTH) + " " 
                + date.get(Calendar.HOUR_OF_DAY) + ":"
                + date.get(Calendar.MINUTE) + ":" 
                + date.get(Calendar.SECOND);
                String insertRequest = "INSERT INTO orderhistory(order_id, "
                + "time_stamp, pricetotal) VALUES(" + orderId++ + ", '"
                + timestamp + "', " + orderTotal + ")";
                try{
                    stmt2.executeUpdate(insertRequest);
                }catch(Exception e){
                    System.out.println(e.toString());
                    return;
                }
                money += orderTotal;
            }

            // Indicate the end of a day
            System.out.println("Day " 
            + date.get(Calendar.DAY_OF_MONTH) + " done, $" + money);
        }
    }
}
