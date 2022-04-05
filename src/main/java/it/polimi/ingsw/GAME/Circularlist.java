package it.polimi.ingsw.GAME;

public class Circularlist {
    public Island head=null;
    public Island tail=null;

    public void add(Island i){
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

    public Island getIsland(int index){
        if(head==null) return null;
        Island p=head;
        do{
            if(p.getId()==index) return p;
            p=p.next;
        }while(p!=tail);
        return null;
    }

    public int size(){
        if(head==null) return 0;
        Island p=head;
        int i;
        for(i=1; p!=tail; i++){
            p=p.next;
        }
        return i;
    }

    public void mergeIslands(Island i1, Island i2){
        //TODO:controllare che i1 e i2 siano consecutive, che abbiano entrambe una torre sopra e che sia dello stesso player -Doot
        SuperIsland i=new SuperIsland(i1.islandCount+i2.islandCount);
        i.getStudents().addAll(i1.getStudents());
        i.getStudents().addAll(i2.getStudents());
        for(int j=0; j<i.islandCount; j++){
            i.towers.add(new Tower(i1.getPlayer()));
        }
        Island first;
        if(i1.getId()<i2.getId()) first=i1;
        else first=i2;
        i.id=first.id;
        Island p=head;
        while(p.next!=first){
            p=p.next;
        }
        i.next=first.next.next;
        p.next=i;
        p=i.next;
        while(p!=tail){
            p.id--;
            p=p.next;
        }
    }
}
