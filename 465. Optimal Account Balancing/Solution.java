class Solution {
    public int minTransfers(int[][] transactions) {
        int[] balances = new int[12];
        for (int[] txn : transactions) {
            int from = txn[0];
            int to = txn[1];
            int amt = txn[2];
            balances[from] -= amt;
            balances[to] += amt;
        }
        return minTxn(balances, 0);
    }

    // backtracking solution
    private int minTxn(int[] balances, int i) {
        // all accounts are balanced
        if (i == 12)
            return 0;
        // balances[i] is already balanced, skip
        if (balances[i] == 0)
            return minTxn(balances, i + 1);

        // try balance balances[i] with a balances[j] that has an opposite sign
        int balance_i = balances[i];
        int cnt = Integer.MAX_VALUE;
        for (int j = i + 1; j < 12; j++) {
            if (balance_i * balances[j] < 0) {
                balances[i] -= balance_i;
                balances[j] += balance_i;
                cnt = Math.min(cnt, 1 + minTxn(balances, i + 1));
                balances[j] -= balance_i;
                balances[i] += balance_i;
            }
        }
        return cnt;
    }
}