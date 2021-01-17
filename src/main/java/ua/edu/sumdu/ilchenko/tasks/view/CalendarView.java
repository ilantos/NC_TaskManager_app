/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 Anton Ilchenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 *  in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ua.edu.sumdu.ilchenko.tasks.view;

import org.apache.log4j.Logger;
import ua.edu.sumdu.ilchenko.tasks.model.Task;
import ua.edu.sumdu.ilchenko.tasks.view.utils.StringsView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public class CalendarView implements IView{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(CalendarView.class);

    /**
     * Console reader.
     */
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Print to console available actions of the calendar.
     * @return action that user performed
     */
    @Override
    public int printInfo() {
        logger.info("Showing menu of intervals for calendar");
        System.out.println("Available intervals for calendar:");
        System.out.println(" 1 | tomorrow");
        System.out.println(" 2 | next 7 days");
        System.out.println(" 3 | this month");
        System.out.println("-1 | back");
        int action;
        for ( ; ; ) {
            System.out.println(StringsView.CHOOSE_ACTIVITY);
            try {
                action = Integer.parseInt(in.readLine());
                break;
            } catch (NumberFormatException e) {
                logger.warn(StringsView.ISSUE_INPUT_NUMBER, e);
                System.out.println(StringsView.ISSUE_INPUT_NUMBER);
            } catch (IOException e) {
                logger.error(StringsView.ISSUE_CONSOLE, e);
                System.out.println(StringsView.ISSUE_CONSOLE);
            }
        }
        return action;
    }

    /**
     * Print to console calendar with some interval.
     * @param calendar calendar
     */
    public void printCalendar(Map<LocalDateTime, Set<Task>> calendar) {
        LocalDate day = null;
        int i = 1;
        for (LocalDateTime time: calendar.keySet()) {
            LocalDate dateOfTime = time.toLocalDate();
            if (!dateOfTime.equals(day)) {
                System.out.println(dateOfTime + ":");
                //i = 1;
            }
            Set<Task> tasks = calendar.get(time);
            for (Task task: tasks) {
                System.out.println(i++ + " | " + task.getTitle());
            }
            day = dateOfTime;
        }
    }
}
