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
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/**
 * Provides the classes to create and storage tasks.*/
package ua.edu.sumdu.ilchenko.tasks.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Main {
	/**
	 * Entry point to the program.
	 * @param args Arguments of method*/
	public static void main(String[] args) throws IOException, InterruptedException {
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		String[] cls = new String[] {"cmd.exe", "/c", "cls"};
		System.out.println("This is a task manager");
		ArrayTaskList taskList = new ArrayTaskList();
		taskList.add(new Task("A", LocalDateTime.now()));
		taskList.add(new Task("B", LocalDateTime.now()));
		taskList.add(new Task("C", LocalDateTime.now()));
		taskList.add(new Task("D", LocalDateTime.now()));
		taskList.add(new Task("E", LocalDateTime.now()));

		AbstractTaskList taskList1 = new ArrayTaskList();
		System.exit(1);
		System.out.println(taskList1);
		try {
			TaskIO.write(taskList, new FileWriter("test.json"));
			TaskIO.read(taskList1, new FileReader("test.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(taskList1);
		Runtime.getRuntime().exec("cls");
		//testClear();
		/*
		try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out))) {
			out.write("Hello");
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		/*for (Task task: taskList1) {
			System.out.println(task.getTitle());
		}*/

		/*Gson gson = new Gson();
		String json = gson.toJson(taskList);
		System.out.println(json);
		ArrayTaskList taskList1 = new ArrayTaskList();
		taskList1 = gson.fromJson(json, ArrayTaskList.class);
		System.out.println(taskList1);
		System.out.println(gson.toJson(taskList1));*/
		/*System.out.println("Count of tasks = " + taskList.size());
		boolean removing = taskList.remove(new Task("A", 10));
		System.out.println(removing);*/

		/*PipedInputStream in = new PipedInputStream();
		try {
			PipedOutputStream out = new PipedOutputStream(in);
			TaskIO.write(taskList, out);

		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	public static void testClear(){
		char c = '\n';
		int length = 25;
		char[] chars = new char[length];
		Arrays.fill(chars, c);
		System.out.print(String.valueOf(chars));
	}
}
