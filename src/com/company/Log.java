package com.company;

/** Class to store one line of parsed log in separated fields
 *  Has only one constructor which takes array of String:
 *  every element in array for one field in Log object (except event and exNum)
 */
public class Log
{
    public String   getIp() { return _ip; }
    public String   getUser() { return _user; }
    public String   getDate() { return _date; }
    public String   getEvent() { return _event; }
    public String   getExNum() { return _exNum; }
    public String   getStatus() { return _status; }

    public void setIp(String s) { _ip = s; }
    public void setUser(String s) { _user = s; }
    public void setDate(String s) { _date = s; }
    public void setEvent(String s) { _event = s; }
    public void setexNum(String s) { _exNum = s; }
    public void setStatus(String s) { _status = s; }


    public Log(String[] lines)
    {
        if (lines.length >= 5)
        {
            this._ip = lines[0];
            this._user = lines[1];
            this._date = lines[2];
            String[] event_tmp = lines[3].split(" ");
            this._event = event_tmp[0]; //split it by space, if 2 lines: second = exNum
            if (event_tmp.length == 2)
                this._exNum = event_tmp[1];
            else
                this._exNum = "";
            this._status = lines[4];
        }
        else
        {
            System.out.println("Wrong line!");
            this._ip = "";
            this._user = "";
            this._date = "";
            this._event = "";
            this._exNum = "";
            this._status = "";
        }
    }


    private String  _ip;
    private String  _user;
    private String  _date;
    private String  _event;
    private String  _exNum;
    private String  _status;

    private Log() {}
}