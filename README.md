# Java-Log-Parser
Parsing text file in program data.

## Execution:
Compile and pass the path to directory with .log files as first (and only) parametr.

## Description:  

### Class LogParser:  
Takes path to directory and parse files with .log extention in this directory using StreamAPI. 
Also, using StreamAPI implemented functions:  
:point_right: int getNumberOfUniqueIPs(Date after, Date before) - count of unique IPs betweend Date after, Date before  
:point_right: Set<String> getUniqueIPs(Date after, Date before) - set of IPs betweend Date after, Date before  
:point_right: Set<String> getIPsForEvent(Event event, Date after, Date before) - unique of IPs, whith Event event betweend Date after, Date before    
:point_right: Set<String> getIPsForStatus(Status status, Date after, Date before) - unique of IPs, whith Status status betweend Date after, Date before  
  
Data stores in ArrayList of Log objects. 
  
### Class Log:  
Log class contains fields to store one line from .log file. 

### Class Event:
Describes event and number of exercise. Number of exercise exists only for events "DONE_TASK" and "SOLVE_TASK"

### Enum Status
Describes all possible statuses.

After parsing creates file "success" and write there only lines with event == "DONE_TASK" and status = "OK".
