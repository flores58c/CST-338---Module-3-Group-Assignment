package deckCards;
import java.util.Random;
import java.util.Scanner;

public class assig3
{

   public static void main(String[] args)
   {
      //Utilizes same code to print cards to console, not replacing with method since prompt does not ask for it.

   	Card c1 = new Card('A',Card.Suit.HEARTS);
		Card c2 = new Card('r',Card.Suit.CLUBS);
		
		Hand h1 = new Hand();
		for(int i=0; i<h1.MAX_CARDS;i++)
		{
			h1.takeCard(c1);
			h1.takeCard(c2);
		}
		System.out.println("After deal: \n" + h1.toString());
		System.out.println("Card 5 is " + h1.inspectCard(5));
		while(h1.getNumCards() > 0)
		{
			System.out.println(h1.playCard());
		}
		System.out.println("Card 5 is " + h1.inspectCard(5));
		System.out.println("After playing all cards:\nHand = ("
            + h1.toString() + " )");
      //int that tracks cards printed to current line to help keep output clean
      int currentLineCardCount = 0;

      /**TESTING OF DECK CLASS**/
      //Declare a deck containing two packs of cards. Do not shuffle.
      Deck deck = new Deck(2);

      // Deal all the cards in a loop until the deck is empty
      // (dealt directly to the display/screen, not to any Hand objects just yet).
      System.out.println("2 PACKS BEFORE SHUFFLE:\n");
      while (deck.getTopCard() >= 0)
      {
         Card card = deck.dealCard();
         //Display each card as it comes off the deck.
         System.out.print(card.toString() + " / ");
         currentLineCardCount++;
         //5 cards per line
         if (currentLineCardCount >= 5)
         {
            //Print a new line
            System.out.println();
            //Reset amount of cards per line
            currentLineCardCount = 0;
         }
      }
      //Ensure amount of cards per line is reset to 0
      currentLineCardCount = 0;

      //Reset the deck by initializing it again (to the same two packs).
      deck.init(2);

      //Shuffle the deck this time
      deck.shuffle();

      //re-deal to the screen in a loop again.
      System.out.println("\n\n2 PACKS AFTER SHUFFLE:\n");

      while (deck.getTopCard() >= 0)
      {
         Card card = deck.dealCard();
         System.out.print(card.toString() + " / ");
         currentLineCardCount++;
         if (currentLineCardCount >= 5)
         {
            System.out.println();
            currentLineCardCount = 0;
         }
      }

      currentLineCardCount = 0;

      //Repeat this double deal, unshuffled, then shuffled, but this time using a single pack deck.
      System.out.println("\n\n1 PACK BEFORE SHUFFLE:\n");
      deck.init(1);
      while (deck.getTopCard() >= 0)
      {
         Card card = deck.dealCard();
         System.out.print(card.toString() + " / ");
         currentLineCardCount++;
         if (currentLineCardCount >= 5)
         {
            System.out.println();
            currentLineCardCount = 0;
         }
      }
      System.out.println("\n\n1 PACK AFTER SHUFFLE:\n");
      deck.init(1);
      deck.shuffle();
      while (deck.getTopCard() >= 0)
      {
         Card card = deck.dealCard();
         System.out.print(card.toString() + " / ");
         currentLineCardCount++;
         if (currentLineCardCount >= 5)
         {
            System.out.println();
            currentLineCardCount = 0;
         }
      }
      /**END TESTING OF DECK CLASS**/


      /**PHASE 4: DECK AND HAND CLASS TESTS**/
      Scanner input = new Scanner(System.in);

      //Ask the user (interactively) to select the number of players (a number from 1 to 10).
      System.out.println("\n\nEnter number of players, must enter between 1 and 10: ");
      int players = input.nextInt();

      //Validate a legal value
      while (players < 1 || players > 10)
      {
         System.out.println("Number must between 1 and 10");
         players = input.nextInt();
      }

      //Instantiate a single-pack Deck object without shuffling (Re-using deck here)
      deck = new Deck(1);

      //Deal deck into that many Hand objects, dealing all cards until the deck is empty.

      //Array of hands
      Hand[] hands = new Hand[players];
      for(int i = 0; i < players; i++)
      {
         hands[i] = new Hand();
      }

      int i = 0;
      //Deal cards until deck is empty
      while (deck.getTopCard() >= 0)
      {
         //Take a card off of the top of the deck, store it in hand
         hands[i].takeCard(deck.dealCard());
         //Ensures that i resets to 0 when it reaches max array element
         i++;
         i %= players;
      }

      //Display all the hands after the deal.
      System.out.println("\nHands from unshuffled deck:");
      for (Hand hand: hands)
      {
         System.out.print(hand.toString() + "\n");
      }

      //Reset the objects to their initial state, but this time shuffle the deck before a second deal (same # of players).
      deck.init(1);
      for (Hand hand: hands)
      {
         hand.resetHand();
      }

      //This time shuffle the deck before a second deal (same # of players).
      deck.shuffle();

      //reset i
      i = 0;

      //Deal cards until deck is empty
      while (deck.getTopCard() >= 0)
      {
         //Take a card off of the top of the deck, store it in hand
         hands[i].takeCard(deck.dealCard());
         //Ensures that i resets to 0 when it reaches max array element
         i++;
         i %= players;
      }

      //Display all the hands after the deal.
      System.out.println("\n\nHands from SHUFFLED deck:");
      for (Hand hand: hands)
      {
         System.out.print(hand.toString() + "\n");
      }

      /**END PHASE 4**/
   }
}

