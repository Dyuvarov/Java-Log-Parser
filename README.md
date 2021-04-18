# Java-Log-Parser
Parsing text file in program data

Class LogParser takes path to directory and parse files with .log extention in this directory. 

Data stores in ArrayList<Log>. 
  
Log class contains fields to store one line from .log file.

After parsing creates file "success" and write there only lines with event == "DONE_TASK" and status = "OK".
