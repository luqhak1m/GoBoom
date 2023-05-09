public class TestClass {
    public static void main(String[] args) {

        Deck c1 = new Deck();

        System.out.println("At the beginning of a game. The first lead card s7 is placed at the center. Player3 is the first player because of s7. Player3 plays s3.");
        System.out.println("c = club");
        System.out.println("d = diamond");
        System.out.println("h = heart");
        System.out.println("s = spade \n");

        //test getDeck method (original and randomised deck)
        c1.addCards();
        System.out.println("Original deck: " + "\n" + c1.getDeck() + "\n" + "Lead card: " 
        + c1.getLeadCard() + "\n");

        c1.randomiseDeck();
        System.out.println("Randomised deck: " + "\n" + c1.getDeck() + "\n" + "Lead card: " 
        + c1.getLeadCard() + "\n");

        c1.randomiseDeck();
        System.out.println("Randomised deck: " + "\n" + c1.getDeck() + "\n" + "Lead card: " 
        + c1.getLeadCard() + "\n");

    }
    
    
    
}
