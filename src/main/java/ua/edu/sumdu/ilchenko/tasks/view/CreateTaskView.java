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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDateTime;

public class CreateTaskView implements IView {
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(CreateTaskView.class);

    /**
     * Console reader.
     */
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Print to console types of creating task.
     * @return action that user performed
     */
    @Override
    public int printInfo() {
        logger.info("Showing menu for adding task");
        System.out.println();
        System.out.println("-----------------");
        System.out.println("|  Adding task  |");
        System.out.println("-----------------");
        System.out.println("Choose type of adding task:");
        System.out.println(" 1 | unrepeated task");
        System.out.println(" 2 | repeated task");
        System.out.println("-1 | cancel");

        int action = 0;
        for ( ; ; ) {
            System.out.println("--- Choose activity (Enter number of activity) ---");
            try {
                action = Integer.parseInt(in.readLine());
                break;
            } catch (NumberFormatException e) {
                logger.warn("Entered number is a string", e);
                System.out.println("You entered not a number");
            } catch (IOException e) {
                logger.error("Cannot read from console", e);
                System.out.println("Cannot read your info :(");
            }
        }
        return action;
    }

    /**
     * Get from console a repeated task.
     * @return entered correct task or null
     */
    public Task getRepeatedTask() {
        try {
            System.out.println("Enter the title of task");
            String title = in.readLine();
            System.out.println("Entering start date of repeated task...");
            LocalDateTime startTime = getDateFromUser();
            System.out.println("Entering end date of repeated task...");
            LocalDateTime endTime = getDateFromUser();
            System.out.println("Enter interval of execution of repeated task");
            int interval = Integer.parseInt(in.readLine());
            return new Task(title, startTime, endTime, interval);
        } catch (NumberFormatException e) {
            logger.warn("Entered number is a string", e);
            System.out.println("You entered not a number");
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage(), e);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            logger.error("Cannot read from console", e);
            System.out.println("Cannot read your info :(");
        }
        return null;
    }

    /**
     * Get from console a non-repeated task.
     * @return entered correct task or null
     */
    public Task getNonRepeatedTask() {
        try {
            System.out.println("Enter the title of task");
            String title = in.readLine();
            System.out.println("Entering date of execution...");
            LocalDateTime executionTime = getDateFromUser();
            return new Task(title, executionTime);
        } catch (NumberFormatException e) {
            logger.warn("Entered number is a string", e);
            System.out.println("You entered not a number");
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage(), e);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            logger.error("Cannot read from console", e);
            System.out.println("Cannot read your info :(");
        }
        return null;
    }

    /**
     * Get date from console.
     * @return entered correct date or null
     */
    private LocalDateTime getDateFromUser() {
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
            logger.warn("Entered number is a string", e);
            System.out.println("You entered not a number");
        } catch (DateTimeException e) {
            logger.warn(e.getMessage(), e);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            logger.error("Cannot read from console", e);
            System.out.println("Cannot read your info :(");
        }
        return time;
    }
}
