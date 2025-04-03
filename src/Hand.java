package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hand {
    private List<Card> cards;
    private static final int MAX_SELECT_SIZE = 5;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void clear() {
        cards.clear();
    }

    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int getCardCount() {
        return cards.size();
    }

    public List<Card> selectCards(List<Integer> selectedIndexes) throws IllegalArgumentException {
        if (selectedIndexes.size() > MAX_SELECT_SIZE) {
            throw new IllegalArgumentException("Too many cards selected, max selection is " + MAX_SELECT_SIZE);
        }
        List<Card> selectedCards = new ArrayList<>();
        if (selectedIndexes.size() > cards.size()) {
            throw new IllegalArgumentException("Not Enough Cards in hand to select");
        }
        for (int index : selectedIndexes) {
            selectedCards.add(cards.get(index));
        }
        return selectedCards;
    }

    public void removeCards(List<Card> selectedCards) {
        cards.removeAll(selectedCards);
    }

    public static boolean isPair(List<Card> cards) {
        if (cards.size() != 2) return false;
        return getNumericValue(cards.get(0)) == getNumericValue(cards.get(1));
    }

    public static boolean isThreeOfAKind(List<Card> cards) {
        if (cards.size() != 3) return false;
        return getNumericValue(cards.get(0)) == getNumericValue(cards.get(1)) &&
               getNumericValue(cards.get(1)) == getNumericValue(cards.get(2));
    }

    public static boolean isFourOfAKind(List<Card> cards) {
        if (cards.size() != 4) return false;
        return getNumericValue(cards.get(0)) == getNumericValue(cards.get(1)) &&
               getNumericValue(cards.get(1)) == getNumericValue(cards.get(2)) &&
               getNumericValue(cards.get(2)) == getNumericValue(cards.get(3));
    }

    public static boolean isFullHouse(List<Card> cards) {
        if (cards.size() != 5) return false;
        List<Integer> values = getSortedValues(cards);
        return (values.get(0) == values.get(1) &&
                values.get(1) == values.get(2) &&
                values.get(3) == values.get(4)) ||
               (values.get(0) == values.get(1) &&
                values.get(2) == values.get(3) &&
                values.get(3) == values.get(4));
    }

    public static boolean isFlush(List<Card> cards) {
        if (cards.size() < 2) return false;
        String suit = cards.get(0).getSuit();
        for (Card card : cards) {
            if (!card.getSuit().equals(suit)) return false;
        }
        return true;
    }

    public static boolean isStraight(List<Card> cards) {
        if (cards.size() < 3) return false;
        List<Integer> values = getSortedValues(cards);

        boolean isNormalStraight = true;
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) != values.get(i-1) + 1) {
                isNormalStraight = false;
                break;
            }
        }

        boolean isAceLowStraight = false;
        if (values.contains(14)) {
            List<Integer> aceLowValues = new ArrayList<>(values);
            aceLowValues.remove(Integer.valueOf(14));
            aceLowValues.add(1);
            Collections.sort(aceLowValues);

            isAceLowStraight = true;
            for (int i = 1; i < aceLowValues.size(); i++) {
                if (aceLowValues.get(i) != aceLowValues.get(i-1) + 1) {
                    isAceLowStraight = false;
                    break;
                }
            }
        }

        return isNormalStraight || isAceLowStraight;
    }

    public static boolean isTwoPair(List<Card> cards) {
        if (cards.size() != 4) return false;
        Map<Integer, Integer> valueCounts = new HashMap<>();
        for (Card card : cards) {
            int val = getNumericValue(card);
            valueCounts.put(val, valueCounts.getOrDefault(val, 0) + 1);
        }
        int pairCount = 0;
        for (int count : valueCounts.values()) {
            if (count == 2) pairCount++;
        }
        return pairCount == 2;
    }

    private static int getNumericValue(Card card) {
        switch (card.getValue()) {
            case "A": return 14;
            case "K": return 13;
            case "Q": return 12;
            case "J": return 11;
            default: return Integer.parseInt(card.getValue());
        }
    }

    private static List<Integer> getSortedValues(List<Card> cards) {
        List<Integer> values = new ArrayList<>();
        for (Card card : cards) {
            values.add(getNumericValue(card));
        }
        Collections.sort(values);
        return values;
    }

    public static String determineHandType(List<Card> cards) {
        if (isFourOfAKind(cards)) return "Four of a Kind";
        if (isFullHouse(cards)) return "Full House";
        if (isFlush(cards)) return "Flush";
        if (isStraight(cards)) return "Straight";
        if (isThreeOfAKind(cards)) return "Three of a Kind";
        if (isTwoPair(cards)) return "Two Pair";
        if (isPair(cards)) return "Pair";
        return "High Card";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            sb.append(i + 1).append(": ").append(cards.get(i)).append("  ");
        }
        return sb.toString();
    }
}
