import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 섬과 섬을 이어주는 다리 정보 클래스
class Edge {
    int from;
    int to;
    int cost;

    Edge(int from, int to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}

public class BOJ17472 {

    static int answer = -1;
    static int[][] board;       // 입력 정보
    static int[][] group;       // 그룹 정보
    static int n, m;

    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};

    static ArrayList<Edge> edges = new ArrayList<>();       // 만들 수 있는 다리 정보
    static int[][] a;                                       // 일부를 선택하여 만든 다리 정보

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        board = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static boolean isBound(int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < m;
    }

    // bfs 활용 섬의 번호를 생성
    private static void bfs(int x, int y, int num) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{x, y});
        group[x][y] = num;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int cx = cur[0];
            int cy = cur[1];

            for (int k = 0; k < 4; k++) {
                int nx = cx + dx[k];
                int ny = cy + dy[k];

                if (isBound(nx, ny)) {
                    if (board[nx][ny] == 1 && group[nx][ny] == 0) {
                        group[nx][ny] = num;
                        q.add(new int[]{nx, ny});
                    }
                }
            }
        }
    }

    // 섬의 번호를 만드는 메서드 (만들어진 섬의 갯수를 반환)
    private static int makeGroup() {
        group = new int[n][m];
        int num = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 1 && group[i][j] == 0) {
                    num++;
                    bfs(i, j, num);
                }
            }
        }
        return num;
    }

    // 모든 섬이 연결되었는지 확인 (dfs 기반)
    private static void dfs(int x, int groupCnt, boolean[] check) {
        for (int y = 1; y <= groupCnt; y++) {
            if (a[x][y] > 0 && !check[y]) {
                check[y] = true;
                dfs(y, groupCnt, check);
            }
        }
    }

    private static void go(int index, int sum, int groupCnt) {
        // 모든 다리를 선택한 경우
        if (index == edges.size()) {
            boolean[] check = new boolean[groupCnt+1];
            check[1] = true;
            dfs(1, groupCnt, check);

            // 연결되지 않은 섬이 하나라도 존재하는지 확인
            for (int i = 1; i <= groupCnt; i++) {
                if (!check[i]) {
                    return;
                }
            }
            if (answer == -1 || answer > sum) {
                answer = sum;
            }
            return;
        }

        Edge e = edges.get(index);

        // 다리를 선택
        a[e.from][e.to] = a[e.to][e.from] = e.cost;
        go(index+1, sum + e.cost, groupCnt);

        // 다리 선택하지 않음
        a[e.from][e.to] = a[e.to][e.from] = 0;
        go(index+1, sum, groupCnt);
    }

    private static void solution() {
        int groupCnt = makeGroup();

        // 섬과 섬을 이어주는 다리의 길이 저장
        int[][] bridge = new int[groupCnt+1][groupCnt+1];

        // 각 섬마다 다리의 최솟값을 구함
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] != 0) {
                    for (int k = 0; k < 4; k++) {
                        int length = 0;

                        int x = i + dx[k];
                        int y = j + dy[k];

                        while (isBound(x, y)) {
                            if (board[x][y] == 0) {

                            }
                            else if (group[x][y] == group[i][j]) {
                                break;
                            }
                            else {
                                // 길이가 2인 다리가 나온 경우
                                if (length >= 2) {
                                    int g1 = group[x][y];
                                    int g2 = group[i][j];

                                    if (bridge[g1][g2] == 0 || bridge[g1][g2] > length) {
                                        bridge[g1][g2] = length;
                                    }
                                }
                                break;
                            }
                            x += dx[k];
                            y += dy[k];
                            length++;
                        }
                    }
                }
            }
        }

        // 만들 수 있는 모든 다리 정보를 저장
        for (int i = 1; i <= groupCnt; i++) {
            for (int j = i+1; j <= groupCnt; j++) {
                if (bridge[i][j] > 0) {
                    edges.add(new Edge(i, j, bridge[i][j]));
                }
            }
        }

        a = new int[groupCnt+1][groupCnt+1];
        go(0, 0, groupCnt);
    }


    public static void main(String[] args) throws Exception {
        input();
        solution();
        System.out.println(answer);
    }
}