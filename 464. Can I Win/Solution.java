class Solution {
    // Memoization table. memo[bitMask] = -1/0/1 means lost/uninitialized/win
    int[] memo;

    public boolean canIWin(int n, int desired) {
        // easy case: one step win
        if (desired <= n)
            return true;
        // impossible case
        if (n * (n + 1) < 2 * desired)
            return false;

        memo = new int[1 << n];
        return isStateWinnable(n, desired, 0);
    }

    // state is a n-bit bitMask of numbers n, n-1, ..., 2, 1
    // set bits means numbers are used
    // returns whether state is winnable if played first
    private boolean isStateWinnable(int n, int desired, int state) {
        if (memo[state] != 0)
            return memo[state] > 0;

        memo[state] = -1;
        for (int i = 1; i <= n; i++) {
            // skip used numbers
            if (((state >> (i - 1)) & 1) == 1)
                continue;
            // base case: one step win
            if (i >= desired) {
                memo[state] = 1;
                return true;
            }
            int newState = state | (1 << (i - 1));
            // if the oppoenent can't win after us, then picking i at current state
            // is a winning strategy
            if (!isStateWinnable(n, desired - i, newState)) {
                memo[state] = 1;
                return true;
            }
        }
        return false;
    }
}