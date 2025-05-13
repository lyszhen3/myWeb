package leetcode.algorithm;

/**
 * 假设我们有两个字符串 word1 和 word2，我们的目标是将 word1 转换成 word2。设 dp[i][j] 表示的是将 word1 的前 i
 * 个字符转换成 word2 的前 j 个字符所需的最小编辑操作数。
 *
 * 情况1: 字符相等
 * 如果 word1 的第 i 个字符（即 word1[i-1]）等于 word2 的第 j 个字符（即
 * word2[j-1]），那么这两个字符之间不需要任何编辑操作，因此 dp[i][j] = dp[i-1][j-1]。
 *
 * 情况2: 字符不等
 * 如果 word1[i-1] != word2[j-1]，那么我们需要考虑三种编辑操作：
 *
 * 替换：我们可以将 word1[i-1] 替换为 word2[j-1]，这样两个字符串的最后一个字符就相同了，然后问题就转化为了将 word1 的前 i-1
 * 个字符转换成 word2 的前 j-1 个字符，所以需要的操作数是 dp[i-1][j-1] + 1。
 *
 * 插入：在 word1 中插入 word2[j-1]，这相当于将 word1 的前 i 个字符转换成 word2 的前 j-1
 * 个字符后，再插入一个字符，因此需要的操作数是 dp[i][j-1] + 1。（考虑前i个字符转换为j-1，再额外插入第j个字符）
 *
 * 删除：从 word1 中删除 word1[i-1]，这就把问题转化为将 word1 的前 i-1 个字符转换成 word2 的前 j
 * 个字符，所需的操作数是 dp[i-1][j] + 1。（不用考虑删除的第i个字符，即前i-1个字符转换为前j个字符）
 * 
 * 先抛除临界点
 * 如果最后一个单词相同
 * dp[i][j] = dp[i-1][j-1]
 * 如果最后一个单词不同
 * dp[i][j] = min(dp[i-1][j],dp[i][j-1],dp[i-1][j-1]) + 1
 */
public class 编辑距离 {

    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }
        return dp[n][m];
    }

    public static void main(String[] args) {
        编辑距离 edit = new 编辑距离();
        String word1 = "horsess";
        String word2 = "ros";
        int result = edit.minDistance(word1, word2);
        System.out.println("最小编辑距离: " + result); // 输出: 3
    }

}
