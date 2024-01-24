package impl;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IMapTransformation;
import interfaces.IReduceOperator;

/**
 * This class represents the iterative implementation of the IListManipulator interface.
 */
public class ListManipulator implements IListManipulator {

    /**
     * This method accepts a ListNode as an argument and returns the size of the ListNode as an int
     */
    @Override
    public int size(ListNode head) {

        int counter = 0;

        ListNode temp = head;
        if (head == null) {
            return 0;
        }
        do {
            temp = temp.next;
            counter++;

        } while (temp != head);
        return counter;
    }

    /**
     * This method accepts a ListNode and the required object to find as an argument and returns a boolean.
     */
    @Override
    public boolean contains(ListNode head, Object element) {
        ListNode temp = head;
        if (head == null) {
            return false;
        }
        do {
            if (temp.element == element) {
                return true;
            }
            temp = temp.next;
        } while (temp != head);
        return false;
    }

    /**
     * This method accepts a ListNode and an element as an object, then counts the occurances of the given element.
     */
    @Override
    public int count(ListNode head, Object element) {
        ListNode temp = head;
        int counter = 0;

        do {
            if (temp.element == element) {
                counter++;
            }
            temp = temp.next;
        } while (temp != head);

        return counter;
    }

    /**
     * This method converts all the elements in the given ListNode to String and returns them in the variable, output.
     */
    @Override
    public String convertToString(ListNode head) {
        if (head == null) {
            return "";
        }
        ListNode temp = head;
        String output = "";

        do {
            output += temp.element;
            if (temp.next != null && temp.next != head) {
                output += ",";

            }
            temp = temp.next;
        } while (temp != head && temp.next != null);


        return output;
    }

    /**
     * This method returns the element within the given ListNode which is in the index passed in.
     * This throws InvalidIndexException when the user passes in an empty ListNode or when the user tries to retrieve an object
     * from an index which is greater than the size of the ListNode
     */
    @Override
    public Object getFromFront(ListNode head, int n) throws InvalidIndexException {
        if (head == null) {
            throw new InvalidIndexException();

        }
        ListNode temp = head;

        for (int i = 0; i < n; i++) {
            if (temp.next == head) {
                throw new InvalidIndexException();
            }
            temp = temp.next;
        }

        return temp.element;
    }

    /**
     * This method, identical to the one above, retrieves an element from the given ListNode, however this is from the back
     * of the ListNode.
     */
    @Override
    public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
        if (head == null) {
            throw new InvalidIndexException();
        }
        ListNode temp = head;

        int negativeIndex = 0;
        do {
            temp = temp.previous;
            if (size(head) > 1) {
                if (temp.equals(head)) {
                    throw new InvalidIndexException();
                }
                // When negative index equals n, loop stops as the iteration it is on will have the required element as 
                // its current element.
                if (n > 0) {
                    negativeIndex++;
                    temp = temp.previous;
                }
            }
        }
        while (negativeIndex != n);

        return temp.element;
    }

    /**
     * This method checks if two ListNodes passed in are equal to each other, both in size and elements.
     */
    @Override
    public boolean equals(ListNode head1, ListNode head2) {

        if (head1 == null && head2 == null) {
            return true;
        } else if (head1 == null || head2 == null) {
            return false;
        }
        // If they do not have the same amount of elements then no need to even check anything else.
        if (count(head1, head1.previous) != count(head2, head2.previous)) {
            return false;
        }
        ListNode temp1 = head1;
        ListNode temp2 = head2;

        // Iterates through all the elements, if they are not equals to each other then just return false
        // Should both rach their heads similtaneously otherwise they are not equal 
        do {
            if (temp1.element.equals(temp2.element)) {
                if (temp1.next == head1 && temp2.next == head2) {
                    return true;
                }
                temp1 = temp1.next;
                temp2 = temp2.next;
            } else {
                return false;
            }
        } while (temp1.next != null && temp2 != null); // Same as it equaling while true, as lists are circular so that ListNode.next will never equal null.
        return true;
    }

    /**
     * This method checks if the given ListNode has any duplicates and returns a boolean value.
     */
    @Override
    public boolean containsDuplicates(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode temp = head;

        do {
            temp = temp.next;
            if (count(head, temp.element) > 1) {
                return true;
            }

        } while (temp.next != head);


        return false;
    }

    /**
     * This ListNode returns another ListNode that joins these two ListNodes together
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

        // Points to each other
        temp2.next = head1;
        head1.previous = temp2;
        temp1.next = head2;
        head2.previous = temp1;

        return head1;
    }

    /**
     * This method returns a ListNode in the opposite order as the ListNode passed in.
     */
    @Override
    public ListNode reverse(ListNode head) {

        if (head == null) {
            return null;
        }

        ListNode temp = head.previous;
        ListNode newHead = head;


        do {
            newHead.next = temp;
            temp = temp.previous;
            newHead = newHead.next;
        } while (temp.next != (head));

        return newHead;
    }

    /**
     * This method returns a ListNode which contains 2 sublists, split from the pointer that is passed in as a parameter
     */
    @Override
    public ListNode split(ListNode head, ListNode node) throws InvalidListException {

        // Checks validity of list
        if (head == null) {
            throw new InvalidListException();
        }
        if (size(head) < 2) {
            throw new InvalidListException();
        }
        if (node == head) {
            throw new InvalidListException();
        }
        if (node == null){
            throw new InvalidListException();
        }
        ListNode tail2 = head.previous;
        ListNode tail = node.previous;
        head.previous = tail;
        tail.next = head;

        node.previous = tail2;
        tail2.next = node;

        // From top of AbstractListManipulatorTest Class
        ListNode listOfLists = new ListNode(null);
        listOfLists.next = new ListNode(null);
        listOfLists.next.next = listOfLists;
        listOfLists.next.previous = listOfLists;
        listOfLists.previous = listOfLists.next;

        listOfLists.element = head;
        listOfLists.next.element = node;


        return listOfLists;
    }

    /**
     * This method returns a ListNode which has all the elements slightly changed in a way, according to IMapTransformation
     */
    @Override
    public ListNode map(ListNode head, IMapTransformation transformation) {
        if (head == null) {
            return null;
        }
        ListNode temp = head;
        // Iterates through all elements and transforms each element one by one
        do {
            temp.element = transformation.transform(temp.element);
            temp = temp.next;
        } while (temp != head);

        return temp;
    }

    @Override
    public Object reduce(ListNode head, IReduceOperator operator, Object initial) {
        if (head == null) {
            return 0;
        }
        ListNode temp = head;
        do {
            initial = operator.operate(initial, temp.element);
            temp = temp.next;
        } while (temp != head);
        return initial;
    }

    /**
     * This method returns a ListNode that has been filtered through the condition passed in
     */
    @Override
    public ListNode filter(ListNode head, IFilterCondition condition) {
        // TODO Auto-generated method stub
        if (head == null) {
            return null;
        }
        ListNode temp = head;
        int size = size(head);

        if (size == 1) {
            if (condition.isSatisfied(head.element)) {
                return head;
            } else {
                return null;
            }
        }
        if (size > 1) {
            for (int i = 0; i < size; i++) {
                if (!condition.isSatisfied(temp.element)) {
                    if (head == temp) {
                        head = head.next;
                    }
                    temp.previous.next = temp.next;
                    temp.next.previous = temp.previous;
                    i++; // To decrement the size, amount of times we loop through everything
                } else if (condition.isSatisfied(temp.element)) {
                    temp = temp.next;
                }
            }
        }
        return temp;
    }
}
