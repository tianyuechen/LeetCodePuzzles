class Solution {
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        char[] ch1 = s1.toCharArray();
        char[] ch2 = s2.toCharArray();
        int m = ch1.length, n = ch2.length;
        int i = 0, j = 0;
        int s1Cnt = 0, s2Cnt = 0;
        int[][] hm = new int[n][];
        while (true) {
            // S1 exhausts before finding a period
            if (s1Cnt == n1)
                return s2Cnt / n2;
            // try to match ch1[i] to ch2[j]
            if (ch1[i++] == ch2[j]) {
                j++;
                if (j == n) {
                    s2Cnt++;
                    j = 0;
                }
            }
            if (i < m)
                continue;
            // arrived at the end of a s1, check map for period
            s1Cnt++;
            i = 0;
            // no period found
            if (hm[j] == null) {
                hm[j] = new int[] { s1Cnt, s2Cnt };
                continue;
            }
            // period found
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
        }
    }
}