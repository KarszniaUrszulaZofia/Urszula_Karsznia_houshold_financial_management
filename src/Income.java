class Income {
    public int id;
    private String source;
    private double amount;

    public Income(String source, double amount) {
        this.source = source;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public double getAmount() {
        return amount;
    }
}