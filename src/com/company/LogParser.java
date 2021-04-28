package com.company;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* Class for parsing .log files in ArrayList<Log>.
* Has one constructor which takes directory with .log files
* Every correct line from all .log files will be stored in ArrayList<Log> _data
*/
public class LogParser implements IPQuery
{
    private Path            _logDir;
    private List<String>    _raw_data;
    private ArrayList<Log>  _data;

    public LogParser(Path logDir)
    {
        this._data = new ArrayList<Log>();
        this._raw_data = new ArrayList<String>();
        this._logDir = logDir;
    }

    public void parse() throws NullPointerException
    {
        ArrayList<File> targetFiles = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\S*).log");
        File[] files = _logDir.toFile().listFiles();
        if (files == null)
        {
            System.out.println("ERROR: Wrong path to directory!\n");
            throw new NullPointerException();
        }

        for (File file : files)
        {
            Matcher matcher = pattern.matcher(file.getPath());
            if (matcher.matches())
                targetFiles.add(file);
        }

        for (File file : targetFiles)
        {
            try (Stream<String> lines = Files.newBufferedReader(file.toPath()).lines())
            {
                _raw_data = lines.collect(Collectors.toList());
                for (String line : _raw_data)
                {
                    _data.add(new Log(line.split("\t")));
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

    public int getNumberOfUniqueIPs(Date after, Date before)
    {
        Set<String> res = new HashSet<String>();
        List<Log> tmpList = filterStreamByDate(_data.stream(), after, before);
        Stream<Log> stream = tmpList.stream();
        stream.forEach(n -> res.add(n.getIp()));
        return res.size();
    }

    public Set<String> getUniqueIPs(Date after, Date before)
    {
        Set<String> res = new HashSet<String>();
        List<Log> tmpList = filterStreamByDate(_data.stream(), after, before);
        Stream<Log> stream = tmpList.stream();
        stream.forEach(n -> res.add(n.getIp()));
        return res;
    }

    public Set<String> getIPsForUSer(String user, Date after, Date before)
    {
        Set<String> res = new HashSet<String>();
        List<Log> tmpList = filterStreamByDate(_data.stream(), after, before);
        Stream<Log> stream = tmpList.stream();
        stream
           .filter(n -> (n.getUser() != null && n.getUser().equals(user)))
           .forEach(n -> res.add(n.getIp()));
        return res;
    }

    public Set<String> getIPsForEvent(Event event, Date after, Date before)
    {
        Set<String> res = new HashSet<String>();
        List<Log> tmpList = filterStreamByDate(_data.stream(), after, before);
        Stream<Log> stream = tmpList.stream();
        stream
            .filter(n -> (n.getEvent().getEvent().equals(event.getEvent())))
            .forEach(n -> res.add(n.getIp()));
        return res;
    }

    public Set<String> getIPsForStatus(Status status, Date after, Date before)
    {
        Set<String> res = new HashSet<String>();
        List<Log> tmpList = filterStreamByDate(_data.stream(), after, before);
        Stream<Log> stream = tmpList.stream();
        stream
            .filter(n -> n.getStatus().equals(status))
            .forEach(n -> res.add(n.getIp()));
        return res;
    }


    public ArrayList<Log> get_data() { return _data; }

    private List<Log> filterStreamByDate(Stream<Log> stream, Date after, Date before)
    {
        if (after != null && before != null)
        {
            return (stream
                        .filter(n -> (n.getDate() != null && n.getDate().after(after) && n.getDate().before(before)))
                        .collect(Collectors.toList()));
        }
        else if (after == null && before != null)
        {
            return (stream
                        .filter(n -> (n.getDate() != null && n.getDate().before(before)))
                        .collect(Collectors.toList()));
        }
        else if (after != null && before == null)
        {
            return (stream
                        .filter(n -> (n.getDate() != null && n.getDate().after(after)))
                        .collect(Collectors.toList()));
        }
        return (stream.collect(Collectors.toList()));
    }

    private LogParser() {}
}