import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ2281 {

    static int N, M;
    static int[] arr;
    static int[][] dp;

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new int[N];
        dp = new int[N][M+2];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
        }
    }

    static int solution(int index, int count) {
        if (index == N) {
            return 0;
        }
        if (dp[index][count] != -1) {
            return dp[index][count];
        }

        int space = M-(count-1);
        int cost = space*space;

        // 다음줄에 쓰는 경우
        int answer = solution(index+1, arr[index]+1) + cost;

        // 이어서 쓰는 경우
        if (count + arr[index] <= M) {
            int cur = solution(index+1, count + arr[index] + 1);

            if (answer > cur) {
                answer = cur;
            }
        }

        return dp[index][count] = answer;
    }

    public static void main(String[] args) throws Exception {
        input();
        int answer = solution(0, 0);
        System.out.println(answer);
    }
}