import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.PrintWriter;
import java.io.IOException;

/*
*JDBC Driver for SQL should be installed.then go file->project structure->modules->dependecies->
*add  "mssql-jdbc-11.2.0.jre11";
*
* */

public class Main {

    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public static void main(String[] args) throws IOException{
        String connectionUrl =
                "jdbc:sqlserver://servername.database.windows.net:1433;"
                        + "database=databasename;"
                        + "user=yourusername;"
                        + "password=yourpassword;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;";

        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);// code tested for errors while it is being executed.connecting to database
             Statement statement = connection.createStatement()) {//Creates a Statement object for sending SQL statements to the database.

            PrintWriter out = new PrintWriter("result.txt"); //  you create the PrintWriter object out
            // and associates it with the file result.txt .

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT TOP 100 a,b,c from databasename.dbo.tablename";//reading top 100 row from sql
            resultSet = statement.executeQuery(selectSql);//executing sql statement

            // Print results from select statement
            while (resultSet.next()) {

                double  a=resultSet.getDouble ("a") ;//getting coeffs from database by its column label
                double  b=resultSet.getDouble ("b") ;
                double  c=resultSet.getDouble ("c") ;

                out.println("\n"+a+","+b+","+c);

                double d=b * b - 4 * a * c;
                double x1,x2;
                if (d> 0) {

                    // two real and distinct roots
                    x1 = (-b + Math.sqrt(d)) / (2 * a);
                    x2=(-b - Math.sqrt(d)) / (2 * a);
                    out.println("\nx1= "+x1 +" and "+" x2= "+x2);

                }else if (d == 0) {
                    x1= x2 = -b / (2 * a);
                    out.println("\nx1 = x2 = "+x1);

                }else {

                    // roots are complex number and distinct
                    double real = -b / (2 * a);
                    double imaginary = Math.sqrt(-d) / (2 * a);
                    out.printf("\nx1 = %.2f+%.2fi ", real, imaginary);
                    out.printf("\nx2 = %.2f-%.2fi ", real, imaginary);
                    out.print(System.lineSeparator());

                }

            }out.close();//close file
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}