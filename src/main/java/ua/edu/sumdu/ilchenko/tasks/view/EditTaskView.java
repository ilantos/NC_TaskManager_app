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
import ua.edu.sumdu.ilchenko.tasks.utils.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDateTime;

public class EditTaskView implements IView {
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(EditTaskView.class);

    /**
     * Console reader.
     */
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * View for show all tasks.
     */
    private IView taskListView;

    /**
     * Ctor.
     * @param taskListView view of the task list
     */
    public EditTaskView(IView taskListView) {
        this.taskListView = taskListView;
    }

    /**
     * Print to console available actions and tasks.
     * @return action that user performed
     */
    @Override
    public int printInfo() {
        logger.info("Showing menu for editing task");
        System.out.println("-----------------");
        System.out.println("| Editing task  |");
        System.out.println("-----------------");
        System.out.println(" n | n - number of a task for removing");
        System.out.println("-1 | back");
        taskListView.printInfo();
        return readAction();
    }

    /**
     * Print info about available editing for the task.
     * @return entered action
     */
    public int printInfoChooseEditing() {
        System.out.println("Available options to edit a task");
        System.out.println(" 1 | Edit title");
        System.out.println(" 2 | Edit time");
        System.out.println(" 3 | Edit active");
        System.out.println("-1 | back");
        return readAction();
    }

    private int readAction() {
        int action = 0;
        for ( ; ; ) {
            System.out.println(Strings.CHOOSE_ACTIVITY);
            try {
                action = Integer.parseInt(in.readLine());
                break;
            } catch (NumberFormatException e) {
                logger.warn(Strings.ISSUE_INPUT_NUMBER, e);
                System.out.println(Strings.ISSUE_INPUT_NUMBER);
            } catch (IOException e) {
                logger.error(Strings.ISSUE_CONSOLE, e);
                System.out.println(Strings.ISSUE_CONSOLE);
            }
        }
        return action;
    }

    /**
     * Edit title of the task.
     * @param task editing task
     * @return successful or unsuccessful result of editing
     */
    public boolean editTitle(Task task) {
        try {
            System.out.println("Enter the title of task");
            String title = in.readLine();
            task.setTitle(title);
            System.out.println("Task successfully changed!");
            return true;
        } catch (IOException e) {
            logger.error(Strings.ISSUE_CONSOLE, e);
            System.out.println(Strings.ISSUE_CONSOLE);
        }
        return false;
    }

    /**
     * Edit time of the task.
     * @param task editing task
     * @return successful or unsuccessful result of editing
     */
    public boolean editTime(Task task) {
        logger.info("Setting time...");
        if (task.isRepeated()) {
            System.out.println("Set start time of interval");
            LocalDateTime start = getDateFromUser();
            if (start == null) {
                System.out.println("The task isn't changed!");
                return false;
            }
            System.out.println("Set end time of interval");
            LocalDateTime end = getDateFromUser();
            if (end == null) {
                System.out.println("The task isn't changed!");
                return false;
            }
            try {
                System.out.println("Set interval");
                int interval = Integer.parseInt(in.readLine());
                task.setTime(start, end, interval);
                System.out.println("Task successfully changed!");
                return true;
            } catch (NumberFormatException e) {
                logger.warn(Strings.ISSUE_INPUT_NUMBER, e);
                System.out.println(Strings.ISSUE_INPUT_NUMBER);
                System.out.println("The task isn't changed!");
            } catch (IOException e) {
                logger.error(Strings.ISSUE_CONSOLE, e);
                System.out.println(Strings.ISSUE_CONSOLE);
            }
        } else {
            System.out.println("Set execution time");
            LocalDateTime executionTime = getDateFromUser();
            if (executionTime != null) {
                task.setTime(executionTime);
                System.out.println("Task successfully changed!");
                return true;
            } else {
                System.out.println("The task isn't changed");
                logger.info("Time isn't changed");
            }
        }
        return false;
    }

    /**
     * Edit active status of the task.
     * @param task editing task
     * @return successful or unsuccessful result of editing
     */
    public boolean editActive(Task task) {
        System.out.println("Actual status of task active is "
                + task.isActive());
        System.out.println("If you want to change status write \"yes\"");
        try {
            String answer = in.readLine();
            if (answer.equals("yes")) {
                task.setActive(!task.isActive());
                System.out.println("Active status is changed successfully!");
                return true;
            } else {
                System.out.println("Active status isn't changed");
            }
        } catch (IOException e) {
            logger.error(Strings.ISSUE_CONSOLE, e);
            System.out.println(Strings.ISSUE_CONSOLE);
        }
        return false;
    }

    /**
     * Get date from console.
     * @return entered correct date or null
     */
    private LocalDateTime getDateFromUser() {
        logger.debug("Getting a new date from user...");
        LocalDateTime time = null;
        try {
            System.out.println("Enter a number of the year");
            int year = Integer.parseInt(in.readLine());
            System.out.println("Enter a number of the month");
            int month = Integer.parseInt(in.readLine());
            System.out.println("Enter a number of the day");
            int day = Integer.parseInt(in.readLine());
            System.out.println("Enter a number of the hours (from 0 to 23)");
            int hour = Integer.parseInt(in.readLine());
            System.out.println("Enter a number of the minutes");
            int minute = Integer.parseInt(in.readLine());
            time = LocalDateTime.of(year, month, day, hour, minute);
        } catch (NumberFormatException e) {
            logger.warn(Strings.ISSUE_INPUT_NUMBER, e);
            System.out.println(Strings.ISSUE_INPUT_NUMBER);
        } catch (DateTimeException e) {
            logger.warn(e.getMessage(), e);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            logger.error(Strings.ISSUE_CONSOLE, e);
            System.out.println(Strings.ISSUE_CONSOLE);
        }
        return time;
    }
}
