package com.ataraxia.IMS.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.ataraxia.Utils.Converter;
import com.ataraxia.Models.BSDate;
import java.time.format.DateTimeParseException;

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

    public static BSDate convertADToBS(LocalDate adDate) {
        return adDate != null ? Converter.convertADtoBS(adDate) : null;
    }
    
    public static BSDate parseBSDate(String dateString) throws DateTimeParseException {
        try {
        	String[] parts = dateString.split("[/-]");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
                
            BSDate date = new BSDate(year, month, day);
            Converter.validateBsDate(date);
            return date;
       } catch (Exception e) {
    	   throw new DateTimeParseException("Invalid BS date format", dateString, 0);
           }
    }

    public static BSDate getCurrentBSDate() {
    	return Converter.convertADtoBS(LocalDate.now());
    }
}
