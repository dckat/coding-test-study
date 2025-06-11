import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ14263 {

    static int n, m;
    static char[][] board;
    static char[] groupColor = new char[100];       // 문자별 그룹 번호
    static int[] colorGroup = new int[256];         // 그룹 번호에 해당되는 문자

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new char[n][m];

        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            board[i] = line.toCharArray();
        }
    }

    private static String sort(boolean[][] g) {
        StringBuilder sb = new StringBuilder();
        int cnt = g.length;
        int[] inDegree = new int[cnt];

        for (int i = 0; i < cnt; i++) {
            for (int j = 0; j < cnt; j++) {
                if (g[i][j]) {
                    inDegree[j]++;
                }
            }
        }

        // 사전 순 정렬을 위해 우선순위 큐 사용
        PriorityQueue<Character> pq = new PriorityQueue<>();
        for (int i = 0; i < cnt; i++) {
            if (inDegree[i] == 0) {
                pq.add(groupColor[i]);
            }
        }

        while (!pq.isEmpty()) {
            char ch = pq.remove();
            sb.append(ch);
            int groupNum = colorGroup[ch];

            for (int i = 0; i < cnt; i++) {
                if (g[groupNum][i]) {
                    inDegree[i]--;

                    if (inDegree[i] == 0) {
                        pq.add(groupColor[i]);
                    }
                }
            }
        }

        // 쌓인 카드의 모든 정보를 담을 수 없는 경우 --> 만들 수 없는 경우
        if (sb.length() != cnt) {
            return "-1";
        }

        return sb.toString();
    }

    private static String solution() {
        String answer = "";

        for (int i = 0; i < 256; i++) {
            colorGroup[i] = -1;
        }

        int groupNum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char ch = board[i][j];

                // 카드의 색이 놓여진 경우
                if (ch != '.') {
                    // 아직 그룹의 번호가 결정되지 않은 경우
                    if (colorGroup[ch] == -1) {
                        colorGroup[ch] = groupNum;
                        groupColor[groupNum] = ch;
                        groupNum++;
                    }
                }
            }
        }

        boolean[][] g = new boolean[groupNum][groupNum];

        // 색깔 별로 카드의 크기 구함
        for (int k = 0; k < groupNum; k++) {
            int minX = n-1;
            int maxX = 0;
            int minY = m-1;
            int maxY = 0;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (board[i][j] == groupColor[k]) {
                        minX = Math.min(minX, i);
                        maxX = Math.max(maxX, i);
                        minY = Math.min(minY, j);
                        maxY = Math.max(maxY, j);
                    }
                }
            }

            // 각 색깔별로 놓여진 색상의 카드 확인
            for (int i = minX; i <= maxX; i++) {
                for (int j = minY; j<= maxY; j++) {
                    // 빈칸이 있으면 놓을수 없는 경우
                    if (board[i][j] == '.') {
                        return "-1";
                    }
                    // 위에 다른 색상의 카드가 있는 경우 나중에 놓아야 함
                    if (board[i][j] != groupColor[k]) {
                        g[k][colorGroup[board[i][j]]] = true;
                    }
                }
            }
        }
        answer = sort(g);
        return answer;
    }

    public static void main(String[] args) throws Exception {
        input();
        String answer = solution();
        System.out.println(answer);;
    }
}