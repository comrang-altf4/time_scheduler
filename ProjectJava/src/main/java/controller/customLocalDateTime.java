package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class customLocalDateTime {
    public LocalDateTime localDateTime;
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
    customLocalDateTime(LocalDateTime l)
    {
        localDateTime=l;
    }
    @Override
    public String toString()
    {
        return localDateTime.format(timeFormatter);
    }
}