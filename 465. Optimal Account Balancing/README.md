# Optimal Account Balancing
## Topics
Backtracking, Array

## Complexity
Time: $O(n\cdot2^n)$, where $n$ = number of people. In the worst case, $n$ people have debt, and an exhaustive search will visit all $2^n$ states (each person has two states, with and without debt). For each state, it can generate at most $n$ new states.
- $n$ = length of `houses` + length of `heaters`, and 
- $k$ = maximum distance between a house and a heater

Space: $O(n)$, because the recursion tree (or the call stack) has a maximum height = $n$.

## Insights
1. Without knowing that the problem is NP-complete, the tight constraint on the parameters hints that solution is an exhaustive search.  
2. Assume people start with $0 in their account and simulate all the transactions. Balancing all debt means bring back their account balances back to $0.
3. For each current state of balances, try all transactions that can reduce the number of people with debt by 1 and recurse. Record the minimum number of transactions and backtrack.

## Helper functions
### minTxn
Returns the minimum number of transactions required to balance all debts, given an array of `balances` representing each person's account balance and `i` as the target person whose account is currently being balanced.
```java
// backtracking solution
private int minTxn(int[] balances, int i) {
    // all accounts are balanced
    if (i == 12) return 0;
    // balances[i] is already balanced, skip
    if (balances[i] == 0) return minTxn(balances, i + 1);

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
```

## Main function
1. Simulate the transactions and record account balances in `balances`. `balances` have length 12 because the test cases have no more than 12 people.
    ```java
    int[] balances = new int[12];
    for (int[] txn: transactions) {
        int from = txn[0];
        int to = txn[1];
        int amt = txn[2];
        balances[from] -= amt;
        balances[to] += amt;
    }
    ```
2. Call all backtracking helper.
    ```java
    return minTxn(balances, 0);
    ```