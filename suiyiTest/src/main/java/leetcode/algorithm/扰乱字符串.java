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

        int n = s1.length();
        Boolean[][] dp = new Boolean[n][n];

        return isScrambleRecursion(s1, s2, dp);
    }

    public boolean isScrambleRecursion(String s1, String s2, Boolean[][] dp) {
        int n = s1.length();
        for (int i = 0; i < n; i++) {
            String sl1 = s1.substring(0, i + 1);
            String sl2 = s2.substring(0, i + 1);
            String sr2 = s2.substring(n - 1 - i, n);

            if (sl1.equals(sl2)) {
                dp[0][i] = true;
                if (i == n - 1) {
                    return true;
                }
                int sr = i + 1;
                Boolean rdp = dp[sr][n - 1];
                if (rdp == null) {
                    return isScrambleRecursion(s1.substring(sr, n - 1), s2.substring(sr, n - 1), dp);
                } else {
                    return rdp;
                }
            }
            if (sl1.equals(sr2)) {
                dp[0][i] = true;
                if (i == n - 1) {
                    return true;
                }
                int sr = i + 1;
                Boolean rdp = dp[sr][n - 1];
                if (rdp == null) {
                    return isScrambleRecursion(s1.substring(sr, n - 1), s2.substring(0, n - 1 - sr), dp);
                } else {
                    return rdp;
                }
            }
            dp[0][i] = false;
        }

        return false;
    }

    public static void main(String[] args) {

        扰乱字符串 m = new 扰乱字符串();
        System.out.println(m.isScramble("great", "rgeat"));

    }

}