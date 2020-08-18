/**
 * LinkedList class implements a doubly-linked list.
 */
public class MyLinkedList<AnyType> implements Iterable<AnyType>
{
    /**
     * Construct an empty LinkedList.
     */
    public MyLinkedList( )
    {
        doClear( );
    }
    
    private void clear( )
    {
        doClear( );
    }
    
    /**
     * Change the size of this collection to zero.
     */
    public void doClear( )
    {
        beginMarker = new Node<>( null, null, null );
        endMarker = new Node<>( null, beginMarker, null );
        beginMarker.next = endMarker;
        
        theSize = 0;
        modCount++;
    }
    
    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size( )
    {
        return theSize;
    }
    
    public boolean isEmpty( )
    {
        return size( ) == 0;
    }
    
    /**
     * Adds an item to this collection, at the end.
     * @param x any object.
     * @return true.
     */
    public boolean add( AnyType x )
    {
        add( size( ), x );   
        return true;         
    }
    
    /**
     * Adds an item to this collection, at specified position.
     * Items at or after that position are slid one position higher.
     * @param x any object.
     * @param idx position to add at.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    public void add( int idx, AnyType x )
    {
        addBefore( getNode( idx, 0, size( ) ), x );
    }
    
    /**
     * Adds an item to this collection, at specified position p.
     * Items at or after that position are slid one position higher.
     * @param p Node to add before.
     * @param x any object.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */    
    private void addBefore( Node<AnyType> p, AnyType x )
    {
        Node<AnyType> newNode = new Node<>( x, p.prev, p );
        newNode.prev.next = newNode;
        p.prev = newNode;         
        theSize++;
        modCount++;
    }   
    
    
    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType get( int idx )
    {
        return getNode( idx ).data;
    }
        
    /**
     * Changes the item at position idx.
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType set( int idx, AnyType newVal )
    {
        Node<AnyType> p = getNode( idx );
        AnyType oldVal = p.data;
        
        p.data = newVal;   
        return oldVal;
    }
    
    /**
     * Gets the Node at position idx, which must range from 0 to size( ) - 1.
     * @param idx index to search at.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1, inclusive.
     */
    private Node<AnyType> getNode( int idx )
    {
        return getNode( idx, 0, size( ) - 1 );
    }

    /**
     * Gets the Node at position idx, which must range from lower to upper.
     * @param idx index to search at.
     * @param lower lowest valid index.
     * @param upper highest valid index.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive.
     */    
    private Node<AnyType> getNode( int idx, int lower, int upper )
    {
        Node<AnyType> p;
        
        if( idx < lower || idx > upper )
            throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );
            
        if( idx < size( ) / 2 )
        {
            p = beginMarker.next;
            for( int i = 0; i < idx; i++ )
                p = p.next;            
        }
        else
        {
            p = endMarker;
            for( int i = size( ); i > idx; i-- )
                p = p.prev;
        } 
        
