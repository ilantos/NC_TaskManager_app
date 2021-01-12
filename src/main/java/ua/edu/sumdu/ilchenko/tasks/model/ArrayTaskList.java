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

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

public class ArrayTaskList extends AbstractTaskList implements Iterable<Task>, Cloneable, Serializable {
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(ArrayTaskList.class);

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -7248470779114965032L;

    /**
     * Tasks of list.
     */
    private Task[] tasks;

    /**
     * Number of increasing length of tasks array.
     */
    private static final double INCREASING_ARRAY_LENGTH = 1.5;

    /**
     * Ctor.
     */
    public ArrayTaskList() {
        listType = ListTypes.types.ARRAY;
        tasks = new Task[10];
        logger.info("Initial array list is created");
    }

    /**
     * Ctor.
     * <p>Create list with tasks in array</p>
     * @param tasks array of tasks
     */
    public ArrayTaskList(Task[] tasks) {
        listType = ListTypes.types.ARRAY;
        this.tasks = tasks;
        this.numTasks = tasks.length;
    }

    /**
     * Add task to the list.
     * @param task added task
     */
    public void add(Task task) {
        if (task == null) {
            logger.warn("Task didn't add");
            throw new IllegalArgumentException("Task cannot be null");
        }
        if (numTasks == tasks.length) {
            long lengthNewArray = (long) (tasks.length * INCREASING_ARRAY_LENGTH);

            if (lengthNewArray > Integer.MAX_VALUE) {
                lengthNewArray = Integer.MAX_VALUE;
            }
            if (numTasks == Integer.MAX_VALUE) {
                logger.warn("Task didn't add");
                throw new ArrayIndexOutOfBoundsException(
                        "Too many tasks on the list. Create new list");
            }

            Task[] newArray = new Task[(int) lengthNewArray];
            System.arraycopy(tasks, 0, newArray, 0, numTasks);
            tasks = newArray;
        }
        tasks[numTasks] = task;
        numTasks++;
        logger.info("Task is added to list");
    }

    /**
     * Remove a first similar task.
     * @param task searched task for removing
     * @return result of removing
     */
    public boolean remove(Task task) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i].equals(task)) {
                Task[] newArray = new Task[tasks.length - 1];
                System.arraycopy(tasks, 0, newArray, 0, i);
                if (i != tasks.length - 1) {
                    System.arraycopy(tasks, i + 1, newArray, i, tasks.length - (i + 1));
                }
                tasks = newArray;
                numTasks--;
                logger.info("Task is removed from list");
                return true;
            }
        }
        logger.warn("Task isn't removed from list");
        return false;
    }

    /**
     * Getter task by index.
     * @param index index of array
     * @return returning task
     */
    public Task getTask(int index) {
        if (index < 0 && index >= numTasks) {
            throw new IndexOutOfBoundsException("Task by index doesn't exist");
        }
        return tasks[index];
    }

    /**
     * Getter of stream.
     * @return stream
     */
    @Override
    public Stream<Task> getStream() {
        return Arrays.stream(Arrays.copyOf(tasks, numTasks));
    }

    /**
     * Getter of iterator.
     */
    @Override
    public Iterator<Task> iterator() {
        return new ArrayTaskListIterator();
    }

    private class ArrayTaskListIterator implements Iterator<Task> {
        /**
         * Index of next item.
         */
        private int nextIndex;

        /**
         * Existence of next item.
         */
        private boolean isNext;

        public ArrayTaskListIterator() {
            nextIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < numTasks;
        }

        @Override
        public Task next() {
            Task nextTask = tasks[nextIndex++];
            isNext = true;
            return nextTask;
        }

        @Override
        public void remove() {
            if (isNext) {
                ArrayTaskList.this.remove(getTask(--nextIndex));
                isNext = false;
            } else {
                throw new IllegalStateException("Cannot use remove()"
                        + " without using next() before");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArrayTaskList taskList = (ArrayTaskList) o;
        return Arrays.equals(tasks, taskList.tasks);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tasks);
    }

    @Override
    public ArrayTaskList clone() {
        try {
            ArrayTaskList result = (ArrayTaskList) super.clone();
            result.tasks = tasks.clone();
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cannot clone ArrayTaskList", e);
        }
    }
}
