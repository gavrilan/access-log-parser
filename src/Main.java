import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
            }
            else {
                if (!fileExist) {
                    System.out.println("Указанный файл не существует");
                    continue;
                }
            }
            System.out.println("Путь указан верно");
            count++;
            System.out.println("Это файл номер " + count);
            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                int min=-1;
                int max=-1;
                int cnt=0;
                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    if (length > 1024) {
                        throw new AccessLogParserException("Есть строка длина которой больше 1024");
                    }
                    cnt++;
                    if (min==-1) {min=length; max=length;}
                    else {
                        if (min>length) min=length;
                        if (max<length) max=length;
                    }
                }
                if (min>=0) System.out.println("Количество строк: " + cnt + ", Минимальная длина строки: " + min + ", Максимальная длина строки: " + max);
                else System.out.println("Количество строк: " + cnt);
            } catch (AccessLogParserException ex1) {
                throw new AccessLogParserException(ex1.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

}
