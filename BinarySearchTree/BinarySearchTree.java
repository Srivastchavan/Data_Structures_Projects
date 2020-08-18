// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate


/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public BinarySearchTree( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        root = insert( x, root );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        root = remove( x, root );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( ) throws Exception
    {
        if( isEmpty( ) )
            throw new Exception("Underflow Exception");
        return findMin( root ).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( ) throws Exception
    {
        if( isEmpty( ) )
            throw new Exception("Underflow Exception");
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains( AnyType x )
    {
        return contains( x, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return new BinaryNode<AnyType>( x, null, null );
        
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
            ;  // Duplicate; do nothing
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return t;   // Item not found; do nothing
            
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
    {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t )
    {
        if( t != null )
            while( t.right != null )
                t = t.right;

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return false;
            
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            return contains( x, t.left );
        else if( compareResult > 0 )
            return contains( x, t.right );
        else
            return true;    // Match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the subtree.
     */
    private void printTree( BinaryNode<AnyType> t )
    {
        if( t != null )
        {
            printTree( t.left );
            System.out.println( t.element );
            printTree( t.right );
        }
    }

    /**
     * Internal method to compute height of a subtree.
     * @param t the node that roots the subtree.
     */
    private int height( BinaryNode<AnyType> t )
    {
        if( t == null )
            return -1;
        else
            return 1 + Math.max( height( t.left ), height( t.right ) );    
    }
    

    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType>
    {
            // Constructors
        BinaryNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
        }

        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
    }

//-----------------------------------------------------------------Project 2 Start-------------------------------------------------------------------------

   // a) nodeCount
   //     Recursively traverses the tree and returns the count of nodes.
    public int nodeCount()
    {
        return nodeCount(root);
    }
    private int nodeCount(BinaryNode<AnyType> t)
    {
        if(t == null)
            return 0;
        else 
            return 1 + nodeCount(t.left) + nodeCount(t.right);   
    }
    
    // b) isFull 
    //     Returns true if the tree is full.  A full tree has every node as either a leaf or a parent with two children.

    public boolean isFull()
    {
        return isFull(root);
    }
    private boolean isFull(BinaryNode<AnyType> t)
    {
        
        if(t == null)
            {
            return true;
            }
          
        if(t.left == null && t.right == null )   
            {
            return true;
            }
        
        if((t.left!=null) && (t.right!=null))           
            {
            return (isFull(t.left) && isFull(t.right));       
            }
          
        else
        return false;
    }
 
   // c) compareStructure 
   //     Compares the structure of current tree to another tree and returns true if they match.


    public boolean compareStructure(BinarySearchTree<AnyType> t1)
    {
        BinaryNode<AnyType> root1 = t1.root;
        return compareStructure(root, root1);
    }
    
  
    private boolean compareStructure(BinaryNode<AnyType> t, BinaryNode<AnyType> t1) 
    {
        
        if ((t == null && t1 != null) || (t != null && t1 == null))  
            {
            return false;
            }
        if (t == null && t1 == null) 
            {
            return true;
            }
        return compareStructure(t.left, t1.left) && compareStructure(t.right, t1.right);
    }

   // d) equals
   //     Compares the current tree to another tree and returns true if they are identical.

    public boolean equals(BinarySearchTree<AnyType> t1)
    {
        BinaryNode<AnyType> root1 = t1.root;
        return equals(root, root1);
    }
    
    
    private boolean equals(BinaryNode<AnyType> t, BinaryNode<AnyType> t1) 
    {
        
        if ((t == null && t1 != null) || (t != null && t1 == null)) 
            {
            return false;
            }

        if (t == null && t1 == null) 
            {
            return true;
            }
        if (t.element != t1.element)    
        {
        return false;
        }
        return equals(t.left, t1.left) && equals(t.right, t1.right);
    }
    
	// e) copy
   //     Creates and returns a new tree that is a copy of the original tree.

     public BinarySearchTree<AnyType> copy(BinarySearchTree<AnyType> oldtree)
        {
            BinarySearchTree<AnyType> newTree = new BinarySearchTree<>();
            return copy(root, newTree);
        }
     private BinarySearchTree<AnyType> copy(BinaryNode<AnyType> t, BinarySearchTree<AnyType> newTree)
        {
            
        if (t == null)       
	    return null;
 
        newTree.insert(t.element);      
        if(t.left!= null)
                {
                  copy(t.left, newTree);  
                }
        
        if(t.right!= null)
                {
                copy(t.right, newTree);  
                }
       
       return newTree;
        }
            
   // f) mirror
   //      Creates and returns a new tree that is a mirror image of the original tree. For example, for the tree on the left, the tree on the right is returned.
   
    public BinarySearchTree<AnyType> mirror()
	{
		BinarySearchTree<AnyType> newTree = new BinarySearchTree<>();
		if (this.root != null)
		{
			newTree.root = new BinaryNode<AnyType>(this.root.element);
			mirror(this.root, newTree.root);
		}
		return newTree;
	}

	private void mirror(BinaryNode<AnyType> oldNode, BinaryNode<AnyType> newNode) 
	{
		if (oldNode != null)
		{
			if (oldNode.left != null)
				newNode.right = new BinaryNode<AnyType>(oldNode.left.element);
			if (oldNode.right != null)
				newNode.left = new BinaryNode<AnyType>(oldNode.right.element);
		}
		if (oldNode.left != null)
			mirror(oldNode.left, newNode.right);
		if (oldNode.right != null)
			mirror(oldNode.right, newNode.left);
	}


   // g) isMirror 
   //     Returns true if the tree is a mirror of the passed tree.
    
    public boolean isMirror(BinarySearchTree<AnyType> t2) 
	{
		if (t2.equals(this.mirror()))
			return true;
		else
			return false;
	}

   // h) rotateRight
   //     Performs a single rotation on the node having the passed value. If a RotateRight on 100 is performed.
   
  	public void rotateRight(AnyType x)
	{
		if(root.element.compareTo(x) == 0)
		{
			System.out.println("Root will be Rotated.");
			BinaryNode<AnyType> node;
			node = root.left;
            root.left = node.right;
			node.right = root;
			root = node;
			System.out.println("New root value is set to: "+ root.element);
		}
		else
		{
			BinaryNode<AnyType> parentNode;
			parentNode = findParent(root,x);
			BinaryNode<AnyType> child;
			BinaryNode<AnyType> node;
			if(parentNode.right != null && parentNode.right.element.compareTo(x) == 0)
			{
				child = parentNode.right;
				node = child.left;
				if(node != null)
				{
					child.left = node.right;
					node.right = child;
					parentNode.right = node;
				}
			}
			if (parentNode.left != null && parentNode.left.element.compareTo(x) == 0)
			{
				child = parentNode.left;
				node = child.left;
				if(node != null)
				{
					child.left = node.right;
					node.right = child;
					parentNode.left = node;
				}
			}
					
		}
	}
	
   // i) rotateLeft 
   //     As above but left rotation.
   
	public void rotateLeft(AnyType x)
	{
		if(root.element.compareTo(x) == 0)
		{
			System.out.println("Root will be Rotated.");
			BinaryNode<AnyType> node;
			node = root.right;
			root.right = node.left;
			node.left = root;
			root = node;
			System.out.println("New root value is set to: "+ root.element);
		}
		else
		{
			BinaryNode<AnyType> parentNode;
			parentNode = findParent(root,x);
			BinaryNode<AnyType> child;
			BinaryNode<AnyType> node;
			if(parentNode.right != null && parentNode.right.element.compareTo(x) == 0)
			{
				child = parentNode.right;
				node = child.right;
				if(node != null)
				{
					child.right = node.left;
					node.left = child;
					parentNode.right = node;
				}
			}
			if (parentNode.left != null && parentNode.left.element.compareTo(x) == 0)
			{
				child = parentNode.left;
				node = child.right;
				if(node != null)
				{
					child.right = node.left;
					node.left = child;
					parentNode.left = node;
				}
			}
					
		}
	}
	
	private BinaryNode<AnyType> findParent(BinaryNode<AnyType> node,AnyType x)
	{
		if(node.left != null && node.left.element.compareTo(x) == 0 )
			return node;
		if(node.right != null && node.right.element.compareTo(x) == 0)
			return node;
		if(node.element.compareTo(x) < 0)
			return findParent(node.right,x);
		else
			return findParent(node.left,x);
			
	}
    
    
   
   //j) printLevels
   //      Performs a level-by-level printing of the tree.
   
	private void printLevels(BinarySearchTree<AnyType> tree)           
    {
        int h = height(root)+1;                            
        for (int i=1; i<=h; i++) 
        {
            printLevels(tree.root, i);
            System.out.println(" <--- Level " + i );
        }
    }
    
     private void printLevels(BinaryNode<AnyType> t, int level)
    {
        if(t == null)
        {
        }
        
       else if(level == 1)                                     
        {
        System.out.print(t.element+" ");
        }
       
        else 
        {
        printLevels(t.left, level-1);                        
        printLevels(t.right, level-1);                
        }
    }
    


//----------------------------------------------------------------------Project 2 end--------------------------------------------------------------------

      /** The tree root. */
    private BinaryNode<AnyType> root;


        // Test program
    public static void main( String [ ] args )
    {
       //  BinarySearchTree<Integer> t = new BinarySearchTree<Integer>( );
//         final int NUMS = 4000;
//         final int GAP  =   37;
// 
//         System.out.println( "Checking... (no more output means success)" );
// 
//         for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
//             t.insert( i );
// 
//         for( int i = 1; i < NUMS; i+= 2 )
//             t.remove( i );
// 
//         if( NUMS < 40 )
//             t.printTree( );
//         if( t.findMin( ) != 2 || t.findMax( ) != NUMS - 2 )
//             System.out.println( "FindMin or FindMax error!" );
// 
//         for( int i = 2; i < NUMS; i+=2 )
//              if( !t.contains( i ) )
//                  System.out.println( "Find error1!" );
// 
//         for( int i = 1; i < NUMS; i+=2 )
//         {
//             if( t.contains( i ) )
//                 System.out.println( "Find error2!" );
//         }



//----------------------------------------------------------------------Project 1 start--------------------------------------------------------------------

        //  k) main 
        //     Demonstrate in your main method that all of your new methods work.

      BinarySearchTree<Integer> t = new BinarySearchTree<>();                         
       t.insert(5);
       t.insert(3);
       t.insert(8);
       t.insert(1);
       t.insert(4);

       
       System.out.println("Inorder Printing: ");
       t.printTree();                                     
       System.out.println();
       
       System.out.println("a)node Count example -");
       int a =  t.nodeCount();
       System.out.println("The number of nodes in the tree is: "+a);             
       System.out.println();
       
       System.out.println("b) isFull example -");   
       t.printLevels(t);
       System.out.println("Checking if the tree1 is full? " + t.isFull());
       boolean isFullCheck = t.isFull();
       if(isFullCheck == true)
           System.out.println("Tree is Full");
       else
           System.out.println("Tree is not Full");
       
       BinarySearchTree<Integer> t1 = new BinarySearchTree<>();        
       t1.insert(10);
       t1.insert(5);
       t1.insert(15);
       t1.insert(2);
       t1.insert(7);
       
       System.out.println();
       System.out.println("c) compareStructure example -");
       boolean StructureCheck = t.compareStructure(t1);                      
       System.out.println("\nTree 1:");
       t.printLevels(t);
       System.out.println("\nTree 2:");
       t1.printLevels(t1);
       
       
       
       if(StructureCheck == true)
           System.out.println("Tree1 and Tree2 have Same Structure");
       else
           System.out.println("Tree1 and Tree2 do not have Same Structure");
           
       System.out.println();
       System.out.println("d) equals example -");
           
       boolean TreeEqualCheck = t.equals(t1);
       System.out.println("\nTree 1:");                          
       t.printLevels(t);
       System.out.println("\nTree 2:");
       t1.printLevels(t1);
       
       
       if(TreeEqualCheck == true)
           System.out.println("Trees are equal");
       else
           System.out.println("Trees are not equal");

       System.out.println();    
       System.out.println("e) copy example -");
       System.out.println("Copying Tree1 to Tree2 ");
       t1.makeEmpty();                                            
       System.out.println("\nOriginal Tree:(Tree1) ");
       t.printLevels(t);
       t1 = t.copy(t);
       System.out.println("\nCopied Tree:(Tree2) ");
       t1.printLevels(t1);   
       System.out.println();
       
       
      System.out.println("f) mirror example -");
		System.out.println("Creating mirror image of tree1 and printing:(MirrorTree) ");
		BinarySearchTree<Integer> mirrorTree = t.mirror();
		mirrorTree.printLevels(mirrorTree);
		System.out.println();

		System.out.println("g) isMirror example -");
		System.out.println("Checking whether the tree1 is a mirror of MirrorTree:(from above example) -" + t.isMirror(mirrorTree));
		System.out.println();
   
      BinarySearchTree<Integer> t2 = new BinarySearchTree<>();
		t2.insert(100);
		t2.insert(50);
		t2.insert(150);
		t2.insert(40);
		t2.insert(45);
      
      System.out.println("h) rotateRight example -");
		System.out.println("Printing levels of tree before rotation:");
		t2.printLevels(t2);
		t2.rotateRight(100);
		System.out.println("Printing levels of tree after rotating right:");
		t2.printLevels(t2);
		System.out.println();
      
      System.out.println("i) rotateLeft example -");
		System.out.println("Printing levels of tree before rotation:");
		t2.printLevels(t2);
		t2.rotateLeft(50);
		System.out.println("Printing levels of tree after rotating left:");
		t2.printLevels(t2);
		System.out.println();
      
      
      System.out.println("j) printLevels example -");
		System.out.println("Printing levels of tree2: " );
		t2.printLevels(t2);
     
     }
//----------------------------------------------------------------------Project 2 end--------------------------------------------------------------------     
     
}