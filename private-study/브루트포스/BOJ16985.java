import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ16985 {

    static int[][][] board = new int[5][5][5];

    static int[] dx = {0, 0, -1, 1, 0, 0};
    static int[] dy = {-1, 1, 0, 0, 0, 0};
    static int[] dz = {0, 0, 0, 0, -1, 1};

    public static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                st = new StringTokenizer(br.readLine());
                for (int k = 0; k < 5; k++) {
                    board[i][j][k] = Integer.parseInt(st.nextToken());
                }
            }
        }
    }

    // 오른쪽 90도 회전
    public static int[][] rotate(int[][] a) {
        int[][] b = new int[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                b[i][j] = a[5-j-1][i];
            }
        }
        return b;
    }

    public static boolean isBound(int x, int y, int z) {
        return 0 <= x && x < 5 && 0 <= y && y < 5 && 0 <= z && z < 5;
    }

    // bfs 기반 최단경로 탐색
    public static int bfs(int[][][] b) {
        // 시작점에서 출발할 수 없는 경우
        if (b[0][0][0] == 0) {
            return -1;
        }
        Queue<int[]> q = new LinkedList<>();
        int[][][] dist = new int[5][5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    dist[i][j][k] = -1;
                }
            }
        }

        q.add(new int[]{0, 0, 0});
        dist[0][0][0] = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            int x = cur[0]; int y = cur[1]; int z = cur[2];

            // 도착점
            if (x == 4 && y == 4 && z == 4) {
                break;
            }

            // 6가지 방향으로 이동
            for (int k = 0; k < 6; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];
                int nz = z + dz[k];

                if (isBound(nx, ny, nz) && dist[nx][ny][nz] == -1 && b[nx][ny][nz] == 1) {
                    dist[nx][ny][nz] = dist[x][y][z] + 1;
                    q.add(new int[]{nx, ny, nz});
                }
            }
        }

        return dist[4][4][4];
    }

    public static int go(int[] order) {
        int answer = -1;
        int[][][] b = new int[5][5][5];

        // 순서에 맞게 board 배치
        for (int i = 0; i < 5; i++) {
            b[order[i]] = board[i];
        }

        // 선택된 판을 각각 회전시켜 거리를 구함
        for (int l1 = 0; l1 < 4; l1++) {
            for (int l2 = 0; l2 < 4; l2++) {
                for (int l3 = 0; l3 < 4; l3++) {
                    for (int l4 = 0; l4 < 4; l4++) {
                        for (int l5 = 0; l5 < 4; l5++) {
                            int tmp = bfs(b);
                            if (tmp != -1) {
                                if (answer == -1 || answer > tmp) {
                                    answer = tmp;
                                }
                            }
                            b[4] = rotate(b[4]);
                        }
                        b[3] = rotate(b[3]);
                    }
                    b[2] = rotate(b[2]);
                }
                b[1] = rotate(b[1]);
            }
            b[0] = rotate(b[0]);
        }
        return answer;
    }

    // 5개 판의 순서를 정함 (순열)
    public static int permutation(int[] order, boolean[] used, int depth) {
        int answer = -1;

        if (depth == 5) {
            int result = go(order);
            return result;
        }

        for (int i = 0; i < 5; i++) {
            if (!used[i]) {
                used[i] = true;
                order[depth] = i;

                // 값 갱신
                int tmp = permutation(order, used, depth + 1);
                if (tmp != -1) {
                    if (answer == -1 || tmp < answer) {
                        answer = tmp;
                    }
                }
                used[i] = false;
            }
        }
        return answer;
    }

    public static int solution() {
        int[] order = new int[5];
        boolean[] used = new boolean[5];

        int answer = permutation(order, used, 0);
        return answer;
    }


    public static void main(String[] args) throws Exception {
        input();
        int answer = solution();
        System.out.println(answer);
    }
}