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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ActionsWithTaskView implements IView {
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(ActionsWithTaskView.class);

    /**
     * Console reader.
     */
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Print to console available actions with tasks.
     * @return action that user performed
     */
    @Override
    public int printInfo() {
        System.out.println("Available actions with task:");
        System.out.println(" 1 | add a new task");
        System.out.println(" 2 | remove one of tasks");
        System.out.println(" 3 | edit one of tasks");
        System.out.println("-1 | back");
        int action;
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
}
