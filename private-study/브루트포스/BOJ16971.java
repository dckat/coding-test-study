import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ16971 {

    static int n, m;
    static int[][] a;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        a = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                a[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static int calc() {
        int answer = 0;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < m-1; j++) {
                answer += a[i][j] + a[i+1][j] + a[i][j+1] + a[i+1][j+1];
            }
        }
        return answer;
    }

    private static void swapRow(int r1, int r2) {
        for (int i = 0; i < m; i++) {
            int tmp = a[r1][i];
            a[r1][i] = a[r2][i];
            a[r2][i] = tmp;
        }
    }

    private static void swapCol(int c1, int c2) {
        for (int i = 0; i < n; i++) {
            int tmp = a[i][c1];
            a[i][c1] = a[i][c2];
            a[i][c2] = tmp;
        }
    }

    private static int solution() {
        int answer = calc();        // 교환하지 않았을때의 B의 값을 먼저 구함
        int[] sumRow = new int[n];
        int[] sumCol = new int[m];

        // 각 행.열의 합을 구함
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sumRow[i] += a[i][j];
                sumCol[j] += a[i][j];
            }
        }

        int minRow = -1;
        int minCol = -1;

        // 더해지는 횟수에 기반하여 가장 작은 합의 행을 찾음
        for (int i = 1; i < n-1; i++) {
            sumRow[i] *= 4;
            sumRow[i] -= 2*(a[i][0] + a[i][m-1]);

            if (minRow == -1 || sumRow[minRow] > sumRow[i]) {
                minRow = i;
            }
        }

        // 더해지는 횟수에 기반하여 가장 작은 합의 열을 찾음
        for (int i = 1; i < m-1; i++) {
            sumCol[i] *= 4;
            sumCol[i] -= 2*(a[0][i] + a[n-1][i]);

            if (minCol == -1 || sumCol[minCol] > sumCol[i]) {
                minCol = i;
            }
        }

        if (minRow != -1) {
            // 다른 행과 첫번째 또는 마지막 행과 바꾸었을때 배열 B의 값이 변함
            // 가장 첫번째 행과 minRow 교환
            swapRow(0, minRow);
            int tmp = calc();
            if (answer < tmp) {
                answer = tmp;
            }
            swapRow(0, minRow);

            // 마지막 행과 minRow 교환
            swapRow(n-1, minRow);
            tmp = calc();
            if (answer < tmp) {
                answer = tmp;
            }
            swapRow(n-1, minRow);
        }

        if (minCol != -1) {
            // 다른 열과 첫번째 또는 마지막 열과 바꾸었을때 배열 B의 값이 변함
            // 가장 첫번째 열과 minCol 교환
            swapCol(0, minCol);
            int tmp = calc();
            if (answer < tmp) {
                answer = tmp;
            }
            swapCol(0, minCol);

            // 마지막 열과 minCol 교환
            swapCol(m-1, minCol);
            tmp = calc();
            if (answer < tmp) {
                answer = tmp;
            }
            swapCol(m-1, minCol);
        }

        return answer;
    }


    public static void main(String[] args) throws Exception {
        input();
        int answer = solution();
        System.out.println(answer);
    }
}