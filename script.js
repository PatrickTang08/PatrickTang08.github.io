const startScreen = document.getElementById('startScreen');
const gameContainer = document.getElementById('gameContainer');
const startButton = document.getElementById('startButton');
const playerHandDiv = document.getElementById('playerHand');
const discardPileDiv = document.getElementById('discardPile');
const selectedCardDiv = document.getElementById('selectedCard');
const scoreDisplay = document.getElementById('score');
const discardsLeftDisplay = document.getElementById('discardsLeft');
const levelDisplay = document.getElementById('level');
const drawButton = document.getElementById('drawButton');
const discardButton = document.getElementById('discardButton');
const playHandButton = document.getElementById('playHandButton');
const resetButton = document.getElementById('resetButton');


let deck = [];
let playerHand = [];
let discardPile = [];
let selectedCards = []; // Array to hold selected cards
let score = 0;
let discardsLeft = 2;
let level = 1;
const MAX_HAND_SIZE = 8;
const MAX_SELECT_SIZE = 5; // Max cards that can be selected

// Card values and suits (You can add more sophisticated card types)
const suits = ["H", "D", "C", "S"];
const values = ["2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"];

// Function to create a deck of cards
function createDeck() {
    deck = [];
    for (let suit of suits) {
        for (let value of values) {
            deck.push({ suit, value });
        }
    }
    shuffleDeck(deck);
}

// Fisher-Yates shuffle algorithm to randomize the deck
function shuffleDeck(deck) {
    for (let i = deck.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [deck[i], deck[j]] = [deck[j], deck[i]];
    }
}

// Function to draw card
function drawCard() {
    if (playerHand.length >= MAX_HAND_SIZE) {
        alert("Hand is full!");
        return;
    }

    if (deck.length === 0) {
        alert("Out of cards!");
        return;
    }
    const card = deck.pop();
    playerHand.push(card);
    renderPlayerHand();
}

// Function to discard card
function discardCard() {
    if (selectedCards.length === 0) {
        alert("Select a card to discard")
        return;
    }
    
    selectedCards.forEach(card => discardPile.push(card));
    
    playerHand = playerHand.filter(card => !selectedCards.includes(card));
    selectedCards = [];
    discardsLeft--;
    
    refillHand();

    renderPlayerHand();
    renderDiscardPile();
    updateGameInfo();
    selectedCardDiv.innerHTML = `<p>Selected Card</p>`
}

// Function to refill the hand to 8 cards
function refillHand() {
    while (playerHand.length < MAX_HAND_SIZE && deck.length > 0) {
        drawCard();
    }
}


// Function to calculate hand value
function calculateHandValue() {
  let handValue = 0;
    let pairs = 0
    let threeOfAKinds = 0;
    let fourOfAKinds = 0;
  const valueCounts = {};
    
  selectedCards.forEach((card) => {
      if (valueCounts[card.value]) {
          valueCounts[card.value]++;
      } else {
          valueCounts[card.value] = 1;
      }
  });

     for (const value in valueCounts) {
    if (valueCounts[value] === 2) {
      pairs++;
    } else if (valueCounts[value] === 3) {
      threeOfAKinds++;
    } else if (valueCounts[value] === 4) {
      fourOfAKinds++;
    }
  }
    
    if (fourOfAKinds > 0) {
      handValue = 100 * fourOfAKinds * level
    }
    else if (threeOfAKinds > 0) {
      handValue = 50 * threeOfAKinds * level
    }
    else if(pairs > 0){
      handValue = 10 * pairs * level
    }
    
  return handValue;
}


// Function to play the hand
function playHand() {
    if (selectedCards.length === 0)
    {
      alert("Please select cards to play")
      return;
    }
    
    const handValue = calculateHandValue();
    score += handValue;
    level++;
    playerHand = playerHand.filter(card => !selectedCards.includes(card));
    discardsLeft = 2;
    deck = deck.concat(discardPile);
    shuffleDeck(deck);
    discardPile = [];
    selectedCards = [];
      refillHand();

    renderPlayerHand();
    renderDiscardPile();
    updateGameInfo();
}

// Function to render the player's hand on the screen
function renderPlayerHand() {
    playerHandDiv.innerHTML = '';
    playerHand.forEach(card => {
        const cardDiv = document.createElement('div');
        cardDiv.textContent = `${card.value}${card.suit}`;
        cardDiv.classList.add('card');
        cardDiv.addEventListener('click', () => selectCard(card, cardDiv));
        playerHandDiv.appendChild(cardDiv);
    });
    if (playerHand.length > 0) {
        playHandButton.disabled = false;
    }
    else {
        playHandButton.disabled = true;
    }
}

// Function to render the discard pile
function renderDiscardPile() {
    discardPileDiv.innerHTML = '<p>Discard Pile</p>';
    discardPile.forEach(card => {
        const cardDiv = document.createElement('div');
        cardDiv.textContent = `${card.value}${card.suit}`;
        cardDiv.classList.add('card');
        discardPileDiv.appendChild(cardDiv);
    });
}

// Function to handle selecting a card
function selectCard(card, cardDiv) {
    
    const isSelected = selectedCards.includes(card)
    if(isSelected){
        selectedCards = selectedCards.filter(c => c !== card);
        cardDiv.classList.remove('selected')
    }
    else
    {
        if (selectedCards.length >= MAX_SELECT_SIZE) {
             alert(`You can only select up to ${MAX_SELECT_SIZE} cards.`);
             return
         }
         selectedCards.push(card);
          cardDiv.classList.add('selected')
     }
    
     if (selectedCards.length > 0)
     {
         discardButton.disabled = false;
         selectedCardDiv.innerHTML = `<p>Selected Cards ${selectedCards.map(c => c.value+c.suit).join(" ")}</p>`
     }
     else
     {
         discardButton.disabled = true;
          selectedCardDiv.innerHTML = `<p>Selected Card</p>`
     }
    
    console.log("selected cards", selectedCards)
}

// Function to reset the game
function resetGame() {
    createDeck();
    playerHand = [];
    discardPile = [];
    selectedCards = [];
    score = 0;
    discardsLeft = 2;
    level = 1;
    // Automatically draw 8 cards at the start
      refillHand();
      renderPlayerHand();
    renderDiscardPile();
    updateGameInfo();
    selectedCardDiv.innerHTML = `<p>Selected Card</p>`
}

// Function to update game information
function updateGameInfo() {
    scoreDisplay.textContent = score;
    discardsLeftDisplay.textContent = discardsLeft;
    levelDisplay.textContent = level;
}

// Button Event Listeners
drawButton.addEventListener('click', drawCard);
discardButton.addEventListener('click', discardCard);
playHandButton.addEventListener('click', playHand);
resetButton.addEventListener('click', resetGame);

// Start button Event Listener
startButton.addEventListener('click', () => {
    startScreen.classList.add('hidden');
    gameContainer.classList.remove('hidden');
    resetGame(); // Initialize the game when the start button is clicked
})
// Initially hide the game container and show the start screen
gameContainer.classList.add('hidden');
