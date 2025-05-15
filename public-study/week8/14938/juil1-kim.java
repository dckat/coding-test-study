import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ14938 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken()); //지역의 개수
        int m = Integer.parseInt(st.nextToken()); //수색 범위
        int r = Integer.parseInt(st.nextToken()); //길의 개수

        st = new StringTokenizer(br.readLine());

        int[] items = new int[n + 1]; // 해당 정점에 몇개의 아이템 수가 있었는지 배열에 저장

        for (int i = 1; i <= n; i++) {
            items[i] = Integer.parseInt(st.nextToken());
        }

        //그래프 만들기
        ArrayList<Node>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<Node>();
        }

        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            //양방향 관계 설정
            graph[from].add(new Node(to, cost));
            graph[to].add(new Node(from, cost));
        }

        int max = 0; //최대로 얻을 수 있는 아이템 개수

        //어느 시작점 부터 내려서 이동해야 최대 아이템 개수를 얻을 수 있을지 구함
        for (int i = 1; i <= n; i++) {
            //큰 아이템 개수로 갱신
            max = Math.max(max, dijkstra(n, i, graph, m, items));
        }

        //최대로 얻을 수 있는 아이템 개수
        System.out.println(max);
    }

    static int dijkstra(int n, int start, ArrayList<Node>[] graph, int m, int[] items) {

        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));

        int count = 0;
        int[] dist = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        dist[start] = 0;
        queue.add(new Node(start, 0));

        while (!queue.isEmpty()) {

            Node curNode = queue.poll();
            if (dist[curNode.v] < curNode.cost) continue;

            //현재 정점으로 들어왔다면 해당 정정에 있는 아이템 개수를 더한다.
            count += items[curNode.v];

            for (int i = 0; i < graph[curNode.v].size(); i++) {
                Node nxtNode = graph[curNode.v].get(i);
                // 다음 정점으로 이동하는 비용 < 갱신된 값 => 탐색 진행
                // 수색범위인 m보다 큰 경우, 탐색 X
                if (dist[nxtNode.v] > dist[curNode.v] + nxtNode.cost && dist[curNode.v] + nxtNode.cost <= m) {
                    dist[nxtNode.v] = dist[curNode.v] + nxtNode.cost;
                    queue.offer(new Node(nxtNode.v, dist[nxtNode.v]));
                }
            }
        }
        return count;
    }

    static class Node {
        int v;
        int cost;

        public Node(int v, int cost) {
            this.v = v;
            this.cost = cost;
        }
    }
}