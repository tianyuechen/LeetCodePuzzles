# Rearrange String k Distance Apart
## Topics
Priority Queue, Hashmap, Queue, Greedy

## Complexity
Time: $O(nlog26) = O(n)$, where $n$ = length of `s`.

Space: $O(k)$ for the waiting queue. $O(1)$ for the hashmap and priority queue. 
## Insights
1. Because we only have $n$ spaces, we want to somehow **prioritize** frequent characters first. Thus, we need a max heap of characters.
2. However, due to the spacing constraint, we need to make sure the character we just used does not enter the heap until $k$ characters later, i.e., we need a delay buffer of size $k$.
3. A delay buffer of size $k$ can be implemented with a queue, where the head of queue is removed once the queue size reaches $k$.

## Main function
1. Handle the obvious case where no rearranging is needed.
    ```java
    if (k <= 1) return s;
    ```
2. Construct the character frequency hashmap. An array-based hashmap uses less space and offers faster lookup and update.
    ```java
    int n = s.length();
    int[] freq = new int[26];
    for (int i = 0; i < n; i++) {
        freq[s.charAt(i) - 'a'] += 1;
    }
    ```
3. Add all entries of hashmap into the priority queue. Use a lambda function to link the comparator to the hashmap.
    ```java
    PriorityQueue<Character> pq = new PriorityQueue<>(
        (c1, c2) -> freq[c2 - 'a'] - freq[c1 - 'a']
    );
    for (char c = 'a'; c <= 'z'; c++) {
        if (freq[c - 'a'] > 0) pq.add(c);
    }
    ```
4. Create the waiting queue and string builder.
    ```java
    Queue<Character> waitQ = new LinkedList<>();
    StringBuilder res = new StringBuilder();
    ```
5. Append the most frequency remaining character to string builder and enqueue it to the waiting queue. Also deque the waiting queue if it has size `k`. Notice that when a character's frequency decreases from 1 to 0, we still enqueue it to the waiting queue, because it contributes to the spacing. However, we don't add it back to the max heap because its frequency is 0.
   ```java
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
   ```
6. When returning, check if all characters in `s` have been used. Characters may be stuck in the waiting queue due to the spacing rule. If this happens, there is no valid rearrangement.
   ```java
   return res.length() == n ? res.toString() : "";
   ```