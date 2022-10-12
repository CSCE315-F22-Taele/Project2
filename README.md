# CSCE331-Project2

To connect to the server:
You first have to create a file called dbsetup.java that contains two public static final String's: user and pswd. These will conatain your username and password needed to connect to the database. You can copy the file from the activity we did from lab because it is the exact same file. The file can not exist in the repo because it would cause you to push your password which is not a good idea. If you have questions, just ask me and I will help you get it setup.

Running the code from powershell: You need to add psql to your environment variables (win, not sure on mac). To do this, you find where your psql is installed (default is C:\Program Files\PostgreSQL\14\bin). You will then copy that path and add it to your environment variables. First search for "environment variables" in the windows search bar and click the first result. Then click "Environment Variables...", select the "Path" variable under the System Variables pane, click "Edit", "New", and then paste the path to your psql installation. You are now good to go!

To run the gui:
Download "The coding pack for java"
https://code.visualstudio.com/docs/languages/java
Click install and open in windows not WSL
Run the code from the top right play button
