/*The MIT license
Copyright (c) 2020 Anton Ilchenko

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/**
 * Provides the classes to create and storage tasks.*/
package ua.edu.sumdu.ilchenko.tasks.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class Task implements Cloneable, Serializable {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -1126685771987151546L;

    /**
     * Title of task.
     */
    private String title;

    /**
     * Start working time of repeated task.
     */
    private LocalDateTime start;

    /**
     * End working time of repeated task.
     */
    private LocalDateTime end;

    /**
     * Interval of execution time.
     */
    private int interval;
    /**
     * Define an active or a non-active task.
     */
    private boolean active;

    /**
     * Ctor. for an inactive and non-repeated task.
     * @param title Title of task
     * @param time Executable time of non-repeated task
     */
    public Task(String title, LocalDateTime time) {
        if (time == null) {
            throw new IllegalArgumentException("Time isn't specified");
        }
        this.title = title;
        this.start = time;
        this.end = time;
        this.interval = 0;
        this.active = false;
    }

    /**Ctor. for an inactive and repeated task.
     * @param title title of task
     * @param start start time of interval
     * @param end end time of interval
     * @param interval interval of execution time
     */
    public Task(String title,
                LocalDateTime start,
                LocalDateTime end,
                int interval) {
        if (start == null) {
            throw new IllegalArgumentException("Start time isn't specified");
        }
        if (end == null) {
            throw new IllegalArgumentException("End time isn't specified");
        }
        if (interval < 0) {
            throw new IllegalArgumentException("Interval cannot be negative");
        }
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.active = false;
    }

    /**
     * @return title of task
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return activity of task
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active state of task to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    //<editor-fold desc="methods of unrepeated tasks">
    /**
     * @return execution time of non-repeated task.
     * If task is repeated then returning start time of repetition
     */
    public LocalDateTime getTime() {
        return start;
    }

    /**
     * @param time execution time of non-repeated task to set.
     * If task is repeated then task is transforming to non-repeated
     */
    public void setTime(LocalDateTime time) {
        if (time == null) {
            throw new IllegalArgumentException("Time isn't specified");
        }
        if (isRepeated()) {
            interval = 0;
        }
        start = time;
        end = time;
    }
    //</editor-fold>

    //<editor-fold desc="methods of repeated tasks">
    /**
     * @return start time of interval of repeated task.
     * If task is non-repeated then returning execution time
     */
    public LocalDateTime getStartTime() {
        return start;
    }

    /**
     * @return end time of interval of repeated task.
     * If task is non-repeated then returning execution time
     */
    public LocalDateTime getEndTime() {
        return end;
    }

    /**
     * @return interval with the task will be executed
     */
    public int getRepeatInterval() {
        return interval;
    }

    /**Setter for repeated task.
     * @param start start time of interval
     * @param end end time of interval
     * @param interval period of executable task*/
    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        if (start == null) {
            throw new IllegalArgumentException("Start time isn't specified");
        }
        if (end == null) {
            throw new IllegalArgumentException("End time isn't specified");
        }
        if (interval < 0) {
            throw new IllegalArgumentException("Interval cannot be negative");
        }
        if (!isRepeated()) {
            this.start = start;
            this.end = end;
            this.interval = interval;
        }
    }
    //</editor-fold>

    /**@return state of task (repeated or non-repeated)*/
    public boolean isRepeated() {
        return interval > 0;
    }

    /**
     * Next time of execution task.
     * @param current current time
     * @return execution time of task. (Can be null)
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (!isActive()) {
            return null;
        }

        if (!isRepeated()) {
            if (current.isBefore(start)) {
                return start;
            } else {
                return null;
            }
        } else {
            if (current.isBefore(start)) {
                return start;
            }

            int countInterval = (int) ((current.toEpochSecond(ZoneOffset.UTC)
                    - start.toEpochSecond(ZoneOffset.UTC)) / interval) + 1;
            LocalDateTime nextTime = start.
                    plusSeconds(countInterval * interval);
            if (nextTime.isBefore(end) || nextTime.equals(end)) {
                return nextTime;
            } else {
                return null;
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
        Task task = (Task) o;
        return getTime() == task.getTime()
                && start == task.start
                && end == task.end
                && interval == task.interval
                && isActive() == task.isActive()
                && getTitle().equals(task.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getTitle(),
                getTime(),
                start, end, interval,
                isActive());
    }

    @Override
    public String toString() {
        return "Task{"
                + "title='" + title + '\''
                + ", start=" + start
                + ", end=" + end
                + ", interval=" + interval
                + ", active=" + active
                + '}';
    }

    @Override
    public Task clone() {
        try {
            return (Task) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cannot clone object Task", e);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(getTitle().length());
        out.writeUTF(getTitle());
        out.writeInt(isActive() ? 1 : 0);
        out.writeInt(getRepeatInterval());
        if (getRepeatInterval() > 0) {
            out.writeLong(
                    getStartTime().toEpochSecond(ZoneOffset.UTC)
            );
            out.writeLong(
                    getEndTime().toEpochSecond(ZoneOffset.UTC)
            );
        } else {
            out.writeLong(getTime().toEpochSecond(ZoneOffset.UTC));
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        int titleLength = in.readInt();
        String title = in.readUTF();
        if (titleLength != title.length()) {
            throw new IOException(
                    "Length of title isn't equal "
                            + "to length before serialization");
        }
        this.title = title;
        this.active = in.readInt() == 1;
        this.interval = in.readInt();
        if (interval > 0) {
            this.start = LocalDateTime.ofEpochSecond(in.readLong(), 0, ZoneOffset.UTC);
            this.end = LocalDateTime.ofEpochSecond(in.readLong(), 0, ZoneOffset.UTC);
        } else {
            long temp = in.readLong();
            this.end = LocalDateTime.ofEpochSecond(temp, 0, ZoneOffset.UTC);
        }
    }
}

