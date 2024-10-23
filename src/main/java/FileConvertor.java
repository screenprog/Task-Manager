import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileConvertor {
    void saveInCSV(){
        ReadTasksFromFile readTasksFromFile = new ReadTasksFromFile();
        List<Task> completedTasks = readTasksFromFile.listCompletedTasks();
        List<String> stringFormatted = completedTasks.stream()
                .map(Task::toString)
                .toList();
        this.csvWriter(stringFormatted);

    }

    void csvWriter(List<String> tasks){
        System.out.println("Name your file:");
        String csvFile = new Scanner(System.in).nextLine().concat(".csv");
        File file = new File(csvFile);
        try(PrintWriter in = new PrintWriter(file)){
            String header = "Id, Name, URL, Starting Date, Ending Date, Status, Description";
            in.println(header);
            tasks.stream()
                    .map(this::csvConvertor)
                    .forEach(in::println);
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } finally {
            if(file.exists())
                System.out.println("Your file \""+ csvFile +"\" is created successfully");
            else
                System.err.println("Failed to create a file");
        }

    }
    String csvConvertor(String task){
        String temp = task.replace("|",",")
                .replace("\n", " ")
                .replace("Task Description: ,"," ");

        return Stream.of(temp)
                .collect(Collectors.joining(","));
    }
}
