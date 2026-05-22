public class CustomStack {

    private final int[][][] stack;
    private int top;  // The top index of the stack
    private final int capacity;  // Maximum capacity of the stack

    public CustomStack(int capacity) {
        this.capacity = capacity;
        this.stack = new int[capacity][9][9];
        this.top = -1;  // Initialize the stack as empty
    }

    // Push a board state onto the stack
    public void push(int[][] board) {
        if (top < capacity - 1) {
            top++;
            for (int i = 0; i < 9; i++) {
                System.arraycopy(board[i], 0, stack[top][i], 0, 9);  // Copy board state into the stack
            }
        } else {
            System.out.println("Stack is full! Unable to push new state.");
        }
    }

    // Pop the top board state from the stack and return it
    public int[][] pop() {
        if (top >= 0) {
            int[][] board = new int[9][9];
            for (int i = 0; i < 9; i++) {
                System.arraycopy(stack[top][i], 0, board[i], 0, 9);  // Copy top board state
            }
            top--;
            return board;
        }
        return null;  // Return null if the stack is empty
    }

    // Clear the stack
    public void clear() {
        top = -1;
    }

    // Check if the stack is empty
    public boolean isEmpty() {
        return top == -1;
    }
}
