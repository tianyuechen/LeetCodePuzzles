# Stickers to Spell Word
## Topics
DP, BitMask, Hashmap

## Complexity
Time: $O(m*2^n)$, where
- $m$ = length of `stickers`
- $n$ = length of `target`

Space: $O(2^n)$
## Insights
1. To define the subproblem, notice that stickers can spell out a **subsequence** of `target`, and there are $2^n$ distinct subsequences. Using a bitMask of $n$-bits, we can identify the subsequences and index into the DP table `T`. For example, for `target = "abcd"`, the bitMask of subsequence `"acd"` is `1101` (or `1011` depending on the implementation). 
2. Hence, `T[i]` = the minimum stickers to spell subsequence `i` of `target`.
3. For each `T[i]`, use each sticker to advance the current subsquence `i` and to a new subsequence `j` and record the minimum stickers used.
4. To reduce the constant factor `m`, we can filter out useless stickers that share no common characters with `target`.

## Helper functions
### useSticker

Use sticker `word` to update the subsequence `bitMask`. Sticker is represented as an array of character frequency. 
```java
private int useSticker(String target, int n, int[] word, int bitMask) {
    int[] used = new int[26];
    for (int i = 0; i < n; i++) {
        char c = target.charAt(i);
        if (((bitMask >> i) & 1) == 0 && used[c - 'a'] < word[c - 'a']) {
            bitMask |= 1 << i;
            used[c - 'a'] += 1;
        }
    }
    return bitMask;
}
```

## Main function
1. Filter out useless stickers. For useful stickers, only record the useful characters.
    ```java
    int n = target.length();
    List<int[]> words = new ArrayList<>();
    int targetSet = 0;
    for (int i = 0; i < n; i++) {
        targetSet |= 1 << (target.charAt(i) - 'a');
    }
    for (String sticker: stickers) {
        int[] stickerCounter = new int[26];
        boolean useful = false;
        for (int i = 0; i < sticker.length(); i++) {
            char c = sticker.charAt(i);
            if (((targetSet >> (c - 'a')) & 1) != 0) {
                useful = true;
                stickerCounter[c - 'a'] += 1;
            }
        }
        if (useful) {
            words.add(stickerCounter);
        }
    }
    ```
2. Create and advance DP table.
    ```java
    int[] T = new int[1 << n];
    Arrays.fill(T, -1);
    T[0] = 0;
    for (int i = 0; i < T.length - 1; i++) {
        if (T[i] == -1) continue;
        // apply each useful word to current subsequence
        for (int[] word: words) {
            int j = useSticker(target, n, word, i);
            if (T[j] == -1 || T[i] + 1 < T[j]) {
                T[j] = T[i] + 1;
            }
        }
    }
    return T[T.length - 1];
    ```