class Solution {
    public long minimumMoves(int[] nums, int k, int maxChanges) {
        // get indices of 1's in nums
        List<Integer> ones = new ArrayList<>(k);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1)
                ones.add(i);
        }
        // edge case: no 1's in nums
        if (ones.size() == 0)
            return 2 * k;
        Integer[] indices = ones.toArray(new Integer[ones.size()]);

        long minMoves = Long.MAX_VALUE;
        // iterate through all combinatinos of numGen + numCollect = k
        maxChanges = Math.min(maxChanges, k);
        for (int numGen = maxChanges; numGen >= maxChanges - 3; numGen--) {
            int numCollect = k - numGen;
            if (numGen >= 0 && 0 < numCollect && numCollect <= indices.length) {
                minMoves = Math.min(minMoves, numGen * 2 + collect(indices, numCollect));
            }
        }
        return minMoves;
    }

    // Find min moves to collect k 1's whose indices are specified by indices
    // indices.size() >= k
    private long collect(Integer[] indices, int k) {
        // find the subarray of length k with min moves
        long minMoves = 0;
        int m = k / 2;
        for (int i = 0; i < k; i++) {
            minMoves += (long) Math.abs(indices[i] - indices[m]);
        }
        // slide the subarray and update minMoves
        long curMoves = minMoves;
        int coeff = 2 * m + 1 - k;
        for (int i = 0; i < indices.length - k; i++) {
            curMoves -= indices[m] - indices[i];
            curMoves += indices[k + i] - indices[m + 1];
            curMoves += coeff * (indices[m + 1] - indices[m]);
            minMoves = Math.min(minMoves, curMoves);
            m += 1;
        }
        return minMoves;
    }
}