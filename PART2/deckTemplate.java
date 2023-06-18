public interface deckTemplate<T> {

    // Return the whole deck. 
    public T getDeck();

    // Set the deck.
    public void setDeck(T d);

    // Print whole deck.
    public void printDeck();

    // Add card to deck.
    public void addCard(Card card);

    // Check if deck is empty.
    public boolean emptyDeck();
}
