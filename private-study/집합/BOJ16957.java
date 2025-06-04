import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ16957 {

    static int r, c;
    static int[][] board;
    static int[] parent;

    static int[] dx = {0, 0, -1, 1, -1, -1, 1, 1};
    static int[] dy = {-1, 1, 0, 0, -1, 1, -1, 1};

    public static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        board = new int[r][c];
        parent = new int[r*c];

        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < c; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static boolean isBound(int x, int y) {
        return 0 <= x && x < r && 0 <= y && y < c;
    }

    // 재귀함수를 수행하여 가장 작은 값의 인덱스 찾기
    public static int go(int x) {
        if (x == parent[x]) {
            return x;
        }
        return parent[x] = go(parent[x]);
    }


    public static void setParent() {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int x = i;
                int y = j;

                // 인접한 8방향 중 이동이 가능한 값 찾기
                for (int k = 0; k < 8; k++) {
                    int nx = i + dx[k];
                    int ny = j + dy[k];

                    if (isBound(nx, ny)) {
                        if (board[nx][ny] < board[x][y]) {
                            x = nx;
                            y = ny;
                        }
                    }
                }
                parent[i*c+j] = x*c+y;
            }
        }
    }

    public static int[] solution() {
        int[] answer = new int[r*c];
        setParent();

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                answer[go(i*c+j)]++;
            }
        }
        return answer;
    }

    public static void print(int[] answer) {
        for (int i = 0 ; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(answer[i*c+j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        input();
        int[] answer = solution();
        print(answer);
    }
}