import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Question1 {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("quiz1/Q1_Input.txt"));
        List<String> list = new ArrayList<>();
        List<Boolean> response = new ArrayList<>();

        String str;
        while ((str = reader.readLine()) != null) {
            list.add(str);
        }

        for (String s : list) {
            boolean check = true;
            for (int i = 0; i < s.length() / 2; i++) {
                //i, n-i번 째 문자가 다르면
                if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
                    check = false;
                    break;
                }
            }
            response.add(check);
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i) + ": " + response.get(i));
        }
    }
}
