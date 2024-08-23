# Count the Repetitions
## Topics
String, Two pointers, Math

## Complexity
Time: $O(m \cdot n)$, where
- $m$ = length of `s1`, and 
- $n$ = length of `s2`

Space: $O(n)$ for the array-based hashmap.

## Insights
1. For the brute force solution, we can loop through the entire `S1 = [s1, n1]`, count the occurrence of `s2`, and divide the total count `s2Cnt` by `n2`.
2. However, because `S1` is periodic, `s2Cnt` will also increase periodically. For example, let `s1 = acba` and `s2 = abcaa`.
We notice that after traversing the third and sixth `s1` in `S1`, we arrive at the same state where we just matched the first `a` in `s2`. 
In other words, `s2Cnt` increases by 1 for every 3 `s1` starting from the 4th `s1`. Thus, we can use this pattern to skip forward to the end of `S1` and count the last incomplete period.
    ```java
    /**
     * S1: acba | acba | acba | acba | acba | acba | acba
     *    (^ ^     ^ ^   ^)(^     ^     ^ ^   ^)(^     ^
     *                      pattern begins here
    ```
3. To detect such a pattern, after traversing each `s1`, record `j` (the next character to be matched in `s2`), `s1Cnt` (number of `s1` traversed so far), and `s2Cnt` (number of `s2` matched so far) in a hashmap. When a `j` is repeated, use the difference in `s1Cnt` and `s2Cnt` to calculate the period.

## Main function
1. Convert `s1` and `s2` to char arrays `ch1` and `ch2` for faster access. Initialize the two pointers `i`, `j` and counters `s1Cnt`, `s2Cnt`. Because we are looking for collisions of index `j`, the hashmap can implemented as an array of `(s1Cnt, s2Cnt)` paris.
    ```java
    char[] ch1 = s1.toCharArray();
    char[] ch2 = s2.toCharArray();
    int m = ch1.length, n = ch2.length;
    int i = 0, j = 0;
    int s1Cnt = 0, s2Cnt = 0;
    int[][] hm = new int[n][];
    ```
2. Infinite loop to match every character in `S1`.
    ```java
    while (true) {
        // ...
    }
    ```
3. Inside the loop, first handle an edge case where `S1` exhausts before finding a period.
    ```java
    if (s1Cnt == n1) return s2Cnt / n2;
    ```
4. Try matching `ch1[i]` to `ch2[j]` until we reach the end of `ch1`.
   ```java
    if (ch1[i++] == ch2[j]) {
        j++;
        if (j == n) {
            s2Cnt++;
            j = 0;
        }
    }
    if (i < m) continue;
    ```
5. Upon reaching the end of `ch1`, if no period is found, store the current `(s1Cnt, s2Cnt)` into the hashmap and continue.
    ```java
    s1Cnt++;
    i = 0;
    if (hm[j] == null) {
        hm[j] = new int[]{s1Cnt, s2Cnt};
        continue;
    }
    ```
6. If we find a period, then we calculate the total occurrences of `s2` (`res`) in three parts: before, inside, and after the repeating pattern. Then the total occurrences of `S2 = [s2, n2]` is just `res / n2`.
   ```java
    int s1CntOld = hm[j][0];
    int s2CntOld = hm[j][1];
    // number of s2 before repeating pattern
    int res = s2CntOld;
    // number of s2 inside repeating pattern
    res += (n1 - s1CntOld) / (s1Cnt - s1CntOld) * (s2Cnt - s2CntOld);
    // number of s2 after repeating pattern
    int n1Remainder = (n1 - s1CntOld) % (s1Cnt - s1CntOld);
    for (int k = 0; k < n1Remainder; k++) {
        for (i = 0; i < m; i++) {
            if (ch1[i] == ch2[j]) {
                j++;
                if (j == n) {
                    res++;
                    j = 0;
                }
            }
        }
    }
    return res / n2;
    ```
