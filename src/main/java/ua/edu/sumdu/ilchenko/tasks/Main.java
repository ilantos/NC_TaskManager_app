package ua.edu.sumdu.ilchenko.tasks;

import org.apache.log4j.Logger;
import ua.edu.sumdu.ilchenko.tasks.controller.EditTaskController;
import ua.edu.sumdu.ilchenko.tasks.controller.MainController;
import ua.edu.sumdu.ilchenko.tasks.controller.TaskListController;
import ua.edu.sumdu.ilchenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.ilchenko.tasks.model.ArrayTaskList;
import ua.edu.sumdu.ilchenko.tasks.model.Task;
import ua.edu.sumdu.ilchenko.tasks.model.TaskIO;
import ua.edu.sumdu.ilchenko.tasks.view.*;

import java.io.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("This is Task Manager app");
        AbstractTaskList taskList = new ArrayTaskList();

        String pathList = "list.json";
        File fileList = new File(pathList);
        if(!fileList.exists()) {
            taskList.add(new Task("task A", LocalDateTime.now()));
            taskList.add(new Task("task B", LocalDateTime.now()));
            TaskIO.writeText(taskList, fileList);
        } else {
            TaskIO.readText(taskList, fileList);
        }
        //

        /*IView menu = new MenuView();
        MainController mainController = new MainController(taskList, menu);
        mainController.run();*/

        TaskListView taskListView = new TaskListView(taskList);
        /*ActionsWithTaskView actionsWithTaskView = new ActionsWithTaskView();
        TaskListController taskListController = new TaskListController(taskList, actionsWithTaskView, taskListView);
        taskListController.run();*/

        EditTaskView editView = new EditTaskView(taskListView);
        EditTaskController editController = new EditTaskController(taskList, editView);
        editController.run();

        TaskIO.writeText(taskList, fileList);
    }
}
