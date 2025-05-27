import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ15684 {

    private static int n, m, h;
    private static int[][] board = null;        // 격자별로 놓인 가로선 정보 (1: 왼쪽. 2: 오른쪽)
    private static boolean flag = false;        // 0 ~ 3개 내에서 만족하는 최소를 찾았는지 확인변수

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());

        board = new int[h+1][n+1];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            board[a][b] = 1;
            board[a][b+1] = 2;
        }
    }

    // 아래로 이동하면서 도착점을 찾기
    private static boolean check(int num) {
        int tmp = num;
        for (int i = 1; i <= h; i++) {
            // 왼쪽 이동
            if (board[i][tmp] == 2) {
                tmp--;
            }
            // 오른쪽 이동
            else if (board[i][tmp] == 1) {
                tmp++;
            }
        }
        return tmp == num;
    }

    private static boolean go() {
        // 1번부터 n번까지 사다리 이동
        for (int i = 1; i <= n; i++) {
            if (!check(i)) {
                return false;
            }
        }
        return true;
    }

    private static void dfs(int start, int current, int maximum) {
        // 이미 최소를 찾았으면 dfs 중단
        if (flag) {
            return;
        }
        // 놓을 수 있는 가로선을 다 놓았다면
        if (current == maximum) {
            // i번 세로선이 i번에서 끝나는지 검사 수행
            if (go()) {
                flag = true;
            }
            return;
        }

        // (1, 1) 부터 (n, n)까지 가로선을 놓을 수 있는지 확인
        for (int i = start; i <= h; i++) {
            for (int j = 1; j < n; j++) {
                // 가로선을 넣을 수 있는 경우 (서로 연결되어 있지 않아야 함)
                if (board[i][j] == 0 && board[i][j+1] == 0) {
                    board[i][j] = 1;
                    board[i][j+1] = 2;
                    dfs(i, current+1, maximum);
                    board[i][j] = 0;
                    board[i][j+1] = 0;
                }
            }
        }
    }

    private static int solution() {
        int answer = -1;

        // 0개부터 3개까지 가로선을 놓음
        for (int i = 0; i <= 3; i++) {
            dfs(1, 0, i);

            // 최솟값을 찾은 경우 중단
            if (flag) {
                answer = i;
                break;
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