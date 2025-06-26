import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ14238 {

    static int A, B, C;
    static int n;
    static int[][][][][] d;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        n = s.length();
        d = new int[n+1][n+1][n+1][3][3];

        // N일 동안 출근기록 확인
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == 'A') {
                A++;
            }
            else if (ch == 'B') {
                B++;
            }
            else if (ch == 'C') {
                C++;
            }
        }
    }

    // dp용 배열 초기화
    private static void init() {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k <= n; k++) {
                    for (int l = 0; l < 3; l++) {
                        Arrays.fill(d[i][j][k][l], -1);
                    }
                }
            }
        }
    }

    private static int solution(int a, int b, int c, int p1, int p2) {
        // N일 출근기록을 모두 확인한 경우 --> 가능한 경우
        if (a+b+c == n) {
            d[a][b][c][p1][p2] = 1;
            return d[a][b][c][p1][p2];
        }

        // 이미 확인한 경우
        if (d[a][b][c][p1][p2] != -1) {
            return d[a][b][c][p1][p2];
        }

        // 오늘 A가 출근 (아무때나 출근 가능)ㄴ
        if (a+1 <= A && solution(a+1, b, c, 0, p1) == 1) {
            d[a][b][c][p1][p2] = 1;
            return d[a][b][c][p1][p2];
        }
        // 오늘 B가 출근 (전날 B가 출근하였는지 확인)
        if (b+1 <= B && p1 != 1) {
            if (solution(a, b+1, c, 1, p1) == 1) {
                d[a][b][c][p1][p2] = 1;
                return d[a][b][c][p1][p2];
            }
        }
        // 오늘 C가 출근 (전날과 전전날에 C가 출근하였는지 확인)
        if (c+1 <= C && p1 != 2 && p2 != 2) {
            if (solution(a, b, c+1, 2, p1) == 1) {
                d[a][b][c][p1][p2] = 1;
                return d[a][b][c][p1][p2];
            }
        }

        d[a][b][c][p1][p2] = 0;
        return d[a][b][c][p1][p2];
    }

    private static String print(int a, int b, int c, int p1, int p2) {
        if (a+b+c == n) {
            return "";
        }

        if (a+1 <= A && solution(a+1, b, c, 0, p1) == 1) {
            return "A" + print(a+1, b, c, 0, p1);
        }
        if (b+1 <= B && p1 != 1) {
            if (solution(a, b+1, c, 1, p1) == 1) {
                return "B" + print(a, b+1, c, 1, p1);
            }
        }
        if (c+1 <= C && p1 != 2 && p2 != 2) {
            if (solution(a, b, c+1, 2, p1) == 1) {
                return "C" + print(a, b, c+1, 2, p1);
            }
        }
        return "";
    }

    public static void main(String[] args) throws Exception {
        input();
        init();
        int answer = solution(0, 0, 0, 0, 0);

        if (answer == 0) {
            System.out.println(-1);
        } else {
            System.out.println(print(0, 0, 0, 0, 0));
        }
    }
}