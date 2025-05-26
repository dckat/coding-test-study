import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ16987 {

    private static int n;
    private static int[] s = null;      // 내구도
    private static int[] w = null;      // 무게
    private static int answer;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        s = new int[n]; w = new int[n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            s[i] = Integer.parseInt(st.nextToken());
            w[i] = Integer.parseInt(st.nextToken());
        }
    }

    private static void solution(int index) {
        // n개를 모두 선택해서 계란치기를 수행한 경우
        if (index == n) {
            int tmp = 0;
            for (int i = 0; i < n; i++) {
                if (s[i] <= 0) {
                    tmp++;
                }
            }

            if (answer < tmp) {
                answer = tmp;
            }
            return;
        }

        // 선택한 계란이 이미 깨진 경우
        if (s[index] <= 0) {
            solution(index+1);
            return;
        }

        boolean ok = false;
        for (int i = 0; i < n; i++) {
            // 자기 자신은 칠수 없음
            if (index == i) {
                continue;
            }

            // 칠 수 있는 계란이 있으면 하나씩 선택하여 진행
            if (s[i] > 0) {
                ok = true;
                s[index] -= w[i];
                s[i] -= w[index];
                solution(index+1);
                s[index] += w[i];
                s[i] += w[index];
            }
        }

        // 모든 계란이 깨졌다면 다음으로 넘어감
        if (!ok) {
            solution(index+1);
        }
    }

    public static void main(String[] args) throws Exception {
        input();
        solution(0);
        System.out.println(answer);
    }
}