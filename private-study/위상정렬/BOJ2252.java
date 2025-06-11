import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ2252 {

    static int n, m;
    static ArrayList<Integer>[] arr;
    static int[] inDegree;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        arr = new ArrayList[n+1];
        inDegree = new int[n+1];

        for (int i = 1; i <= n; i++) {
            arr[i] = new ArrayList<>();
        }

        // 입력시 inDegree 정보를 저장
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            arr[a].add(b);
            inDegree[b]++;
        }
    }

    private static int[] solution() {
        int[] answer = new int[n+1];
        Queue<Integer> q = new LinkedList<>();

        // 차수가 0인 값을 큐에 추가
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                q.add(i);
            }
        }

        int index = 0;
        while (!q.isEmpty()) {
            int num = q.poll();
            answer[index] = num;
            index++;

            // 현재 인덱스에서 다음으로 서야할 학생 탐색
            for (int i = 0; i < arr[num].size(); i++) {
                int next = arr[num].get(i);
                inDegree[next]--;

                // 차수가 0이 된 경우 큐에 추가
                if (inDegree[next] == 0) {
                    q.add(next);
                }
            }
        }

        return answer;
    }

    private static void print(int[] answer) {
        for (int i = 0; i < n; i++) {
            System.out.print(answer[i] + " ");
        }
    }

    public static void main(String[] args) throws Exception {
        input();
        int[] answer = solution();
        print(answer);
    }
}