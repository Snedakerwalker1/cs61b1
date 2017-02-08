/**
 * Created by wsnedaker on 2/6/2017.
 */
public class Palindrome {
    /*
    Turns a string into a Deque of characters
     */
    public static Deque<Character> wordToDeque(String word) {
        int index = 0;
        ArrayDequeSolution<Character> worddeque = new ArrayDequeSolution<>();
        while (index < word.length()) {
            worddeque.addLast(word.charAt(index));
            index += 1;
        }
        return worddeque;
    }

    /*
    Checks if the word is a palindrome using the Deque operation.
     */
    public static boolean isPalindrome(String word) {
        Deque<Character> worddeq = wordToDeque(word);
        if (worddeq.size() % 2 == 0) {
            while (worddeq.size() != 0) {
                if (worddeq.removeFirst() != worddeq.removeLast()) {
                    return false;
                }
            }
            return true;
        } else {
            while (worddeq.size() != 1) {
                if (worddeq.removeFirst() != worddeq.removeLast()) {
                    return false;
                }
            }
            return true;
        }
    }

    public static boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> worddeq = wordToDeque(word);
        if (worddeq.size() % 2 == 0) {
            while (worddeq.size() != 0) {
                if (!cc.equalChars(worddeq.removeFirst(), worddeq.removeLast())) {
                    return false;
                }
            }
            return true;
        } else {
            while (worddeq.size() != 1) {
                if (!cc.equalChars(worddeq.removeFirst(), worddeq.removeLast())) {
                    return false;
                }
            }
            return true;
        }
    }
}
