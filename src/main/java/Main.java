import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String format = "%5s   %s";
        while(true){
            System.out.printf(format,"1.", "Add a new task\n");
            System.out.printf(format,"2.","List pending tasks\n");
            System.out.printf(format, "3.","List completed tasks\n");
            System.out.printf(format,"4.","Update the status of a task\n");
            System.out.printf(format,"5.","Save completed tasks into a \".csv\" file\n");
            System.out.printf(format,"0.","Exit\n");
            System.out.print("Select an operation: ");
            Scanner in = new Scanner(System.in);
            int ch = Integer.parseInt(in.nextLine());
            switch(ch)
            {
                case 0 -> System.exit(1);
                case 1 -> new TaskProcesses().appendToFile();
                case 2 -> {
                    List<Task> pendingTasks = new ReadTasksFromFile().listPendingTasks();
                    if(pendingTasks.isEmpty())
                        System.out.println("No pending task");
                    else{
                        System.out.println("Pending tasks: ");
                        pendingTasks.forEach(System.out::println);
                    }
                }
                case 3 -> {
                    List<Task> completedTasks = new ReadTasksFromFile().listCompletedTasks();
                    if(completedTasks.isEmpty())
                        System.err.println("No task is completed.");
                    else{
                        System.out.println("Completed tasks: ");
                        completedTasks.forEach(System.out::println);
                    }
                }
                case 4 -> new ReadTasksFromFile().updateTaskStatus();
                case 5 -> new FileConvertor().saveInCSV();
                default -> System.out.println("Wrong input.");
            }
        }


    }

}


