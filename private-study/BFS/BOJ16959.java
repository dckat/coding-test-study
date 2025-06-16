import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Chess {
    int x;
    int y;
    int num;
    int piece;

    Chess (int x, int y, int num, int piece) {
        this.x = x;
        this.y = y;
        this.num = num;
        this.piece = piece;
    }
}

public class BOJ16959 {

    static int n;
    static int[][] board;
    static int[][][][] dist;

    // 나이트. 룩. 비숍의 이동정보
    static int[][] knight = new int[][]{{-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}};
    static int[][] rook = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
    static int[][] bishop = new int[][]{{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        board = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static void initDist() {
        dist = new int[n][n][n*n+1][3];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int num = 1; num <= n*n; num++) {
                    for (int k = 0; k < 3; k++) {
                        dist[i][j][num][k] = -1;
                    }
                }
            }
        }
    }

    // 범위 확인
    private static boolean isBound(int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < n;
    }

    private static int solution() {
        int answer = -1;
        initDist();

        Queue<Chess> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 1) {
                    for (int k = 0; k < 3; k++) {
                        q.add(new Chess(i, j, 1, k));
                        dist[i][j][1][k] = 0;
                    }
                }
            }
        }

        while(!q.isEmpty()) {
            Chess c = q.poll();
            int x = c.x;
            int y = c.y;
            int num = c.num;
            int piece = c.piece;

            // 1부터 N^2까지 모두 방문한 경우
            if (num == n*n) {
                if (answer == -1 || answer > dist[x][y][num][piece]) {
                    answer = dist[x][y][num][piece];
                }
            }

            // 말의 변경
            for (int k = 0; k < 3; k++) {
                // 같은 말로는 변경이 불가
                if (piece == k) {
                    continue;
                }
                if (dist[x][y][num][k] == -1) {
                    dist[x][y][num][k] = dist[x][y][num][piece] + 1;
                    q.add(new Chess(x, y, num, k));
                }
            }

            // 말의 이동
            // 나이트
            if (piece == 0) {
                for (int k = 0; k < 8; k++) {
                    int nx = x + knight[k][0];
                    int ny = y + knight[k][1];

                    if (isBound(nx, ny)) {
                        int nextNum = num;

                        if (board[nx][ny] == num+1) {
                            nextNum = num+1;
                        }

                        if (dist[nx][ny][nextNum][piece] == -1) {
                            dist[nx][ny][nextNum][piece] = dist[x][y][num][piece] + 1;
                            q.add(new Chess(nx, ny, nextNum, piece));
                        }
                    }
                }
            }
            // 룩
            else if (piece == 1) {
                for (int k = 0; k < 4; k++) {
                    // 룩은 상하좌우로 1칸 이상 이동가능 (경계를 넘기 전까지 모든 칸으로 이동 가능)
                    for (int l = 1;; l++) {
                        int nx = x + rook[k][0]*l;
                        int ny = y + rook[k][1]*l;

                        if (isBound(nx, ny)) {
                            int nextNum = num;

                            if (board[nx][ny] == num+1) {
                                nextNum = num+1;
                            }

                            if (dist[nx][ny][nextNum][piece] == -1) {
                                dist[nx][ny][nextNum][piece] = dist[x][y][num][piece] + 1;
                                q.add(new Chess(nx, ny, nextNum, piece));
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            // 비숍
            else if (piece == 2) {
                for (int k = 0; k < 4; k++) {
                    // 비숍은 각 대각선 방향으로 1칸 이상 이동가능 (경계를 넘기 전까지 모든 칸으로 이동 가능)
                    for (int l = 1;; l++) {
                        int nx = x + bishop[k][0]*l;
                        int ny = y + bishop[k][1]*l;

                        if (isBound(nx, ny)) {
                            int nextNum = num;

                            if (board[nx][ny] == num+1) {
                                nextNum = num+1;
                            }

                            if (dist[nx][ny][nextNum][piece] == -1) {
                                dist[nx][ny][nextNum][piece] = dist[x][y][num][piece] + 1;
                                q.add(new Chess(nx, ny, nextNum, piece));
                            }
                        }
                        else {
                            break;
                        }
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