        return p;
    }
    
    /**
     * Removes an item from this collection.
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public AnyType remove( int idx )
    {
        return remove( getNode( idx ) );
    }
    
    /**
     * Removes the object contained in Node p.
     * @param p the Node containing the object.
     * @return the item was removed from the collection.
     */
    private AnyType remove( Node<AnyType> p )
    {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;
        
        return p.data;
    }
    
    /**
     * Returns a String representation of this collection.
     */
    public String toString( )
    {
        StringBuilder sb = new StringBuilder( "[ " );

        for( AnyType x : this )
            sb.append( x + " " );
        sb.append( "]" );

        return new String( sb );
    }

    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public java.util.Iterator<AnyType> iterator( )
    {
        return new LinkedListIterator( );
    }

    /**
     * This is the implementation of the LinkedListIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the MyLinkedList.
     */
    private class LinkedListIterator implements java.util.Iterator<AnyType>
    {
        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;
        
        public boolean hasNext( )
        {
            return current != endMarker;
        }
        
        public AnyType next( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !hasNext( ) )
                throw new java.util.NoSuchElementException( ); 
                   
            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }
        
        public void remove( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !okToRemove )
                throw new IllegalStateException( );
                
            MyLinkedList.this.remove( current.prev );
            expectedModCount++;
            okToRemove = false;       
        }
    }
    
    /**
     * This is the doubly-linked list node.
     */
    private static class Node<AnyType>
    {
        public Node( AnyType d, Node<AnyType> p, Node<AnyType> n )
        {
            data = d; prev = p; next = n;
        }
        
        public AnyType data;
        public Node<AnyType>   prev;
        public Node<AnyType>   next;
    }
    
     /**
     -------------------------------------------------------------Project 1 Start----------------------------------------------------------------------
      */
    /**
     * This is Swap method.
     */
     public void swap(int idx1, int idx2)
    {
        if( idx1 >= size() || idx2 >= size() )
            throw new IndexOutOfBoundsException( "index1: " + idx1 + " and/or index2: " + idx2 + " is greater than size: " + size( ) );
        
        Node<AnyType> p = getNode( idx1 );
        Node<AnyType> q = getNode( idx2 );
        Node<AnyType> tmp;
             
        p.prev.next = q;
        q.prev.next = p;
        tmp = p.next;
        p.next = q.next;                              
        q.next = tmp;
    }
    
    
    /**
     * This is Shift method.
     */
    public void shift(int a)  
    {             
        if(a>0)
          {
             int idx = size() - 1;
             while( a>0 ) 
                {
                 Node<AnyType> p = getNode( 0 );
                 add( size() - 1 , remove(p) );
                 a--;
                }
          }
        else if(a<0)
          {
             while (a<0)
                {
                 Node<AnyType> p = getNode( size() - 1);
                 add(0 , remove(p));
                 a++;
                }
          } 
    } 
    
    /**
     * This is Erase method.
     */
    void erase(MyLinkedList<AnyType> lst ,int idx, int count)        
    {
        int t = idx+count;
        
        if ( idx >= size())
            throw new IndexOutOfBoundsException( "index: " + idx + " is greater than size: " + size( ) );
        else if ( t >= size())
            throw new IndexOutOfBoundsException( "index + count: " + t + " is greater than size: " + size( ) );
        else
        { 
          Node<AnyType> n1 = getNode(idx);
          Node<AnyType> n2 = getNode((idx + count) - 1);
          
          if(n1.data.equals(n2))
          {
              remove(getNode(idx));   
          } 
          else
          {
              for( int i = idx; i < idx+count; i++ )
              {
                  remove (getNode(idx));
              }
          }
        }
    }
    
    
    /**
     * This is InsertList method.
     */
    public void insertList( MyLinkedList<AnyType> newlst , int idx)
    {   
        if ( idx >= size())
            throw new IndexOutOfBoundsException( "index: " + idx + " is greater than size: " + size( ) );
            
        else
        {
        int i = 0;
        while( i < newlst.size() )
            {
            add( idx, newlst.beginMarker.next.data);
            newlst.remove(newlst.getNode(0));
            idx++;
            }
        }
    }
    
    /**
     -------------------------------------------------------------Project 1 End ----------------------------------------------------------------------
      */
      
    private int theSize;
    private int modCount = 0;
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;
}

class TestLinkedList
{
    public static void main( String [ ] args )
    {
        MyLinkedList<Integer> lst = new MyLinkedList<>( );

        for( int i = 0; i < 10; i++ )
                lst.add( i );
        for( int i = 20; i < 30; i++ )
                lst.add( 0, i );

        lst.remove( 0 );
        lst.remove( lst.size( ) - 1 );

        System.out.println( lst );

        java.util.Iterator<Integer> itr = lst.iterator( );
        while( itr.hasNext( ) )
        {
                itr.next( );
                itr.remove( );
                System.out.println( lst );
        }
        
        /**
         -------------------------------------------------------------Project 1 Start ----------------------------------------------------------------------
        */
        for( int i = 0; i < 10; i++ )
            lst.add( i );
                
        System.out.println("New List for swapping : " + lst );
        
        System.out.println("Swapping nodes 1 and 5");
        lst.swap(1,5);
        System.out.println( lst );
        
        lst.doClear(); 
        
        for( int i = 1; i < 5; i++ )
        {
        lst.add( i );
        }
        System.out.println("\nNew List for shifting (as in question): " +lst);
        System.out.println("Shifting +2");
        lst.shift(2);
        System.out.println( lst );
         
      
        lst.doClear(); 
        
        for( int i = 1; i < 5; i++ )
          {
          lst.add( i );
          }
        System.out.println("\nNew List for shifting (as in question): " +lst);
        System.out.println("Shifting -1");
        lst.shift(-1);
        System.out.println( lst );
       
       
      
        MyLinkedList<Integer> lst1 = new MyLinkedList<>( );
      
        for( int i = 10; i <= 15; i++ )
            {
            lst1.add( i );
            }
            
            System.out.println("\nList to insert to : " +lst);
            System.out.println("New list inserted at index 2:" +lst1);
            lst.insertList(lst1, 2);
            System.out.println( lst );
        
       lst.doClear(); 
         for( int i = 0; i < 10; i++ )
         {
            lst.add( i );
         }
            
            
        System.out.println("\nNew List for erase: " +lst);
        System.out.println("After Erasing 4 elements from index 3");
        
        lst.erase(lst,3,4);
        System.out.println(lst);
        
        /**
            -------------------------------------------------------------Project 1 End ----------------------------------------------------------------------
        */
    }
}