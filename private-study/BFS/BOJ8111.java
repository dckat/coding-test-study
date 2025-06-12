import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ8111 {

    static int n, num;
    static int[] from, how, dist;

    private static String solution(int num) {
        from = new int[num];    // 이전에 어느 수에서 왔는지 저장
        how = new int[num];     // 무엇을 붙였는지 저장 (0 또는 1)
        dist = new int[num];    // 수의 길이

        for (int i = 0; i < num; i++) {
            from[i] = how[i] = dist[i] = -1;
        }

        // 1을 나눈 나머지 셋팅
        Queue<Integer> q = new LinkedList<>();
        q.add(1%num);
        how[1%num] = 1;
        dist[1%num] = 0;

        // BFS 기반 나머지를 찾음
        while (!q.isEmpty()) {
            int cur = q.poll();

            // 0과 1을 붙여 다음 수의 나머지에 대한 정보 저장
            for (int i = 0; i <= 1; i++) {
                int next = (cur*10+i)%num;

                if (dist[next] == -1) {
                    dist[next] = dist[cur] + 1;
                    from[next] = cur;
                    how[next] = i;
                    q.add(next);
                }
            }
        }

        StringBuilder answer = new StringBuilder();

        // 배수가 존재하지 않는 경우
        if (dist[0] == -1) {
            return "BRAK";
        } else {
            // from.how 기반으로 어느 수인지 추적
            for (int i = 0; i != -1; i = from[i]) {
                answer.append(how[i]);
            }
            answer.reverse();
        }

        return answer.toString();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            num = Integer.parseInt(st.nextToken());

            String answer = solution(num);
            System.out.println(answer);;
        }

    }
}