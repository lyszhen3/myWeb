package leetcode.algorithm;

public class 扰乱字符串 {

    /**
     * n是字符串得长度
     * <<标识递归
     * dp[n-1,n-1] = dp[0,n-2]<<&(s[n-1] == s1[n-1] || s[n-1]== s[n-1-(n-1)])
     * 
     * @param s1
     * @param s2
     * @return
     */
    public boolean isScramble(String s1, String s2) {

        if (s1 == null || s2 == null) {
            return false;
        }
        if (s1.length() != s2.length()) {
            return false;
        }
        if (s1.length() == 0 || s2.length() == 0) {
            return true;
        }

        int n = s1.length();
        Boolean[][] dp = new Boolean[n][n];

        return isScrambleRecursion(0, s1, s2, dp);
    }

    public boolean isScrambleRecursion(int start, String s1, String s2, Boolean[][] dp) {
        int n = s1.length();

        if (s1.equals(s2)) {
            dp[start][start + n - 1] = true;
            return true;
        }
        for (int i = 0; i < n; i++) {
            String lsl1 = s1.substring(0, i + 1);
            String lsl2 = s2.substring(0, i + 1);
            String lsr2 = s2.substring(n - 1 - i, n);

            if (lsl1.equals(lsl2)) {
                dp[start][i + start] = true;

                int sr = i + 1;
                Boolean rdp = dp[start + sr][start + n - 1];
                if (rdp == null) {
                    return isScrambleRecursion(start + sr, s1.substring(sr, n - 1), s2.substring(sr, n - 1), dp);
                }
            }
            if (lsl1.equals(lsr2)) {
                dp[start][start + i] = true;

                int sr = i + 1;
                Boolean rdp = dp[start + sr][start + n - 1];
                if (rdp == null) {
                    return isScrambleRecursion(start + sr, s1.substring(sr, n - 1), s2.substring(0, n - 1 - sr), dp);
                }
            }

            String rsr1 = s1.substring(i + 1, n);
            String rsr2 = s2.substring(i + 1, n);
            String rsl2 = s2.substring(0, n - 1 - i);

            if (rsr1.equals(rsr2)) {
                dp[start + i + 1][start + n - 1] = true;
                Boolean rdp = dp[start][start + i];
                if (rdp == null) {
                    return isScrambleRecursion(start, s1.substring(0, i + 1), s2.substring(0, i + 1), dp);
                }
            }
            if (rsr1.equals(rsl2)) {
                dp[start + i + 1][start + n - 1] = true;
                Boolean rdp = dp[start][start + i];
                if (rdp == null) {
                    return isScrambleRecursion(start, s1.substring(0, i + 1), s2.substring(n - 1 - i, n - 1), dp);
                }
            }
            dp[start][start + i] = false;
        }

        return false;
    }

    public static void main(String[] args) {

        扰乱字符串 m = new 扰乱字符串();
        System.out.println(m.isScramble("greate", "rgeate"));

    }

}