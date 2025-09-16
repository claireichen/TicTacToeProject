# CS 3560 Tic-Tac-Toe Assignment
**Group 12: Claire Chen, Ben Edwards**

## How to Run
1. Click the Green Code Button and Clone using the web URL in your IDE of choice
2. Run the file named "ConsoleApp.java" to start!

## Design Rationale
- Where did encapsulation prevent bugs?
  - Centralized validation in Board.place(...) (bounds, empty cell, correct turn) stopped illegal moves before they touched state, avoiding duplicated checks across UI/AI.
  - Private grid + no setters meant nothing outside Board could overwrite cells or skip turn logic; read-only access goes through getCell(r,c).
  - Move is immutable (row/col/mark final), so UI parsing can’t “accidentally” mutate a move after validation.
  - Undo state is internal (Deque<Move>); only undo()/canUndo() can modify history, preventing out-of-order or partial undos.
  - Constructor checks (size ≥ 3, 3 ≤ winCondition ≤ size) keep invariants true from the start.
- Why inheritance here (vs. strategy/composition)?
  - An abstract Player with nextMove(Board) lets Game treat humans and AIs interchangeably—no if (human) ... else (ai) branches—so the loop stays small and testable.
  - Variants (HumanPlayer, RandomAIPlayer, SmartAIPlayer) plug in via polymorphism, matching the assignment’s goal to practice inheritance.
- What would you refactor if the rules changed to 4‑in‑a‑row on a 5×5 board?
  - Board size: change Board to accept a size constructor arg (new Board(5)) and allocate grid = new Mark[size][size]. Add a size() getter. Keep fields private and initialization in one place.
  - Input/printing: update console UI to respect size. For 5×5, show 1..25 (or prompt for row/col 0..4). This only affects the UI layer, not Board
  - Tests: add/adjust unit tests for 5×5 / 4-in-a-row (rows, cols, both diagonals), plus out-of-bounds and invalid-move tests at the new size. Ensure draw detection still works

## UML Diagram

![alt text](UMLDiagram.png "UML Diagram")

## Test Results
- (Placeholder)

