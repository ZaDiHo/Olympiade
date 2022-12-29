package fr.zadiho.hepickstudio.olympiade.utils;

import java.time.LocalTime;

public class Chrono {

    public static String format(int timer){
        LocalTime timeOfDay = LocalTime.ofSecondOfDay(timer);
        timeOfDay.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
        return timeOfDay.toString();
    }
}
