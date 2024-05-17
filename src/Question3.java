import java.io.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Question3 {
    public static List<String> readFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines().toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void writeLine(BufferedWriter writer, String line) throws IOException {
        if (line != null && !line.isEmpty()) {
            writer.write(line);
            if (!line.endsWith(".") && !line.endsWith("!") && !line.endsWith("?")) {
                writer.write(" ");
            } else {
                writer.newLine();
            }
        }
    }

    public static void writeFile(List<String> file1, List<String> file2, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            int maxLength = Math.max(file1.size(), file2.size());

            for (int i = 0; i < maxLength; i++) {
                if (i < file1.size()) {
                    writeLine(writer, file1.get(i));
                }
                if (i < file2.size()) {
                    writeLine(writer, file2.get(i));
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void main(String[] args) {
        // 파일 경로 설정
        String path1 = "quiz3/file1.txt";
        String path2 = "quiz3/file2.txt";
        String outputPath = "quiz/output.txt";

        ExecutorService executor = Executors.newFixedThreadPool(3);

        CompletableFuture<List<String>> futureFile1 = CompletableFuture.supplyAsync(() -> readFile(path1), executor);
        CompletableFuture<List<String>> futureFile2 = CompletableFuture.supplyAsync(() -> readFile(path2), executor);

        futureFile1.thenCombine(futureFile2, (file1, file2) -> {
                    writeFile(file1, file2, outputPath);
                    return null;
                })
                .join();

        executor.shutdown();
    }
}
