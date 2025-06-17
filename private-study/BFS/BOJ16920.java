import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int n, m, p;
    static int[][] board;

    static int[] s;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        p = Integer.parseInt(st.nextToken());

        board = new int[n][m];
        s = new int[p+1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= p; i++) {
            s[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < n; i++) {
            String line = br.readLine();

            for (int j = 0; j < m; j++) {
                char ch = line.charAt(j);

                // 빈칸
                if (ch == '.') {
                    board[i][j] = 0;
                } // 벽
                else if (ch == '#') {
                    board[i][j] = -1;
                } // 성
                else {
                    board[i][j] = ch - '0';
                }
            }
        }
    }

    private static boolean isBound(int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < m;
    }

    private static int[] solution() {
        int[] answer = new int[p+1];

        Queue<int[]>[] q = new LinkedList[p+1];             // 현재 턴에서의 확장
        Queue<int[]>[] next_q = new LinkedList[p+1];        // 다음 턴에서의 확장

        for (int i = 1; i <= p; i++) {
            q[i] = new LinkedList<>();
            next_q[i] = new LinkedList<>();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] > 0) {
                    int num = board[i][j];
                    q[num].add(new int[]{i, j});
                }
            }
        }

        // 더 이상 확장이 없을때 까지 진행
        while (true) {
            boolean check = false;      // 단 한명이라도 확장이 가능한지 확인

            for (int i = 1; i <= p; i++) {
                int[][] d = new int[n][m];

                while (!q[i].isEmpty()) {
                    check = true;
                    int[] cur = q[i].poll();

                    int x = cur[0];
                    int y = cur[1];

                    // 다음 턴에서의 시작점
                    if (d[x][y] == s[i]) {
                        next_q[i].add(new int[]{x, y});
                    }

                    // 다른 사람의 성이 있는 경우 해당 부분은 확장하지 않음
                    if (board[x][y] > 0 && board[x][y] != i) {
                        continue;
                    }

                    board[x][y] = i;
                    for (int k = 0; k < 4; k++) {
                        int nx = x + dx[k];
                        int ny = y + dy[k];

                        if (isBound(nx, ny)) {
                            // 빈칸인 경우
                            if (board[nx][ny] == 0) {
                                d[nx][ny] = d[x][y] + 1;

                                // 플레이어 별 거리 이내에 성을 확장할 수 있는 경우
                                if (d[nx][ny] <= s[i]) {
                                    board[nx][ny] = i;
                                    q[i].add(new int[]{nx, ny});
                                }
                            }
                        }
                    }
                }
                q[i] = next_q[i];
                next_q[i] = new LinkedList<>();
            }

            // 모든 사람의 확장이 다 끝난 경우 종료
            if (!check) {
                break;
            }
        }

        // 확장이 모두 끝나고 플레이어별 성의 갯수를 카운팅
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] > 0) {
                    int num = board[i][j];
                    answer[num]++;
                }
            }
        }

        return answer;
    }

    private static void print(int[] answer) {
        for (int i = 1; i < answer.length; i++) {
            System.out.print(answer[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        input();
        int[] answer = solution();
        print(answer);
    }
}