// class for creating cards
class Card
{
   public enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES}
   private char value;
   private Suit suit;
   private boolean errorFlag;

   public Card()
   {
      value = 'A';
      suit = Suit.SPADES;
   }

   public Card(char value, Suit suit)
   {
      set(value,suit);
   }

   public String toString()
   {
      if(errorFlag)
      {
         return "not valid";
      }
      else
      {
         return getValue() + " of " + getSuit();
      }
   }

   // this function initializes a card based on the values passed
   public boolean set(char value, Suit suit)
   {
      if(isValid(value,suit))
      {
         this.value = value;
         this.suit = suit;
         return true;
      }
      else
      {
         errorFlag = true;
         return false;
      }
   }

   // function checks if two cards equal each other
   public boolean equals(Card card)
   {
      return (value == card.value) && (suit == card.suit);
   }


   //this function uses an array as a reference to check if the value is valid
   //accVal == accepted values
   // boolean created inside function to check if the array contains the value
   private boolean isValid(char value, Suit suit)
   {
      char[] accVal = {'A','J','Q','K','2','3','4','5','6','7','8','9','T'};

      //immediately returns true if valid, returns false otherwise
      for(int i = 0; i <accVal.length; i++)
      {
         if(value == accVal[i])
         {
            return true;
         }
      }
      return false;
   }

   // accessors for value,suit and errorflag
   public char getValue()
   {
      return value;
   }

   public Suit getSuit()
   {
      return suit;
   }

   public boolean getErrorFlag()
   {
      return errorFlag;
   }

}

/*****************FERNANDO PHASE2*************/

class Hand
{
   public static int MAX_CARDS = 50;
   private Card[] myCards;
   private int numCards;

   //DEFAULT CONSTRUCTOR
   public Hand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   //RESET HAND - REMOVES ALL CARDS FROM THE DECK
   public void resetHand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   //TAKE CARD - ADDS A CARD TO THE NEXT AVAILABLE POSITION IN THE myCards ARRAY.
   public boolean takeCard(Card card)
   {
      if (numCards < MAX_CARDS)
      {
         myCards[numCards] = new Card(card.getValue(), card.getSuit());
         numCards++;
         return true;
      }
      return false;
   }

   //PLAY THE CARD - RETURNS AND REMOVES THE CARD
   public Card playCard()
   {
      Card card = myCards[numCards - 1];
      myCards[numCards -1] = null;
      numCards--;                        //decremented numCards
      return card;
   }

   //STRING REPRESENTATOIN OF HAND CLASS
   public String toString()
   {
      String hand = "Hand = ( ";
      if (numCards == 0)
      {
         hand += ")";
      }
      else
      {
         for (int i = 0; i < numCards - 1; i++)
         {
            hand += myCards[i] + ", ";
         }
         hand += myCards[numCards - 1] + " )";
      }
      return hand;
   }

