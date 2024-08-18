# Heaters
## Topics
Binary Search, Two Pointers, Sorting

## Complexity
Time: O(nlog(k)), where
- n = length of `houses` + length of `heaters`, and 
- k = maximum distance between a house and a heater

Space: O(1)
## Insights
1. To verify whether `heaters` can cover all `houses` with radius `r` efficiently, both arrays need to be sorted.
2. For each heater, try cover as many houses before moving to the next.
3. Use binary search to find the smallest `r` that makes `heaters` cover all `houses`.

## Helper functions
### canCover
Given a radiu `r`, returns whether `heaters` can cover all `houses`.
```java
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
```

## Main function
1. Sort `houses` and `heaters`.
    ```java
    Arrays.sort(houses);
    Arrays.sort(heaters); roots[i] = i;
    ```
2. Binary search for `radius`. The lower bound of `radius` is 0 (each house has its own heater at its location). The upper bound is the maximum distance between a house and a heater.
    ```java
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
    ```