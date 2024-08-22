# Can I Win
## Topics
Recursion, Memoization, Bitmask

## Complexity
Time: $O(2^n)$, where $n$ = `maxChoosableInteger`.

Space: $O(2^n)$ for the memoization table.

## Insights
1. With $n$ integers, there are $2^n$ states of the game (distinct subsets of $\{1, 2, 3, \ldots n\}$).
2. To save space, we can use bitmask of length `n` to identify these states. For example, a state of `110` means $3$ and $2$ are picked, where $1$ can be picked and advance to the next state `111`.
3. To determine whether a state is winnable, we need to know whether the next state is winnable. This naturally leads to a recursive solution.
4. Memoize recursion results to avoid reduntant computation.

## Helper function
### isStateWinnable
Given the current `state` bitmask and `desired` total of the game, return whether the player that moves first has a winning strategy.
1. Check the memoization table first. I use `1` to represent winnable, `-1` to represent not winnable, and `0` to represent uninitialized.
    ```java
    if (memo[state] != 0) return memo[state] > 0;
    ```
2. For each unused number `i`, advance `state` to `newState` using `i`. `state` by default is not winnable.
    ```java
    memo[state] = -1;
    for (int i = 1; i <= n; i++) {
        // ...
        if (((state >> (i - 1)) & 1) == 1) continue;
    }
    return false;
    ```
3. However, if picking `i` exceeds the `desired` total, then `state` is winnable (base case).
    ```java
    if (i >= desired) {
        memo[state] = 1;
        return true;
    }
    ```
4. `state` is also winnable if we can pick `i` and force the opponent into an unwinnable `nextState` (recursive case).
    ```java
    int nextState = state | (1 << (i - 1));
    if (!isStateWinnable(n, desired - i, nextState)) {
        memo[state] = 1;
        return true;
    }
    ```

## Main function
1. Handle the obvious case where it's a one step win.
    ```java
    if (desired <= n) return true;
    ```
2. Handle the impossible case where `desired` total is more than the sum of all numbers.
    ```java
    if (n * (n + 1) < 2 * desired) return false;
    ```
3. Initialize the memoization table and call the recursive helper.
    ```java
    memo = new int[1 << n];
    return isStateWinnable(n, desired, 0);
    ```