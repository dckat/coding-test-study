#include <iostream>
using namespace std;

int n, m;
int a[500][500];
bool check[500][500];
int dx[] = {0, 0, 1, -1};
int dy[] = {1, -1, 0, 0};
int ans = 0;

// ㅜ 모양을 제외한 나머지 테트로미노는 연속된 4칸을 방문하는 형태의 블록
void go(int x, int y, int sum, int cnt) {
    if (cnt == 4) {
        if (ans < sum) {
            ans = sum;
        }
        return;
    }
    // 범위를 벗어난 경우
    if (x < 0 || x >= n || y < 0 || y >= m) {
        return;
    }
    // 이미 방문한 경우
    if (check[x][y]) {
        return;
    }

    check[x][y] = true;
    for (int i = 0; i < 4; i++) {
        go(x+dx[i], y+dy[i], sum+a[x][y], cnt+1);
    }
    check[x][y] = false;
}

int main() {
    cin >> n >> m;

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            cin >> a[i][j];
        }
    }

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            go(i, j, 0, 0);

            // ㅜ 모양의 블록은 별도의 if문으로 처리
            if (j+2 < m) {
                int temp = a[i][j] + a[i][j+1] + a[i][j+2];
                
                if (i-1 >= 0) {
                    int temp2 = temp + a[i-1][j+1];
                    if (temp2 > ans) {
                        ans = temp2;
                    }
                }
                if (i+1 < n) {
                    int temp2 = temp + a[i+1][j+1];
                    if (temp2 > ans) {
                        ans = temp2;
                    }
                }
            }
            if (i+2 < n) {
                int temp = a[i][j] + a[i+1][j] + a[i+2][j];

                if (j-1 >= 0) {
                    int temp2 = temp + a[i+1][j-1];
                    if (temp2 > ans) {
                        ans = temp2;
                    }
                }
                if (j+1 < m) {
                    int temp2 = temp + a[i+1][j+1];
                    if (temp2 > ans) {
                        ans = temp2;
                    }
                }
            }
        }
    }
    cout << ans << '\n';
    return 0;
}