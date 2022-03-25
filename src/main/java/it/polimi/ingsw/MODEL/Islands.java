package it.polimi.ingsw.MODEL;

public class Islands {
    public IslandType head=null;
    public IslandType tail=null;

    public void add(IslandType i){
        if(head==null){
            head=i;
            tail=i;
            i.next=head;
        }
        else{
            tail.next=i;
            tail=i;
            tail.next=head;
        }
    }

    public IslandType getIsland(int index){
        if(head==null) return null;
        IslandType p=head;
        do{
            if(p.getId()==index) return p;
            p=p.next;
        }while(p!=tail);
        return null;
    }

    public int size(){
        if(head==null) return 0;
        IslandType p=head;
        int i;
        for(i=1; p!=tail; i++){
            p=p.next;
        }
        return i;
    }
}
