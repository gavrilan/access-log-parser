import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Случайное число от 0 до 1: " + Math.random());
        int count = 0;
        while (true) {
            System.out.println("Введите путь к файлу: ");
            String path = new Scanner(System.in).nextLine();
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
        }

    }

}
