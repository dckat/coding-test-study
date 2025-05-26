import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ17825 {

    private static int[] a = new int[10];
    private static int[] cur = new int[4];
    private static int[] score = {0, 2, 4, 6, 8,
            10, 12, 14, 16, 18,
            20, 22, 24, 26, 28,
            30, 32, 34, 36, 38,
            13, 16, 19, 22, 24,
            28, 27, 26, 25, 30,
            35, 40, 0};
    // 현재 위치 기준 다음 인덱스
    private static int[] next = {1, 2, 3, 4, 5,
            6, 7, 8, 9, 10,
            11, 12, 13, 14, 15,
            16, 17, 18, 19, 31,
            21, 22, 28, 24, 28,
            26, 27, 28, 29, 30,
            31, 32};
    private static int answer = 0;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < 10; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }
    }

    private static int getNext(int step, int start) {
        int current = start;

        // 시작점이 파란색인지 확인
        if (current == 5) {
            current = 20;
        }
        else if (current == 10) {
            current = 23;
        }
        else if (current == 15) {
            current = 25;
        }
        else {
            current = next[current];
        }

        // 1칸씩 이동
        for (int i = 1; i < step; i++) {
            // 도착지에 온 경우
            if (current == 32) {
                break;
            }
            current = next[current];
        }
        return current;
    }

    private static void solution(int index, int curScore) {
        if (index == 10) {
            if (curScore > answer) {
                answer = curScore;
            }
            return;
        }

        for (int i = 0; i < 4; i++) {
            boolean ok = true;
            int tmp = cur[i];

            // 고른 말이 이미 도착하면 고르지 않음
            if (cur[i] == 32) {
                continue;
            }

            int nextLoc = getNext(a[index], cur[i]);

            // 도착지 제외 이동하려는 칸에 말이 있는지 확인
            if (nextLoc != 32) {
                for (int j = 0; j < 4; j++) {
                    if (i == j) {
                        continue;
                    }
                    if (cur[j] == nextLoc) {
                        ok = false;
                        break;
                    }
                }
            }

            // 이동이 가능한 경우
            if (ok) {
                cur[i] = nextLoc;
                solution(index+1, curScore+score[nextLoc]);
                cur[i] = tmp;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        input();
        solution(0, 0);
        System.out.println(answer);
    }
}