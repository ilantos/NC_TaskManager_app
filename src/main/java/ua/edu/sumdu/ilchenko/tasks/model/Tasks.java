/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 Anton Ilchenko
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

package ua.edu.sumdu.ilchenko.tasks.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public final class Tasks {

    private Tasks() {
    }
    /**
     * Get tasks that are included in the specified interval date.
     * @param tasks iterator of tasks list
     * @param start start of interval
     * @param end end of interval
     * @return iterable of incoming tasks
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks,
                                          LocalDateTime start,
                                          LocalDateTime end) {

        LinkedTaskList result = new LinkedTaskList();
        LocalDateTime income;
        for (Task task : tasks) {
            income = task.nextTimeAfter(start);
            if (income == null) {
                continue;
            }
            if ((income.isAfter(start) || income.isEqual(start))
                    && (income.isBefore(end) || income.isEqual(end))) {
                result.add(task);
            }
        }
        return result;
    }

    /**
     * Calendar of tasks in interval.
     * @param tasks tasks for selection
     * @param start start of interval
     * @param end end of interval
     * @return map with time and execution task
     */
    public static SortedMap<LocalDateTime, Set<Task>> calendar(
            Iterable<Task> tasks,
            LocalDateTime start,
            LocalDateTime end) {

        TreeMap<LocalDateTime, Set<Task>> result = new TreeMap<>();

        LocalDateTime nextTime;
        LocalDateTime startSearch = start;
        for (Task task : tasks) {
            while ((nextTime = task.nextTimeAfter(startSearch)) != null) {
                if (!(nextTime.isAfter(start) || nextTime.isEqual(start))
                        || !(nextTime.isBefore(end) || nextTime.isEqual(end))) {
                    break;
                }
                if (!result.containsKey(nextTime)) {
                    result.put(nextTime, new HashSet<>());
                }
                result.get(nextTime).add(task);
                startSearch = startSearch.plusSeconds(1);
            }
            startSearch = start;
        }

        return result;
    }
}
