import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ12872 {

    static int N, M, P;
    static long[][] dp;

    static final long MOD = 1000000007;

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());

        dp = new long[P+1][N+1];

        for (int i = 0; i <= P; i++) {
            Arrays.fill(dp[i], -1L);
        }
    }

    static long solution(int p, int x) {
        int y = N-x;

        if (p == P) {
            if (y == 0) {
                return 1;
            }
            else {
                return 0;
            }
        }
        if (dp[p][x] != -1) {
            return dp[p][x];
        }

        long answer = 0;

        // 추가되지 않은 노래를 추가
        if (y > 0) {
            answer += solution(p+1, x+1) * y;
        }
        // 이미 추가된 노래를 추가
        if (x > M) {
            answer += solution(p+1, x) * (x-M);
        }

        return dp[p][x] = answer % MOD;
    }

    public static void main(String[] args) throws Exception {
        input();
        long answer = solution(0, 0);
        System.out.println(answer);
    }
}