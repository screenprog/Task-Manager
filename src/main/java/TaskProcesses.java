import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskProcesses {

    private final boolean append = true;


    List<Task> inputTasks()
    {
        List<Task> tasks = new ArrayList<>();
        Scanner write = new Scanner(System.in);
        do{
            String id = "TASK".concat(String.valueOf((int)(Math.random()*1000)));
            System.out.println("Enter Task Details");
            System.out.print("Task name : ");
            String name = write.nextLine();
            System.out.print("Task URL : ");
            String url = write.nextLine();
            System.out.print("Task description : ");
            String description = write.nextLine();
            System.out.print("Task's starting date\nYear : ");
            int year = write.nextInt();
            int month;
            int day;
                do {
                    System.out.print("Month : ");
                    month = write.nextInt();
                }while( month > 12 || month < 1);

                do {
                    System.out.print("Day : ");
                    day = write.nextInt();
                } while (!dayIsValid(year,month,day));

                LocalDate startDate = LocalDate.of(year, month, day);
                System.out.print("Task's ending date\nYear : ");
                year = write.nextInt();
                do {
                    System.out.print("Month : ");
                    month = write.nextInt();
                }while( month > 12 || month < 1);
                do {
                    System.out.print("Day : ");
                    day = write.nextInt();
                } while (!dayIsValid(year,month,day));
                LocalDate endingDate = LocalDate.of(year, month, day);
                Task.Status taskStatus = Task.Status.PENDING;
                Task task = new Task(id, name, url, startDate, endingDate, taskStatus, description);
                tasks.add(task);
                write.nextLine();
            System.out.print("Do you want to more tasks?(y/n) : ");
        }while(write.nextLine().equalsIgnoreCase("y"));
        return tasks;
    }

    private boolean dayIsValid(int year, int month, int day) {
        switch (month)
        {
            case 2:{
                if(IsoChronology.INSTANCE.isLeapYear(year) && day <= 29) {
                    return true;
                }
                if(day <= 28) {
                    return true;
                }
                break;
            }

            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if(day <= 31)
                    return true;
            case 4:
            case 6:
            case 9:
            case 11:
                if(day <= 30)
                    return true;
        }

        return false;
    }


    void appendToFile() {
        File file = new File("tasks.ser");
        List<Task> tasks = this.inputTasks();
        if(!file.exists()){
            try{
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(file.length() == 0)
        {
            try(FileOutputStream fileOutput = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOutput)){
                for (Task task : tasks)
                    out.writeObject(task);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{

            try (FileOutputStream fileOut = new FileOutputStream(file,append);
                 AppendableObjectOutputStream out = new AppendableObjectOutputStream(fileOut)) {
                for (Task task : tasks)
                    out.writeObject(task);
//            System.out.println("Serialized data is saved in tasks.ser");
            } catch (IOException i) {
                throw new RuntimeException(i);
            }
        }
    }

    void replaceOldTasks()
    {
        this.overWriteToFile(this.inputTasks());
    }
    void overWriteToFile(List<Task> tasks) {
        try (FileOutputStream fileOut = new FileOutputStream("tasks.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            for (Task task : tasks)
                out.writeObject(task);
//            System.out.println("Serialized data is saved in tasks.ser");
        } catch (IOException i) {
            throw new RuntimeException(i);
        }
    }

    public void appendCompletedTasks(List<Task> completedTasks)
    {
        File file = new File("CompletedTask.ser");
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (file.length()==0) {
            try (FileOutputStream fos = new FileOutputStream("CompletedTask.ser");
                 ObjectOutputStream out = new ObjectOutputStream(fos)){
                for(Task task : completedTasks)
                    out.writeObject(task);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        else {
            try (FileOutputStream fileOutputStream = new FileOutputStream("CompletedTask.ser",append);
                 AppendableObjectOutputStream  out = new AppendableObjectOutputStream(fileOutputStream)
            ){
                for(Task task : completedTasks)
                    out.writeObject(task);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


//    TODO: Update status of a task
//    TODO: create a separate method to append the file :Done
//    TODO: create a Main class to use the methods and classes :Done
//    TODO: create a Class containing a method to remove or modify a wrong task
}
