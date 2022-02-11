package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class provide LocalDateTime with some important attributes and functions
 * @author comrang-altf4
 */
public class customLocalDateTime {
    public LocalDateTime localDateTime;
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
    customLocalDateTime(LocalDateTime l)
    {
        localDateTime=l;
    }

    /**
     * override toString() to display the time in desired format
     * @return  time in desired format
     */
    @Override
    public String toString()
    {
        return localDateTime.format(timeFormatter);
    }
}
