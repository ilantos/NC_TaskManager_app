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
import ua.edu.sumdu.ilchenko.tasks.view.EditTaskView;

public class EditTaskController implements IController {
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(EditTaskController.class);

    /**
     * List of tasks.
     */
    private AbstractTaskList taskList;

    /**
     * View for creating task.
     */
    private EditTaskView editTaskView;

    /**
     * Ctor.
     * @param taskList list of tasks
     * @param editTaskView view for editing the task
     */
    public EditTaskController(AbstractTaskList taskList, EditTaskView editTaskView) {
        this.taskList = taskList;
        this.editTaskView = editTaskView;
    }

    /**
     * Run controller.
     */
    @Override
    public void run() {
        for ( ; ; ) {
            int actionMenu = editTaskView.printInfo();
            if (actionMenu == -1) {
                break;
            }
            if (actionMenu > 0 && actionMenu <= taskList.size()) {
                int i = 1;
                for (Task task: taskList) {
                    if (actionMenu == i++) {
                        logger.info("user choose task for editing: " + task.toString());
                        int action = editTaskView.printInfoChooseEditing();
                        doEditing(task, action);
                        break;
                    }
                }
            } else {
                System.out.println("You chose an incorrect number of task");
                logger.warn("Entered not existing activity");
            }
        }
    }

    /**
     * Do editing the task.
     * @param task task for editing
     * @param action specified part of task for editing
     */
    private void doEditing(Task task, int action) {
        switch (action) {
            case 1:
                logger.info("User chose editing title");
                editTaskView.editTitle(task);
                break;
            case 2:
                logger.info("User chose editing execution time");
                editTaskView.editTime(task);
                break;
            case 3:
                logger.info("User chose changing status active");
                editTaskView.editActive(task);
                break;
            default:
                System.out.println("You chose an incorrect activity");
                logger.warn("Entered not existing activity");
        }
    }
}
