import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ15558 {

    static int n, k;
    static String[] board = new String[2];
    static int[][] dir;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        dir = new int[][]{{0, -1}, {0, 1}, {1, k}};     // 이동정보 저장

        // 2개의 막대 정보 입력
        for (int i = 0; i < 2; i++) {
            st = new StringTokenizer(br.readLine());
            board[i] = st.nextToken();
        }
    }

    private static char solution() {
        char answer = '0';
        int[][] dist = new int[2][n];

        // 최소시간 정보 초기화
        for (int i = 0; i < 2; i++) {
            Arrays.fill(dist[i], -1);
        }

        // 1번 막대의 0번에서 시작
        Queue<int[]> q = new LinkedList<>();
        dist[0][0] = 0;
        q.add(new int[]{0, 0});

        boolean check = false;      // 게임 클리어 여부

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];

            // 3가지의 행동에 따라 이동
            // 앞으로 1칸, 뒤로 1칸, 옆의 막대로 이동 후 k칸 이동
            for (int k = 0; k < 3; k++) {
                int dx = dir[k][0];
                int dy = dir[k][1];

                int nx = (x+dx)%2;
                int ny = y + dy;

                // n보다 더 큰 칸으로 이동하면 게임을 클리어
                if (ny >= n) {
                    check = true;
                    break;
                }

                // 범위 확인
                if (ny > 0) {
                    // 0이 아닌 칸을 처음으로 방문
                    if (dist[nx][ny] == -1 && board[nx].charAt(ny) != '0') {
                        // 이동한 칸이 없어지지 않는 경우
                        if (ny >= dist[x][y] + 1) {
                            dist[nx][ny] = dist[x][y] + 1;
                            q.add(new int[]{nx, ny});
                        }
                    }
                }
            }
            // 게임이 클리어 되었는지 확인
            if (check) {
                break;
            }
        }

        // 클리어시 1을 반환
        if (check) {
            answer = '1';
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        input();
        char answer = solution();
        System.out.println(answer);
    }
}