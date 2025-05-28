import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ16953 {

    private static int a, b;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
    }

    private static int solution() {
        int answer = 1;

        // b가 a보다 작으면 만들수 없으므로 반복문 종료
        while (a <= b) {
            // 만들 수 있는 경우
            if (a == b) {
                return answer;
            }
            // 맨 끝자리가 1인 경우
            if (b % 10 == 1) {
                b = (b-1) / 10;
            }
            // 짝수인 경우 (2를 곱한 경우)
            else if (b % 2 == 0) {
                b /= 2;
            }
            // 만들수 없는 경우
            else {
                break;
            }
            answer++;
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
        input();
        int answer = solution();
        System.out.println(answer);
    }
}