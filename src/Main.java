import java.io.FileReader;
import java.util.Scanner;

import java.io.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestWordMax;


class ConsoleMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static JsonArray data;

    public static void main(String[] args) {
        data = readJsonData(); // Считываем данные из JSON-файла

        while (true) {
            System.out.println("Выберите действие: ");
            System.out.println("1. Войти");
            System.out.println("0. Выйти");
            int choice = getChoice(2);

            switch (choice) {
                case 1:
                    authenticate();
                    break;
                case 0:
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private static int getChoice(int maxChoice) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 0 && choice <= maxChoice) {
                    return choice;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println("Некорректный выбор. Попробуйте снова.");
        }
    }

    // Метод для считывания данных в JSON-файл
    private static JsonArray readJsonData() {
        try (Reader reader = new FileReader("users.json")) {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(reader);
            if (jsonElement.isJsonArray()) {
                return jsonElement.getAsJsonArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка при считывания данных.");
        }
        return new JsonArray();
    }

    // Метод для сохранения данных в JSON-файл
    private static void saveJsonData(JsonArray data) {
        try (Writer writer = new FileWriter("users.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка при сохранении данных.");
        }
    }

    private static int authenticateUser(String login, String password) {
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                JsonObject user = data.get(i).getAsJsonObject();
                String userLogin = user.get("login").getAsString();
                String userPassword = user.get("password").getAsString();

                if (userLogin.equals(login) && userPassword.equals(password)) {
                    return user.get("role").getAsInt();
                }
            }
        }
        return -1; // -1 означает, что пользователь с заданными учетными данными не найден.
    }

    private static void authenticate() {
        while (true) {
            System.out.print("Введите логин: ");
            String login = scanner.nextLine().replaceAll("\\s", ""); // Удаляем все пробелы
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine().replaceAll("\\s", ""); // Удаляем все пробелы

            int role = authenticateUser(login, password);

            if (role != -1) {
                System.out.println("Вход выполнен успешно.");

                if (role == 1) {
                    // Администратор
                    adminMenu();
                } else if (role == 0) {
                    // Пользователь
                    userMenu();
                }
            } else {
                System.out.println("Неверный логин или пароль. Попробуйте снова.");
            }
        }
    }

    public static void userMenu() {
        while (true) {
            System.out.println("Меню пользователя:");
            System.out.println("1. Показать доступные документы");
            System.out.println("2. Работа с данными");
            System.out.println("0. Выйти из аккаунта");

            int choice = getChoice(2); // Получить выбор пользователя (0, 1 или 2)

            switch (choice) {
                case 1:
                    showAvailableDocuments();
                    break;
                case 2:
                    dataManagement();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    public static void showAvailableDocuments() {
        while (true) {
            System.out.println("1. Пупупу");
            System.out.println("0. Выйти в предыдущее меню");

            int choice = getChoice(1); // Получить выбор пользователя (0 или 1)

            switch (choice) {
                case 1:
                    System.out.println("Хотите сформировать документ?");
                    System.out.println("1. Да");
                    System.out.println("2. Нет");
                    int confirm = getChoice(2); // Получить подтверждение (1 или 2)

                    switch (confirm) {
                        case 1:
                            createDocument();
                            break;
                        case 2:
                            return;
                        default:
                            System.out.println("Некорректный выбор. Попробуйте снова.");
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    public static void createDocument() {
        System.out.println("Сформировать документ...");
        // Реализация создания документа
    }

    public static void dataManagement() {
        while (true) {
            System.out.println("Работа с данными");
            System.out.println("1. Поиск данных для формирования документа");
            System.out.println("2. Фильтрация данных для формирования документа");
            System.out.println("0. Выйти в предыдущее меню");

            int choice = getChoice(2); // Получить выбор пользователя (0, 1 или 2)

            switch (choice) {
                case 1:
                    searchDataForDocument();
                    break;
                case 2:
                    filterDataForDocument();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    public static void searchDataForDocument() {
        System.out.println("Поиск данных для формирования документа...");
        // Реализация поиска данных для формирования документа
    }

    public static void filterDataForDocument() {
        System.out.println("Фильтрация данных для формирования документа...");
        // Реализация фильтрации данных для формирования документа
    }

    public static void adminMenu() {
        while (true) {
            System.out.println("Меню администратора:");
            System.out.println("1. Управление пользователями");
            System.out.println("2. Просмотр статистики");
            System.out.println("3. Графики работы");
            System.out.println("4. Экспорт данных");
            System.out.println("0. Выйти из аккаунта");

            int choice = getChoice(4); // Получить выбор пользователя (0, 1, 2, 3 или 4)

            switch (choice) {
                case 1:
                    userManagement();
                    break;
                case 2:
                    viewStatistics();
                    break;
                case 3:
                    generateCharts();
                    break;
                case 4:
                    exportData();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    public static void userManagement() {
        while (true) {
            System.out.println("Управление пользователями");
            System.out.println("1. Показать всех пользователей");
            System.out.println("2. Создать нового пользователя");
            System.out.println("3. Изменить данные пользователя");
            System.out.println("4. Изменить статус пользователя");
            System.out.println("5. Удалить пользователя");
            System.out.println("0. Выйти в предыдущее меню");

            int choice = getChoice(5); // Получить выбор пользователя (0, 1, 2, 3, 4 или 5)

            switch (choice) {
                case 1:
                    showAllUsers();
                    sortFilterOrFind();
                    break;
                case 2:
                    createUser();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    changeUserStatus();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    public static void showAllUsers() {
        System.out.println("Показать всех пользователей");
        JsonArray userData = readJsonData();

        if (userData.size() == 0) {
            System.out.println("Нет доступных пользователей.");
        } else {
            AsciiTable table = new AsciiTable();
            table.addRule();
            table.addRow("ID", "Фамилия", "Имя", "Логин", "Роль", "Статус", "Последний вход", "Последний выход", "Кол-во входов", "Кол-во выходов", "Время работы");
            table.addRule();

            for (int i = 0; i < userData.size(); i++) {
                JsonObject user = userData.get(i).getAsJsonObject();
                String id = user.get("id").getAsString();
                String lastName = user.get("last_name").getAsString();
                String firstName = user.get("first_name").getAsString();
                String login = user.get("login").getAsString();
                String role = user.get("role").getAsString();
                String status = user.get("status").getAsString();
                String lastLogin = user.has("last_login") ? user.get("last_login").getAsString() : "";
                String lastExit = user.has("last_exit") ? user.get("last_exit").getAsString() : "";
                String loginCount = user.has("login_count") ? user.get("login_count").getAsString() : "";
                String logoutCount = user.has("logout_count") ? user.get("logout_count").getAsString() : "";
                String workTime = user.has("work_time") ? user.get("work_time").getAsString() : "";

                table.addRow(id, lastName, firstName, login, role, status, lastLogin, lastExit, loginCount, logoutCount, workTime);
                table.addRule();
            }

            // Настройка ширины колонок
            table.getRenderer().setCWC(new CWC_LongestWordMax(50)); // 50 - максимальная ширина столбца

            // Вывод таблицы
            System.out.println(table.render());
        }
    }

    public static void sortFilterOrFind() {
        while (true) {
            System.out.println("Выберите действие над данными");
            System.out.println("1. Отсортировать");
            System.out.println("2. Отфильтровать");
            System.out.println("3. Искать по атрибуту");
            System.out.println("0. Выйти в предыдущее меню");

            int choice = getChoice(3); // Получить выбор пользователя (0, 1, 2, 3)

            switch (choice) {
                case 1:
                    sortChooseAttribute();
                    break;
                case 2:
                    filterChooseAttribute();
                    break;
                case 3:
                    findData();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    public static void sortChooseAttribute() {
        while (true) {
            System.out.println("Выберите поле для сортировки");
            System.out.println("1. По фамилии");
            System.out.println("2. По имени");
            System.out.println("3. По логину");
            System.out.println("4. По паролю");
            System.out.println("0. Выйти в предыдущее меню");

            int fieldChoice = getChoice(4); // Получить выбор поля для сортировки (0, 1, 2, 3, 4)

            if (fieldChoice == 0) {
                return;
            }

            int directionChoice = getSortDirection(); // Получить выбор направления сортировки

            sortData(fieldChoice, directionChoice);
        }
    }

    public static int getSortDirection() {
        System.out.println("Выберите направление сортировки");
        System.out.println("1. По возрастанию");
        System.out.println("2. По убыванию");
        return getChoice(2); // Получить выбор направления сортировки (1, 2)
    }


    public static void quickSort(JsonArray data, String attribute, boolean ascending) {
        if (data == null || data.size() <= 1) {
            return;
        }
        quickSortRecursive(data, attribute, ascending, 0, data.size() - 1);
    }

    private static void quickSortRecursive(JsonArray data, String attribute, boolean ascending, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(data, attribute, ascending, left, right);
            quickSortRecursive(data, attribute, ascending, left, partitionIndex - 1);
            quickSortRecursive(data, attribute, ascending, partitionIndex + 1, right);
        }
    }

    private static int partition(JsonArray data, String attribute, boolean ascending, int left, int right) {
        JsonObject pivot = data.get(right).getAsJsonObject();
        String pivotValue = pivot.get(attribute).getAsString();
        int i = (left - 1);

        for (int j = left; j < right; j++) {
            JsonObject current = data.get(j).getAsJsonObject();
            String currentValue = current.get(attribute).getAsString();
            int comparisonResult = currentValue.compareTo(pivotValue);

            if ((ascending && comparisonResult < 0) || (!ascending && comparisonResult > 0)) {
                i++;
                // Swap data[i] and data[j]
                JsonElement temp = data.get(i);
                data.set(i, data.get(j));
                data.set(j, temp);
            }
        }

        // Swap data[i+1] and data[right] (pivot)
        JsonElement temp = data.get(i + 1);
        data.set(i + 1, data.get(right));
        data.set(right, temp);

        return i + 1;
    }



    public static void sortData(int fieldChoice, int directionChoice) {
        String attribute;
        boolean ascending = true;

        switch (fieldChoice) {
            case 1:
                attribute = "last_name";
                break;
            case 2:
                attribute = "first_name";
                break;
            case 3:
                attribute = "login";
                break;
            case 4:
                attribute = "password";
                break;
            default:
                return;
        }

        if (directionChoice == 2) {
            ascending = false;
        }

        JsonArray data = readJsonData();
        displaySortedData(data, attribute, ascending);
    }


    public static void displaySortedData(JsonArray data, String attribute, boolean ascending) {
        quickSort(data, attribute, ascending);

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("last_name", "first_name", "login", "password"); // Заголовки столбцов

        for (JsonElement element : data) {
            JsonObject jsonObject = element.getAsJsonObject();
            table.addRule();
            table.addRow(jsonObject.get("last_name").getAsString(),
                    jsonObject.get("first_name").getAsString(),
                    jsonObject.get("login").getAsString(),
                    jsonObject.get("password").getAsString());
        }
        table.addRule();

        System.out.println(table.render());
    }

    public static void filterChooseAttribute() {
        while (true) {
            System.out.println("Выберите атрибут для фильтрации");
            System.out.println("1. Администраторы");
            System.out.println("2. Пользователи");
            System.out.println("3. Отключенные");
            System.out.println("4. Включенные");
            System.out.println("0. Выйти в предыдущее меню");

            int attributeChoice = getChoice(4); // Получить выбор атрибута для фильтрации (0, 1, 2, 3, 4)

            if (attributeChoice == 0) {
                return;
            }
        }
    }

    public static void findData() {
        System.out.println("Искать по атрибуту...");
        // Реализация поиска данных по атрибуту
    }

    private static boolean checkUserExistence(String login, JsonArray users) {
        for (JsonElement user : users) {
            JsonObject userObject = user.getAsJsonObject();
            if (userObject.has("login") && userObject.get("login").getAsString().equals(login)) {
                return true;
            }
        }
        return false;
    }


    public static void createUser() {
        System.out.println("Создание нового пользователя");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите фамилию: ");
        String lastName = scanner.next();

        System.out.print("Введите имя: ");
        String firstName = scanner.next();

        String login;
        while (true) {
            try {
                System.out.print("Введите логин: ");
                login = scanner.next();
                if (login.isEmpty()) {
                    throw new IllegalArgumentException("Логин не может быть пустым. Пожалуйста, введите логин.");
                } else if (checkUserExistence(login, readJsonData())) {
                    throw new IllegalArgumentException("Пользователь с таким логином уже существует. Пожалуйста, выберите другой логин.");
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        String password;
        while (true) {
            try {
                System.out.print("Введите пароль: ");
                password = scanner.next();
                if (password.length() < 6) {
                    throw new IllegalArgumentException("Пароль должен содержать как минимум 6 символов. Пожалуйста, введите пароль заново.");
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        int role;
        while (true) {
            try {
                System.out.print("Введите роль (0 для пользователя, 1 для администратора): ");
                String roleStr = scanner.next();
                if (roleStr.isEmpty()) {
                    throw new IllegalArgumentException("Роль не может быть пустой. Пожалуйста, введите роль.");
                } else if (roleStr.equals("0") || roleStr.equals("1")) {
                    role = Integer.parseInt(roleStr);
                    break;
                } else {
                    throw new IllegalArgumentException("Роль должна быть 0 или 1.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            JsonArray users = readJsonData();
            JsonObject newUser = new JsonObject();

            int userId = users.size() + 1;
            newUser.addProperty("id", userId);
            newUser.addProperty("last_name", lastName);
            newUser.addProperty("first_name", firstName);
            newUser.addProperty("login", login);
            newUser.addProperty("password", password);
            newUser.addProperty("role", role);
            newUser.addProperty("status", "active");

            users.add(newUser);

            saveJsonData(users);

            System.out.println("Пользователь успешно создан и добавлен в файл.");
        } catch (Exception e) {
            System.out.println("Произошла ошибка при сохранении пользователя: " + e.getMessage());
        }
    }

    public static void updateUser() {
        System.out.println("Изменить данные пользователя...");
        // Реализация изменения данных пользователя
    }

    public static void changeUserStatus() {
        System.out.println("Изменить статус пользователя...");
        // Реализация изменения статуса пользователя
    }

    public static void deleteUser() {
        System.out.println("Удалить пользователя...");
        // Реализация удаления пользователя
    }

    public static void viewStatistics() {
        while (true) {
            System.out.println("Просмотр статистики");
            System.out.println("1. Показать статистику о количестве входов пользователей");
            System.out.println("2. Показать статистику по продолжительности работы пользователей");
            System.out.println("0. Выйти в предыдущее меню");

            int choice = getChoice(2); // Получить выбор пользователя (0, 1 или 2)

            switch (choice) {
                case 1:
                    showUserLoginStatistics();
                    break;
                case 2:
                    showUserWorkDurationStatistics();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    public static void showUserLoginStatistics() {
        System.out.println("Показать статистику о количестве входов пользователей...");
        // Реализация показа статистики о количестве входов пользователей
    }

    public static void showUserWorkDurationStatistics() {
        System.out.println("Показать статистику по продолжительности работы пользователей...");
        // Реализация показа статистики по продолжительности работы пользователей
    }

    public static void generateCharts() {
        while (true) {
            System.out.println("Графики работы");
            System.out.println("1. Для всех пользователей");
            System.out.println("2. Для конкретного пользователя");
            System.out.println("0. Выйти в предыдущее меню");

            int choice = getChoice(2); // Получить выбор пользователя (0, 1 или 2)

            switch (choice) {
                case 1:
                    generateChartForAllUsers();
                    break;
                case 2:
                    generateChartForUser();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    public static void generateChartForAllUsers() {
        while (true) {
            System.out.println("Для всех пользователей");
            System.out.println("1. Количество входов пользователей на текущей неделе");
            System.out.println("2. Количество входов пользователей в разрезе месяца");
            System.out.println("0. Выйти в предыдущее меню");

            int choice = getChoice(2); // Получить выбор пользователя (0, 1 или 2)

            switch (choice) {
                case 1:
                    generateEntryCountThisWeek();
                    break;
                case 2:
                    generateEntryCountThisMonth();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
                    break;
            }
        }
    }

    public static void generateEntryCountThisWeek() {
        System.out.println("Построение графика количества входов на текущей неделе...");
        // Реализация построения графика количества входов на текущей неделе
    }

    public static void generateEntryCountThisMonth() {
        System.out.println("Количество входов пользователей в разрезе месяца");
        System.out.println("Выберите месяц (от 1 до 12):");

        int month = getChoice(12); // Получить выбор месяца (от 1 до 12)

        if (month >= 1 && month <= 12) {
            System.out.println("Построение графика количества входов в разрезе выбранного месяца");
            // Реализация построения графика количества входов в разрезе выбранного месяца
        } else {
            System.out.println("Некорректный выбор месяца.");
        }
    }

    public static void generateChartForUser() {
        while (true) {
            System.out.println("Для конкретного пользователя");
            System.out.println("1. Количество входов пользователя на текущей неделе");
            System.out.println("2. Количество входов пользователя в разрезе месяца");
            System.out.println("0. Выйти в предыдущее меню");

            int choice = getChoice(2); // Получить выбор пользователя (0, 1 или 2)

            switch (choice) {
                case 1:
                    generateUserEntryCountThisWeek();
                    break;
                case 2:
                    generateUserEntryCountThisMonth();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
                    break;
            }
        }
    }

    public static void generateUserEntryCountThisWeek() {
        System.out.println("Построение графика количества входов пользователя на текущей неделе...");
        // Реализация построения графика количества входов пользователя на текущей неделе
    }

    public static void generateUserEntryCountThisMonth() {
        System.out.println("Выберите месяц (от 1 до 12):");

        int month = getChoice(12); // Получить выбор месяца (от 1 до 12)

        if (month >= 1 && month <= 12) {
            System.out.println("Построение графика количества входов пользователя в разрезе выбранного месяца...");
            // Реализация построения графика количества входов пользователя в разрезе выбранного месяца
        } else {
            System.out.println("Некорректный выбор месяца.");
        }
    }

    public static void exportData() {
        while (true) {
            System.out.println("Экспорт данных");
            System.out.println("1. Выгрузка в файл логотипа компании");
            System.out.println("2. Выгрузка в файл таблицы с данными");
            System.out.println("0. Выйти в предыдущее меню");

            int choice = getChoice(2); // Получить выбор пользователя (0, 1 или 2)

            switch (choice) {
                case 1:
                    exportCompanyLogoFile();
                    break;
                case 2:
                    exportDataToFile();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    public static void exportCompanyLogoFile() {
        System.out.println("Выгрузка в файл логотипа компании...");
        // Реализация выгрузки в файл логотипа компании
    }

    public static void exportDataToFile() {
        System.out.println("Выгрузка в файл таблицы с данными...");
        // Реализация выгрузки в файл таблицы с данными
    }
}
