import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ17090 {

    private static int n, m;
    private static char[][] board;
    private static int[][] d;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        board = new char[n][m];
        d = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String token = st.nextToken();
            board[i] = token.toCharArray();
            Arrays.fill(d[i], -1);
        }
    }

    private static boolean isBound(int x, int y) {
        if (0 <= x && x < n && 0 <= y && y < m) {
            return true;
        }
        return false;
    }

    private static int go(int x, int y) {
        // 바깥으로 탈출한 경우
        if (!isBound(x, y)) {
            return 1;
        }

        // 이미 방문한 칸
        if (d[x][y] != -1) {
            return d[x][y];
        }

        d[x][y] = 0;
        if (board[x][y] == 'U') {
            d[x][y] = go(x-1, y);
        }
        else if (board[x][y] == 'D') {
            d[x][y] = go(x+1, y);
        }
        else if (board[x][y] == 'L'){
            d[x][y] = go(x, y-1);
        }
        else if (board[x][y] == 'R') {
            d[x][y] = go(x, y+1);
        }
        return d[x][y];
    }

    private static int solution() {
        int answer = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                answer += go(i, j);
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