 Java Sudoku Game (DSA Project)

A fully functional, GUI-based Sudoku game built in Java. This project demonstrates the practical application of Data Structures and Algorithms (DSA), specifically focusing on **Recursive Backtracking** for puzzle solving/generation and a **Custom Stack** for state management.

🚀 Features

Three Difficulty Levels: Easy (10 mins), Medium (6 mins), and Difficult (3 mins).
Interactive GUI: Built using Java Swing and AWT.
Smart Hints: Uses the solver to safely reveal the correct number for an empty cell.
Undo Functionality: Revert to previous board states using a custom-built stack.
Check Move: Validates the player's current inputs against the puzzle's true solution.
Auto-Solve: Instantly resolves the rest of the board using the backtracking algorithm.
Procedural Generation: Generates a unique, valid Sudoku board every game through matrix permutation.

 🧠 Data Structures & Algorithms Used

 1. Backtracking Algorithm (`SudokuSolver.java`)
The core engine of the game relies on recursive backtracking. 
How it works: The algorithm finds an empty cell and attempts to place a digit (1-9). It checks against Sudoku constraints (row, column, and 3x3 subgrid). If valid, it recursively attempts to solve the rest of the board. If it hits a dead end, it backtracks, clears the cell, and tries the next number.
Time Complexity: O(9^E) in the absolute worst-case scenario (where E is the number of empty cells). However, the practical execution time is drastically lower thanks to **pruning**. By running `isMoveValid()` before proceeding down a recursive path, the algorithm immediately abandons invalid branches, bypassing millions of redundant checks and solving the board in milliseconds.
Space Complexity: O(E) for the call stack during recursion.

 2. Custom Stack (`CustomStack.java`)
Instead of using Java's built-in `java.util.Stack`, this project features a custom implementation using a 3D array (`int[][][]`).
Purpose: Powers the "Undo" feature by saving snapshots of the 9x9 board.
Mechanics: 
   `push()`: Copies the current 2D board state into the next available 3D layer.
   `pop()`: Retrieves the most recent board state and decrements the top pointer.
Complexity: O(1) time complexity for state management (with a fixed O(N^2) space copy operation per move).

 📂 Project Structure

`SudokuMenu`: The entry point. Handles the main menu UI and difficulty selection.
`Sudoku`: The main game window. Manages the grid, timers, button event listeners, and overall game logic.
`SudokuGenerator`: Creates a valid base Sudoku board, randomizes it via row/col permutations, and removes numbers based on difficulty to create the puzzle.
`SudokuSolver`: Contains the backtracking logic used to validate moves, generate hints, and auto-solve the grid.
`CustomStack`: A fixed-capacity array-based stack used to track board history.
`Point`: A lightweight helper class to store `(x, y)` matrix coordinates.

 💻 How to Run

1. Ensure you have the Java Development Kit (JDK 8 or higher) installed on your machine.
2. Clone or download the repository.
3. Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse, or VS Code).
4. Verify the file path for the main menu background image in `SudokuMenu.java` points to your local image directory (e.g., changing it from an absolute path to a relative one).
5. Compile and run `SudokuMenu.java`.

 🎮 How to Play
1. Launch the application and select your desired difficulty.
2. Click Start to begin the timer and reveal the board.
3. Click on the empty white squares and type a number (1-9).
4. Use Check Move if you are unsure if your current placements are correct.
5. If you make a mistake, use Undo to revert the board.
6. Complete the grid before the timer runs out!
