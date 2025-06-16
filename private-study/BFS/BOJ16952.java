import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 시간과 말의 변경 횟수를 저장할 pair
class Pair implements Comparable<Pair> {
    int dist;
    int change;

    Pair(int dist, int change) {
        this.dist = dist;
        this.change = change;
    }


    @Override
    public int compareTo(Pair o) {
        // 시간 비교
        if (this.dist < o.dist) {
            return -1;
        }
        // 시간이 같은 경우 말의 변경 횟수 비교
        else if (this.dist == o.dist) {
            if (this.change < o.change) {
                return -1;
            }
            else if (this.change == o.change) {
                return 0;
            }
            else {
                return 1;
            }
        } else {
            return 1;
        }
    }
}

// 체스판 이동정보 저장 클래스
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

public class BOJ16952 {

    static int n;
    static int[][] board;
    static Pair[][][][] p;

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

    // 이동시간과 변경횟수 초기화
    private static void initPair() {
        p = new Pair[n][n][n*n+1][3];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int num = 1; num <= n*n; num++) {
                    for (int k = 0; k < 3; k++) {
                        p[i][j][num][k] = new Pair(-1, -1);
                    }
                }
            }
        }
    }

    // 범위 확인
    private static boolean isBound(int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < n;
    }

    private static Pair solution() {
        Pair answer = new Pair(-1, -1);
        initPair();

        Queue<Chess> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 1) {
                    for (int k = 0; k < 3; k++) {
                        q.add(new Chess(i, j, 1, k));
                        p[i][j][1][k] = new Pair(0, 0);
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

            Pair pair = p[x][y][num][piece];

            // 1부터 N^2까지 모두 방문한 경우
            if (num == n*n) {
                // 시간과 말의 변경횟수를 모두 비교하여 갱신
                if (answer.dist == -1 || answer.compareTo(pair) > 0) {
                    answer = pair;
                }
            }

            // 말의 변경
            for (int k = 0; k < 3; k++) {
                // 같은 말로는 변경이 불가
                if (piece == k) {
                    continue;
                }

                Pair np = new Pair(pair.dist+1, pair.change+1);
                if (p[x][y][num][k].dist == -1 || p[x][y][num][k].compareTo(np) > 0) {
                    p[x][y][num][k] = np;
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

                        Pair np = new Pair(pair.dist+1, pair.change);
                        if (p[nx][ny][nextNum][piece].dist == -1 || p[nx][ny][nextNum][piece].compareTo(np) > 0) {
                            p[nx][ny][nextNum][piece] = np;
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

                            Pair np = new Pair(pair.dist+1, pair.change);
                            if (p[nx][ny][nextNum][piece].dist == -1 || p[nx][ny][nextNum][piece].compareTo(np) > 0) {
                                p[nx][ny][nextNum][piece] = np;
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

                            Pair np = new Pair(pair.dist+1, pair.change);
                            if (p[nx][ny][nextNum][piece].dist == -1 || p[nx][ny][nextNum][piece].compareTo(np) > 0) {
                                p[nx][ny][nextNum][piece] = np;
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
        Pair answer = solution();
        System.out.println(answer.dist + " " + answer.change);
    }
}