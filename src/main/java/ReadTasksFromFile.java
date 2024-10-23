import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadTasksFromFile {

    List<Task> listPendingTasks() {
        List<Task> tasks = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream("tasks.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            while (true) {
                try {
                    Task task = (Task) in.readObject();
                    tasks.add(task);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException i) {
            System.err.println("Tasks file does not exist");
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Task class not found");
            throw new RuntimeException(c);
        }
        return tasks;
    }

    List<Task> listCompletedTasks(){
        List<Task> tasks = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream("CompletedTask.ser");
        ObjectInputStream read = new ObjectInputStream(fis)){
           while(true){
               try{
                   Task task = (Task) read.readObject();
                   tasks.add(task);
               } catch (EOFException e) {
                   break;
               } catch (ClassNotFoundException e) {
                   throw new RuntimeException(e);
               }
           }
        } catch (FileNotFoundException e) {
            System.err.println("Completed tasks file does not exist");
            e.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
        return tasks;
    }

    void updateTaskStatus() {
        Scanner write = new Scanner(System.in);
        List<Task> tasks = this.listPendingTasks();
        tasks.forEach(System.out::println);
        System.out.println("To update task status");
        System.out.print("Select a task by it's id : ");
        String taskID = write.nextLine();
        String taskStatus;
        do{
        System.out.print("Status (Completed / Pending) : ");
        taskStatus = write.nextLine();
        }
        while(!(taskStatus.equalsIgnoreCase(Task.Status.COMPLETED.name()) || taskStatus.equalsIgnoreCase(Task.Status.PENDING.name())));

        final String finalTaskStatus = taskStatus;
            tasks.forEach(task ->
                    {
                        if (task.getId().equalsIgnoreCase(taskID) && finalTaskStatus.equalsIgnoreCase(Task.Status.COMPLETED.name()))
                            task.setTaskStatus(Task.Status.COMPLETED);
                    }
            );

        List<Task> pendingTasks = tasks.stream()
                .filter(task -> task.getTaskStatus().equals(Task.Status.PENDING))
                .toList();
        List<Task> completedTasks = tasks.stream()
                .filter(task -> task.getTaskStatus().equals(Task.Status.COMPLETED))
                .toList();
        TaskProcesses taskProcesses = new TaskProcesses();
        taskProcesses.overWriteToFile(pendingTasks);
        taskProcesses.appendCompletedTasks(completedTasks);
        System.out.println("Status updated successfully");
    }
}
