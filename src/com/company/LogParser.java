package com.company;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* Class for parsing .log files in ArrayList<Log>.
* Has one constructor which takes directory with .log files
* Every correct line from all .log files will be stored in ArrayList<Log> _data
*/
public class LogParser {
    public LogParser(Path logDir)
    {
        this._data = new ArrayList<Log>();
        this._logDir = logDir;
    }

    public void parse()
    {
        ArrayList<File> targetFiles = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\S*).log");
        for (File file : _logDir.toFile().listFiles())
        {
            Matcher matcher = pattern.matcher(file.getPath());
            if (matcher.matches())
                targetFiles.add(file);
        }

        for (File file : targetFiles)
        {
            try
            {
                FileReader fr = new FileReader(file);
                BufferedReader bReader = new BufferedReader(fr);

                String line = bReader.readLine();
                while (line != null)
                {
                    _data.add(new Log(line.split("\t")));
                    line = bReader.readLine();
                }
            }
            catch (FileNotFoundException ex)
            {
                System.out.println("Cannot read file: " + file.getName() + ": " + ex.getMessage());
                continue ;
            }
            catch (IOException ex)
            {
                System.out.println("Reading error: " + file.getName() + ": " + ex.getMessage());
            }

        }
    }

    public ArrayList<Log> get_data() { return _data; }

    private Path _logDir;
    private ArrayList<Log> _data;
    private LogParser() {}
}