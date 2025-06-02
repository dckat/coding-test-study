import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ16958 {

    private static int n, m, t;
    private static int[] x = null;
    private static int[] y = null;
    private static int[] s = null;
    private static int[][] dist = null;

    public static void initDist() {
        for (int i = 1; i <= n; i++) {
            for (int j = i+1; j <= n; j++) {
                int d = Math.abs(x[i] - x[j]) + Math.abs(y[i] - y[j]);
                dist[i][j] = dist[j][i] = d;
            }
        }
    }

    private static int near(int u) {
        int result = -1;
        int tDist = -1;

        for (int i = 1; i <= n; i++) {
            // 텔레포트로 이동할 수 없는 도시인 경우
            if (s[i] != 1) {
                continue;
            }

            int d = dist[u][i];
            if (tDist == -1 || tDist > d) {
                tDist = d;
                result = i;
            }
        }

        return result;
    }

    private static int calc(int a, int b) {
        int answer = dist[a][b];

        // 두 도시가 모두 특별한 도시인 경우
        if (s[a] == 1 && s[b] == 1) {
            if (answer > t) {
                answer = t;
            }
        }

        int au = near(a);
        int bu = near(b);

        if (au != -1 && bu != -1 && s[au] == 1 && s[bu] == 1) {
            int tmp = dist[a][au] + t + dist[bu][b];

            if (answer > tmp) {
                answer = tmp;
            }
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());

        x = new int[n+1];
        y = new int[n+1];
        s = new int[n+1];
        dist = new int[n+1][n+1];

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());

            s[i] = Integer.parseInt(st.nextToken());
            x[i] = Integer.parseInt(st.nextToken());
            y[i] = Integer.parseInt(st.nextToken());
        }
        initDist();

        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            int answer = calc(a, b);
            System.out.println(answer);
        }
    }
}