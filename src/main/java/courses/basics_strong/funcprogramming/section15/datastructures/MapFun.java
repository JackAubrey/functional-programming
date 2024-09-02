package courses.basics_strong.funcprogramming.section15.datastructures;

public class MapFun<K,V> {

    private final Entry<K, V>[] entries;
    private final int size;

    public MapFun(int size) {
        this.size = size;
        this.entries = new Entry[size];

        for(int i = 0 ; i < size ; i++) {
            this.entries[i] = new Entry<>();
        }

    }

    private MapFun(Entry<K, V>[] entries, int size) {
        this.size = size;
        this.entries = entries;
    }

    public Integer getHash(K key) {
        return key.hashCode() % size ;
    }


    public MapFun<K, V> put(K key, V value){
        int hash = getHash(key);
        Entry<K, V> existingEntry = entries[hash];
        if(isDuplicate(key)) {
            existingEntry.value = value;
        }
        Entry<K, V> newEntry = new Entry<>(key, value);
        entries[hash] = newEntry;
        newEntry.next = existingEntry;

        return  new MapFun<>(entries, size);
    }

    private boolean isDuplicate(K key) {
        Entry<K, V> entry = entries[getHash(key)];
        while(entry != null) {
            if(key.equals(entry.key)) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    public V getValue(K key) {
        V val = null;
        int hash = getHash(key);
        Entry<K, V> entry = entries[hash];

        while(entry.next != null) {
            if(key.equals(entry.getKey())) {
                val = entry.getValue();
                break;
            }

            entry = entry.next;
        }

        return val;
    }

    public void display() {
        for(int i = 0 ; i < this.entries.length ; i++) {
            System.out.println(this.entries[i]);
        }
    }
}
