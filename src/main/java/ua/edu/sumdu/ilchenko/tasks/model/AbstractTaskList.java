/*
 * The MIT license
 * Copyright (c) 2020 Ilchenko Anton
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package ua.edu.sumdu.ilchenko.tasks.model;

import java.io.Serializable;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Serializable {
    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = 344803398663849451L;

    /**
     * Number of tasks.
     */
    protected int numTasks;

    /**
     * Enum type of list
     */
    protected ListTypes.types listType;

    /**
     * Add task to the list.
     * @param task added task
     */
    public abstract void add(Task task);

    /**
     * Remove a first similar task.
     * @param task searched task for removing
     * @return result of removing
     */
    public abstract boolean remove(Task task);

    /**
     * Count tasks in list.
     * @return number of tasks
     */
    public int size() {
        return numTasks;
    }

    /**
     * Getter task by index.
     * @param index index of array
     * @return returning task
     */
    public abstract Task getTask(int index);

    /**
     * Getter of stream.
     * @return stream
     */
    public abstract Stream<Task> getStream();
}
