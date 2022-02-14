package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class QuoteGenerator {
    public static String[] quotes=new String[1000];
    public static int totalQuotes=0;
    public static int random_int;
    public QuoteGenerator() throws FileNotFoundException {
        File myObj = new File(System.getProperty("user.dir")+"/Quotes.txt");
        System.out.println(myObj.exists());
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            quotes[totalQuotes]=data;
            totalQuotes++;
        }
    }
    public static void generate()
    {
        int min = 1;
        int max = totalQuotes;
        random_int = (int)Math.floor(Math.random()*(max-min+1)+min)-1;
        if (random_int%2!=0)random_int-=1;
    }
    public static String getAuthor()
    {
        return String.format("- %s -",quotes[random_int+1].length()<2?"Anonymous": quotes[random_int+1]);
    }
    public static String getQuote()
    {
        return quotes[random_int];
    }
}
