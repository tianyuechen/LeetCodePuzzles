class Solution {
    public int findNumberOfLIS(int[] nums) {
        List<List<int[]>> piles = new ArrayList<>();
        for (int num : nums) {
            int index = binSearch(piles, num);
            int cnt = 0;
            // track "pointers" to previous pile and update cnt
            if (index == 0) {
                cnt = 1;
            } else {
                for (int[] pair : piles.get(index - 1)) {
                    if (pair[0] < num)
                        cnt += pair[1];
                }
            }
            // insert num:cnt into piles
            if (index == piles.size()) {
                piles.add(new ArrayList<>());
            }
            piles.get(index).add(new int[] { num, cnt });
        }
        int res = 0;
        for (int[] pair : piles.get(piles.size() - 1)) {
            res += pair[1];
        }
        return res;
    }

    // find the first pile in piles whose top element >= target
    private int binSearch(List<List<int[]>> piles, int target) {
        int lo = 0;
        int hi = piles.size() - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            List<int[]> pile = piles.get(mid);
            List<int[]> prevPile = null;
            if (mid > 0)
                prevPile = piles.get(mid - 1);
            // compare pile top with target
            if (pile.get(pile.size() - 1)[0] < target) {
                lo = mid + 1;
            } else if (mid == 0 || prevPile.get(prevPile.size() - 1)[0] < target) {
                return mid;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }
}