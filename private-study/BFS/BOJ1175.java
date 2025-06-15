import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 배달 경로의 현재 위치 정보 클래스
class Delivery {
    int x;
    int y;
    int dir;
    int s;

    Delivery(int x, int y, int dir, int s) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.s = s;
    }
}

public class BOJ1175 {

    static int n, m;
    static char[][] board;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    static int[][][][] dist;

    static int sx, sy;
    static int cx1, cy1, cx2, cy2;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        board = new char[n][m];
        dist = new int[n+1][m+1][4][4];
        cx1 = cy1 = cx2 = cy2 = -1;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            board[i] = s.toCharArray();

            for (int j = 0; j < m; j++) {
                // 시작점
                if (board[i][j] == 'S') {
                    sx = i;
                    sy = j;
                }
                // C를 찾은 경우
                // 2개가 존재하기 때문에 따로 저장
                else if (board[i][j] == 'C') {
                    if (cx1 == -1) {
                        cx1 = i;
                        cy1 = j;
                    }
                    else {
                        cx2 = i;
                        cy2 = j;
                    }
                }
            }
        }
    }

    // 범위 확인
    private static boolean isBound(int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < m;
    }

    private static int solution() {
        int answer = -1;
        Queue<Delivery> q = new LinkedList<>();

        // 걸리는 시간 초기화
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < 4; k++) {
                    for (int s = 0; s < 4; s++) {
                        dist[i][j][k][s] = -1;
                    }
                }
            }
        }

        // 시작점에 대한 정보
        for (int k = 0; k < 4; k++) {
            dist[sx][sy][k][0] = 0;
            q.add(new Delivery(sx, sy, k, 0));
        }

        while (!q.isEmpty()) {
            Delivery cur = q.poll();
            int x = cur.x;
            int y = cur.y;
            int dir = cur.dir;
            int s = cur.s;

            // 2개를 모두 배달한 경우
            if (s == 3) {
                answer = dist[x][y][dir][s];
                break;
            }

            for (int k = 0; k < 4; k++) {
                // 이전과는 같은 방향으로 이동이 불가능
                if (dir == k) {
                    continue;
                }
                int nx = x + dx[k];
                int ny = y + dy[k];

                if (isBound(nx, ny)) {
                    // 벽이 아닌 경우
                    if (board[nx][ny] != '#') {
                        int ns = s;

                        // C를 찾은 경우
                        if (board[nx][ny] == 'C') {
                            // 첫번째 C를 찾았을 때
                            if (nx == cx1 && ny == cy1) {
                                ns |= 1;
                            }
                            // 두번째 C를 찾았을때
                            else {
                                ns |= 2;
                            }
                        }

                        // 아직 방문하지 않은 경우 == 해당 위치 + 방향 + 완료한 C의 갯수를 기반으로는 최소시간
                        if (dist[nx][ny][k][ns] == -1) {
                            dist[nx][ny][k][ns] = dist[x][y][dir][s] + 1;
                            q.add(new Delivery(nx, ny, k, ns));
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