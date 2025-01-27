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
let selectedCard = null;
let score = 0;
let discardsLeft = 2;
let level = 1;
const MAX_HAND_SIZE = 8; // Limit of cards in the hand

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
    if (selectedCard === null) {
        alert("Select a card to discard")
        return;
    }
    discardPile.push(selectedCard);
    playerHand = playerHand.filter(card => card !== selectedCard);
    selectedCard = null;
    discardsLeft--;

    renderPlayerHand();
    renderDiscardPile();
    updateGameInfo();
}

// Function to calculate hand value
function calculateHandValue() {
    let handValue = 0
    let numPairs = 0;

    // Create a mapping to count the occurrences of each value in the hand
    const valueCounts = {};
    playerHand.forEach(card => {
        if (valueCounts[card.value]) {
            valueCounts[card.value]++;
        } else {
            valueCounts[card.value] = 1;
        }
    });

    // Check for pairs
    for (const value in valueCounts) {
        if (valueCounts[value] >= 2) {
            numPairs++;
        }
    }

    if (numPairs >= 1) {
        handValue = 10 * (numPairs * level);
        if (numPairs >= 2) {
            handValue += 10 * (numPairs * level)
        }
    }


    return handValue;
}

// Function to play the hand
function playHand() {
    const handValue = calculateHandValue();
    score += handValue;
    level++;
    playerHand = [];
    discardsLeft = 2;
    deck = deck.concat(discardPile);
    shuffleDeck(deck);
    discardPile = [];

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
    if (selectedCard) {
        const selectedDiv = document.querySelector('.card.selected');
        if (selectedDiv) {
            selectedDiv.classList.remove('selected');
        }
    }

    selectedCard = card;
    cardDiv.classList.add('selected')
    selectedCardDiv.innerHTML = `<p>Selected Card ${card.value}${card.suit}</p>`
    discardButton.disabled = false;

    console.log("selected card", card)
}

// Function to reset the game
function resetGame() {
    createDeck();
    playerHand = [];
    discardPile = [];
    selectedCard = null;
    score = 0;
    discardsLeft = 2;
    level = 1;
    // Automatically draw 8 cards at the start
    for (let i = 0; i < MAX_HAND_SIZE; i++) {
        if (deck.length > 0) {
            drawCard();
        }
    }
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


// Start the game!
resetGame()
