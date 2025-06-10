import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ14056 {

    static String s;
    static int k;

    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        s = st.nextToken();

        st = new StringTokenizer(br.readLine());
        k = Integer.parseInt(st.nextToken());
    }

    private static ArrayList<String> getStrings() {
        ArrayList<String> strList = new ArrayList<>();
        strList.add("()");

        // 가능한 모든 올바른 문자열을 생성
        for (int len = 4; len <= s.length(); len+=2) {
            for (int i = 0; i < strList.size(); i++) {
                String cur = strList.get(i);
                if ((len-2) % cur.length() == 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("(");

                    for (int j = 0; j < (len-2)/cur.length(); j++) {
                        sb.append(cur);
                    }
                    sb.append(")");
                    strList.add(sb.toString());
                }
            }
        }
        return strList;
    }

    private static String solution() {
        ArrayList<String> strList = getStrings();

        // 사전 순으로 정렬
        Collections.sort(strList);

        for (String cur: strList) {
            int index = 0;

            // 입력받은 문자열의 일부분이 올바른 문자열과 같은지 확인
            for (int i = 0; index < cur.length() && i < s.length(); i++) {
                if (cur.charAt(index) == s.charAt(i)) {
                    index++;
                }
            }

            // 찾은 경우
            if (index == cur.length()) {
                k--;

                // k번째 찾은 경우
                if (k == 0) {
                    return cur;
                }
            }
        }
        return "-1";
    }


    public static void main(String[] args) throws Exception {
        input();
        String answer = solution();
        System.out.println(answer);
    }
}