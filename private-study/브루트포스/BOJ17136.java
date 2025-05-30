import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ17136 {

    private static int[][] board = new int[10][10];
    private static int[] avail = {0, 5, 5, 5, 5, 5};

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        for (int i = 0; i < 10; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 10; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static boolean check() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isBound(int x, int y, int k) {
        if (x+k-1 < 10 && y+k-1 < 10) {
            return true;
        }
        return false;
    }

    private static int go(int z) {
        // 모든 칸을 다 확인한 경우
        if (z == 100) {
            if (check()) {
                int cnt = 0;
                for (int i = 1; i <= 5; i++) {
                    cnt += (5 - avail[i]);
                }
                return cnt;
            }
            return -1;
        }
        int x = z / 10;
        int y = z % 10;

        // 이미 붙였거나 붙이지 않아도 되는 칸인 경우
        if (board[x][y] == 0) {
            return go(z+1);
        }

        int answer = -1;
        // 크기가 1x1부터 5x5 색종이까지 붙이기 시도
        for (int k = 1; k <= 5; k++) {
            // 현재 크기의 붙일수 있는 색종이가 있는 경우
            if (avail[k] > 0) {
                // 범위 확인
                if (isBound(x, y, k)) {
                    // 붙일 영역이 모두 1인지 우선 확인
                    boolean ok = true;
                    for (int i = x; i < x+k; i++) {
                        for (int j = y; j < y+k; j++) {
                            // 붙이려는 칸에 1이 없는 경우
                            if (board[i][j] == 0) {
                                ok = false;
                            }
                            if (!ok) {
                                break;
                            }
                        }
                    }

                    if (ok) {
                        for (int i = x; i < x+k; i++) {
                            for (int j = y; j < y+k; j++) {
                                board[i][j] = 0;
                            }
                        }
                        avail[k]--;
                        int tmp = go(z+1);
                        if (tmp != -1) {
                            if (answer == -1 || answer > tmp) {
                                answer = tmp;
                            }
                        }
                        avail[k]++;
                        for (int i = x; i < x+k; i++) {
                            for (int j = y; j < y+k; j++) {
                                board[i][j] = 1;
                            }
                        }
                    }
                }
            }
        }

        return answer;
    }


    private static int solution() {
        int answer = -1;
        answer = go(0);
        return answer;
    }

    public static void main(String[] args) throws Exception {
        input();
        int answer = solution();
        System.out.println(answer);
    }
}