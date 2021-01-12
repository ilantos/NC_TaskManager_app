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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskListView implements IView {
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(TaskListView.class);

    /**
     * Iterable of tasks.
     */
    private Iterable<Task> tasks;

    /**
     * Ctor.
     * @param tasks iterable of tasks
     */
    public TaskListView(Iterable<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Print to console list of tasks.
     * @return in anyway 0
     */
    @Override
    public int printInfo() {
        logger.info("Showing all tasks");
        System.out.println("Tasks:");
        int i = 1;
        for (Task task: tasks) {
            System.out.println(i++ + " | " + formatTask(task));
        }
        return 0;
    }

    private String formatTask(Task task) {
        StringBuilder formattedTask = new StringBuilder();
        formattedTask.append(task.getTitle());
        formattedTask.append(" | ");

        //Adding active status to str
        String active = "not active";
        if (task.isActive()) {
            active = "active";
        }
        formattedTask.append(active);
        formattedTask.append(" | ");

        //Adding repeated status and times of the task
        if (task.isRepeated()) {
            formattedTask.append("(repeated) ");
            formattedTask.append("start time: ");
            formattedTask.append(formatDate(task.getStartTime()));
            formattedTask.append(", end time: ");
            formattedTask.append(formatDate(task.getEndTime()));
            formattedTask.append(", interval: ");
            formattedTask.append(task.getRepeatInterval());
        }else {
            formattedTask.append("(non-repeated) ");
            formattedTask.append("time: ");
            formattedTask.append(formatDate(task.getTime()));
        }

        return formattedTask.toString();
    }

    private String formatDate(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return time.format(formatter);
    }
}
