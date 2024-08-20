class Solution {
    public String rearrangeString(String s, int k) {
        if (k <= 1)
            return s;
        int n = s.length();
        int[] freq = new int[26];
        for (int i = 0; i < n; i++) {
            freq[s.charAt(i) - 'a'] += 1;
        }
        // maxHeap of characters (highest frequency at top)
        PriorityQueue<Character> pq = new PriorityQueue<>(
                (c1, c2) -> freq[c2 - 'a'] - freq[c1 - 'a']);
        for (char c = 'a'; c <= 'z'; c++) {
            if (freq[c - 'a'] > 0)
                pq.add(c);
        }
        // used to space characters at least k distance apart
        Queue<Character> waitQ = new LinkedList<>();
        StringBuilder res = new StringBuilder();
        // append the most "urgent" character that's not in waitQ
        while (!pq.isEmpty()) {
            char c = pq.poll();
            int remaining = freq[c - 'a'];
            if (remaining > 0) {
                freq[c - 'a'] = remaining - 1;
                res.append(c);
                waitQ.add(c);
            }
            if (waitQ.size() == k) {
                pq.offer(waitQ.remove());
            }
        }
        return res.length() == n ? res.toString() : "";
    }
}