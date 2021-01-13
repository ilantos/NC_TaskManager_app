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
import ua.edu.sumdu.ilchenko.tasks.view.CreateTaskView;
import ua.edu.sumdu.ilchenko.tasks.view.EditTaskView;
import ua.edu.sumdu.ilchenko.tasks.view.IView;
import ua.edu.sumdu.ilchenko.tasks.view.RemoveTaskView;

import java.util.HashMap;
import java.util.Map;

public class TaskListController implements IController {
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(TaskListController.class);

    /**
     * List of tasks.
     */
    private AbstractTaskList taskList;

    /**
     * Available actions with tasks view.
     */
    private IView actionsWithTaskView;

    /**
     * task list view.
     */
    private IView taskListView;

    /**
     * Possible using controllers from this controller
     */
    private Map<Integer, IController> controllers = new HashMap<>();

    /**
     * Ctor.
     * <p></p>
     * @param taskList list of tasks
     * @param actionsWithTaskView available actions with tasks
     * @param taskListView view for tasks
     */
    public TaskListController(AbstractTaskList taskList, IView actionsWithTaskView, IView taskListView) {
        this.taskList = taskList;
        this.actionsWithTaskView = actionsWithTaskView;
        this.taskListView = taskListView;

        controllers.put(1, new CreateTaskController(taskList, new CreateTaskView()));
        controllers.put(2, new RemoveTaskController(taskList, new RemoveTaskView(taskListView)));
        controllers.put(3, new EditTaskController(taskList, new EditTaskView(taskListView)));
    }

    /**
     * Run controller.
     */
    @Override
    public void run() {
        for ( ; ; ) {
            taskListView.printInfo();
            int action = actionsWithTaskView.printInfo();
            logger.info("Chosen action: " + action);
            if (action == -1) {
                break;
            }
            if (controllers.containsKey(action)) {
                controllers.get(action).run();
            } else {
                //Нужно ли выводить эти данные в контроллере? Или это во вью нужно выводить?
                System.out.println("You entered not existing activity");
                logger.warn("Entered not existing activity");
            }
        }
    }
}
