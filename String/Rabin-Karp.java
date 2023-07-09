import java.util.ArrayList;
import java.util.List;


public class Solution {
    static final int BASE = 26;

    public static List<Integer> stringMatch(String text, String pattern) {
        List<Integer> occurrences = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        if (n < m) {
            return occurrences;
        }

        long patternHash = calculateHash(pattern);
        long textHash = calculateHash(text.substring(0, m));

        if (patternHash == textHash && text.substring(0, m).equals(pattern)) {
            occurrences.add(1); //1- based index
        }

        for (int i = 1; i <= n - m; i++) {
            char oldChar = text.charAt(i - 1);
            char newChar = text.charAt(i + m - 1);
            textHash = updateHash(textHash, oldChar, newChar, m);

            if (patternHash == textHash) {
                if (text.substring(i, i + m).equals(pattern)) {
                    occurrences.add(i + 1); //1- based index
                }
            }
        }

        return occurrences;
    }

    static long calculateHash(String str) {
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash * BASE + (str.charAt(i) - 'a'));
        }
        return hash;
    }

    static long updateHash(long oldHash, char oldChar, char newChar, int patternLength) {
        long powerBase = 1;
        for (int i = 0; i < patternLength; i++) {
            powerBase = (powerBase * BASE);
        }

        long removeHash = (oldChar - 'a') * powerBase;
        long addHash = (newChar - 'a') ;

        long newHash = ((oldHash * BASE)+ addHash - removeHash );
        return newHash;
    }
}
