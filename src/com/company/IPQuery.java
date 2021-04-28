package com.company;

import java.util.Date;
import java.util.Set;

public interface IPQuery
{
    int getNumberOfUniqueIPs(Date after, Date before);

    Set<String> getUNiqueIPs(Date after, Date before);

    Set<String> getIPsForUSer(String user, Date after, Date before);

    Set<String> getIPsForEvent(Event event, Date after, Date before);

    Set<String> getIPsForStatus(Status status, Date after, Date before);
}

