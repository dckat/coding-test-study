import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int n, k;
    static int[][] dist = new int[500001][2];

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= 500000; i++) {
            dist[i][0] = dist[i][1] = -1;
        }
    }

    // 범위 확인
    private static boolean isBound(int x) {
        return 0 <= x && x <= 500000;
    }

    // bfs 기반 수빈의 이동
    private static void bfs() {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{n, 0});
        dist[n][0] = 0;

        // 수빈의 이동
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int t = cur[1];

            // 1초후 이동
            for (int y: new int[]{x + 1, x - 1, 2 * x}) {
                if (!isBound(y)) {
                    continue;
                }

                // 해당 위치가 처음 방문한 곳이면 (홀수시간. 짝수시간)
                if (dist[y][1-t] == -1) {
                    dist[y][1-t] = dist[x][t] + 1;
                    q.add(new int[]{y, 1-t});
                }
            }
        }
    }

    private static int solution() {
        int answer = -1;
        bfs();

        // 동생의 이동
        for (int t = 0;; t++) {
            k += t;

            // 범위를 벗어난 경우 -1 리턴
            if (k > 500000) {
                return answer;
            }

            // 홀수시간 또는 짝수시간에 수빈이가 먼저 도착한 경우
            // 2초에 한번씩 제자리에서 대기하면 되므로 정답은 t초
            // 수빈이가 뒤늦게 도착한 경우에는 동생이 이미 다른 곳으로 이동한 상태
            if (dist[k][t%2] <= t) {
                answer = t;
                break;
            }
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        input();
        int answer = solution();
        System.out.println(answer);;
    }
}