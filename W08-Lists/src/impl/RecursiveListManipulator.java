package impl;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IMapTransformation;
import interfaces.IReduceOperator;

/**
 * This class represents the recursive implementation of the IListManipulator interface.
 */
public class RecursiveListManipulator implements IListManipulator {

    
    @Override
    public int size(ListNode head) {
        if (head == (null)) {
            return 0;
        }
        ListNode temp = head;
        temp = temp.next;
        return (size(head, temp)) + 1;
    }

    private int size(ListNode head, ListNode temp) {
        if (head == (temp)) {
            return 0;
        }
        return size(head, temp.next) + 1;
    }
    /**
     * This method accepts a ListNode and the required object to find as an argument and returns a boolean. 
     */
    @Override
    public boolean contains(ListNode head, Object element) {
        if (head == null) {
            return false;
        }
        if (head.element.equals(element)) {
            return true;
        }
        return contains(head, element, head.next);

    }

    private boolean contains(ListNode head, Object element, ListNode temp) {
        if (element == temp.element) {
            return true;
        }
        if (temp == head) {
            return false;
        } else {
            return contains(head, element, temp.next);
        }

    }
    /**
     * This method accepts a ListNode and an element as an object, then counts the occurances of the given element.
     */
    @Override
    public int count(ListNode head, Object element) {
        if (head == null) {
            return 0;
        }
        if (head.element.equals(element)) {
            return count(head, element, head.next) + 1;
        } else
            return count(head, element, head.next);
    }

    private int count(ListNode head, Object element, ListNode temp) {

        if (head == temp) {
            return 0;
        }
        if (temp.element.equals(element)) {
            return count(head, element, temp.next) + 1;
        }


        return count(head, element, temp.next);

    }
    /**
     * This method converts all the elements in the given ListNode to String and returns them in the variable, output.
     */
    @Override
    public String convertToString(ListNode head) {
        if (head == null) {
            return "";
        }
        String output = "";
        output += head.element;
        if (size(head) > 1) {
            output += ",";
        }
        return convertToString(head, head.next, output);
    }

    private String convertToString(ListNode head, ListNode temp, String output) {
        if (temp == head) {
            return output;
        }
        output += temp.element;
        if (temp.next != head) {
            output += ",";
        }
        return convertToString(head, temp.next, output);
    }
    
    @Override
    public Object getFromFront(ListNode head, int n) throws InvalidIndexException {
        if (head == null) {
            throw new InvalidIndexException();
        }
        if (n >= 1) {
            return getFromFront(head, n - 1, head.next);
        }
        return head.element;
    }

    private Object getFromFront(ListNode head, int n, ListNode temp) throws InvalidIndexException {
        if (temp == head) {
            throw new InvalidIndexException();
        }
        if (n == 0) {
            return temp.element;
        }
        return getFromFront(head, n - 1, temp.next);
    }

    /**
     * This method validates the list, checks that head isn't what is supposed to be retrieved
     * then calls the other get from back
     */
    @Override
    public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
        if (head == null) {
            throw new InvalidIndexException();
        }

        head = head.previous;

