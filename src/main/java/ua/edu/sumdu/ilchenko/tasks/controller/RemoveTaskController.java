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
import ua.edu.sumdu.ilchenko.tasks.view.IView;

import java.io.File;

public class RemoveTaskController implements IController {
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(RemoveTaskController.class);

    /**
     * List of tasks.
     */
    private AbstractTaskList taskList;

    /**
     * View for creating task.
     */
    private IView removeTaskView;

    /**
     * Ctor.
     * @param taskList from this task list will be removing a task
     * @param removeTaskView view
     */
    public RemoveTaskController(AbstractTaskList taskList, IView removeTaskView) {
        this.taskList = taskList;
        this.removeTaskView = removeTaskView;
    }

    /**
     * Run controller.
     */
    @Override
    public void run() {
        int action;
        for ( ; ; ) {
            action = removeTaskView.printInfo();
            if (action == -1) {
                break;
            }
            if (action > 0 && action <= taskList.size()) {
                int i = 1;
                for (Task task: taskList) {
                    if (action == i++) {
                        logger.info("Task for removing:" + task.toString());
                        if (taskList.remove(task)) {
                            TaskIO.writeText(taskList, new File(Configuration.PATH_STORE_TASKS));
                        }
                        break;
                    }
                }
            } else {
                removeTaskView.printMessage(Strings.NOT_EXISTING_ACTIVITY);
                logger.warn(Strings.NOT_EXISTING_ACTIVITY);
            }
        }
    }
}
