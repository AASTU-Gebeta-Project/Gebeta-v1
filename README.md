#  Gebeta (Mancala) Game Logic

Gebeta is a traditional two-player strategy board game, also known as Mancala.  
This project implements the **core game logic** based on the official Gebeta rules.

---

##  Game Overview

- **Players:** 2
- **Board Type:** Mancala-style board
- **Goal:** Collect more stones in your own store (Mancala) than your opponent.

---

##  Game Components

### 1. Small Pits
- Total of **12 small pits**
- **6 pits per player**
- Each pit holds stones (seeds)

### 2. Stores (Mancalas)
- **2 large stores**, one for each player
- Located on the **right-hand side** of each player
- Used to collect captured stones

### 3. Stones
- Total stones: **48**
- At the start of the game:
  - Each of the 12 pits contains **4 stones**

---

##  Objective of the Game

The objective is to **collect more stones in your own Mancala** than your opponent by the end of the game.

---

##  Basic Gameplay Rules

1. The game is played in **turns**.
2. **Player 1** usually starts the game.
3. On a turn, a player must:
   - Choose **one of the six pits on their side** that contains at least one stone.
4. All stones are removed from the selected pit.
5. The player distributes the stones **one by one** in a **counter-clockwise (right) direction**.
6. Stones are placed into:
   - The player’s own pits
   - The player’s own Mancala
   - The opponent’s pits
7. **The opponent’s Mancala is always skipped** during stone distribution.
8. The turn ends when all stones in hand are placed.

---

##  Extra Turn (Free Move) Rule

- If the **last stone lands in the player’s own Mancala**, the player gets:
  - **One extra turn**
- This can result in multiple consecutive turns.
- This rule is an important strategic element of the game.

---

##  Capture Rule

A capture occurs when **all** of the following conditions are met:

1. The last stone lands in a pit on the **player’s own side**.
2. That pit was **empty before** the stone was placed.
3. The directly opposite pit on the opponent’s side contains **one or more stones**.

### Capture Result:
- The player captures:
  - The last placed stone
  - All stones from the opposite opponent pit
- All captured stones are moved to the player’s **Mancala**.

---

##  End of Game Condition

The game ends when:
- **All six pits on one player’s side are empty**

When this happens:
- The opponent collects **all remaining stones** from their side.
- These stones are added to the opponent’s Mancala.
- **No further moves are allowed.**

---

##  Determining the Winner

1. Each player counts the stones in their Mancala.
2. The player with **more stones wins**.
3. If both players have the same number of stones:
   - The game is declared a **draw (tie)**.

---

##  Project Focus

This repository focuses on:
- Correct implementation of Gebeta rules
- Turn handling
- Capture logic
- End-game detection
- Winner determination

---

##  License

This project is for **educational purposes** and follows the traditional Gebeta rules.

---

##  Acknowledgment

Gebeta is a cultural and strategic game widely played in Ethiopia and other regions.

---

**Happy Coding & Strategic Playing!**


