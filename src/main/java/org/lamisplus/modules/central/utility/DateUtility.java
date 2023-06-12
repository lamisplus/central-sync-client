package org.lamisplus.modules.central.utility;

import org.springframework.stereotype.Component;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtility {
    public String ConvertDateToString(LocalDate localDate){
        if(localDate == null){
            return null;
        }
        else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return localDate.format(formatter);
        }
    }

    public String ConvertDateTimeToString(LocalDateTime datetime){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(datetime);
    }
}
