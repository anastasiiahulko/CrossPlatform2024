package app;

class NameData implements Comparable<NameData> {
    private String name;
    private int count;
    private int rank;

    public NameData(String name, int count, int rank) {
        this.name = name;
        this.count = count;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public int compareTo(NameData other) {
        // Спочатку порівнюємо за рангом, потім за кількістю
        if (this.rank != other.rank) {
            return Integer.compare(this.rank, other.rank); // Менший ранг - вище в списку
        } else {
            // Якщо ранги однакові, порівнюємо кількість
            return Integer.compare(other.count, this.count);
        }
    }
}