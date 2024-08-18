class Solution {
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int lo = 0;
        int hi = Math.max(houses[houses.length - 1] - heaters[0],
                          heaters[heaters.length - 1] - houses[0]);
        int res = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (canCover(houses, heaters, mid)) {
                res = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return res;
    }

    private boolean canCover(int[] houses, int[] heaters, int r) {
        int i = 0;
        int j = 0;
        while (i < houses.length && j < heaters.length) {
            if (Math.abs(houses[i] - heaters[j]) <= r) {
                i += 1;
            } else {
                j += 1;
            }
        }
        return i == houses.length;
    }
}