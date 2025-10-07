import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N, M;
    static int[] a;
    static int[] sum;

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        a = new int[N+1];
        sum = new int[N+1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            a[i] = Integer.parseInt(st.nextToken());
            sum[i] = sum[i-1] + a[i];
        }

        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
    }

    static int solution() {
        int[][] dp = new int[N+1][4];

        for (int i = M; i <= N; i++) {
            for (int j = 1; j <= 3; j++) {
                dp[i][j] = Math.max(dp[i-1][j], dp[i-M][j-1] + (sum[i] - sum[i-M]));
            }
        }

        return dp[N][3];
    }

    public static void main(String[] args) throws Exception {
        input();
        int answer = solution();
        System.out.println(answer);
    }
}