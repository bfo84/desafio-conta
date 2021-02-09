package br.com.deliverit.util;

import java.time.LocalDateTime;

public class DateUtil {
	
	public static boolean validarData(LocalDateTime data) {
    	
        return data.getDayOfMonth() > 0 && data.getDayOfMonth() < 32 
        	   && data.getMonthValue() > 0 && data.getMonthValue() < 13 
        	   && data.getYear() > 0 && ((data.getMonthValue() == 1 
        	   || data.getMonthValue() == 3 || data.getMonthValue() == 5 
        	   || data.getMonthValue() == 7 || data.getMonthValue() == 8 
        	   || data.getMonthValue() == 10 || data.getMonthValue() == 12) 
        	   || ((data.getMonthValue() == 4 || data.getMonthValue() == 6 
        	   || data.getMonthValue() == 9 || data.getMonthValue() == 11) 
        	   && data.getDayOfMonth() <= 30) || (data.getMonthValue() == 2 
        	   && (data.getDayOfMonth() <= 29 && data.getYear() % 4 == 0 
        	   && (data.getYear() % 100 != 0 || data.getYear() % 400 == 0)) || data.getDayOfMonth() <= 28));
    }

}
