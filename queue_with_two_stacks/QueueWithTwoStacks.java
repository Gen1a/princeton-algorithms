package queue_with_two_stacks;
import java.util.NoSuchElementException;
import java.util.Scanner;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class QueueWithTwoStacks<Item> {

    private Stack <Item> stack1;
    private Stack <Item> stack2;

    public QueueWithTwoStacks() {
        stack1 = new Stack<Item>();
        stack2 = new Stack<Item>();
    }

    public void enqueue(Item t) {
        stack1.push(t);
    }

    public Item dequeue() {
        if(isEmpty()) {
            throw new NoSuchElementException("No items present in queue");
        }
        if (stack2.isEmpty()) {
            convertStack1ToStack2();
        }
        return stack2.pop();
    }

    private void convertStack1ToStack2() {
        while (!stack1.isEmpty()){
            Item t = stack1.pop();
            stack2.push(t);
        }
    }

    // Checks if queue is empty (represented by 2 stacks)
    public boolean isEmpty() {
        return stack1.isEmpty() && stack2.isEmpty(); // && to prevent dequeue with 2 empty stacks
    }

    public int length(){
        return stack1.size() + stack2.size();
    }

    // Testing Client
    // If word encountered: push to stack
    // If - encountered: pop and print
    public static void main (String [] args){
        String test = "to be or not to - be - - that - - - is";
        Scanner scanner = new Scanner(test);
        QueueWithTwoStacks<String> q = new QueueWithTwoStacks<String>();
        
        while (scanner.hasNext()){
            String item = scanner.next();
            if (!item.equals("-")){
                q.enqueue(item);
            }
            else if (!q.isEmpty()){
                StdOut.print(q.dequeue() + " ");
            }
        }
        StdOut.print("\n" + q.length() + " items still in queue: ");
        StdOut.print(q.dequeue() + " + " + q.dequeue());
        scanner.close();
    }
}