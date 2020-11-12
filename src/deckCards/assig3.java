package deckCards;

import java.util.Arrays;

public class assig3
{
	public static void main(String[] args)
	{
		Card c1 = new Card();
		Card c2 = new Card('J',Card.Suit.CLUBS);
		Card c3 =  new Card('Z',Card.Suit.HEARTS);

		System.out.println(c1.toString());
		System.out.println(c2.toString());
		System.out.println(c3.toString());
		c1.set('W', Card.Suit.SPADES);
		c2.set('3', Card.Suit.DIAMONDS);
		c3.set('T', Card.Suit.HEARTS);
		System.out.println();
		System.out.println(c1.toString());
		System.out.println(c2.toString());
		System.out.println(c3.toString());
	}
}

class Card
{
	public enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES}
	private char value;
	private Suit suit;
	private boolean errorFlag;

	public Card()
	{
		value = 'A';
		suit = suit.SPADES;
	}

	//lower case a stands for assigned for aValue and aSuit
	public Card(char aValue, Suit aSuit)
	{
		set(aValue,aSuit);
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

	public boolean set(char value, Suit suit)
	{
		if(isValid(value,suit))
		{
			this.value = value;
			this.suit = suit;
			errorFlag = false;
			return true;
		}
		else
		{
			errorFlag = true;
			return false;
		}
	}

	public boolean equals(Card card)
	{
		return value == card.value && suit == card.suit;
	}


	//this function uses an array as a reference to check if the value is valid
	//accVal == accepted values
	// boolean created inside function to check if the array contains the value
	private boolean isValid(char value, Suit suit)
	{
		char[] accVal = {'A','J','Q','K','2','3','4','5','6','7','8','9','T'};
		boolean check = false;
		//boolean check = Arrays.asList(accVal).contains(value);
		for(int i = 0; i<accVal.length;i++)
		{
			if(value == accVal[i])
			{
				check = true;
				break;
			}
			else
			{
				check = false;
			}
		}
		return check;
	}

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