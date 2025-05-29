import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static int n;
    private static int[] a;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());

        a = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

    }

    private static int solution() {
        int answer = 0;
        int len = 0;

        for (int i = 0; i < n; i++) {
            int cnt = 0;
            while (a[i] > 0) {
                cnt++;
                answer += a[i] % 2;
                a[i] /= 2;
            }
            len = Math.max(len, cnt);
        }
        answer += len - 1;
        if (answer < 0) {
            answer = 0;
        }
        return answer;
    }

    public static void main(String[] args) throws Exception {
        input();
        int answer = solution();
        System.out.println(answer);
    }
}