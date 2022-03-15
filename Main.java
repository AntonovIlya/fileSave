package fileSave;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private static int count = 0;
    private static List<String> objects = new ArrayList<>();

    public static void main(String[] args) {
        GameProgress[] gameProgresses = {
                new GameProgress(75, 2, 7, 34.24),
                new GameProgress(75, 3, 8, 38.24),
                new GameProgress(46, 7, 12, 90),
        };

        for (GameProgress games : gameProgresses) {
            String path = "C://SomeDir//saveGames//save" + ++count + ".dat";
            saveGame(path, games);
            objects.add(path);
        }
        zipFiles("C://SomeDir//saveGames//zip.zip", objects);


        for (String s : objects) {
            File file = new File(s);
            if (file.delete()) System.out.println("Файл " + s + " удалён");
        }
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> objects) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(path))) {
            for (String s : objects) {
                try (FileInputStream fileInputStream = new FileInputStream(s)) {
                    ZipEntry zipEntry = new ZipEntry(s);
                    zipOutputStream.putNextEntry(zipEntry);
                    byte[] buffer = new byte[fileInputStream.available()];
                    fileInputStream.read(buffer);
                    zipOutputStream.write(buffer);
                    zipOutputStream.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
