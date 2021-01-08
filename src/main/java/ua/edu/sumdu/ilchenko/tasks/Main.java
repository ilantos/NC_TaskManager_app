package ua.edu.sumdu.ilchenko.tasks;

import org.apache.log4j.Logger;
import ua.edu.sumdu.ilchenko.tasks.controller.MainController;
import ua.edu.sumdu.ilchenko.tasks.controller.TaskListController;
import ua.edu.sumdu.ilchenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.ilchenko.tasks.model.ArrayTaskList;
import ua.edu.sumdu.ilchenko.tasks.model.Task;
import ua.edu.sumdu.ilchenko.tasks.view.ActionsWithTaskView;
import ua.edu.sumdu.ilchenko.tasks.view.IView;
import ua.edu.sumdu.ilchenko.tasks.view.MenuView;
import ua.edu.sumdu.ilchenko.tasks.view.TaskListView;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args){
        System.out.println("This is Task Manager app");
        AbstractTaskList taskList = new ArrayTaskList();
        taskList.add(new Task("task A", LocalDateTime.now()));
        taskList.add(new Task("task B", LocalDateTime.now()));
        /*IView menu = new MenuView();
        MainController mainController = new MainController(taskList, menu);
        mainController.run();*/
        TaskListView taskListView = new TaskListView(taskList);
        ActionsWithTaskView actionsWithTaskView = new ActionsWithTaskView();
        TaskListController taskListController = new TaskListController(taskList, actionsWithTaskView, taskListView);
        taskListController.run();
    }
}
