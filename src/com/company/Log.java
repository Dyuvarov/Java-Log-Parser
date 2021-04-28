package com.company;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Class to store one line of parsed log in separated fields
 *  Has only one constructor which takes array of String:
 *  every element in array for one field in Log object (except event and exNum)
 */
public class Log
{
    public String   getIp() { return _ip; }
    public String   getUser() { return _user; }
    public Date     getDate() { return _date; }
    public Event    getEvent() { return _event; }
    public Status   getStatus() { return _status; }

    public Log(String[] lines)
    {
        if (lines.length >= 5)
        {
            this._ip = lines[0];
            this._user = lines[1];

            DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            try
            {
                this._date = df.parse(lines[2]);
            }
            catch (ParseException e)
            {
                this._date = null;
                System.out.println("Cant read date!");
            }

            this._event = new Event(lines[3]);

            switch(lines[4])
            {
                case("OK"):
                    this._status = Status.OK;
                    break;
                case("FAILED"):
                    this._status = Status.FAILED;
                    break;
                case("ERROR"):
                    this._status = Status.ERROR;
                    break;
                default:
                    this._status = null;
                    break;
            }
        }
        else
        {
            System.out.println("Wrong line!");
            this._ip = "";
            this._user = "";
            this._date = null;
            this._event = null;
            this._status = null;
        }
    }

    @Override
    public String toString()
    {
        return (_ip + "\t" + _user + "\t" + _date +
                "\t" + _event + "\t" + _status.get_string()
                + "\n");
    }

    private String     _ip;
    private String     _user;
    private Date       _date;
    private Event      _event;
    private Status     _status;

    private Log() {}
}

class Event
{
    public Event(String event)
    {
        String[] event_tmp = event.split(" ");
        this._event = event_tmp[0]; //split it by space, if 2 lines: second is exNum
        if (event_tmp.length == 2)
            this._exNum = Integer.parseInt(event_tmp[1]);
        else
            this._exNum = null;
    }

    @Override
    public String toString()
    {
        return (_event + " " + _exNum);
    }

    public String getEvent()
    {
        return _event;
    }

    public Integer getExNum()
    {
        return _exNum;
    }

    private String  _event;
    private Integer _exNum;

    private Event() {};
}

enum Status
{
    OK("OK"),
    FAILED("FAILED"),
    ERROR("ERROR");

    private final String _status;

    Status(String status)
    {
        this._status = status;
    }

    public String get_string()
    {
        return _status;
    }
}