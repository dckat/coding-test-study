import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ16945 {

    // 3x3의 매직스퀘어를 1차원배열로 생성하여 풀이
    private static int[] a = new int[9];

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                a[i*3+j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static boolean check(int[] order) {
        // 행의 합
        for (int i = 0; i < 3; i++) {
            if (order[3*i+0] + order[3*i+1] + order[3*i+2] != 15) {
                return false;
            }
        }

        // 열의 합
        for (int j = 0; j < 3; j++) {
            if (order[j+0] + order[j+3] + order[j+6] != 15) {
                return false;
            }
        }

        // 대각선의 합
        if (order[0] + order[4] + order[8] != 15) {
            return false;
        }
        if (order[2] + order[4] + order[6] != 15) {
            return false;
        }
        return true;
    }

    private static int getDiff(int[] order) {
        int diff = 0;
        for (int i = 0; i < 9; i++) {
            diff += Math.abs(order[i] - a[i]);
        }

        return diff;
    }

    private static int permutation(int[] order, boolean[] used, int depth) {
        int answer = 100;
        if (depth == 9) {
            // 매직스퀘어 만족 시 비용 계산
            if (check(order)) {
                int diff = getDiff(order);
                return diff;
            }
        }

        // 가능한 순열을 visited를 활용하여 생성
        for (int i = 0; i < 9; i++) {
            if (!used[i]) {
                used[i] = true;
                order[depth] = i+1;
                int result = permutation(order, used, depth+1);
                if (result < answer) {
                    answer = result;
                }
                used[i] = false;
            }
        }
        return answer;
    }

    private static int solution() {
        // 순열을 만들기 위해 활용할 배열
        int[] order = new int[9];
        boolean[] used = new boolean[9];

        int answer = permutation(order, used, 0);
        return answer;
    }

    public static void main(String[] args) throws Exception {
        input();
        int answer = solution();
        System.out.println(answer);
    }
}