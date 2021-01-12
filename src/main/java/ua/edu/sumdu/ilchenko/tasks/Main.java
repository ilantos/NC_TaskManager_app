package ua.edu.sumdu.ilchenko.tasks;

import org.apache.log4j.Logger;
import ua.edu.sumdu.ilchenko.tasks.controller.CalendarController;
import ua.edu.sumdu.ilchenko.tasks.controller.EditTaskController;
import ua.edu.sumdu.ilchenko.tasks.controller.MainController;
import ua.edu.sumdu.ilchenko.tasks.controller.TaskListController;
import ua.edu.sumdu.ilchenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.ilchenko.tasks.model.ArrayTaskList;
import ua.edu.sumdu.ilchenko.tasks.model.Task;
import ua.edu.sumdu.ilchenko.tasks.model.TaskIO;
import ua.edu.sumdu.ilchenko.tasks.notification.NotificationManager;
import ua.edu.sumdu.ilchenko.tasks.notification.TrayNotification;
import ua.edu.sumdu.ilchenko.tasks.view.*;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("This is Task Manager app");
        AbstractTaskList taskList = new ArrayTaskList();

        String pathList = "list.json";
        File fileList = new File(pathList);
        LocalDateTime now = LocalDateTime.now();
        if(!fileList.exists()) {
            int hour = 60 * 60;
            taskList.add(new Task("task A", now.plusMinutes(1)));
            taskList.add(new Task("task A2", now.plusMinutes(2)));
            taskList.add(new Task("task B", now.plusDays(1)));
            taskList.add(new Task("task C", now.plusDays(1), now.plusDays(10), 20 * hour));
            taskList.add(new Task("task D", now.plusDays(2), now.plusDays(3), 10 * hour));
            taskList.add(new Task("task E", now.plusDays(2)));
            taskList.add(new Task("task F", now.plusDays(3), now.plusDays(15), 100 * hour));
            taskList.add(new Task("task G", now.plusDays(7)));
            taskList.add(new Task("task H", now.plusDays(7), now.plusDays(17), 20 * hour));
            taskList.add(new Task("task I", now.plusDays(10), now.plusDays(18), 10 * hour));
            taskList.add(new Task("task K", now.plusDays(12)));
            taskList.add(new Task("task L", now.plusDays(13), now.plusDays(14), 100 * hour));
            TaskIO.writeText(taskList, fileList);
        } else {
            TaskIO.readText(taskList, fileList);
        }
        /*TrayNotification trayNotification = new TrayNotification();
        Set<Task> taskSet = new HashSet<>();
        taskSet.add(new Task("task A", now));
        trayNotification.notify(taskSet);*/
        NotificationManager notificationManager = new NotificationManager(taskList, new TrayNotification());
        notificationManager.setDaemon(true);
        notificationManager.start();
        //
        /*for (Task task: taskList) {
            task.setActive(true);
        }*/
        /*IView menu = new MenuView();
        MainController mainController = new MainController(taskList, menu);
        mainController.run();*/

        /*TaskListView taskListView = new TaskListView(taskList);
        taskListView.printInfo();*/
        /*ActionsWithTaskView actionsWithTaskView = new ActionsWithTaskView();
        TaskListController taskListController = new TaskListController(taskList, actionsWithTaskView, taskListView);
        taskListController.run();*/

        /*EditTaskView editView = new EditTaskView(taskListView);
        EditTaskController editController = new EditTaskController(taskList, editView);
        editController.run();*/

        //CalendarView calendarView = new CalendarView();
        /*TreeMap<LocalDateTime, Set<Task>> test = new TreeMap<>();
        HashSet<Task> tasks = new HashSet<>();
        LocalDateTime now = LocalDateTime.now();
        tasks.add(new Task("task A", now));
        tasks.add(new Task("task B", now));
        test.put(now, tasks);
        calendarView.printCalendar(test);*/

        /*CalendarController calendarController = new CalendarController(taskList, calendarView);
        calendarController.run();*/
        MenuView view = new MenuView();
        MainController mainController = new MainController(taskList, view);
        mainController.run();

        TaskIO.writeText(taskList, fileList);
    }
}
