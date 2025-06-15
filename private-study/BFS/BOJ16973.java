import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ16973 {

    static int n, m;
    static int[][] board;
    static int[][] dist;
    static int[][] sum;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static int h, w, sr, sc, fr, fc;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        board = new int[n+1][m+1];
        dist = new int[n+1][m+1];
        sum = new int[n+1][m+1];

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= m; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                dist[i][j] = -1;
            }
        }

        st = new StringTokenizer(br.readLine());

        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        sr = Integer.parseInt(st.nextToken());
        sc = Integer.parseInt(st.nextToken());
        fr = Integer.parseInt(st.nextToken());
        fc = Integer.parseInt(st.nextToken());
    }

    // 각 칸별로의 부분합을 구함
    private static void initSum() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                sum[i][j] = sum[i][j-1] + sum[i-1][j] - sum[i-1][j-1] + board[i][j];
            }
        }
    }

    // 범위 확인
    private static boolean isBound(int x, int y) {
        return 1 <= x && x+h-1 <= n && 1 <= y && y+w-1 <= m;
    }

    // (x1, y1) ~ (x2, y2) 합을 구함
    private static int sum(int x1, int y1, int x2, int y2) {
        return sum[x2][y2] - sum[x2][y1-1] - sum[x1-1][y2] + sum[x1-1][y1-1];
    }

    private static int solution() {
        int answer = -1;
        initSum();

        // (sr, sc) 출발
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{sr, sc});
        dist[sr][sc] = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];

            // 도착
            if (x == fr && y == fc) {
                answer = dist[x][y];
                break;
            }

            for (int k = 0; k < 4; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];

                // 범위 확인
                if (isBound(nx, ny)) {
                    // 합이 0일때 이동 가능 (1은 이동이 불가능한 칸)
                    if (sum(nx, ny, nx+h-1, ny+w-1) == 0 && dist[nx][ny] == -1) {
                        q.add(new int[]{nx, ny});
                        dist[nx][ny] = dist[x][y] + 1;
                    }
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        input();
        int answer = solution();
        System.out.println(answer);
    }
}