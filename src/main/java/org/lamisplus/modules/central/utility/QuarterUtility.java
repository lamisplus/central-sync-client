package org.lamisplus.modules.central.utility;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.dto.Quarter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;

@Component
@Slf4j
@RequiredArgsConstructor
public class QuarterUtility {
    public Quarter getPreviousQuarter(LocalDate endDate) {
        int monthValue = endDate.getMonth().getValue();

        if (monthValue < 4) {
            //current quarter = q2, previous quarter = q1
            return getQuarter(10, endDate.getYear() - 1, "Q1");
        } else if (monthValue < 7) {
            //current quarter = q3, previous quarter = q2
            return getQuarter(1, endDate.getYear(), "Q2");
        } else if (monthValue < 10) {
            //current quarter = q4, previous quarter = q3
            return getQuarter(4, endDate.getYear(), "Q3");
        } else {
            //current quarter = q1, previous quarter = q4
            return getQuarter(7, endDate.getYear(), "Q4");
        }

    }


    public Quarter getCurrentQuarter(LocalDate endDate) {
        int monthValue = endDate.getMonth().getValue();

        if (monthValue < 4) {
            //q2
            return getQuarter(1, endDate.getYear(), "Q2");
        } else if (monthValue < 7) {
            //q3
            return getQuarter(4, endDate.getYear(), "Q3");
        } else if (monthValue < 10) {
            //q4
            return getQuarter(7, endDate.getYear(), "Q4");
        } else {
            //q1
            return getQuarter(10, endDate.getYear(), "Q1");
        }
    }

    private Quarter getQuarter(int startMonth, int year, String quarterName) {
        LocalDate start = LocalDate.of(year, startMonth, 1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, startMonth + 1);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(startMonth + 2 == 9 && lastDay == 31){
            lastDay = 30;
        }
        if(startMonth + 2 == 6 && lastDay == 31){
            lastDay = 30;
        }
        LocalDate end = LocalDate.of(year, startMonth + 2, lastDay);
        return new Quarter(start, end, quarterName);

    }
}

