import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ17471 {

    private static int n;           // 구역 수
    private static int[] p;         // 인구수
    private static int[][] adj;     // 인접 지역
    private static int[] area;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());

        p = new int[n+1];
        area = new int[n+1];
        adj = new int[n+1][];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            p[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            adj[i] = new int[num];

            for (int j = 0; j < num; j++) {
                adj[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static boolean check() {
        List<Integer> part1 = new ArrayList<>();
        List<Integer> part2 = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (area[i] == 1) {
                part1.add(i);
            }
            else {
                part2.add(i);
            }
        }

        // 빈 구역이 있으면 안 됨
        if (part1.isEmpty() || part2.isEmpty()) {
            return false;
        }

        // 연결 확인
        if (!isConnected(part1, 1)) {
            return false;
        }
        if (!isConnected(part2, 2)) {
            return false;
        }

        return true;
    }

    // 같은 구역에 속한 노드들이 인접 그래프 상에서 서로 연결되어 있는지 검사 (BFS)
    private static boolean isConnected(List<Integer> part, int color) {
        boolean[] seen = new boolean[n+1];
        Queue<Integer> q = new LinkedList<>();

        // 시작점: grp.get(0)
        q.add(part.get(0));
        seen[part.get(0)] = true;
        int cnt = 1;

        while (!q.isEmpty()) {
            int u = q.poll();
            // 인접 노드 탐색
            for (int v : adj[u]) {
                if (!seen[v] && area[v] == color) {
                    seen[v] = true;
                    q.add(v);
                    cnt++;
                }
            }
        }

        return cnt == part.size();
    }

    // 인구수 차이 값 구하기
    private static int calc() {
        int area1 = 0;
        int area2 = 0;

        for (int i = 1; i <= n; i++) {
            if (area[i] == 1) {
                area1 += p[i];
            }
            else {
                area2 += p[i];
            }
        }
        return Math.abs(area1-area2);
    }

    private static int go(int index) {
        int answer = -1;
        if (index == n+1) {
            if (check()) {
                int diff = calc();
                return diff;
            }
            return answer;
        }

        area[index] = 1;
        int result = go(index+1);

        if (result != -1) {
            if (answer == -1 || result < answer) {
                answer = result;
            }
        }

        area[index] = 2;
        result = go(index+1);

        if (result != -1) {
            if (answer == -1 || result < answer) {
                answer = result;
            }
        }

        return answer;
    }

    private static int solution() {
        int answer = -1;
        answer = go(1);
        return answer;
    }

    public static void main(String[] args) throws Exception {
        input();
        int answer = solution();
        System.out.println(answer);
    }
}