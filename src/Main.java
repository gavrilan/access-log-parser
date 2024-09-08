import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        String path;
        while (true) {
            System.out.println("Введите путь к файлу: ");
            path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExist = file.exists();
            boolean isDirectory = file.isDirectory();
            if (isDirectory) {
                System.out.println("Это путь к папке");
                continue;
            } else {
                if (!fileExist) {
                    System.out.println("Указанный файл не существует");
                    continue;
                }
            }
            System.out.println("Путь указан верно");
            count++;
            System.out.println("Это файл номер " + count);

//            path = "/Users/deathstalker/Downloads/access.log";

            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                Statistics statistics = new Statistics();
                LogEntry logEntry;
                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    if (length > 1024) {
                        throw new AccessLogParserException("Есть строка длина которой больше 1024");
                    }
                    logEntry = new LogEntry(line);
                    statistics.addEntry(logEntry);
                }

                System.out.println("total: " + statistics.getTotalTraffic());
                System.out.println("minTime: " + statistics.getMinTime());
                System.out.println("maxTime: " + statistics.getMaxTime());

                System.out.println("TrafficRate: " + statistics.getTrafficRate());

            } catch (AccessLogParserException ex1) {
                throw new AccessLogParserException(ex1.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
//            break;
        }

    }

}