        if (n < 0) {
            throw new InvalidIndexException();
        }
        if (n > 0) {
            return getFromBack(head, n - 1, head.previous);
        }
        return head.element;
    }
    /**
     * This method checks if n is equal to 0 and returns if it is, it decrements n each iteration
     * @param head is the head, used to make sure InvalidIndexEception is thrown when it should
     * @param n is the index of the element to be retrieved
     * @param temp is the current element
     * @returns the element if n = 0 otherwise it returns/calls the method again
     * @throws InvalidIndexException
     */
    private Object getFromBack(ListNode head, int n, ListNode temp) throws InvalidIndexException {
        if (temp == head) {
            throw new InvalidIndexException();
        }
        System.out.println(n);
        if (n == 0) {
            return temp.element;
        }
        return getFromFront(head, n - 1, temp.previous);
    }

    /**
     * This method validates the list by checking their both not null then checks that they have the same length
     * then calls the other equals method
     */
    @Override
    public boolean equals(ListNode head1, ListNode head2) {
        if (head1 == null && head2 == null) {
            return true;
        } else if (head1 == null || head2 == null) {
            return false;
        }
        if (count(head1, head1.previous) != count(head2, head2.previous)) {
            return false;
        }
        return equals(head1, head2, head1.next, head2.next);
    }

    /**
     * This method checks that each current element is equal to each other, if they both reach their head at the same time
     * then the program returns true otherwise it returns/calls the method again
     * @param head1 is the head of the first ListNode
     * @param head2 is the head of the second ListNode
     * @param temp1 is the current element of the first ListNode
     * @param temp2 is the current element of the second ListNode
     * @returns a boolean value which corresponds to if the ListNodes are equal
     */
    private boolean equals(ListNode head1, ListNode head2, ListNode temp1, ListNode temp2) {
        if (temp1.element.equals(temp2.element)) {
            if (temp1.next == head1 && temp2.next == head2) {
                return true;
            }
            return equals(head1, head2, temp1.next, temp2.next);
        }
        return false;
    }

    /**
     * This method validates the passed in ListNode and checks if the head element occurs more than once in the ListNode
     */
    @Override
    public boolean containsDuplicates(ListNode head) {
        if (head == null) {
            return false;
        }
        if (count(head, head.element) > 1) {
            return true;
        }
        if (size(head) > 1) {
            return containsDuplicates(head, head);
        }
        return false;
    }

    /**
     * This method counts the occurances of the current element
     * @param head is the ListNode used to verify when the list has been fully wrapped around
     * @param temp is the current element being checked
     * @returns true as soon as it is true or false after every element has been checked
     */
    private boolean containsDuplicates(ListNode head, ListNode temp) {
        if (temp == head) {
            return false;
        }
        if (count(head, temp.element) > 1) {
            return true;
        }
        return containsDuplicates(head, temp.next);
    }

    /**
     * This method joins two ListNodes together by pointing the nodes towards each other
     */
    @Override
    public ListNode append(ListNode head1, ListNode head2) {

        if (head1 == null && head2 == null) {
            return null;
        }
        if (head1 == null && head2 != null) {
            return head2;
        }
        if (head1 != null && head2 == null) {
            return head1;
        }
        ListNode temp1 = head1.previous;
        ListNode temp2 = head2.previous;

        temp2.next = head1;
        head1.previous = temp2;
        temp1.next = head2;
        head2.previous = temp1;

        return head1;
    }

    /**
     * This method validates the list to be reversed, if the only element is the head then return that.
     */
    @Override
    public ListNode reverse(ListNode head) {

        if (head == null) {
            return null;
        }
        System.out.println(head.element);
        if (size(head) > 1) {
            return reverse(head.previous, head, head.previous);
        }
        return head;

    }

    /**
     * This method reverses the order of the ListNode
     * @param newHead is the newHead to be returned when finished
     * @param head is used to verify end of loop
     * @param temp is the current element
     * @returns the List in reverse order
     */
    private ListNode reverse(ListNode newHead, ListNode head, ListNode temp) {

        newHead.next = temp.previous;
        temp = temp.previous;
        newHead = newHead.next;
        if (temp == head) {
            return newHead;
        }

        return reverse(newHead, head, temp);

    }

    /**
     * This method returns a ListNode which contains 2 sublists, split from the pointer that is passed in as a parameter
     */
    @Override
    public ListNode split(ListNode head, ListNode node) throws InvalidListException {
        if (head == null || size(head) < 2) {
            throw new InvalidListException();
        }

        

        // Sets the end of each new list
        ListNode tail2 = head.previous;
        ListNode tail = node.previous;
        head.previous = tail;
        tail.next = head;

        // Sets the new lists
        node.previous = tail2;
        tail2.next = node;

        // Declares new list which will hold both sub lists
        // Code taken from AbstractListManipulatorTest Class
        ListNode listOfLists = new ListNode(null);
        listOfLists.next = new ListNode(null);
        listOfLists.next.previous = listOfLists;
        listOfLists.next.next = listOfLists;
        listOfLists.previous = listOfLists.next;

        // End of taken code
        listOfLists.element = head; listOfLists.next.element = node;


        return listOfLists;
    }

    /**
     * This method checks the validity of the list before calling the method to apply the transformation
     */
    @Override
    public ListNode map(ListNode head, IMapTransformation transformation) {
        if (head == null) {
            return null;
        }
        return map(head, transformation, head);
    }

    /**
     * This method applies the transformation passed in
     * @param head is the element used to keep track of the ListNode and make sure we dont loop all the way around.
     * @param transformation is what is going to be applied to the current element
     * @param temp is the current element to be transformed
     * @return completed ListNode when done
     */
    private ListNode map(ListNode head, IMapTransformation transformation, ListNode temp) {
        temp.element = transformation.transform(temp.element);
        temp = temp.next;
        if (temp == head) {
            return temp;
        }
        return map(head, transformation, temp);
    }

    @Override
    public Object reduce(ListNode head, IReduceOperator operator, Object initial) {
        if (head == null) {
            return 0;
        }
        return reduce(head, operator, initial, head);
    }

    private Object reduce(ListNode head, IReduceOperator operator, Object initial, ListNode temp) {
        initial = operator.operate(initial, temp.element);
        temp = temp.next;

        if (temp == head) {
            return initial;
        } else return reduce(head, operator, initial, temp);
    }

    /**
     * This method filters through the given ListNode and returns the required ListNode
     */
    @Override
    public ListNode filter(ListNode head, IFilterCondition condition) {
        if (head == null) {
            return null;
        }
        if (size(head) > 1) {
            return filter(head, condition, head);
        } else if (condition.isSatisfied(head.element)) {
            return head;
        } else {
            return null;
        }
    }

    private ListNode filter(ListNode head, IFilterCondition condition, ListNode temp) {
        if (condition.isSatisfied(temp.element)) {
            temp = temp.next;
        } else {           
            
            // TAKEN FROM https://studres.cs.st-andrews.ac.uk/CS2001/Lectures/JL/JL08-DoublyLinkedLists/Lecture08-Code/src/cs2001/lecture08/
            if (head == temp) {
                head = head.next;
            }
            temp.previous.next = temp.next;
            temp.next.previous = temp.previous;
            // END OF TAKEN CODE

        }
        if (temp == head) {
            return temp;
        }
        return filter(head, condition, temp);
    }
}