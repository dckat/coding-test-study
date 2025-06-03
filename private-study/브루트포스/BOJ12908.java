import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Teleport {
    int x1;
    int y1;
    int x2;
    int y2;
}

public class Main {

    private static int sx, sy, ex, ey;
    private static Teleport[] t = new Teleport[3];

    public static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sx = Integer.parseInt(st.nextToken());
        sy = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        ex = Integer.parseInt(st.nextToken());
        ey = Integer.parseInt(st.nextToken());

        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());

            t[i] = new Teleport();
            t[i].x1 = Integer.parseInt(st.nextToken());
            t[i].y1 = Integer.parseInt(st.nextToken());
            t[i].x2 = Integer.parseInt(st.nextToken());
            t[i].y2 = Integer.parseInt(st.nextToken());
        }
    }

    public static long solution(int sx, int sy, int ex, int ey, Teleport[] t) {
        long answer = Math.abs(ex-sx) + Math.abs(ey-sy);

        for (int i = 0; i < t.length; i++) {
            Teleport[] ot = new Teleport[t.length - 1];

            for (int j = 0, k = 0; j < t.length; j++) {
                if (i == j) {
                    continue;
                }
                ot[k++] = t[j];
            }
            int x1 = t[i].x1; int y1 = t[i].y1;
            int x2 = t[i].x2; int y2 = t[i].y2;

            long tmp1 = Math.abs(x1-sx) + Math.abs(y1-sy) + 10L + solution(x2, y2, ex, ey, ot);
            long tmp2 = Math.abs(x2-sx) + Math.abs(y2-sy) + 10L + solution(x1, y1, ex, ey, ot);

            if (answer > tmp1) {
                answer = tmp1;
            }
            if (answer > tmp2) {
                answer = tmp2;
            }
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        input();
        long answer = solution(sx, sy, ex, ey, t);
        System.out.println(answer);
    }
}