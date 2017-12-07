import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestGendos {
    static char[] arr = {'1', '2', '3', 'a', 'b', 'c'};

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        fun(n);
    }

    private static void fun(int n) {
        StringBuilder s = new StringBuilder(arr[n-1]);
    }
}
