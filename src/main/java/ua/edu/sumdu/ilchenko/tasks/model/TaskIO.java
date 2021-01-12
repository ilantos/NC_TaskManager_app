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

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.*;

public final class TaskIO {
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(TaskIO.class);

    private TaskIO() {
    }

    /**
     * Read tasks in binary from input stream and add to list of tasks.
     * @param tasks list where tasks add
     * @param in input stream
     */
    public static void read(AbstractTaskList tasks, InputStream in) {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(in))) {
            logger.info("Start reading tasks in binary...");
            int numberTasks = ois.readInt();
            for (int i = 0; i < numberTasks; i++) {
                tasks.add((Task) ois.readObject());
            }
            logger.info("Reading tasks successfully ended");
        } catch (ClassNotFoundException e) {
            logger.error("Some error with reading tasks", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("Some error with reading tasks", e);
            e.printStackTrace();
        }
    }

    /**
     * Read tasks in text from reader and add to list of tasks.
     * @param tasks list where tasks add
     * @param in reader
     */
    public static void read(AbstractTaskList tasks, Reader in) {
        try (BufferedReader br = new BufferedReader(in)) {
            logger.info("Start reading tasks in json...");
            Gson gson = new Gson();
            String json = br.readLine();
            AbstractTaskList readTasks = gson.fromJson(json,
                    TaskListFactory.createTaskList(tasks.listType).getClass());
            for (Task task: readTasks) {
                tasks.add(task);
            }
            logger.info("Reading tasks successfully ended");
        } catch (IOException e) {
            logger.error("Some error with reading tasks", e);
            e.printStackTrace();
        }
    }

    /**
     * Read tasks in binary from file and write them down to list.
     * @param tasks list where tasks add
     * @param file file where read tasks
     */
    public static void readBinary(AbstractTaskList tasks, File file) {
        try (FileInputStream fis = new FileInputStream(file)){
            read(tasks, fis);
            logger.info("Reading tasks from file successfully ended");
        } catch (FileNotFoundException e) {
            logger.error("Some error with reading tasks", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("Some error with reading tasks", e);
            e.printStackTrace();
        }
    }

    /**
     * Read tasks in text from file and write them down to list.
     * @param tasks list where tasks add
     * @param file file where read tasks
     */
    public static void readText(AbstractTaskList tasks, File file) {
        try (FileReader fr = new FileReader(file)) {
            read(tasks, fr);
            logger.info("Reading tasks from file successfully ended");
        } catch (FileNotFoundException e) {
            logger.error("Some error with reading tasks", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("Some error with reading tasks", e);
            e.printStackTrace();
        }
    }

    /**
     * Write tasks in binary of the list to the output stream.
     * @param tasks list of tasks
     * @param out output stream
     */
    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (ObjectOutputStream bos = new ObjectOutputStream(new BufferedOutputStream(out))) {
            bos.writeInt(tasks.numTasks);
            for (Task task: tasks) {
                bos.writeObject(task);
            }
            logger.info("Writing tasks successfully ended");
        } catch (IOException e) {
            logger.error("Some error with writing tasks", e);
            e.printStackTrace();
        }
    }

    /**
     * Write tasks in text of the list to the writer.
     * @param tasks list of tasks
     * @param out writer
     */
    public static void write(AbstractTaskList tasks, Writer out) {
        try (BufferedWriter bw = new BufferedWriter(out)){
            Gson gson = new Gson();
            String tasksJson = gson.toJson(tasks);
            bw.write(tasksJson);
            logger.info("Writing tasks successfully ended");
        } catch (IOException e) {
            logger.error("Some error with writing tasks", e);
            e.printStackTrace();
        }
    }

    /**
     * Write down tasks in binary from the list to the file.
     * @param tasks tasks to writing down
     * @param file file where write down tasks
     */
    public static void writeBinary(AbstractTaskList tasks, File file) {
        try (FileOutputStream fos = new FileOutputStream(file)){
            write(tasks, fos);
            logger.info("Writing tasks in file successfully ended");
        } catch (FileNotFoundException e) {
            logger.error("Some error with writing tasks", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("Some error with writing tasks", e);
            e.printStackTrace();
        }
    }

    /**
     * Write down tasks in text from the list to the file.
     * @param tasks tasks to writing down
     * @param file file where write down tasks
     */
    public static void writeText(AbstractTaskList tasks, File file) {
        try (FileWriter fw = new FileWriter(file)) {
            write(tasks, fw);
            logger.info("Writing tasks in file successfully ended");
        } catch (IOException e) {
            logger.error("Some error with writing tasks", e);
            e.printStackTrace();
        }
    }
}

