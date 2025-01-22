/**
  Project Requirement: Refer to the project document provided with the assignment 
  Project Description:
  Information retrieval systems allow users to enter keywords and retrieve articles that have those keywords associated with them.
  For example, once a student named Yi Li wrote a paper called, â€œObject Class Recognition using Images of Abstract Regions,"
  and included the following keywords: `object recognition', `abstract regions', `mixture models', and `EM algorithm'.
  If someone does a search for all articles about the EM algorithm, this paper (and many others) will be retrieved.

  Implement a binary search tree and use it to store and retrieve articles. The tree will be sorted by keyword, and each node will
  contain an unordered linked list of Record objects which contain information about each article that corresponds to that
  keyword

  datafile contains the following per Article record
  Title Id
  Title
  Author
  Number of keys identifier
  List of keys in each corresponding article

  Keys are inserted into the Binary Search tree using the insert method in the BST class
  Each key will reference an unordered linked list of article objects (articleid, titleid, and author)

  Algorithm:
    - Create a BufferedReader Object to read the text from an Input stream (datafile.txt) by buffering characters that seamlessly
    reads lines (characters, arrays or lines).
    Note: Each read request made of a Reader causes a corresponding read request to be made of the underlying character or byte stream.
    It wraps BufferedReader in Java around a java FileReaders (whose read() operations may be costly)
    - Loop:
      - read titleid, title, author
        - create an article object
      - read the number of keys identifier
        - Loop read number of keys
          - insert each key into a BST data structure (BST class will insert the key if not exist)
          - add the article object to each of the respective keyword node in the BST data structure

      - ouput the resultant BST along with the list of articles per keyword
      
  */
