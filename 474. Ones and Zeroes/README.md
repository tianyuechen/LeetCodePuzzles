# Ones and Zeroes
## Topics
DP, Memoization

## Complexity
Time: $O(m\cdot n \cdot k)$, where $k$ = length of `strs`

Space: $O(m \cdot n)$ for the DP table
## Insights
1. As a start, identify the subproblem in the recursive solution. For each `s = strs[i]`, we are either picking or skipping it depending on the number of `zeroes` and `ones` picked so far.
2. To cover all the possible values of `i, ones, zeroes`, the `memo` table has to be a 3D array (`int[k][m+1][n+1]`).
3. `solve` should return 0 when `i == k`, since no numbers can be picked from an empty array.
4. As for the recurrence, `solve(i, zeroes, ones)` should be the maximum of
   1. `solve(i + 1, zeroes, ones)` (skipping)
   2. `1 + solve(i + 1, zeroes + numZeroes(s), ones + numOnes(s))` (picking)
5. If we are using a bottom-up `dp` table, the recurrence looks like: `dp[i][r][c] = max(dp[i - 1][r][c], 1 + dp[i-1][r - numZeroes(s)][c - numOnes(s)])`
6. Notice that when updating a cell in `dp[i]`, we are not checking any cells to the right or bottom in `dp[i-1]`. If we update each `dp[i]` from bottom right to the top left, we can reuse the same table for all strings.
7. Another small optimzation: because there can be duplicates in `strs`, we can use a hashmap that maps each string to its number of ones to avoid redundant calculation.
   
## Main function
1. Initialize DP table and onesFrequency hashmap.
    ```java
    int[][] dp = new int[m+1][n+1];
    Map<String, Integer> oneFreq = new HashMap<>();
    int cnt;
    ```
2. For each string in `strs`, find its zeroes and ones frequency, and update the dp table. Notice the bounds on the for loops to avoid negative indices.
    ```java
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
    ```