package ua.edu.sumdu.ilchenko.tasks;

import ua.edu.sumdu.ilchenko.tasks.controller.MainController;
import ua.edu.sumdu.ilchenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.ilchenko.tasks.model.ArrayTaskList;
import ua.edu.sumdu.ilchenko.tasks.model.Task;
import ua.edu.sumdu.ilchenko.tasks.model.TaskIO;
import ua.edu.sumdu.ilchenko.tasks.notification.NotificationManager;
import ua.edu.sumdu.ilchenko.tasks.notification.TrayNotification;
import ua.edu.sumdu.ilchenko.tasks.utils.Configuration;
import ua.edu.sumdu.ilchenko.tasks.view.*;

import java.io.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        AbstractTaskList taskList = new ArrayTaskList();

        String pathList = Configuration.PATH_STORE_TASKS;
        File fileList = new File(pathList);
        LocalDateTime now = LocalDateTime.now();
        if(!fileList.exists()) {
            //Creating test tasks
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

        NotificationManager notificationManager = new NotificationManager(taskList, new TrayNotification());
        notificationManager.setDaemon(true);
        notificationManager.start();

        MenuView view = new MenuView();
        view.printMessage("This is a task manager app");
        MainController mainController = new MainController(taskList, view);
        mainController.run();

        System.exit(1);
    }
}
