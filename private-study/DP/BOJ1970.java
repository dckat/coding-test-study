import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ1970 {

    static int N;
    static int[] a;
    static int[][] dp;

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        a = new int[N+1];

        dp = new int[N+1][N+1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }
    }

    static int solution(int s, int e) {
        if (s >= e) {
            return 0;
        }
        if (dp[s][e] != -1) {
            return dp[s][e];
        }

        int answer = solution(s+1, e);
        for (int k = s+1; k <= e; k++) {
            if (a[s] == a[k]) {
                int temp = solution(s+1, k-1) + solution(k+1, e) + 1;

                if (temp > answer) {
                    answer = temp;
                }
            }
        }

        return dp[s][e] = answer;
    }

    public static void main(String[] args) throws Exception {
        input();

        for (int i = 1; i <= N; i++) {
            Arrays.fill(dp[i], -1);
        }

        int answer = solution(1, N);
        System.out.println(answer);
    }
}