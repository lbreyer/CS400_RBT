// --== CS400 File Header Information ==--
// Name: Luke Breyer
// Email: lbreyer@wisc.edu
// Team: KF
// Role: Front End Developer
// TA: Siddharth Mohan
// Lecturer: Gary Dahl
// Notes to Grader: Hello World!

/**
 * @author Luke Breyer
 */

import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Binary Search Tree implementation with a Node inner class for representing the nodes within a
 * binary search tree. You can use this class' insert method to build a binary search tree, and its
 * toString method to display the level order (breadth first) traversal of values in that tree.
 */
public class RedBlackTree<T extends Comparable<T>> {
  /**
   * This class represents a node holding a single value within a binary tree the parent, left, and
   * right child references are always be maintained.
   */
  protected static class Node<T> {
    public T data;
    public String name;
    public Node<T> parent; // null for root node
    public Node<T> leftChild;
    public Node<T> rightChild;
    public boolean isBlack;

    public Node(T data, String name) {
      this.data = data;
      this.name = name;
      isBlack = false;
    }

    /**
     * @return true when this node has a parent and is the left child of that parent, otherwise
     *         return false
     */
    public boolean isLeftChild() {
      return parent != null && parent.leftChild == this;
    }

    /**
     * This method performs a level order traversal of the tree rooted at the current node. The
     * string representations of each data value within this tree are assembled into a comma
     * separated string within brackets (similar to many implementations of java.util.Collection).
     * 
     * @return string containing the values of this tree in level order
     */
    @Override
    public String toString() { // display subtree in order traversal
      String output = "[";
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this);
      while (!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if (next.leftChild != null)
          q.add(next.leftChild);
        if (next.rightChild != null)
          q.add(next.rightChild);
        output += next.data.toString();
        if (!q.isEmpty())
          output += ", ";
      }
      return output + "]";
    }
  }

  protected Node<T> root; // reference to root node of tree, null when empty

  /**
   * Performs a naive insertion into a binary search tree: adding the input * data value to a new
   * node in a leaf position within the tree. After this insertion, no attempt is made to
   * restructure or balance the tree. This tree will not hold null references, nor duplicate data
   * values.
   * 
   * @param data to be added into this binary search tree
   * @throws NullPointerException     when the provided data argument is null
   * @throws IllegalArgumentException when the tree already contains data
   */
  public void insert(T data, String name) throws NullPointerException, IllegalArgumentException {
    // null references cannot be stored within this tree
    if (data == null)
      throw new NullPointerException("This RedBlackTree cannot store null references.");
    Node<T> newNode = new Node<>(data, name);
    if (root == null) {
      root = newNode;
    } // add first node to an empty tree
    else {
      insertHelper(newNode, root); // recursively insert into subtree
    }
    root.isBlack = true;
  }

  /**
   * Recursive helper method to find the subtree with a null reference in the position that the
   * newNode should be inserted, and then extend this tree by the newNode in that position.
   * 
   * @param newNode is the new node that is being added to this tree
   * @param subtree is the reference to a node within this tree which the newNode should be inserted
   *                as a descenedent beneath
   * @throws IllegalArgumentException when the newNode and subtree contain equal data references (as
   *                                  defined by Comparable.compareTo())
   */
  private void insertHelper(Node<T> newNode, Node<T> subtree) {
    int compare = newNode.name.compareTo(subtree.name);
    // do not allow duplicate values to be stored within this tree
    if (compare == 0)
      throw new IllegalArgumentException("This RedBlackTree already contains that value.");
    // store newNode within left subtree of subtree
    else if (compare < 0) {
      if (subtree.leftChild == null) { // left subtree empty, add here
        subtree.leftChild = newNode;
        newNode.parent = subtree;
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.leftChild);
    }
    // store newNode within the right subtree of subtree
    else {
      if (subtree.rightChild == null) { // right subtree empty, add here
        subtree.rightChild = newNode;
        newNode.parent = subtree;
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.rightChild);
    }
    enforceRBTreePropertiesAfterInsert(newNode);
  }

  /**
   * This method performs a level order traversal of the tree. The string representations of each
   * data value within this tree are assembled into a comma separated string within brackets
   * (similar to many implementations of java.util.Collection, like java.util.ArrayList, LinkedList,
   * etc).
   * 
   * @return string containing the values of this tree in level order
   */
  @Override
  public String toString() {
    return root.toString();
  }

  /**
   * Performs the rotation operation on the provided nodes within this BST. When the provided child
   * is a leftChild of the provided parent, this method will perform a right rotation (sometimes
   * called a left-right rotation). When the provided child is a rightChild of the provided parent,
   * this method will perform a left rotation (sometimes called a right-left rotation). When the
   * provided nodes are not related in one of these ways, this method will throw an
   * IllegalArgumentException.
   * 
   * @param child  is the node being rotated from child to parent position (between these two node
   *               arguments)
   * @param parent is the node being rotated from parent to child position (between these two node
   *               arguments)
   * @throws IllegalArgumentException when the provided child and parent node references are not
   *                                  initially (pre-rotation) related that way
   */
  private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
    if (child.parent != parent) {
      throw new IllegalArgumentException();
    } else if (child.isLeftChild()) {
      parent.leftChild = child.rightChild;
      if (child.rightChild != null) {
        child.rightChild.parent = parent;
      }
      child.parent = parent.parent;
      if (parent.parent == null) {
        root = child;
      } else if (parent.isLeftChild()) {
        parent.parent.leftChild = child;
      } else {
        parent.parent.rightChild = child;
      }
      child.rightChild = parent;
      parent.parent = child;
    } else {
      parent.rightChild = child.leftChild;
      if (child.leftChild != null) {
        child.leftChild.parent = parent;
      }
      child.parent = parent.parent;
      if (parent.parent == null) {
        root = child;
      } else if (parent.isLeftChild()) {
        parent.parent.leftChild = child;
      } else {
        parent.parent.rightChild = child;
      }
      child.leftChild = parent;
      parent.parent = child;
    }
  }

  private void enforceRBTreePropertiesAfterInsert(Node<T> newNode) {
    if (newNode.parent != null && !newNode.parent.isBlack) {
      if (newNode.parent.isLeftChild()) {
        // Case 1
        if (newNode.parent.parent.rightChild != null && !newNode.parent.parent.rightChild.isBlack) {
          newNode.parent.isBlack = true;
          newNode.parent.parent.isBlack = false;
          newNode.parent.parent.rightChild.isBlack = true;
          enforceRBTreePropertiesAfterInsert(newNode.parent.parent);
        } else {
          // Case 2
          if (newNode.isLeftChild()) {
            newNode.parent.isBlack = true;
            newNode.parent.parent.isBlack = false;
            rotate(newNode.parent, newNode.parent.parent);
          } else { // Case 3
            newNode.isBlack = true;
            newNode.parent.parent.isBlack = false;
            rotate(newNode, newNode.parent);
            rotate(newNode, newNode.parent);
          }
        }
      } else {
        // Case 1
        if (newNode.parent.parent.leftChild != null && !newNode.parent.parent.leftChild.isBlack) {
          newNode.parent.isBlack = true;
          newNode.parent.parent.isBlack = false;
          newNode.parent.parent.leftChild.isBlack = true;
          enforceRBTreePropertiesAfterInsert(newNode.parent.parent);
        } else {
          // Case 2
          if (!newNode.isLeftChild()) {
            newNode.parent.isBlack = true;
            newNode.parent.parent.isBlack = false;
            rotate(newNode.parent, newNode.parent.parent);
          } else { // Case 3
            newNode.isBlack = true;
            newNode.parent.parent.isBlack = false;
            rotate(newNode, newNode.parent);
            rotate(newNode, newNode.parent);
          }
        }
      }
    }
  }

  /**
   * Performs a deletion from a red-black tree: removes the node with the desired name field from
   * the tree and then calls helper methods to ensure that the tree remains balanced and maintains
   * RBT properties.
   * 
   * @param name - name field to be sought and removed
   * @return Node - the node removed from the tree or null if the field could not be found
   */
  public Node<T> delete(String name) {
    Node<T> remove;
    // null references cannot be removed from this tree
    if (name == null)
      throw new NullPointerException("This RedBlackTree cannot remove null references.");
    // Can not remove from an empty tree
    if (root == null) {
      return null;
    } else {
      remove = search(root, name);
      if (remove != null) {
        deleteHelper(name);
      }
      return remove; // recursively insert into subtree
    }
  }

  /**
   * Transplant - transplants nodeB with nodeA within the RBT. Used to prime the removal method for
   * a given node. This method is simplified from a complete transplant because it is assumed that
   * this will be used only within applicable cases.
   * 
   * @param nodeA - The node representing the position to whick B will be transplanted
   * @param nodeB - The node to transplant
   */
  private void transplant(Node<T> nodeA, Node<T> nodeB) {
    if (nodeA.parent == root) {
      root = nodeB;
    } else if (nodeA.isLeftChild()) {
      nodeA.parent.leftChild = nodeB;
    } else {
      nodeA.parent.rightChild = nodeB;
    }
    nodeB.parent = nodeA.parent;
  }

  /**
   * minimum - computes the minimum value of a true for use in transplanting.
   * 
   * @param node - the node below which to search for a minimum
   * @return node - the minimum node below the parameter within the tree
   */
  public Node<T> minimum(Node node) {
    while (node.leftChild != null) {
      node = node.leftChild;
    }
    return node;
  }

  /**
   * Helper method to find and delete a selected node. Searches through the tree and performs a
   * standard transplant and removal for a BST before calling the balancing method to restore RBT
   * properties.
   * 
   * @param name - the name to search for within the tree before removing
   * @return node - the removed node if it was found or null if not
   */
  private Node<T> deleteHelper(String name) {
    Node<T> returnNode = null;
    Node<T> rep;
    Node<T> opRep;

    // Search for node with given name
    returnNode = search(root, name);

    // Check if node was found within the tree
    if (returnNode == null) {
      return returnNode;
    }

    opRep = returnNode;
    boolean opRepColor = opRep.isBlack;
    // If missing one child, must be able to replace node with other child (is either a node or
    // null)
    if (returnNode.leftChild == null) {
      rep = returnNode.rightChild;
      transplant(returnNode, returnNode.rightChild);
    } else if (returnNode.rightChild == null) {
      rep = returnNode.leftChild;
      transplant(returnNode, returnNode.leftChild);
    } else { // If it has both children, replace with minimum from right subtree
      opRep = minimum(returnNode.rightChild);
      opRepColor = opRep.isBlack;
      rep = opRep.rightChild;
      if (opRep.parent == returnNode) {
        rep.parent = opRep;
      } else {
        transplant(opRep, opRep.rightChild);
        opRep.rightChild = returnNode.rightChild;
        opRep.rightChild.parent = opRep;
      }
      // Replace the node to be removed with its replacement and fix assignment
      transplant(returnNode, opRep);
      opRep.leftChild = returnNode.leftChild;
      opRep.leftChild.parent = opRep;
      opRep.isBlack = returnNode.isBlack;
    }
    if (opRepColor) { // If the removed node was a red leaf we do not have to balance, otherwise we
                      // call the enforcement method
      enforceRBTreePropertiesAfterDelete(rep);
    }
    return returnNode;
  }

  private void enforceRBTreePropertiesAfterDelete(Node<T> node) {
    Node<T> sibling;
    // Recursively work DB node up tree to the root position
    while (node != root && node.isBlack) {
      if (node.isLeftChild()) {
        sibling = node.parent.rightChild;
        // Case 1: Sibling is Red
        if (!sibling.isBlack) {
          sibling.isBlack = true;
          node.parent.isBlack = false;
          rotate(sibling, node.parent);
          sibling = node.parent.rightChild;
        }
        // Case 2: sibling is black with black children
        if (sibling.leftChild.isBlack && sibling.rightChild.isBlack) {
          sibling.isBlack = false;
          node = node.parent;
        } else {
          // Case 3A: Same side red child
          if (sibling.rightChild.isBlack) {
            sibling.leftChild.isBlack = true;
            sibling.isBlack = false;
            rotate(sibling.leftChild, sibling);
            sibling = node.parent.rightChild;
          }
          // Case 3B: Opp side red child
          sibling.isBlack = node.parent.isBlack;
          node.parent.isBlack = true;
          sibling.rightChild.isBlack = true;
          rotate(sibling, node.parent);
          node = root;
        }
      } else {
        sibling = node.parent.leftChild;
        // Case 1: Sibling is Red
        if (!sibling.isBlack) {
          sibling.isBlack = true;
          node.parent.isBlack = false;
          rotate(sibling, node.parent);
          sibling = node.parent.leftChild;
        }
        // Case 2: sibling is black with black children
        if (sibling.rightChild.isBlack && sibling.rightChild.isBlack) {
          sibling.isBlack = false;
          node = node.parent;
        } else {
          // Case 3A: Same side red child
          if (sibling.leftChild.isBlack) {
            sibling.rightChild.isBlack = true;
            sibling.isBlack = false;
            rotate(sibling.rightChild, sibling);
            sibling = node.parent.leftChild;
          }
          // Case 3B: Opp side red child
          sibling.isBlack = node.parent.isBlack;
          node.parent.isBlack = true;
          sibling.leftChild.isBlack = true;
          rotate(sibling, node.parent);
          node = root;
        }
      }
    }
    node.isBlack = true; // Set root to be black
  }

  /**
   * Search - inOrder traversal of the RBT. Searches recursivly for a node with a name matching the
   * field.
   * 
   * @param node - node element to compare
   * @param name - name to search for an remove
   * @return Node<T> - The node removed or null if not present
   */
  public Node<T> search(Node<T> node, String name) {
    if (node == null) {
      return null;
    }

    Node<T> returnNode;

    returnNode = search(node.leftChild, name);
    if (returnNode != null) {
      return returnNode;
    }
    if (node.name.equals(name)) {
      return node;
    }
    returnNode = search(node.rightChild, name);
    if (returnNode != null) {
      return returnNode;
    }
    return null;

  }

}
