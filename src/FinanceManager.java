import java.sql.*;

public class FinanceManager {
    private Connection connection; // Pole przechowujące połączenie z bazą danych

    // Konstruktor klasy FinanceManager, inicjalizuje połączenie z bazą danych
    public FinanceManager(Connection connection) {
        this.connection = connection;
    }

    // Metoda dodająca wydatek do bazy danych
    public void addExpense(Expense expense) {
        try {
            String query = "INSERT INTO expenses (description, amount) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, expense.getDescription());
                statement.setDouble(2, expense.getAmount());

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Adding expense failed, no rows affected.");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        expense = new Expense(expense.getDescription(), expense.getAmount());
                        expense.id = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Adding expense failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metoda dodająca dochód do bazy danych
    public void addIncome(Income income) {
        try {
            String query = "INSERT INTO incomes (source, amount) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, income.getSource());
                statement.setDouble(2, income.getAmount());

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Adding income failed, no rows affected.");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        income = new Income(income.getSource(), income.getAmount());
                        income.id = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Adding income failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metoda generująca raport finansowy
    public void generateFinancialReport() {
        try {
            String query = "SELECT * FROM expenses";
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                System.out.println("Expense Report:");
                while (resultSet.next()) {
                    int id = resultSet.getInt("expense_id");
                    String description = resultSet.getString("description");
                    double amount = resultSet.getDouble("amount");
                    System.out.println("ID: " + id + ", Opis: " + description + ", Kwota: " + amount);
                }
            }

            query = "SELECT * FROM incomes";
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                System.out.println("Income Report:");
                while (resultSet.next()) {
                    int id = resultSet.getInt("income_id");
                    String source = resultSet.getString("source");
                    double amount = resultSet.getDouble("amount");
                    System.out.println("ID: " + id + ", Źródło: " + source + ", Kwota: " + amount);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
