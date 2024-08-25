class Solution {
    public String encode(String s) {
        int n = s.length();
        if (n <= 4) return s;
        // T[i][j] = encode(S[i:j+1])
        String[][] T = new String[n][n];
        StringBuilder encoding = new StringBuilder(n);
        for (int len = 1; len <= n; len++) {
            for (int l = 0; l + len <= n; l++) {
                String sub = s.substring(l, l + len);
                int r = l + len - 1;
                // length <= 4, no encoding
                if (len <= 4) {
                    T[l][r] = sub;
                    continue;
                }
                // encode the entire substring as k[...]
                int repeatIdx = (sub + sub).indexOf(sub, 1);
                encoding.setLength(0);
                // substring is not periodic (i.e., k = 1), no encoding
                if (repeatIdx == sub.length()) {
                    encoding.append(sub);
                // substring is periodic, encode as k[...]
                } else {
                    encoding.append(len / repeatIdx);
                    encoding.append('[');
                    encoding.append(T[l][l + repeatIdx - 1]); // this allows nested brackets
                    encoding.append(']');
                }

                // try partition and save the best result
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
            }
        }
        return T[0][n-1];
    }
}