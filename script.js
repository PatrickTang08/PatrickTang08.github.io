body {
    font-family: sans-serif;
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background-color: #f0f0f0;
}

.start-screen {
    background-color: white;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    text-align: center;
}

.game-container {
    background-color: white;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    text-align: center;
}

.game-container.hidden {
    display: none;
}

.player-hand {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    justify-content: center;
    margin: 20px 0;
    border: 1px solid #ccc;
    padding: 10px;
    border-radius: 5px;
}

.discard-pile {
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 5px;
  margin: 20px 0;
}
.selected-card {
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 5px;
  margin: 20px 0;
}

.card {
    border: 1px solid black;
    padding: 10px;
    border-radius: 5px;
    cursor: pointer;
    background-color: #eee;
}

.card.selected {
    background-color: #aaf;
}

.controls button{
    padding: 10px;
    margin: 5px;
}
