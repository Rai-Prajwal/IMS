package com.ataraxia.IMS.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

public class DateUtils {
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static LocalDate sqlDateToLocalDate(Date sqlDate) {
        return sqlDate != null ? sqlDate.toLocalDate() : null;
    }
    
    public static Date localDateToSqlDate(LocalDate localDate) {
        return localDate != null ? Date.valueOf(localDate) : null;
    }
    
    public static String formatLocalDate(LocalDate date) {
        return date != null ? date.format(DISPLAY_FORMATTER) : "";
    }
    
    public static LocalDate parseDate(String dateStr) {
        return dateStr != null && !dateStr.trim().isEmpty() 
               ? LocalDate.parse(dateStr, DISPLAY_FORMATTER) 
               : null;
    }
}