import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ10564 {

    static int T;
    static int N, M;
    static int[] a;
    static int[][] dp;

    static void solution(int count, int score) {
        if (count > N || dp[count][score] == 1) {
            return;
        }

        dp[count][score] = 1;

        for (int i = 0; i < M; i++) {
            int cur = a[i];
            solution(count+score+cur, score+cur);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        T = Integer.parseInt(st.nextToken());

        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            a = new int[M];

            dp = new int[N+1][501];
            for (int j = 0; j <= N; j++) {
                Arrays.fill(dp[j], -1);
            }

            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                a[j] = Integer.parseInt(st.nextToken());
            }

            solution(0, 0);

            int answer = -1;
            for (int j = 0; j <= 500; j++) {
                if (dp[N][j] == 1) {
                    answer = j;
                }
            }
            System.out.println(answer);
        }
    }
}