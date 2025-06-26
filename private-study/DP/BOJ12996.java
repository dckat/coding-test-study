import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ12996 {

    // 부를 수 있는 경우의 수
    static int[][] CASE = new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}, {1, 1, 0}, {1, 0, 1}, {0, 1, 1}, {1, 1, 1}};
    static final long MOD = 1000000007;

    static int S, A, B, C;
    static long[][][][] d;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        S = Integer.parseInt(st.nextToken());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        d = new long[S+1][A+1][B+1][C+1];
    }

    private static long solution(int s, int a, int b, int c) {
        // 불가능한 경우
        if (a == -1 || b == -1 || c == -1 || d[s][a][b][c] == -1) {
            return 0;
        }

        // 이미 경우의 수를 가져온 경우
        if (d[s][a][b][c] > 0) {
            return d[s][a][b][c];
        }

        if (s == 0) {
            // 가능한 경우
            if (a == 0 && b == 0 && c == 0) {
                return 1;
            }
            return 0;
        }

        // 가능한 모든 경우를 7가지로 나누어 구함
        long v = 0;
        for (int i = 0; i < 7; i++) {
            v += solution(s-1, a-CASE[i][0], b-CASE[i][1], c-CASE[i][2]);
        }

        // 불가능한 경우
        if (v == 0) {
            d[s][a][b][c] = -1;
            return 0;
        }

        // 가능한 경우 나눈 나머지를 리턴
        d[s][a][b][c] = (int) (v % MOD);
        return d[s][a][b][c];
    }

    public static void main(String[] args) throws Exception {
        input();
        long answer = solution(S, A, B, C);
        System.out.println(answer);
    }
}