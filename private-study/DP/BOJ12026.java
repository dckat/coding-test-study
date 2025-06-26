import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ12026 {

    static int n;
    static char[] a;
    static int[] d;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        a = new char[n+1];
        d = new int[n+1];

        String s = br.readLine();
        for (int i = 1; i <= n; i++) {
            a[i] = s.charAt(i-1);
        }
    }

    private static int solution() {
        Arrays.fill(d, -1);
        d[1] = 0;

        for (int i = 2; i <= n; i++) {
            char ch = a[i];

            // 현재칸에 따라 가능한 이전칸에서 올수 있는 최소 비용을 구함
            // 이전칸 역시 올수 있는 경우여야 함
            // 현재칸이 B --> 이전칸이 J
            if (ch == 'B') {
                for (int j = 1; j < i; j++) {
                    if (a[j] == 'J' && d[j] != -1) {
                        int tmp = d[j] + ((i-j)*(i-j));
                        if (d[i] == -1 || d[i] > tmp) {
                            d[i] = tmp;
                        }
                    }
                }
            }   // 현재칸이 O --> 이전칸이 B
            else if (ch == 'O') {
                for (int j = 1; j < i; j++) {
                    if (a[j] == 'B' && d[j] != -1) {
                        int tmp = d[j] + ((i-j)*(i-j));
                        if (d[i] == -1 || d[i] > tmp) {
                            d[i] = tmp;
                        }
                    }
                }
            }   // 현재칸이 J --> 이전칸이 O
            else if (ch == 'J') {
                for (int j = 1; j < i; j++) {
                    if (a[j] == 'O' && d[j] != -1) {
                        int tmp = d[j] + ((i-j)*(i-j));
                        if (d[i] == -1 || d[i] > tmp) {
                            d[i] = tmp;
                        }
                    }
                }
            }
        }

        return d[n];
    }

    public static void main(String[] args) throws Exception {
        input();
        int answer = solution();
        System.out.println(answer);
    }
}