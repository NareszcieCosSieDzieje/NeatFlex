package NeatFlexClasses.Simulation;

import javafx.collections.*;


import java.io.Serializable;
import java.util.*;

public class DummyManager implements Serializable {


    //=======================================MEMBERS===========================================\\

    private NeatFlexService service;

    transient private ObservableList<User> dummyUsers;
    transient private ObservableList<Distributor> dummyDistributors;

    transient private HashMap<User,Thread> dummyUsersThreads;
    transient private HashMap<Distributor,Thread> dummyDistributorsThreads;
    private Vector<User> serializationUsers;
    private Vector<Distributor> serializationDistributors;

    public DummyManager(NeatFlexService service){
        this.service = service;
        this.serializationDistributors=null;
        this.serializationUsers=null;
        this.dummyUsers = FXCollections.observableList(new Vector<>());
        this.dummyDistributors = FXCollections.observableList(new Vector<>());
        this.dummyUsersThreads = new LinkedHashMap<>(100);
        this.dummyDistributorsThreads = new LinkedHashMap<>(100);
    }

    public ObservableList<User> getUserListWrapper(){
        return dummyUsers;
    }

    public ObservableList<Distributor> getDistributorListWrapper(){
        return dummyDistributors;
    }


    public synchronized Distributor getRandomDistributor(){
        int ran = RandomGenerator.randomWithRange(0, dummyDistributors.size()-1);
        return dummyDistributors.get(ran);
    }

    public boolean hasAnyDistributors(){ //synchronized
        return !(this.dummyDistributors.isEmpty());
    }


    public void addUser() {
        User runUser = new User(this.service);
        Thread threadUser = new Thread(runUser);
        threadUser.setDaemon(true);
        threadUser.start();
        dummyUsers.add(runUser);
        dummyUsersThreads.put( runUser, threadUser );
    }

    public void addDistributor(){
        Distributor runDist = new Distributor(this.service);
        Thread threadDist = new Thread(runDist);
        threadDist.setDaemon(true);
        threadDist.start();
        dummyDistributors.add(runDist);
        dummyDistributorsThreads.put(runDist, threadDist);
    }


    public void removeDistributor(Distributor d) {
        dummyDistributorsThreads.get(d).interrupt();
        dummyDistributorsThreads.remove(d);
        dummyDistributors.remove(d);
    }

    public void removeUser(User u){
        dummyUsersThreads.get(u).interrupt();
        dummyUsersThreads.remove(u);
        dummyUsers.remove(u);
    }

    public void startAllDistributorThreads(){
        if(this.dummyDistributorsThreads!=null){
            this.dummyDistributorsThreads.clear();
        }
        else {
            this.dummyDistributorsThreads=new LinkedHashMap<>();
        }
        for(Distributor distributor : this.serializationDistributors){
            Thread distThread = new Thread(distributor);
            this.dummyDistributorsThreads.put(distributor,distThread);
        }
        for (Map.Entry<Distributor, Thread> entry : dummyDistributorsThreads.entrySet()) {
            entry.getValue().start();
        }
    }


    public void startAllUserThreads(){
        if(this.dummyUsersThreads!=null){
            this.dummyUsersThreads.clear();
        }
        else {
            this.dummyUsersThreads=new LinkedHashMap<>();
        }
        for(User user : this.serializationUsers){
            Thread userThread = new Thread(user);
            this.dummyUsersThreads.put(user,userThread);
        }
        for (Map.Entry<User, Thread> entry : dummyUsersThreads.entrySet()) {
            entry.getValue().start();
        }
    }

    public void deserializeToObservableLists(){
        this.dummyDistributors = FXCollections.observableList(this.serializationDistributors);
        for(Distributor d: this.dummyDistributors){
            d.deserializeProducts();
        }
        this.dummyUsers = FXCollections.observableList(this.serializationUsers);
        for(User u: this.dummyUsers){
            u.deserializeProducts();
        }
    }

    public void interruptAllDistributorThreads(){
        for (Map.Entry<Distributor, Thread> entry : dummyDistributorsThreads.entrySet()) {
            entry.getValue().interrupt();
        }
    }

    public void interruptAllUserThreads(){
        for (Map.Entry<User, Thread> entry : dummyUsersThreads.entrySet()) {
            entry.getValue().interrupt();
        }
    }

    public void serializeUsers(){
        Vector<User> userList = new Vector<>();
        for(Map.Entry<User,Thread> entry : dummyUsersThreads.entrySet()){
            User u = entry.getKey();
            u.serializeProducts();
            userList.add(u);
        }
        this.serializationUsers = userList;
    }

    public void serializeDistributors(){
        Vector<Distributor> distributorList = new Vector<>();
        for(Map.Entry<Distributor,Thread> entry : dummyDistributorsThreads.entrySet()){
            Distributor d = entry.getKey();
            d.serializeProducts();
            distributorList.add(d);
        }
        this.serializationDistributors = distributorList;
    }


}
