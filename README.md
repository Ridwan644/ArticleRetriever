🗂️ Information Retrieval System Using Binary Search Trees

A Java-based keyword-driven article lookup system

📌 Overview

This project implements an Information Retrieval System that stores and retrieves academic articles based on their associated keywords. Using a Binary Search Tree (BST) sorted by keywords, each node stores an unordered linked list of Record objects (articles) that share that keyword.

This allows users or programs to efficiently search for all articles related to a given topic simply by querying a keyword.

🎯 Project Goals

Parse data from a structured text input file

Create Article objects containing:

Title ID

Title

Author

Insert each keyword into a BST

Store all associated articles in each keyword node through an unordered linked list

Output the constructed BST and all article lists for each keyword

🧠 How the System Works
1. File Input Structure (datafile.txt)

Each article is stored in the following format:

<titleId>
<title>
<author>
<number_of_keywords>
<keyword_1>
<keyword_2>
...
<keyword_n>


Example:

1023
Object Class Recognition using Images of Abstract Regions
Yi Li
4
object recognition
abstract regions
mixture models
EM algorithm

2. Core Algorithm
Step 1 — Read Input

A BufferedReader is used to efficiently read lines from datafile.txt.

Step 2 — Create Article Object

For each article entry:

Read titleId

Read title

Read author

Build a new Article object

Step 3 — Insert Keywords Into BST

For each keyword:

Insert keyword into the BST

If node exists → reuse it

If node doesn't exist → create new BST node

Step 4 — Attach Article to Keyword Node

Each keyword node keeps an unordered linked list of Record objects.
Each Record contains:

articleId

title

author

Step 5 — Output Entire BST

Prints each keyword alphabetically with its corresponding article list.

🏗️ Data Structures Used
Binary Search Tree (BST)

Nodes sorted by keyword

Each node holds a linked list of articles

Unordered Linked List

Stores all Record objects for a given keyword

Allows fast insertions (O(1))

📁 Project Structure
/src
  ├── Article.java
  ├── Record.java
  ├── BST.java
  ├── BSTNode.java
  ├── LinkedList.java
  ├── LinkedListNode.java
  ├── Main.java
datafile.txt
README.md

▶️ How to Run the Program
1. Compile
javac *.java

2. Run
java Main

3. Make sure the input file exists

Place datafile.txt in the same directory as the .java files or update the path in the code.

📝 Example Output (Simplified)
Keyword: abstract regions
  -> Article ID: 1023 | Yi Li | Object Class Recognition...
Keyword: EM algorithm
  -> Article ID: 1023 | Yi Li | Object Class Recognition...
Keyword: mixture models
  -> Article ID: 1023 | Yi Li | Object Class Recognition...
Keyword: object recognition
  -> Article ID: 1023 | Yi Li | Object Class Recognition...

🧪 Environment Used

Java 11.0.4

OnlineGDB IDE (compatible with standard Java compilers)

🙌 Acknowledgements

This project follows the specification provided in the assignment for implementing an information retrieval system using BSTs and linked lists.
