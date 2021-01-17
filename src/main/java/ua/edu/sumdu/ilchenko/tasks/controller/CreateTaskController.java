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
import ua.edu.sumdu.ilchenko.tasks.model.TaskIO;
import ua.edu.sumdu.ilchenko.tasks.utils.Configuration;
import ua.edu.sumdu.ilchenko.tasks.utils.Strings;
import ua.edu.sumdu.ilchenko.tasks.view.CreateTaskView;

import java.io.File;

public class CreateTaskController implements IController{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(CreateTaskController.class);

    /**
     * List of tasks.
     */
    private AbstractTaskList taskList;

    /**
     * View for creating task.
     */
    private CreateTaskView createTaskView;

    /**
     * Ctor.
     * @param taskList list where tasks will add
     * @param createTaskView for creating task
     */
    public CreateTaskController(AbstractTaskList taskList, CreateTaskView createTaskView) {
        this.taskList = taskList;
        this.createTaskView = createTaskView;
    }

    /**
     * Run controller.
     */
    @Override
    public void run() {
        int action;
        for ( ; ; ) {
            Task task = null;
            action = createTaskView.printInfo();
            if (action == -1) {
                break;
            }
            switch (action) {
                case 1:
                    logger.info("User choose creating non-repeated task");
                    task = createTaskView.getNonRepeatedTask();
                    break;
                case 2:
                    logger.info("User choose creating repeated task");
                    task = createTaskView.getRepeatedTask();
                    break;
                default:
                    createTaskView.printMessage(Strings.NOT_EXISTING_ACTIVITY);
                    logger.warn(Strings.NOT_EXISTING_ACTIVITY);
            }
            if (task != null) {
                taskList.add(task);
                TaskIO.writeText(taskList, new File(Configuration.PATH_STORE_TASKS));
                createTaskView.printMessage("Task added successfully!");
            }
        }
    }
}
