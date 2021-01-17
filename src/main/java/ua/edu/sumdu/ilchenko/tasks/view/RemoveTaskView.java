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
import ua.edu.sumdu.ilchenko.tasks.view.utils.StringsView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RemoveTaskView implements IView {
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(RemoveTaskView.class);

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
     * @param taskListView view of tasks
     */
    public RemoveTaskView(IView taskListView) {
        this.taskListView = taskListView;
    }

    /**
     * Print to console info about removing and available tasks for removing.
     * @return action that user performed
     */
    @Override
    public int printInfo() {
        logger.info("Showing menu for removing task");
        System.out.println();
        System.out.println("-----------------");
        System.out.println("| Removing task |");
        System.out.println("-----------------");
        System.out.println(" n | n - number of a task for removing");
        System.out.println("-1 | back");
        taskListView.printInfo();

        int action = 0;
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
}
