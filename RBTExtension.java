// --== CS400 File Header Information ==--
// Name: Luke Breyer
// Email: lbreyer@wisc.edu
// Team: KF
// Role: Back End Developer
// TA: Siddharth Mohan
// Lecturer: Gary Dahl
// Notes to Grader: Hello World!

/**
 * @author Luke Breyer
 */

public class RBTExtension<T extends Comparable<T>> extends RedBlackTree<T> {

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
      throw new NullPointerException();
    } else {
      remove = search(root, name);

      if (remove != null) {
        if (remove.parent == null) {
          root = null;
        } else {
          deleteHelper(name);
        }
      }
      return remove; // recursively insert into subtree
    }
  }

  /**
   * Transplant - transplants nodeB with nodeA within the RBT. Used to prime the removal method for
   * a given node. This method is simplified from a complete transplant because it is assumed that
   * this will be used only within applicable cases.
   * 
   * @param nodeA - The node representing the position to which B will be transplanted
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

    if (nodeB != null) {
      nodeB.parent = nodeA.parent;
    }
  }

  /**
   * minimum - computes the minimum value of a true for use in transplanting.
   * 
   * @param node - the node below which to search for a minimum
   * @return node - the minimum node below the parameter within the tree
   */
  public Node<T> minimum(Node<T> node) {
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
    // If missing one child, must be able to replace node with other child (is
    // either a node or null)
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
   * Search - inOrder traversal of the RBT. Searches recursively for a node with a name matching the
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

  /**
   * Search - inOrder traversal of the RBT. Searches recursively for a node with a name matching the
   * field.
   * 
   * @param node - node element to compare
   * @param name - name to search for an remove
   * @return Node<T> - The node removed or null if not present
   */
  public void searchPrint(Node<T> node) {
    if (node == null) {
      return;
    }

    searchPrint(node.rightChild);

    System.out.println("- " + node.name + " (" + node.team + "): " + node.data);

    searchPrint(node.leftChild);
  }

}
