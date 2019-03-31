package GUI;

import NeatFlexClasses.Simulation.NeatFlexService;

public abstract class TableViewItem {

    static protected Object item;
    static protected NeatFlexService service;
    static private boolean itemDeleted;

    static protected Object getObject(){
        return item;
    }

    protected NeatFlexService getService(){
        return service;
    }

    static public void setService(NeatFlexService vod){
        service=vod;
    }

    static public void initialize(Object item){
        TableViewItem.item=item;
        itemDeleted=false;
    }

    protected void markToDelete(){
        itemDeleted=true;
    }

    static public boolean wasItemDeleted(){
        return TableViewItem.itemDeleted;
    }

}