   //RETURNS THE NUMBER OF CARDS
   public int getNumCards()
   {
      return numCards;
   }

   //INSPECT THE CARD IN HAND
   public Card inspectCard (int k)
   {
      if ((k <= numCards) && (k > 0))
      {
         return myCards[k - 1];
      }
      //Returns card with errorflag = true if k is less than 1 or greater than the size of the player's hand
      return new Card ('Z', Card.Suit.SPADES);
   }
}

/*** PHASE 3 ***/
class Deck
{
   //6 packs of cards
   public final int MAX_CARDS = 6;
   //Master pack containing all 52 cards
   private static Card[] masterPack;
   //Holds our current deck
   private Card[] cards;
   //Holds the index of the topCard
   private int topCard;

   public Deck()
   {
      allocateMasterPack();
      //Wording in prompt is unclear, so im going to assume prof wants us to call init() here
      init(1);
   }

   public Deck(int numPacks)
   {
      //Prompt doesn't specify how to deal with over 6 packs, so I am just going to call System.exit()
      if (numPacks > MAX_CARDS || numPacks < 1)
      {
         System.out.println("Fatal error");
         System.exit(1);
      }
      //If we didn't exit...
      allocateMasterPack();
      init(numPacks);
   }

   //Creates numPack copies of masterPack and stores them in cards array
   public void init (int numPacks)
   {
      cards = new Card[52 * numPacks];
      this.topCard = cards.length - 1;
      //iterates from 0 - numPacks * 52
      int cardCounter = 0;
      for (int i = 0; i < numPacks; i++)
      {
         for (Card card: masterPack)
         {
            cards[cardCounter] = card;
            cardCounter++;
         }
      }
   }

   //Fisher-Yates shuffle
   public void shuffle()
   {
      Random random = new Random();

      //Upper bound of array
      int upperBound = topCard;

      //iterate from topCard down to 1
      for (int i = upperBound; i > 1; i--)
      {
         //random integer between 0 and i inclusive
         int randomInt = random.nextInt(i + 1);

         //Deep copy of card at cards[i]
         Card copyCard = new Card(cards[i].getValue(), cards[i].getSuit());

         //Can now safely update cards[i] with new card (swap it)
         cards[i] = new Card(cards[randomInt].getValue(), cards[randomInt].getSuit());

         //Update cards[randomInt] with cards[i]'s old values
         cards[randomInt] = new Card(copyCard.getValue(), copyCard.getSuit());
      }
   }

   //returns and removes the card in the top occupied position of cards[].
   //returns null if the deck is empty
   public Card dealCard()
   {
      if (topCard >= 0)
      {
         //Need to create a deep copy so that we can safely delete the top card
         Card card = new Card(cards[topCard].getValue(), cards[topCard].getSuit());

         //Delete topCard and decrement
         cards[topCard] = null;
         topCard--;
         return card;
      }
      //if no cards are available
      return null;
   }

   //returns the index of the top card
   public int getTopCard()
   {
      return topCard;
   }

   /*
   Unclear if prompt is asking for the kth card, or the card at the kth index.
   So I am returning the card at the kth index.
   */
   //Accessor for an individual card.
   //Returns a card with errorFlag = true if k is bad.
   public Card inspectCard(int k)
   {
      if (k >= 0 && k <= topCard)
      {
         //return a deep copy so that the inspector can't change the card.
         return new Card(cards[k].getValue(), cards[k].getSuit());
      }
      //if no cards are available or bad k is passed
      return new Card('X', Card.Suit.SPADES);
   }

   //Initializes masterPack if it hasn't been done already.
   private static void allocateMasterPack()
   {
      //only initialize masterPack if it doesnt exist already
      if (masterPack == null)
      {
         masterPack = new Card[52];
         char[] accVal = {'A','J','Q','K','2','3','4','5','6','7','8','9','T'};
         int i = 0;

         //iterates over all 4 suits
         for (Card.Suit suit: Card.Suit.values())
         {
            //iterates over all valid values
            for (char value: accVal)
            {
               //creates new card from current suit and value, adds it to masterpack
               //Doesn't check for i exceeding 52
               masterPack[i] = new Card(value, suit);
               i++;
            }
         }
      }
   }
}