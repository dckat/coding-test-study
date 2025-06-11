import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ2056 {

    static int n;
    static ArrayList<Integer>[] arr;
    static int[] inDegree;
    static int[] works;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        arr = new ArrayList[n+1];
        inDegree = new int[n+1];
        works = new int[n+1];

        // 각 작업별 ArrayList 초기화
        for (int i = 1; i <= n; i++) {
            arr[i] = new ArrayList<>();
        }

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());

            works[i] = Integer.parseInt(st.nextToken());
            inDegree[i] = Integer.parseInt(st.nextToken());

            // 선행되어야 할 작업 추가
            for (int j = 0; j < inDegree[i]; j++) {
                int x = Integer.parseInt(st.nextToken());
                arr[x].add(i);
            }
        }
    }

    private static int solution() {
        int answer = 0;
        Queue<Integer> q = new LinkedList<>();
        int[] d = new int[n+1];

        // 차수가 0인 값을 큐에 추가
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                q.add(i);
                d[i] = works[i];
            }
        }

        while (!q.isEmpty()) {
            int num = q.poll();

            // 현재 인덱스에서 다음으로 수행할 작업들 순회
            for (int i = 0; i < arr[num].size(); i++) {
                int next = arr[num].get(i);
                inDegree[next]--;

                if (d[next] < d[num] + works[next]) {
                    d[next] = d[num] + works[next];
                }

                // 차수가 0이 된 경우 큐에 추가
                if (inDegree[next] == 0) {
                    q.add(next);
                }
            }
        }

        // 가장 오래걸리는 시간의 작업을 찾음 (이것이 정답)
        for (int i = 1; i <= n; i++) {
            if (answer < d[i]) {
                answer = d[i];
            }
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        input();
        int answer = solution();
        System.out.println(answer);;
    }
}