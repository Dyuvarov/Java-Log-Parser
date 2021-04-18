package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args)
    {
	    LogParser logParser = new LogParser(Paths.get("/home/dyuvarov/SberIT/logParser/logs"));
	    logParser.parse();
        try
        {
            FileWriter fileWriter = new FileWriter("success", false);
            for (Log line : logParser.get_data())
            {
                if (line.getEvent().equals("DONE_TASK") && line.getStatus().equals("OK"))
                {
                    fileWriter.append(line.getIp() + "\t" + line.getUser() + "\t" + line.getDate() +
                            "\t" + line.getEvent() + " " + line.getExNum() + "\t" + line.getStatus()
                            + "\n");
                }
            }
            fileWriter.flush();
        }
        catch (IOException ex)
        {
            System.out.println("Cannot write file: " + ex.getMessage());
        }
    }
}





