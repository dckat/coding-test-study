import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ1695 {

    static int n;
    static int[] a;
    static int[][] d;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        a = new int[n+1];
        d = new int[n+1][n+1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }
    }

    private static int dp(int left, int right) {
        if (left > right) {
            return 0;
        }

        if (d[left][right] != -1) {
            return d[left][right];
        }

        // 양 끝이 같은 문자인 경우
        if (a[left] == a[right]) {
            d[left][right] = dp(left+1, right-1);
        } // 다른 문자로 끝나는 경우
        else {
            // 왼쪽에 추가하는 경우와 오른쪽에 추가하는 경우를 비교하여 최솟값에 1 더한것을 저장
            d[left][right] = Math.min(dp(left+1, right), dp(left, right-1)) + 1;
        }
        return d[left][right];
    }

    private static int solution() {
        for (int i = 1; i <= n; i++) {
            Arrays.fill(d[i], -1);
        }

        return dp(1, n);
    }


    public static void main(String[] args) throws Exception {
        input();
        int answer = solution();
        System.out.println(answer);
    }
}