import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static int n, m;
    private static int[][] board = null;
    private static boolean[][] visited = null;

    private static int[] dx = {-1, 1, 0, 0};
    private static int[] dy = {0, 0, -1, 1};

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new int[n][m];
        visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    // 범위 확인 메서드
    private static boolean isBound(int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < m;
    }

    private static int bfs() {
        int result = 0;

        // 초기화
        for (int i = 0; i < n; i++) {
            Arrays.fill(visited[i], false);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // BFS로 방문하지 않은 적의 돌인 경우 BFS 수행
                if (!visited[i][j] && board[i][j] == 2) {
                    int cnt = 1;
                    boolean check = true;  // 둘러쌓였는지 확인 변수

                    Queue<int[]> q = new LinkedList<>();
                    q.add(new int[]{i, j});
                    visited[i][j] = true;

                    // BFS를 돌면서 둘러쌓여 있는지 확인
                    while (!q.isEmpty()) {
                        int[] loc = q.poll();
                        int x = loc[0];
                        int y = loc[1];

                        // 상하좌우 인접칸 확인
                        for (int k = 0; k < 4; k++) {
                            int nx = x + dx[k];
                            int ny = y + dy[k];

                            // 범위 확인
                            if (isBound(nx, ny)) {
                                // 빈칸이면 둘러쌓이지 않음
                                if (board[nx][ny] == 0) {
                                    check = false;
                                }
                                // 인접한 곳이 적의 돌이면 큐에 추가하고 카운트 1증가
                                else if (board[nx][ny] == 2 && !visited[nx][ny]) {
                                    q.add(new int[]{nx, ny});
                                    visited[nx][ny] = true;
                                    cnt++;
                                }
                            }
                        }
                    }
                    // 최종적으로 둘러쌓인 영역이면 cnt 만큼 증가
                    if (check) {
                        result += cnt;
                    }
                }
            }
        }

        return result;
    }

    private static int solution() {
        int answer = 0;
        for (int r1 = 0; r1 < n; r1++) {
            for (int c1 = 0; c1 < m; c1++) {
                // 상대 돌이 놓여져 있는 경우
                if (board[r1][c1] != 0) {
                    continue;
                }
                for (int r2 = 0; r2 < n; r2++) {
                    for (int c2 = 0; c2 < m; c2++) {
                        // 같은 위치에 돌은 놓는 경우
                        if (r1 == r2 && c1 == c2) {
                            continue;
                        }
                        // 이미 다른 돌이 놓인 경우
                        if (board[r2][c2] != 0) {
                            continue;
                        }
                        board[r1][c1] = 1;
                        board[r2][c2] = 1;
                        int result = bfs();

                        if (result > answer) {
                            answer = result;
                        }

                        board[r1][c1] = 0;
                        board[r2][c2] = 0;
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