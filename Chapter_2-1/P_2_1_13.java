import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_1_13 {
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

    private static boolean less(int i, int j) {
        return i < j;
    }

    // Rule 1: we could only check 2 cards
    // -- sortSuit: check suit of card i one by one
    // -- sortRank: check card j and card j - 1 in each block

    // Rule 2: we could only swap 2 cards
    // -- sortSuit: swap card i and card next
    // -- sortRank: swap card j and card j - 1 
    public static void sortSuit(Card[] deck) {
        int N = deck.length;

        // Sort by suit O(N)
        // deck[0...12]: suit
        // deck[13...25]: hearts
        // ...
        int next = 0;
        for (Suit s: Suit.values()) {
            for (int i = next; i < N; i++) {
                if (deck[i].getSuit().equals(s)) {
                    // if find target suit, swap it to the front
                    swap(deck, i, next);
                    next++;
                }
            }
        }
    }

    public static void sortRank(Card[] deck) {
        for (int block = 0; block < 4; block++) {
            int start = block * 13;
            int end = start + 13;
            // insertion sort
            for (int i = start + 1; i < end; i++) {
                for (int j = i; j > start && 
                less(deck[j].getRank().ordinal(), deck[j-1].getRank().ordinal()); j--) {
                    swap(deck, j, j-1);
                }
            }
        }
    }

    public static void main(String[] args) {
        // Build a deck
        Card[] deck = new Card[52];
        int i = 0;
        for (Suit s : Suit.values()) {
            for (Rank r: Rank.values()) {
                deck[i++] = new Card(s, r);
            }
        }

        // shuffle the deck
        shuffle(deck);
        StdOut.println("Shuffled deck:");
        StdOut.println(Arrays.toString(deck));

        // Sort the deck by suit and rank
        sortSuit(deck);
        StdOut.println("Sorted by suit:");
        StdOut.println(Arrays.toString(deck));

        sortRank(deck);
        StdOut.println("Sorted by rank:");
        StdOut.println(Arrays.toString(deck));
    }
}


// Shuffled deck:
// [SJ, S2, D3, C10, HA, C8, D6, C7, S9, H5, H2, C4, H9, C6, CK, H7, S8, H10, S6, HJ, SQ, HK, H8, S3, H6, S5, C2, SK, SA, DA, D8, H3, S4, CQ, S7, D5, C5, CJ, DJ, D9, S10, C9, D4, HQ, DK, CA, C3, H4, D10, D2, DQ, D7]
// Sorted by suit:
// [SJ, S2, S9, S8, S6, SQ, S3, S5, SK, SA, S4, S7, S10, H7, H10, HA, HJ, HK, H8, H6, H5, H3, H2, H9, HQ, H4, C2, C8, CK, C6, CQ, C4, C5, CJ, C9, C10, CA, C3, C7, D9, D6, D3, D4, D5, DK, DA, D8, DJ, D10, D2, DQ, D7]
// Sorted by rank:
// [SA, S2, S3, S4, S5, S6, S7, S8, S9, S10, SJ, SQ, SK, HA, H2, H3, H4, H5, H6, H7, H8, H9, H10, HJ, HQ, HK, CA, C2, C3, C4, C5, C6, C7, C8, C9, C10, CJ, CQ, CK, DA, D2, D3, D4, D5, D6, D7, D8, D9, D10, DJ, DQ, DK]