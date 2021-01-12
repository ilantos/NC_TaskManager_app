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

package ua.edu.sumdu.ilchenko.tasks.notification;

import org.apache.log4j.Logger;
import ua.edu.sumdu.ilchenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.ilchenko.tasks.model.Task;
import ua.edu.sumdu.ilchenko.tasks.view.CalendarView;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

public class NotificationManager extends Thread{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(NotificationManager.class);

    /**
     * List of tasks.
     */
    private AbstractTaskList taskList;

    /**
     * Notificator.
     */
    private INotification notificator;

    /**
     * Ctor.
     * @param taskList tasks
     */
    public NotificationManager(AbstractTaskList taskList, INotification notificator) {
        this.taskList = taskList;
        this.notificator = notificator;
    }

    /**
     * Run checking tasks to notify.
     */
    @Override
    public void run() {
        while (true) {
            logger.info("notification working...");
            int requiredDelta = 60;
            LocalDateTime now = LocalDateTime.now();
            Set<Task> tasksToNotify = new HashSet<>();
            for (Task task: taskList) {
                LocalDateTime nextTime = task.nextTimeAfter(now);
                if (nextTime == null) {
                    logger.debug("Not checking for notify: " + task.toString());
                    continue;
                }
                long delta = task.nextTimeAfter(now).toEpochSecond(ZoneOffset.UTC)
                        - now.toEpochSecond(ZoneOffset.UTC);
                logger.debug(task.getTitle() + ": seconds to notify = " + delta);
                if (task.isActive() && Math.abs(delta) <= requiredDelta) {
                    tasksToNotify.add(task);
                }
            }
            if (!tasksToNotify.isEmpty()){
                notificator.notify(tasksToNotify);
            }

            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
