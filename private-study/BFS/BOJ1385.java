import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 벌집의 좌표정보
class Coord {
    int x;
    int y;

    Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class BOJ1385 {

    static int a, b;
    static int[][] board = new int[1501][1501];
    static int MAX_VALUE = 1000000;

    // 6각형 벌집에서의 이동방향
    static int[] dx = {-1, 0, 1, 1, 0, -1};
    static int[] dy = {1, 1, 0, -1, -1, 0};

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
    }

    // 벌집을 2차원 보드로 변환
    private static void initBoard() {
        // 중심좌표에 1이 저장
        int x = 750;
        int y = 750;

        board[x][y] = 1;
        int cur = 2;
        int level = 1;

        // 각 변의 길이만큼 벌집 생성
        while (true) {
            // 레벨이 변할때 처음에는 위로 이동
            x += dx[5];
            y += dy[5];
            board[x][y] = cur++;

            // 6개의 방향을 순서대로 이동하여 2차원 보드로 구현
            for (int k = 0; k < 6; k++) {
                int loop = level;

                // 오른쪽 위 대각선은 level-1 만큼 이동
                if (k == 0) {
                    loop--;
                }

                for (int l = 0; l < loop; l++) {
                    x += dx[k];
                    y += dy[k];
                    board[x][y] = cur++;

                    // 100만이 넘으면 종료
                    if (cur == 1000000) {
                        return;
                    }
                }
            }
            level++;
        }
    }

    // 범위 확인
    private static boolean isBound(int x, int y) {
        return 0 <= x && x <= 1500 && 0 <= y && y <= 1500;
    }

    private static ArrayList<Integer> solution() {
        ArrayList<Integer> answer = new ArrayList<>();
        initBoard();

        Coord[] coords = new Coord[MAX_VALUE+1];
        for (int i = 0; i <= 1500; i++) {
            for (int j = 0; j <= 1500; j++) {
                if (board[i][j] != 0 && board[i][j] <= MAX_VALUE) {
                    coords[board[i][j]] = new Coord(i, j);
                }
            }
        }

        // 거리와 해당 좌표가 이전에 어디서 왔는지 저장
        int[] dist = new int[MAX_VALUE+1];
        int[] from = new int[MAX_VALUE+1];
        Arrays.fill(dist, -1);
        Arrays.fill(from, -1);

        // 도착점을 시작점으로 하여 BFS 구현
        // 어디서 왔는지 경로를 체크하여야 하므로 도착점을 시작점으로 하였음
        Queue<Integer> q = new LinkedList<>();
        q.add(b);
        dist[b] = 0;
        from[b] = -1;

        while (!q.isEmpty()) {
            int cur = q.poll();
            int x = coords[cur].x;
            int y = coords[cur].y;

            // 6방향으로 이동하여 BFS 수행
            for (int k = 0; k < 6; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];

                if (isBound(nx, ny) && board[nx][ny] != 0) {
                    int next = board[nx][ny];
                    if (dist[next] == -1) {
                        dist[next] = dist[cur] + 1;
                        from[next] = cur;
                        q.add(next);
                    }
                }
            }
        }

        // 시작점을 결과 리스트에 추가
        answer.add(a);

        // 이전에 찾았던 경로를 역산하여 결과 리스트에 추가
        while (a != b) {
            a = from[a];
            answer.add(a);
        }

        return answer;
    }

    private static void print(ArrayList<Integer> answer) {
        for (int i = 0; i < answer.size(); i++) {
            System.out.print(answer.get(i) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        input();
        ArrayList<Integer> answer = solution();
        print(answer);
    }
}