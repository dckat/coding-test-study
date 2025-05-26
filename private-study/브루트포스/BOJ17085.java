import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ17085 {

    private static char[][] a;
    private static int n, m;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        a = new char[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String token = st.nextToken();
            a[i] = token.toCharArray();
        }
    }

    // 범위 체크
    private static boolean isBound(int num, int size, int boundary) {
        if (num - size >= 0 && num + size < boundary) {
            return true;
        }
        return false;
    }

    // 놓을 수 있는 위치인지 확인
    private static boolean canSet(int r, int c, int k, char C) {
        if (a[r-k][c] == C && a[r+k][c] == C && a[r][c-k] == C && a[r][c+k] == C) {
            return true;
        }
        return false;
    }

    private static int solution() {
        int answer = 0;

        for (int r1 = 0; r1 < n; r1++) {
            for (int c1 = 0; c1 < m; c1++) {
                // 십자가 확장 (상.하.좌.우)
                for (int k1 = 0;;k1++) {
                    // 범위와 놓을 수 있는 위치인지 확인
                    if (isBound(r1, k1, n) && isBound(c1, k1, m)) {
                        if (canSet(r1, c1, k1, '#')) {
                            a[r1-k1][c1] = '*';
                            a[r1+k1][c1] = '*';
                            a[r1][c1-k1] = '*';
                            a[r1][c1+k1] = '*';
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        break;
                    }

                    // 2번째 십자가 놓기
                    for (int r2 = 0; r2 < n; r2++) {
                        for (int c2 = 0; c2 < m; c2++) {
                            if (a[r2][c2] != '#') {
                                continue;
                            }
                            for (int k2 = 0;;k2++) {
                                if (isBound(r2, k2, n) && isBound(c2, k2, m)) {
                                    if (canSet(r2, c2, k2, '#')) {
                                        int tmp = (4*k1+1) * (4*k2+1);
                                        if (tmp > answer) {
                                            answer = tmp;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                                else {
                                    break;
                                }
                            }
                        }
                    }
                }

                // 원래대로 복구
                for (int k = 0;;k++) {
                    if (isBound(r1, k, n) && isBound(c1, k, m)) {
                        if (canSet(r1, c1, k, '*')) {
                            a[r1][c1-k] = '#';
                            a[r1][c1+k] = '#';
                            a[r1+k][c1] = '#';
                            a[r1-k][c1] = '#';
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        break;
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