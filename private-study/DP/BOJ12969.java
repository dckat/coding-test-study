import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ12969 {

    static int n, k;
    static char[] arr;
    static boolean[][][][] d;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        arr = new char[n];
        d = new boolean[n][n][n][n*(n-1)/2];
    }

    private static boolean solution(int i, int a, int b, int p) {
        // 길이가 n이 된 경우
        if (i == n) {
            // 쌍의 갯수가 K인 경우
            if (p == k) {
                return true;
            }
            return false;
        }

        // 이미 확인한 경우 --> 불가능한 것을 찾음 (가능할 경우에는 바로 return 하여 종료)
        if (d[i][a][b][p]) {
            return false;
        }
        d[i][a][b][p] = true;

        // A 추가
        arr[i] = 'A';
        if (solution(i+1, a+1, b, p)) {
            return true;
        }

        // B 추가
        arr[i] = 'B';
        if (solution(i+1, a, b+1, p+a)) {
            return true;
        }

        // C 추가
        arr[i] = 'C';
        if (solution(i+1, a, b, p+a+b)) {
            return true;
        }

        return false;
    }


    public static void main(String[] args) throws Exception {
        input();
        boolean answer = solution(0, 0, 0, 0);

        if (answer) {
            System.out.println(new String(arr));
        } else {
            System.out.println(-1);
        }
    }
}