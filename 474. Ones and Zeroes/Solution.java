class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m+1][n+1];
        Map<String, Integer> oneFreq = new HashMap<>();
        int cnt;
        for (String s: strs) {
            if (!oneFreq.containsKey(s)) {
                cnt = 0;
                for (int i = 0; i < s.length(); i++) {
                    cnt += s.charAt(i) - '0';
                }
                oneFreq.put(s, cnt);
            }
            int minOnes = oneFreq.get(s);
            int minZeroes = s.length() - minOnes;
            for (int i = m; i >= minZeroes; i--) {
                for (int j = n; j >= minOnes; j--) {
                    dp[i][j] = Math.max(dp[i][j], 1 + dp[i-minZeroes][j-minOnes]);
                }
            }
        }
        return dp[m][n];
    }
}