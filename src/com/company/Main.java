package com.company;

import sun.security.krb5.internal.PAEncTSEnc;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;


public class Main {

    public static void main(String[] args) throws ParseException
    {
        if (validateArgs(args))
        {
            System.out.println("Wrong Args!\ninput path to direcotry int 1-st argument");
            return ;
        }

        LogParser logParser = new LogParser(Paths.get(args[0]));
        try
        {
            logParser.parse();
        }
        catch(NullPointerException e)
        {
            return ;
        }

	    //output in file "success" lines where event is "DONE_TASK" && status is "OK"
        try(FileWriter fileWriter = new FileWriter("success", false);)
        {
            for (Log line : logParser.get_data())
            {
                Event ev = line.getEvent();
                Status st = line.getStatus();
                if ( ev != null && st != null && ev.getEvent().equals("DONE_TASK") && st.equals(Status.OK))
                {
                    fileWriter.append(line.toString());
                }
            }
            fileWriter.flush();
        }
        catch (IOException ex)
        {
            System.out.println("Cannot write file: " + ex.getMessage());
        }

        //testing IPQuery implementation
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        System.out.println("***testing \"getNumberOfUniqueIPs\": ***");
        {
            System.out.print("(null, null): ");
            int c = logParser.getNumberOfUniqueIPs(null, null);
            if (c == 5)
                System.out.println("[SUCCESS]");
            else
                System.out.println("[FAIL] expected " + 6);
        }
        {
            System.out.print("(null, 30.08.2012 16:08:41): ");
            int c = logParser.getNumberOfUniqueIPs(null, df.parse("30.08.2012 16:08:41"));
            if (c == 2)
                System.out.println("[SUCCESS]");
            else
                System.out.println("[FAIL] expected " + 2);
        }
        {
            System.out.print("(01.01.2014 03:45:23, null): ");
            int c = logParser.getNumberOfUniqueIPs(df.parse("01.01.2014 03:45:23"), null);
            if (c == 5)
                System.out.println("[SUCCESS]");
            else
                System.out.println("[FAIL] expected " + 8);
        }
        {
            System.out.print("(03.01.1999 03:45:23, 01.01.1990 03:45:23): ");
            int c = logParser.getNumberOfUniqueIPs(df.parse("03.01.1999 03:45:23"), df.parse("01.01.1990 03:45:23"));
            if (c == 0)
                System.out.println("[SUCCESS]");
            else
                System.out.println("[FAIL] expected " + 0);
        }

        System.out.println('\n');

        System.out.println("***testing \"getIPsForStatus\": ***");
        {
            System.out.println("(OK, 30.08.2012 16:08:12, 14.11.2015 07:08:02): ");
            Set<String> res = logParser.getIPsForStatus(Status.OK, df.parse("30.08.2012 16:08:12"), df.parse("14.11.2015 07:08:02"));
            for (String str : res)
            {
                System.out.println(str);
            }
        }

        System.out.println('\n');

        System.out.println("***testing \"getIPsForEvent\": ***");
        {
            System.out.println("(WRITE_MESSAGE, 14.11.2001 07:08:02, 14.11.2115 07:08:02): ");
            Set<String> res = logParser.getIPsForEvent(new Event("WRITE_MESSAGE"), df.parse("14.11.2001 07:08:02"), df.parse("14.11.2115 07:08:02"));
            for (String str : res)
            {
                System.out.println(str);
            }
        }

    }

    private static boolean validateArgs(String[] args)
    {
        if (args.length != 1)
            return true;
        else
            return false;
    }
}





