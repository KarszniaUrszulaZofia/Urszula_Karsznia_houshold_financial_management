import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/Financial"; // Adres bazy danych PostgreSQL
        String user = "postgres"; // Nazwa użytkownika
        String password = "Violetta1989!"; // Hasło

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            FinanceManager financeManager = new FinanceManager(connection);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("1. Wpisz wydatek ");
                System.out.println("2. Dodaj dochód");
                System.out.println("3. Wygeneruj raport finansowy");
                System.out.println("4. Wyjście");
                System.out.print("Wybierz opcję: ");

                int option = scanner.nextInt();
                scanner.nextLine(); // Consuming newline

                switch (option) {
                    case 1:
                        System.out.print("Wpisz dane wydatku: ");
                        String expenseDescription = scanner.nextLine();
                        System.out.print("Podaj kwotę: ");
                        double expenseAmount = scanner.nextDouble();
                        financeManager.addExpense(new Expense(expenseDescription, expenseAmount));
                        break;
                    case 2:
                        System.out.print("Podaj dane przychodu: ");
                        String incomeSource = scanner.nextLine();
                        System.out.print("Wpisz kwotę: ");
                        double incomeAmount = scanner.nextDouble();
                        financeManager.addIncome(new Income(incomeSource, incomeAmount));
                        break;
                    case 3:
                        financeManager.generateFinancialReport();
                        break;
                    case 4:
                        System.exit(0);
                    default:
                        System.out.println("Błąd. Ponów swe dzieło.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}