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
        }while(p!=tail.next);
        return null;
    }

    public int size(){
        if(head==null)
            return 0;
        Island tmp=head;
        int ret=1;
        while(tmp.getId()!= tail.getId()){
            tmp=tmp.next;
            ret++;
        }
        return ret;
    }

    public void mergeIslands(Island i1, Island i2){
        SuperIsland i=new SuperIsland(i1.islandCount+i2.islandCount);
        i.getStudents().addAll(i1.getStudents());
        i.getStudents().addAll(i2.getStudents());
        for(int j=0; j<i.islandCount; j++){
            i.towers.add(new Tower(i1.getPlayer()));
        }
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
        i.id=first.id;
        Island p=head;
        while(p.next!=first){
            p=p.next;
        }
        i.next=second.next;
        p.next=i;
        p=i.next;
        if(first == head) {
            head = i;
            while(p!=tail){
                p.id--;
                p=p.next;
            }
        }
        else if(second==head){
            tail=i;
            head=i.next;
            i.id--;
            while(p!=tail){
                p.id--;
                p=p.next;
            }
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
        i.motherNature=true;
    }

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
