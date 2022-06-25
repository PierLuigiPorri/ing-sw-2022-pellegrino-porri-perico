package it.polimi.ingsw.GAME;

/**
 * Represents an elements' configuration, in which, from the last one, you can reach the first one, since the elements are placed in a circular way.
 * It contains the head and the tail of this circular list.
 * The index of the head is always 1.
 * @author GC56
 */
public class CircularList {
    public Island head=null;
    public Island tail=null;

    /**
     * This method is called at the beginning, to build the CircularList adding islands to it.
     * As the model stands at the moment, is called 12 times, one for each island.
     * @param i Index of the island to be added. Int value between 1-12.
     * @requires (i!=null)
     * @ensures (\old(CircularList.size()) == CircularList.size()-1)
     * @author GC56
     */
    public void add(Island i){
        if(head==null){
            // if there is no head, means the list is empty,
            // so the island added will be both the first one and the last one (head and tail).
            head=i;
            tail=i;
            i.next=head;
        }
        else{
            // if the head is not null,
            // the island need to be appended to the end of the list (island i will be the new tail)
            tail.next=i;
            tail=i;
            tail.next=head;
        }
    }

    /**
     * Returns the island at the specified position in the CircularList.
     * @param index the index of the island to return.
     * @return the Island class instance specified with the index.
     * @requires (index>=1 && index<=12)
     * @author GC56
     */
    public Island getIsland(int index){
        // The list is empty.
        // (condition never taken because when the game is created, the islands and the circular list do the same, so is not possible to call this method before the CircularList is created and fully filled)
        if(head==null) return null;

        // if the list is not empty, is needed to cross the list until the island with id=index is found.
        Island p=head;
        do{
            if(p.getId()==index) return p;
            p=p.next;
        }while(p!=tail.next);
        return null;
    }

    /**
     * Returns the number of island in the CircularList.
     * @return the number of island in the CircularList.
     * @author GC56
     */
    public int size(){
        if(head==null) // The list is empty.
            return 0;
        Island tmp=head;
        int ret=1;
        while(tmp.getId()!= tail.getId()){
            // Counting every element between head and tail.
            tmp=tmp.next;
            ret++;
        }
        return ret;
    }

    /**
     * Merges two islands into one. It is called whenever two consecutive islands have to merge following conditions dictated by the game's logic.
     * @param i1 one of the two islands to be merged.
     * @param i2 the other island of the two to be merged.
     * @requires (i1!= null && i2!=null && (i1.getId()==i2.getId()+1 || i1.getId()+1=i2.getId()))
     * @ensures (!CircularList.contains(i1) && !CircularList.contains(i2) && CircularList.size()-1==\old(CircularList.size()))
     * @author GC56
     */
    public void mergeIslands(Island i1, Island i2){
        SuperIsland i=new SuperIsland(i1.islandCount+i2.islandCount); //creates a new SuperIsland to be added in the CircularList

        //gets all the students and all the towers from the two specified island and add them to the new SuperIsland.
        i.getStudents().addAll(i1.getStudents());
        i.getStudents().addAll(i2.getStudents());
        for(int j=0; j<i.islandCount; j++){
            i.towers.add(new Tower(i1.getPlayer()));
        }

        //sets in first i1 and in second i2 if (i1.getId()<i2.getId())
        //set  in first i2 and in second i1 if (i1.getId()>i2.getId())
        Island first, second;
        if((head==i1&&tail==i2)||(head==i2&&tail==i1)){
            first=tail;
            second=head;
        }
        else if(i1.getId()<i2.getId()){
            first=i1;
            second=i2;
        }
        else{
            first=i2;
            second=i1;
        }
        //looking for the minimum id between i1.getId() and i2.getId(). The minimum will be the SuperIsland id.
        i.id=Math.min(first.id, second.id);
        Island p=head;
        while(p.next!=first){
            p=p.next;
        }
        //sets the previous and the next island of the new SuperIsland.
        i.next=second.next;
        p.next=i;
        Island bef=p; //before
        p=i.next;

        //Decrease all the id to shift all the subsequence of islands with id > SuperIsland.getId()
        if(first == head) {
            head = i;
            while(p!=tail){
                p.id--;
                p=p.next;
            }
            if(p==tail){
                p.id--;
            }
        }
        else if(second==head){
            tail=bef;
            head=i;
        }
        else if(second==tail){
            tail=i;
        }
        else{
            while(p!=tail){
                p.id--;
                p=p.next;
            }
            p.id--;

        }
        //set mother nature on the SuperIsland, which is always true thanks to game's logic.
        i.motherNature=true;
    }

    /**
     * Says if the specified island is contained in the CircularList.
     * @param i the island to be searched in the CircularList.
     * @requires (i!=null && i.getId()>=head.getID() && i.getId<=tail.getId())
     * @return true if and only if the specified island is contained in the CircularList.
     * @author GC56
     */
    public boolean contains(Island i){
        Island p = head;
        while (!p.equals(i)) {
            if (p.getId() == tail.getId())
                return false;
            p=p.next;
        }
        return true;
    }
}
