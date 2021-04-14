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
            _ip = lines[0];
            _user = lines[1];
            _date = lines[2];
            //_event = lines[3]; split it by space, if 2 lines: second = exNum
        }
    }


    private String  _ip;
    private String  _user;
    private String  _date;
    private String  _event;
    private String  _exNum;
    private String  _status;

    private Log();
}
