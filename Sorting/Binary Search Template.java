/**
 * The short rule to remember is: if you used hi = mid - 1, then use the higher midpoint. 
 * If you used lo = mid + 1, then use the lower midpoint. If you used both of these, then you can use either midpoint.
 *  If you didn’t use either (i.e., you have lo = mid and hi = mid), then, unfortunately, your code is buggy, and you won’t be able to guarantee convergence.
 * 
 * Whenever we want the upper middle, we use either mid = (lo + hi + 1) / 2 or mid = lo + (hi - lo + 1) / 2.
 *  These formulas ensure that on even-lengthed search spaces, the upper middle is chosen and on odd-lengthed search spaces, the actual middle is chosen
 */