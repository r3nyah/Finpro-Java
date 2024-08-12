package main.java.service;

import main.java.model.User;
import java.util.LinkedList;

public class HashTable {
    private LinkedList<User>[] table;
    private int capacity;

    public HashTable(int capacity) {
        this.capacity = capacity;
        table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public LinkedList<User> getChain(int index) {
        if (index < 0 || index >= capacity) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return table[index];
    }

    public void insert(User user) {
        int index = getIndex(user.getUsername());
        table[index].add(user);
    }

    public User search(String username) {
        int index = getIndex(username);
        for (User user : table[index]) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void update(String oldUsername, User newUser) {
        int index = getIndex(oldUsername);
        LinkedList<User> chain = table[index];
        for (int i = 0; i < chain.size(); i++) {
            User user = chain.get(i);
            if (user.getUsername().equals(oldUsername)) {
                chain.set(i, newUser);
                return;
            }
        }
    }

    public void delete(String username) {
        int index = getIndex(username);
        LinkedList<User> chain = table[index];
        chain.removeIf(user -> user.getUsername().equals(username));
    }

    private int getIndex(String username) {
        return Math.abs(username.hashCode()) % capacity;
    }
}
