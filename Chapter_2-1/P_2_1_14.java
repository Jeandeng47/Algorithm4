import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_1_14 {
    enum Suit {
        SPADES,
        HEARTS,
        CLUBS,
        DIAMONDS
    }
    enum Rank {
        ACE, TWO, THREE, FOUR, FIVE,
        SIX, SEVEN, EIGHT, NINE, TEN, 
        JACK, QUEEN, KING
    }

    public static class Card {
        private final Suit suit;
        private final Rank rank;
        // convert to string
        private static final String[] RANKS =  {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        private static final char[] SUITS =  {'S','H','C','D'};

        public Card(Suit s, Rank r) {
            this.suit = s;
            this.rank = r;
        }

        public String toString() {
            return SUITS[suit.ordinal()] + RANKS[rank.ordinal()];
        }

        public Suit getSuit() {
            return this.suit;
        }

        public Rank getRank() {
            return this.rank;
        }
    }

    // A deck as a queue with head index
    // Rule 1: we could only check the top 2 cards, peek1() & peek2()
    // Rule 2: we could only swap the top 2 cards, swapTop()
    // Rule 3: we could only swap the top card to the bottom, rotate()

    public static class Deck {
        private final Card[] cards;
        private int head;

        public Deck(Card[] cards) {
            this.cards = cards;
            this.head = 0;
        }

        public int size() {
            return cards.length;
        }

        public Card peekFirst() {
            return cards[head];
        }

        public Card peekSecond() {
            return cards[(head + 1) % size()];
        }

        // Swap the top 2 cards
        public void swapTop() {
            int i = head;
            int j = (head + 1) % size();
            Card tmp = cards[i];
            cards[i] = cards[j];
            cards[j] = tmp; 
        }

        // Rotate the front to the bottom
        public void rotate() {
            head = (head + 1) % size();
        }

        public void printDeck() {
            for (int i = 0; i < size(); i++) {
                StdOut.print(cards[(head + i) % size()] + " ");
            }
            StdOut.println();
        }
    }

    private static void shuffle(Card[] deck) {
        Random rand = new Random();
        for (int i = deck.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1); // range: [0, i]
            swap(deck, i, j);
        }
    }

    public static void swap(Card[] deck, int i, int j) {
        Card tmp = deck[i];
        deck[i] = deck[j];
        deck[j] = tmp;
    }

    private static int compare(Card a, Card b) {
        // sort suit, then sort rank
        int c = a.getSuit().ordinal() - b.getSuit().ordinal();
        return (c != 0)? c : (a.getRank().ordinal() - b.getRank().ordinal());
    }
    

    public static void dequeSort(Deck deck) {
        int N = deck.size();
        // N‑1 passes
        for (int pass = 0; pass < N - 1; pass++) {
            // 1) bubble the largest of the remaining N‑pass cards to the "bottom"
            for (int i = 0; i < N - 1; i++) {
                if (compare(deck.peekFirst(), deck.peekSecond()) > 0)
                    deck.swapTop();
                deck.rotate();
            }
            // 2) now head=(original+N‑1)%N, so rotate once more to reset head
            deck.rotate();
        }
    }

    public static void main(String[] args) {
        // Build a deck
        Card[] cards = new Card[52];
        int i = 0;
        for (Suit s : Suit.values()) {
            for (Rank r: Rank.values()) {
                cards[i++] = new Card(s, r);
            }
        }

        // shuffle the deck
        shuffle(cards);
        StdOut.println("Shuffled deck:");
        StdOut.println(Arrays.toString(cards));

        // construct a deck structure
        Deck deck = new Deck(cards);
        dequeSort(deck);
        StdOut.println("After bubble sort: ");
        deck.printDeck();
    }
}


// Example: a = [4, 2, 5, 1, 3], head = 0
// N = 5, each pass does 4 compare + rotate, and
// one last rotate to bring head back to 0

// Pass 1
// Step     1st & 2nd       Action      array               head
//                                      [4, 2, 5, 1, 3] 
// 1        4,2             swap        [2, 4, 5, 1, 3]     0
//                          rotate                          1

// 2        4,5             no swap     [2, 4, 5, 1, 3]     1
//                          rotate                          2

// 3        5,1             swap        [2, 4, 1, 5, 3]     2
//                          rotate                          3


// 4        5,3             swap        [2, 4, 1, 3, 5]     3
//                          rotate                          4

// extra rotate
//                          rotate                          0 ((4 + 1) % 5 = 0)

// After pass 1, the largest element 5 is bubble up to end.

// Pass 2
// Step     1st & 2nd       Action      array               head
//                                      [2, 4, 1, 3, 5] 
// 1        2,4             no swap     [2, 4, 1, 3, 5]     0
//                          rotate                          1

// 2        4,1             swap        [2, 1, 4, 3, 5]     1
//                          rotate                          2

// 3        4,3             swap        [2, 1, 3, 4, 5]     2
//                          rotate                          3

// 4        4,5             no swap     [2, 1, 3, 4, 5]     3
//                          rotate                          4
// extra rotate
//                          rotate                          0 ((4 + 1) % 5 = 0)


// After pass 2, the second largest element 4 is bubble up to index 3.

// pass 3: 
// Step     1st & 2nd       Action      array               head
//                                      [2, 1, 3, 4, 5] 
// 1        2, 1            swap        [1, 2, 3, 4, 5]     0
//                          rotate                          1
// 2- 4     no swap...