import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Question2 {

    public static List<String> versionDefault(List<String> list) {
        return list.stream()
                .map(str -> str.replace(',', '@'))
                .map(str -> str.replace("\t", ","))
                .toList();
    }

    public static List<String> versionA(List<String> list) {
        return list.stream()
                .filter(str -> !str.startsWith("#"))
                .toList();
    }

    public static List<String> versionB(List<String> list) {
        return list.stream()
                .map(str -> str.replace(",", "\t"))
                .map(str -> str.replace("@", ","))
                .toList();
    }

    public static List<String> versionE(List<String> list) {
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        targetFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return list.stream()
                .map(str -> {
                    String[] columns = str.split(",", 2);
                    if (columns.length < 1) {
                        return str;
                    } else {
                        try {
                            double timestamp = Double.parseDouble(columns[0]);
                            long seconds = (long) timestamp;
                            Date date = new Date(seconds * 1000);
                            columns[0] = targetFormat.format(date);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        return String.join(",", columns);
                    }
                })
                .toList();
    }

    public static void saveFile(List<String> list, String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for (String str : list) {
            writer.write(str);
            writer.newLine(); // 줄바꿈
        }
        writer.close();
    }

    public static List<String> readFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines().toList();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) throws IOException {
        String inputPath = "quiz2/http.log.sample";
        String outputPath = "quiz2/quiz2_output.txt";
        List<String> logList = readFile(inputPath);
        List<String> response;
        response = versionDefault(logList);
        response = versionA(response);
//        response = versionB(response);
        response = versionE(response);
        saveFile(response, outputPath);

    }
}
