import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ1413 {

    static int N, M;

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
    }

    static long getGCD(long a, long b) {
        if (b == 0) {
            return a;
        }

        return getGCD(b, a%b);
    }

    static String solution() {
        String answer = "";

        long[][] dp = new long[N+1][N+1];
        dp[1][1] = 1;

        for (int i = 2; i <= N; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i][j] = dp[i-1][j-1] + (i-1) * dp[i-1][j];
            }
        }

        long total = 0;
        for (int i = 1; i <= N; i++) {
            total += dp[N][i];
        }

        long key = 0;
        for (int i = 1; i <= M; i++) {
            key += dp[N][i];
        }

        long gcd = getGCD(total, key);

        answer = key/gcd + "/" + total/gcd;
        return answer;
    }

    public static void main(String[] args) throws Exception {
        input();
        String answer = solution();
        System.out.println(answer);
    }
}