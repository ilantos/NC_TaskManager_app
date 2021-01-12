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

package ua.edu.sumdu.ilchenko.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.ilchenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.ilchenko.tasks.model.Task;
import ua.edu.sumdu.ilchenko.tasks.model.Tasks;
import ua.edu.sumdu.ilchenko.tasks.view.CalendarView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;

public class CalendarController implements IController{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(CalendarController.class);

    /**
     * List of tasks.
     */
    private AbstractTaskList taskList;

    /**
     * View of calendar with tasks.
     */
    private CalendarView calendarView;

    /**
     * Ctor.
     * @param taskList list of tasks
     * @param calendarView view for showing calendar
     */
    public CalendarController(AbstractTaskList taskList, CalendarView calendarView) {
        this.taskList = taskList;
        this.calendarView = calendarView;
    }

    /**
     * Run controller.
     */
    @Override
    public void run() {
        int action;
        for ( ; ; ) {
            action = calendarView.printInfo();
            if (action == -1) {
                break;
            }
            switch (action) {
                case 1:
                    logger.info("Showing tomorrow task");
                    calendarView.printCalendar(getTomorrowTasks());
                    break;
                case 2:
                    logger.info("Showing tasks for next 7 days");
                    calendarView.printCalendar(getNextDaysTasks(7));
                    break;
                case 3:
                    logger.info("Showing tasks for this month");
                    calendarView.printCalendar(getMonthTasks());
                    break;
                default:
                    System.out.println("You entered not existing activity");
                    logger.warn("Entered not existing activity");
            }
        }
    }

    private Map<LocalDateTime, Set<Task>> getTomorrowTasks() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrowStart = LocalDateTime.of(now.plusDays(1).toLocalDate(), LocalTime.MIN);
        LocalDateTime tomorrowEnd = LocalDateTime.of(now.plusDays(1).toLocalDate(), LocalTime.MAX);
        return Tasks.calendar(taskList, tomorrowStart, tomorrowEnd);
    }

    private Map<LocalDateTime, Set<Task>> getNextDaysTasks(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextDays = LocalDateTime.of(now.toLocalDate().plusDays(days), LocalTime.MAX);
        return Tasks.calendar(taskList, now, nextDays);
    }

    private Map<LocalDateTime, Set<Task>> getMonthTasks() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate();
        LocalDateTime monthEnd = LocalDateTime.of(
                nowDate.withDayOfMonth(nowDate.lengthOfMonth()),
                LocalTime.MAX);
        return Tasks.calendar(taskList, now, monthEnd);
    }
}
