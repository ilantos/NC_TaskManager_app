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
        System.out.println("Tasks:");
        int i = 1;
        for (Task task: tasks) {
            System.out.println(i++ + ". " + task);
        }
        return 0;
    }
}
