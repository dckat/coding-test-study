import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Pair {
    boolean first;
    boolean second;

    Pair(boolean first, boolean second) {
        this.first = first;
        this.second = second;
    }
}

class Point {
    int x;
    int y;

    Point (int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Points {
    int rx;
    int ry;
    int bx;
    int by;

    Points(int rx, int ry, int bx, int by) {
        this.rx = rx;
        this.ry = ry;
        this.bx = bx;
        this.by = by;
    }
}

public class BOJ15653 {

    static int n, m;
    static char[][] board;

    static int[][][][] dist;
    static int rx, ry, bx, by;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        board = new char[n][m];
        dist = new int[n][m][n][m];

        for (int i = 0; i < n; i++) {
            String line = br.readLine();

            for (int j = 0; j < m; j++) {
                board[i][j] = line.charAt(j);

                // 파란 구슬
                if (board[i][j] == 'B') {
                    bx = i;
                    by = j;
                    board[i][j] = '.';
                } // 빨간 구슬
                else if (board[i][j] == 'R') {
                    rx = i;
                    ry = j;
                    board[i][j] = '.';
                }
            }
        }
    }

    // 초기화
    private static void initDist() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < m; l++) {
                        dist[i][j][k][l] = -1;
                    }
                }
            }
        }
    }

    private static boolean isBound(int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < m;
    }

    private static Pair simulate(char[][] b, Point p, int dir) {
        int x = p.x;
        int y = p.y;

        // 이미 구멍에 빠진 경우
        if (b[x][y] == '.') {
            return new Pair(false, false);
        }

        boolean moved = false;
        while (true) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if (!isBound(nx, ny)) {
                p.x = x;
                p.y = y;
                return new Pair(moved, false);
            }

            char ch = b[nx][ny];
            if (ch == '#') {
                p.x = x;
                p.y = y;
                return new Pair(moved, false);
            }
            else if (ch == 'R' || ch == 'B') {
                p.x = x;
                p.y = y;
                return new Pair(moved, false);
            }
            else if (ch == '.') {
                char temp = b[nx][ny];
                b[nx][ny] = b[x][y];
                b[x][y] = temp;
                x = nx;
                y = ny;
                moved = true;
            } else if (ch == 'O') {
                b[x][y] = '.';
                p.x = x;
                p.y = y;
                moved = true;
                return new Pair(moved, true);
            }
        }
    }

    private static Pair getNext(char[][] a, Points p, int dir) {
        char[][] b = new char[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                b[i][j] = a[i][j];
            }
        }

        int crx = p.rx; int cry = p.ry;
        int cbx = p.bx; int cby = p.by;
        b[crx][cry] = 'R';
        b[cbx][cby] = 'B';

        boolean hole1 = false;
        boolean hole2 = false;

        Point red = new Point(crx, cry);
        Point blue = new Point(cbx, cby);

        while (true) {
            Pair p1 = simulate(b, red, dir);
            Pair p2 = simulate(b, blue, dir);

            if (p1.first == false && p2.first == false) {
                break;
            }
            if (p1.second) hole1 = true;
            if (p2.second) hole2 = true;
        }
        p.rx = red.x; p.ry = red.y; p.bx = blue.x; p.by = blue.y;
        return new Pair(hole1, hole2);
    }

    private static int solution() {
        int answer = -1;
        initDist();

        Queue<Points> q = new LinkedList<>();
        q.add(new Points(rx, ry, bx, by));
        dist[rx][ry][bx][by] = 0;
        boolean check = false;

        while (!q.isEmpty()) {
            Points cur = q.poll();
            rx = cur.rx; ry = cur.ry;
            bx = cur.bx; by = cur.by;

            for (int k = 0; k < 4; k++) {
                boolean hole1, hole2;

                int nrx = rx; int nry = ry;
                int nbx = bx; int nby = by;

                cur = new Points(nrx, nry, nbx, nby);

                Pair next = getNext(board, cur, k);
                nrx = cur.rx; nry = cur.ry;
                nbx = cur.bx; nby = cur.by;

                hole1 = next.first;
                hole2 = next.second;

                if (hole2) continue;
                if (hole1) {
                    check = true;
                    answer = dist[rx][ry][bx][by] + 1;
                    break;
                }

                if (dist[nrx][nry][nbx][nby] == -1) {
                    dist[nrx][nry][nbx][nby] = dist[rx][ry][bx][by] + 1;
                    q.add(new Points(nrx, nry, nbx, nby));
                }
            }
            if (check) {
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