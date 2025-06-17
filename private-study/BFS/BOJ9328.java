import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ9328 {

    static int T;
    static int h, w;
    static char[][] board;

    static String key = null;               // 초기에 가지는 key
    static boolean[] keys;                  // 각 소문자별 열쇠의 존재 여부
    static boolean[][] visited;             // 각 테스트케이스별 방문 여부

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    // 범위 확인
    private static boolean isBound(int x, int y) {
        return 0 <= x && x < h+2 && 0 <= y && y < w+2;
    }

    private static int solution() {
        int answer = 0;

        Queue<int[]> q = new LinkedList<>();                // BFS 이동에서의 위치정보
        Queue<int[]>[] doors = new LinkedList[26];          // 열쇠를 대기하는 문의 위치 정보

        for (int i = 0 ; i < 26; i++) {
            doors[i] = new LinkedList<>();
        }

        q.add(new int[]{0, 0});
        visited[0][0] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];

            for (int k = 0; k < 4; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];

                if (isBound(nx, ny) && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    char next = board[nx][ny];

                    // 벽을 만난 경우
                    if (next == '*') continue;

                    // 빈 공간
                    if (next == '.') {
                        q.add(new int[]{nx, ny});
                    }
                    // 대문자 (문)
                    else if (Character.isUpperCase(next)) {
                        // 문에 맞는 키가 존재하면 열고 이동
                        if (keys[Character.toLowerCase(next)-'a']) {
                            q.add(new int[]{nx, ny});
                        }
                        // 존재하지 않으면 이동이 불가능하므로 해당 알파벳의 대기 큐로 이동
                        else {
                            doors[next-'A'].add(new int[]{nx, ny});
                        }
                    }
                    // 소문자 (열쇠)
                    else if (Character.isLowerCase(next)) {
                        q.add(new int[]{nx, ny});

                        // 발견한 열쇠가 기존에 없는 경우
                        if (!keys[next-'a']) {
                            keys[next-'a'] = true;

                            // 해당 열쇠와 매핑되는 문의 대기 큐에서 빼서 이동 큐에 추가
                            while(!doors[next-'a'].isEmpty()) {
                                q.add(doors[next-'a'].poll());
                            }
                        }
                    }
                    // 문서
                    else if (next == '$') {
                        answer++;
                        q.add(new int[]{nx, ny});
                    }
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        T = Integer.parseInt(st.nextToken());

        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            board = new char[h+2][w+2];
            visited = new boolean[h+2][w+2];

            for (int i = 1; i <= h; i++) {
                String line = br.readLine();
                for (int j = 1; j <= w; j++) {
                    board[i][j] = line.charAt(j-1);
                }
            }

            // 가장자리는 모두 이동할 수 있는 곳으로 처리
            for (int i = 0; i <= h+1; i++) {
                board[i][0] = '.';
                board[i][w+1] = '.';
            }
            for (int j = 1; j <= w; j++) {
                board[0][j] = '.';
                board[h+1][j] = '.';
            }

            key = br.readLine();
            keys = new boolean[26];

            // 시작할때 특정 소문자의 열쇠를 가지는 경우
            if (!key.equals("0")) {
                for (int k = 0; k < key.length(); k++) {
                    char ch = key.charAt(k);
                    keys[ch-'a'] = true;
                }
            }

            int answer = solution();
            System.out.println(answer);
        }
    }
}