import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ3012 {

    static int N, M;
    static String s;
    static long[][] dp;

    // 뒤의 5자리를 표현하기 위한 나머지 연산
    static long mod = 100000L;

    // 여는 괄호, 닫는 괄호
    static String open = "({[";
    static String close = ")}]";

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        dp = new long[N][N];

        s = br.readLine();

        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
        }
    }

    static long solution(int i, int j) {
        if (i > j) {
            return 1;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        long answer = 0;
        for (int k = i+1; k <= j; k+=2) {
            for (int l = 0; l < 3; l++) {
                if (s.charAt(i) == open.charAt(l) || s.charAt(i) == '?') {
                    if (s.charAt(k) == close.charAt(l) || s.charAt(k) == '?') {
                        long tmp = solution(i+1, k-1) * solution(k+1, j);
                        answer += tmp;

                        // 뒤의 다섯자리만 저장
                        if (answer >= mod) {
                            answer = mod + answer % mod;
                        }
                    }
                }
            }
        }

        dp[i][j] = answer;
        return answer;
    }

    public static void main(String[] args) throws Exception {
        input();
        long answer = solution(0, N-1);

        if (answer >= mod) {
            System.out.printf("%05d\n", answer % mod);
        }
        else {
            System.out.println(answer);
        }
    }
}