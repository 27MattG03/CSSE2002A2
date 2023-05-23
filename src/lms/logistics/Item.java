package lms.logistics;

public class Item {
    private String name;
    public Item (String name) throws IllegalArgumentException {
        if (name != null && name != "") {
            this.name = name;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if( o.getClass().equals(this.getClass())&& (o.hashCode() == this.hashCode()) ){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return this.name;
    }
}

