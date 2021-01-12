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
import java.util.Objects;
import java.util.stream.Stream;

public class LinkedTaskList extends AbstractTaskList implements Iterable<Task>, Cloneable, Serializable {
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(LinkedTaskList.class);

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -8569698506612377146L;

    /**
     * Last task of linked list.
     */
    private Node lastNode;
    /**
     * First task of linked list.
     */
    private Node firstNode;

    /**
     * Nested class for creating node of linked list.
     */
    private class Node implements Serializable {
        /**
         * Serialization marker.
         */
        private static final long serialVersionUID = 1765011183767302240L;

        /**
         * Task of node.
         */
        private Task task;

        /**
         * Next node of list.
         */
        private Node next;

        Node(Task task,  Node next) {
            this.task = task;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return Objects.equals(task, node.task)
                    && Objects.equals(next, node.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(task, next);
        }

        @Override
        public Node clone() throws CloneNotSupportedException {
            return (Node) super.clone();
        }

    }

    /**
     * Ctor.
     */
    public LinkedTaskList() {
        listType = ListTypes.types.LINKED;
        numTasks = 0;
        logger.info("Initial linked list is created");
    }

    /**
     * Ctor.
     * <p>Create list with tasks in array</p>
     * @param tasks array of tasks
     */
    public LinkedTaskList(Task[] tasks) {
        listType = ListTypes.types.LINKED;
        for (int i = 0; i < tasks.length; i++) {
            this.add(tasks[i]);
        }
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            logger.warn("Task didn't add");
            throw new IllegalArgumentException("Task cannot be null");
        }
        if (numTasks == 0) {
            firstNode = new Node(task, null);
            lastNode = firstNode;
        } else {
            Node newNode = new Node(task, null);
            lastNode.next = newNode;
            lastNode = newNode;
        }
        numTasks++;
        logger.info("Task is added to list");
    }

    @Override
    public boolean remove(Task task) {
        Node prev = null;
        Node current = firstNode;

        while (current != null) {
            if (task.equals(current.task)) {
                if (prev == null) {
                    if (current.next == null) {
                        firstNode = null;
                        lastNode = null;
                        numTasks = 0;
                        return true;
                    }
                    firstNode = current.next;
                } else {
                    prev.next = current.next;
                    if (current.next == null) {
                        lastNode = prev;
                    }
                }
                numTasks--;
                logger.info("Task is removed from list");
                return true;
            }
            prev = current;
            current = current.next;
        }
        logger.warn("Task isn't removed from list");
        return false;
    }

    @Override
    public Task getTask(int index) {
        if (index < 0 && index >= numTasks) {
            throw new IndexOutOfBoundsException("Task by index doesn't exist");
        }

        int i = 0;
        Node current = firstNode;
        while (i != index) {
            current = current.next;
            i++;
        }
        return current.task;
    }

    /**
     * Getter of stream.
     * @return stream
     */
    @Override
    public Stream<Task> getStream() {
        Task[] array = new Task[numTasks];
        int i = 0;
        for (Task task: this) {
            array[i] = task;
            i++;
        }
        return Arrays.stream(array);
    }

    @Override
    public Iterator<Task> iterator() {
        return new LinkedTaskListIterator();
    }

    private class LinkedTaskListIterator implements Iterator<Task> {
        /**
         * Next node.
         */
        private Node nextNode;

        /**
         * Next index of list item.
         */
        private int nextIndex;

        /**
         * Existence next node.
         */
        private boolean isNext;

        LinkedTaskListIterator() {
            nextNode = firstNode;
            nextIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < numTasks && nextNode != null;
        }

        @Override
        public Task next() {
            Task nextTask = nextNode.task;
            nextNode = nextNode.next;
            nextIndex++;
            isNext = true;
            return nextTask;
        }

        @Override
        public void remove() {
            if (isNext) {
                LinkedTaskList.this.remove(getTask(--nextIndex));
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
        LinkedTaskList that = (LinkedTaskList) o;
        return Objects.equals(lastNode, that.lastNode)
                && Objects.equals(firstNode, that.firstNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastNode, firstNode);
    }

    @Override
    public LinkedTaskList clone() {
        try {
            LinkedTaskList clone = (LinkedTaskList) super.clone();
            clone.firstNode = null;
            clone.lastNode = null;
            clone.numTasks = 0;

            for (Node node = this.firstNode; node != null; node = node.next) {
                clone.add(node.task);
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cannot clone LinkedTaskList", e);
        }
    }
}
