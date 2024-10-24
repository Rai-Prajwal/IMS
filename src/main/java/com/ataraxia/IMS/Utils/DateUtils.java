package com.ataraxia.IMS.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.Date;
import com.github.binodnme.dateconverter.converter.DateConverter;
import com.github.binodnme.dateconverter.utils.DateBS;

public class DateUtils {
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static LocalDate sqlDateToLocalDate(java.sql.Date sqlDate) {
        return sqlDate != null ? sqlDate.toLocalDate() : null;
    }
    
    public static java.sql.Date localDateToSqlDate(LocalDate localDate) {
        return localDate != null ? java.sql.Date.valueOf(localDate) : null;
    }
    
    public static String formatLocalDate(LocalDate date) {
        return date != null ? date.format(DISPLAY_FORMATTER) : "";
    }
    
    public static LocalDate parseDate(String dateStr) {
        return dateStr != null && !dateStr.trim().isEmpty() 
               ? LocalDate.parse(dateStr, DISPLAY_FORMATTER) 
               : null;
    }
    
    public static DateBS convertADToBS(LocalDate adDate) {
        if (adDate == null) return null;
        Date date = Date.from(adDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return DateConverter.convertADToBS(date);
    }
}