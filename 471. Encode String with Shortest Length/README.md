# Encode String with Shortest Length
## Topics
DP, String

## Complexity
Time: $O(n^4)$, where $n$ = length of `s`.

Space: $O(n^2)$ for the DP table.

## Insights
1. To test all combinations of substrings, recursively partition the string into two substrings, find the shortest encoding for each, and concatenate them.
2. For string with fewer than 5 characters, encoding will not reduce the length (**base case 1**).
3. For periodic strings `s` like `abcabcabc`, the shortest encoding does require further partition. Use the trick `(s + s).indexOf(s, 1)` to determine the length of the period (`L`). If `s` has a period of length `L`, then `s` will appear at index `0, L, 2L...` in `s+s`. `indexOf(s, 1)` starts checking from index 1 and thus guarantees to return `L` (**base case 2**).
4. Now convert the recursive definition into a DP solution. We have a subproblem for each subtring of `s`, hence the DP table `T` should be a `String[n][n]` array.
5. Like the recursive solution, we need to build the solution bottom-up, so we solve the subproblem of shorter substrings first.

## Main function
1. Handle an easy base case and initialize the DP table. `T[l][r]` stores the result of `encode(S[l:r+1])`. `encoding` is a reusable string builder for concatenating strings.
    ```java
    int n = s.length();
    if (n <= 4) return s;
    String[][] T = new String[n][n];
    StringBuilder encoding = new StringBuilder(n);
    ```
2. Fill in the DP table bottom-up.
    ```java
    for (int len = 1; len <= n; len++) {
        for (int l = 0; l + len <= n; l++) {
            // ...
        }
    }
    ```
3. Handle **base case 1**.
    ```java
    String sub = s.substring(l, l + len);
    int r = l + len - 1;
    // length <= 4, no encoding
    if (len <= 4) {
        T[l][r] = sub;
        continue;
    }
    ```
4. Handle **base case 2**. If `sub` is not periodic, repeatIdx will be its length, and the shortest encoding is just itself. Otherwise, the encoding should be `k[p]`, where `p` is the shortest encoding of one period.
    ```java
    int repeatIdx = (sub + sub).indexOf(sub, 1);
    encoding.setLength(0);
    if (repeatIdx == sub.length()) {
        encoding.append(sub);
    } else {
        encoding.append(len / repeatIdx);
        encoding.append('[');
        encoding.append(T[l][l + repeatIdx - 1]); // could be nested brackets
        encoding.append(']');
    }
    ```
5. Try all possible partition and save the shortest combined encoding.
    ```java
    for (int mid = l + 1; mid <= r; mid++) {
        String leftEncoding = T[l][mid - 1];
        String rightEncoding = T[mid][r];
        if (leftEncoding.length() + rightEncoding.length() < encoding.length()) {
            encoding.setLength(0);
            encoding.append(leftEncoding);
            encoding.append(rightEncoding);
        }
    }
    T[l][r] = encoding.toString();
    ```
6. Return the final result.
    ```java
    return T[0][n-1];
    ```