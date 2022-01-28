
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


class Main {
    static int documentc = 0;
    static void Algorithm(ArrayList<String> words, HashSet<String> table) {
        int p = 0;
        int q = words.size() - 1;
        int tmpP = 0;
        int tmpq = 0;
        HashMap<String, Integer> tmp = new HashMap<>(20000);
        int i = 0;
        while ((words.size() - tmpP) > table.size() && i < words.size()) { // iterate over the word but end loop if there  are not enough words
            tmp.put(words.get(i), i);
            if (tmp.size() == table.size()) {
                tmpP = words.size();
                for (int k : tmp.values()) { // get the minimum value from the this set to find the p value
                    tmpP = k < tmpP ? k : tmpP;
                }
                tmpq = i;
                if (tmpq - tmpP < (q - p)) { //set values
                    p = tmpP;
                    q = tmpq;
                }
                tmp.remove(words.get(tmpP)); // remove the smallest value from the list
            }
            i++;
        }
        System.out.println("Document " + (documentc++ + 1) + ": " + (p + 1) + " " + (q + 1));
    }
    static void read() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int input = 0;
        StringBuilder buildString = new StringBuilder();
        bf.readLine(); //skip no need to store
        ArrayList<String> words = new ArrayList<>(20000);
        HashSet<String> UniqueWords = new HashSet<>(20000);
        while ((input = bf.read()) != -1) {
            if ((input >= 'a' && input <= 'z') || (input == 'E' || input == 'N' || input == 'D')) {
                buildString.append((char) input);
            } else {
                String word = buildString.toString();
                switch (word) {
                    case "END":
                        Algorithm(words, UniqueWords);
                        words.clear();
                        UniqueWords.clear();
                        buildString.setLength(0);
                        break;
                    case "":
                        break;
                    default:
                        words.add(word);
                        UniqueWords.add(word);
                        buildString.setLength(0);
                        break;
                }
            }
        }

    }
    public static void main(String[] args) throws IOException {
        read();
    }
}