//NOTE: I used onlineGDP as my compiler/IDE version 11.0.4 and my code ran successfully. 
//Added comments to ensure clarity and explain thought process
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Project2336F24 {

    public static void main(String args[]) {
        BinarySearchTree<String> bst = new BinarySearchTree<>();
        readFileRecords(bst, "datafile.txt");

        System.out.println("\t\tWelcome to Information Retrieval System");

        Scanner input = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. InOrder Traversal with Details <Output keywords along with their associated referenced articles.>");
            System.out.println("2. InOrder Traversal (Keywords Only) <Output only the keywords, excluding the referenced articles.>");
            System.out.println("3. PreOrder Traversal (Keywords Only) <Output only the keywords in pre-order traversal, without the referenced articles.>");
            System.out.println("4. Search for a specific Keyword <If found, display the keyword with referenced articles; otherwise, output the keyword not found message.>");
            System.out.println("5. Exit <Terminates the program.>");
            System.out.print("\nEnter a choice? ");
            choice = input.nextInt();
            
            //Added a switch statement based on the option the user wants 

            switch (choice) 
            {
                
                //Case 1 is the InOrder Traversal with Details 
                //This outputs keywords along with their associated referenced articles
                case 1:
                    bst.inOrderWithDetails();
                    break;
                
                //Case 2 is the InOrder Traversal (Keywords Only)
                //This outputs keywords without the referenced article
                case 2:
                    bst.inOrderKeywordsOnly();
                    break;
                    
                //Case 3 is the PreOrder Traversal
                case 3:
                    bst.preOrderKeywordsOnly();
                    break;
                
                //Case 4 seraches what keyword to search 

                case 4:
                    System.out.print("Enter the keyword to search: ");
                    String keyword = input.next();
                    bst.searchKeyword(keyword);
                    break;
                case 5:
                    System.out.println(" ----jGRASP: operation complete.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        input.close();
    }

    public static void readFileRecords(BinarySearchTree<String> bst, String filename) {
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            if (fileReader == null) {
                System.out.println("Error: file must be opened first!");
                break;
            } else {
                try {
                    String strId = fileReader.readLine();
                    if (strId == null) break;
                    int id = Integer.parseInt(strId);
                    String title = fileReader.readLine();
                    String author = fileReader.readLine();
                    int numKeys = Integer.parseInt(fileReader.readLine());

                    String keyword;
                    Article art = new Article(id, title, author);
                    for (int i = 0; i < numKeys; i++) {
                        keyword = fileReader.readLine();
                        bst.insert(keyword, art);
                    }

                    fileReader.readLine(); // Skip blank line
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        if (fileReader != null) {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class BinarySearchTree<E extends Comparable<E>> {
    protected TreeNode<E> root;
    protected int size;

    public TreeNode<E> search(E element) {
        TreeNode<E> current = root;
        while (current != null) {
            if (element.compareTo(current.element) < 0) {
                current = current.leftC;
            } else if (element.compareTo(current.element) > 0) {
                current = current.rightC;
            } else {
                return current;
            }
        }
        return null;
    }

    public void insert(E element, Article art) {
        if (root == null) {
            root = new TreeNode<>(element);
            root.head.addFirst(art);
        } else {
            TreeNode<E> parent = search(element);
            if (parent == null || !parent.element.equals(element)) {
                insertNewNode(root, element, art);
            } else {
                parent.head.addFirst(art);
            }
        }
        size++;
    }

    /*
    I added this method to handle recursive node placement 
    This method handles the recursive logic for adding a new 
    keyword to the binary tree. 
    It navigates left or right based on the keywords value, 
    finds the correct position, creates a new node and links the associated article to it. 
    */
    private void insertNewNode(TreeNode<E> node, E element, Article art)
    {
        //// If the new element is smaller than the current node's element
        if (element.compareTo(node.element) < 0) {
            if (node.leftC == null) 
            {
                // If null create a new node on the left and add the article to it
                node.leftC = new TreeNode<>(element);
                node.leftC.head.addFirst(art);
            } else 
            {
                // If not null insertNewNode on the left child
                insertNewNode(node.leftC, element, art);
            }
        } else 
        {   // If the new element is greater than or equal to the current node's element do the same as I did before 
            if (node.rightC == null) {
                node.rightC = new TreeNode<>(element);
                node.rightC.head.addFirst(art);
            } else {
                insertNewNode(node.rightC, element, art);
            }
        }
    }

    public void inOrder() {
        System.out.println("\n====================================================");
        System.out.println("Running InOrder Traversal of the Binary Search tree:");
        inOrder(root, "", true);
    }

    protected void inOrder(TreeNode<E> root, String prefix, boolean isLeft) {
        if (root == null) return;
        inOrder(root.leftC, prefix + (isLeft ? " " : "  "), true);
        System.out.printf("%s%s %s\n", prefix, (isLeft ? "L" : "R"), root.element);
        inOrder(root.rightC, prefix + (isLeft ? " " : "  "), false);
    }
    
    
    //Initiates a pre-order traversal of the BST, starting at the root node.
    public void preOrder() 
    {
        System.out.println("\n====================================================");
        System.out.println("Running PreOrder Traversal of the Binary Search tree:");
        preOrder(root, "", true);
    }
    
    //This is the helper method to handle the recursive pre-order traversal.
    //The isLeft boolean indiactes wether the current node is a left or right child 
    protected void preOrder(TreeNode<E> root, String prefix, boolean isLeft) 
    {
        //base case 
        if (root == null) return;
        //Prints the current node with its prefix and relationship (L or R).
        
        // Print the current node's keyword
        System.out.printf("%s%s %s\n", prefix, (isLeft ? "L" : "R"), root.element);
        
        // Recursively process the left subtree
        preOrder(root.leftC, prefix + (isLeft ? " " : " "), true);
        
        // Recursively process the right subtree,
        preOrder(root.rightC, prefix + (isLeft ? " " : " "), false);
    }

    //Initiates an in-order traversal of the BST and includes detailed information (articles) for each keyword.
    public void inOrderWithDetails() 
    {
        System.out.println("\n====================================================");
        System.out.println("Running InOrder Traversal of the Binary Search tree (With Details):");
        inOrderWithDetails(root, "", true);
    }

    //Helper method to handle the recursive in-order traversal with detailed output.
    private void inOrderWithDetails(TreeNode<E> root, String indent, boolean isLeft) 
    {
        if (root == null) return;
        //Recursively traverses the left subtree of the current node.
        inOrderWithDetails(root.leftC, indent + " ", true);
        
        //Prints the current node's details
        System.out.printf("%s%s %s\n", indent, (isLeft ? "L" : "R"), root.element);
        
        //Iterates through the list of articles
        for (Article article : root.head)
        {
            System.out.println(article);
        }
        
        
        System.out.println(); 
        
        //In an in-order traversal, the right subtree is visited after processing the current node.
        inOrderWithDetails(root.rightC, indent + " ", false);
    }

    //Initiates an in-order traversal of the BST, but only prints the keywords (no article details).
    
    public void inOrderKeywordsOnly() 
    {
        System.out.println("\n====================================================");
        System.out.println("Running InOrder Traversal of the Binary Search tree (Keywords Only):");
        inOrderKeywordsOnly(root, "", true);
    }
    
    //Helper method to handle the recursive in-order traversal for keywords only.
    private void inOrderKeywordsOnly(TreeNode<E> root, String indent, boolean isLeft) 
    {
        if (root == null) return;
        // Recursively process the left subtree
        inOrderKeywordsOnly(root.leftC, indent + " ", true);
        
        // Print the current node's keyword
        System.out.printf("%s%s %s\n", indent, (isLeft ? "L" : "R"), root.element);
        
        // Recursively process the right subtree
        inOrderKeywordsOnly(root.rightC, indent + " ", false);
    }
    
    //a pre-order traversal of the BST, printing only the keywords (no article details).
    public void preOrderKeywordsOnly() {
        System.out.println("\n====================================================");
        System.out.println("Running PreOrder Traversal of the Binary Search tree (Keywords Only):");
        preOrderKeywordsOnly(root, "", true);
    }
    
    //Helper method to handle the recursive pre-order traversal for keywords only.
    private void preOrderKeywordsOnly(TreeNode<E> root, String indent, boolean isLeft) 
    {
        if (root == null) return;
        
        // Print the current node's keyword, indicating if it's a left (L) or right (R) child
        System.out.printf("%s%s %s\n", indent, (isLeft ? "L" : "R"), root.element);
        
        // Recursively traverse the left subtree
        preOrderKeywordsOnly(root.leftC, indent + " ", true);
        
        //Recursively tranverse the right subtree 
        preOrderKeywordsOnly(root.rightC, indent + " ", false);
    }

    public void searchKeyword(E keyword) {
        TreeNode<E> node = search(keyword);
        if (node == null) {
            System.out.println(keyword + " does not exist in the Information Retrieval System!");
        } else {
            System.out.println(keyword);
            for (Article article : node.head) {
                System.out.println(article);
            }
        }
    }
}

class TreeNode<E> {
    protected E element;
    protected TreeNode<E> leftC;
    protected TreeNode<E> rightC;
    protected LinkedList<Article> head = new LinkedList<>();

    public TreeNode(E element) {
        this.element = element;
    }
}

class Article {
    private int id;
    private String title;
    private String author;

    public Article(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return String.format("\t%d | %s | %s", id, title, author);
    }
}
