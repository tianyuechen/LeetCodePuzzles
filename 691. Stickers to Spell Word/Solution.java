class Solution {
    public int minStickers(String[] stickers, String target) {
        // filter out useless stickers
        int n = target.length();
        List<int[]> words = new ArrayList<>();
        int targetSet = 0;
        for (int i = 0; i < n; i++) {
            targetSet |= 1 << (target.charAt(i) - 'a');
        }
        for (String sticker : stickers) {
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
        // DP, T[i] = min stickers to spell out target with bitMask i
        int[] T = new int[1 << n];
        Arrays.fill(T, -1);
        T[0] = 0;
        for (int i = 0; i < T.length - 1; i++) {
            if (T[i] == -1)
                continue;
            // apply each useful word to current subsequence
            for (int[] word : words) {
                int j = useSticker(target, n, word, i);
                if (T[j] == -1 || T[i] + 1 < T[j]) {
                    T[j] = T[i] + 1;
                }
            }
        }
        return T[T.length - 1];
    }

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
}