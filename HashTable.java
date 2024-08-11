import java.util.LinkedList;

public class HashTable {
    private LinkedList<User>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public HashTable(int size) {
        this.size = size;
        table = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private int midSquareHash(String key) {
        int hash = key.hashCode();
        int squared = hash * hash;
        String squaredStr = String.valueOf(squared);
        int midIndex = squaredStr.length() / 2;
        String midSquareStr = squaredStr.substring(Math.max(0, midIndex - 2), Math.min(squaredStr.length(), midIndex + 2));
        return Math.abs(Integer.parseInt(midSquareStr) % size);
    }

    public void insert(User user) {
        int index = midSquareHash(user.getUsername());
        table[index].add(user);
    }

    public User search(String username) {
        int index = midSquareHash(username);
        for (User user : table[index]) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void delete(String username) {
        int index = midSquareHash(username);
        table[index].removeIf(user -> user.getUsername().equals(username));
    }

    public void update(String oldUsername, User newUser) {
        int index = midSquareHash(oldUsername);
        for (int i = 0; i < table[index].size(); i++) {
            if (table[index].get(i).getUsername().equals(oldUsername)) {
                table[index].set(i, newUser);
                break;
            }
        }
    }

    public LinkedList<User> getChain(int index) {
        return table[index];
    }

    public int getSize() {
        return size;
    }
}
