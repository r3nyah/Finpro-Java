package service;

import model.User;

import java.util.LinkedList;

public class HashTable {
    private static final int CAPACITY = 100; // Adjust as needed
    private LinkedList<User>[] table;

    @SuppressWarnings("unchecked")
    public HashTable() {
        table = new LinkedList[CAPACITY];
        for (int i = 0; i < CAPACITY; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private int hash(String username) {
        return Math.abs(username.hashCode()) % CAPACITY;
    }

    public void insert(User user) {
        int index = hash(user.getUsername());
        table[index].add(user);
    }

    public User search(String username) {
        int index = hash(username);
        for (User user : table[index]) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void update(String oldUsername, User newUser) {
        int index = hash(oldUsername);
        LinkedList<User> bucket = table[index];
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).getUsername().equals(oldUsername)) {
                bucket.set(i, newUser);
                return;
            }
        }
    }

    public void delete(String username) {
        int index = hash(username);
        LinkedList<User> bucket = table[index];
        bucket.removeIf(user -> user.getUsername().equals(username));
    }

    public int getCapacity() {
        return CAPACITY;
    }

    public LinkedList<User> getChain(int index) {
        return table[index];
    }
}
