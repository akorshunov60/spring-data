package com.example.spring_data;

import com.example.spring_data.model.entity.CartEntry;
import com.example.spring_data.model.entity.Order;
import com.example.spring_data.model.entity.Product;
import com.example.spring_data.model.entity.User;
import com.example.spring_data.model.repository.ProductRepository;
import com.example.spring_data.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

    private final static Logger log = LogManager.getLogger(SpringDataApplication.class);

    public static void main(String[] args) { SpringApplication.run(SpringDataApplication.class, args); }

    private final ProductRepository pr;
    private final UserRepository ur;

    @Override
    public void run(String... args) {

        //получение товара по id
        System.out.println("\n----------------------------------");
        System.out.println("\nПродукт: " + pr.findById(5L).orElseThrow(() ->
                new NoResultException("Товар с указанным id не существует!")));

        //получение всех товаров
        System.out.println("\n----------------------------------");
        System.out.println("\nСписок всех продуктов: ");
        System.out.println("\n----------------------------------");
        pr.findAll().forEach(System.out::println);

        //получение всех товаров с разбивкой по 5 названий на странице
        System.out.println("\n----------------------------------");
        int prodPerPage = 5;
        PageRequest pageRequest = PageRequest.of(0, prodPerPage);
        Page<Product> products = pr.findAll(pageRequest);

        while (true) {
            System.out.println("\n----------------------------------");
            System.out.println(products);
            products.getContent().forEach(System.out::println);
            if (products.hasNext()) {
                products = pr.findAll(products.nextPageable());
            } else {
                break;
            }
        }

        System.out.println("\n----------------------------------");

        Product product = null;

        //обновление товара по id
        System.out.println("\n----------------------------------");
        System.out.println("Обновление товара по id... 2");
        System.out.println("\n----------------------------------");

        product = pr.findById(2L).orElseThrow(() ->
                new NoResultException("Товар с указанным id (2) не существует!"));
        product.setName("New product");
        product.setPrice(BigDecimal.valueOf(70.99));
        pr.save(product);
        System.out.println("\nПродукт: " + pr.findById(2L).orElseThrow(() ->
                new NoResultException("Товар с указанным id (2) не существует!")));
        System.out.println("\n----------------------------------");

        //удаление товара по id
        Long prodId = product.getId();
        System.out.println("\n----------------------------------");
        System.out.println("Удаление товара... id = " + prodId);
        System.out.println("\n----------------------------------");
        pr.deleteById(prodId);
        System.out.println("\nПродукт: " + pr.findById(prodId).orElseThrow());

        System.out.println("\n----------------------------------");

        // фильтрация по максимальной цене
        System.out.println("\n----------------------------------");
        System.out.println("\nПродукты с ценой до 50: ");
        System.out.println("\n----------------------------------");
        pr.findProductByPriceLessThan(BigDecimal.valueOf(50)).forEach(System.out::println);

        System.out.println("\n----------------------------------");

        // фильтрация по минимальной цене
        System.out.println("\n----------------------------------");
        System.out.println("\nПродукты с ценой более 150: ");
        System.out.println("\n----------------------------------");
        pr.findProductByPriceGreaterThan(BigDecimal.valueOf(150)).forEach(System.out::println);

        // фильтрация по цене в диапазоне min-max
        System.out.println("\n----------------------------------");
        System.out.println("\nПродукты в диапазоне цены 50-150: ");
        System.out.println("\n----------------------------------");
        pr.findProductByPriceBetween(BigDecimal.valueOf(50), BigDecimal.valueOf(150)).forEach(System.out::println);

        log.info("Начало тестового сценария");
        testOfHibernateLinks();
        log.info("\n\n------------------------------\n\n");
        log.info("Конец тестового сценария");
    }

    public void testOfHibernateLinks() {
        Optional<User> user;
        Optional<Product> product;
        int numSelected;
        String str = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n ТЕСТИРОВАНИЕ ЗАВИСИМОСТЕЙ (СВЯЗЕЙ) ");

        while(!str.equals("exit")) {
            System.out.println("\n----------------------------------------");
            System.out.println("1. Список товаров по ID покупателя");
            System.out.println("2. Список покупателей по ID товара");
            System.out.println("3. Завершить");
            System.out.print("Выберите действие: ");
            try {
                str = in.readLine();
                numSelected = Integer.parseInt(str);
                if (numSelected < 1 || numSelected > 3) throw new NumberFormatException();
            } catch (Exception e) {
                log.info("Неверное значение. Попробуйте снова...");
                continue;
            }

            System.out.println("\n----------------------------------------");

            switch (numSelected) {
                case 1:

                    System.out.print("Введите id покупателя: ");
                    try {
                        str = in.readLine();
                        Long userId = Long.parseLong(str);
                        user = Optional.ofNullable(ur.findById(userId).orElseThrow());
                    } catch (Exception e) {
                        log.info("ID покупателя указан неверно!");
                        continue;
                    }
                    detailsByUser(user);
                    break;

                case 2:

                    System.out.print("Введите id товара: ");
                    try {
                        str = in.readLine();
                        Long prodId = Long.parseLong(str);
                        product = Optional.ofNullable(pr.findById(prodId).orElseThrow());
                    } catch (Exception e) {
                        log.info("ID товара указан неверно!");
                        continue;
                    }

                    detailsByProduct(product);
                    break;

                case 3:

                    str = "exit";
                    break;
            }
        }
    }

    public void detailsByUser(Optional<User> user) {
        System.out.println("Пользователь: " + user);
        System.out.println("Количество заказов: " + user.orElseThrow().getOrders().size());
        System.out.println("Список заказанных товаров с детализацией на момент покупки:");
        for (Order order : user.get().getOrders()) {
        System.out.println("Заказ: " + order);
            for (CartEntry cartEntry : order.getCartEntries()) {
                System.out.println("Товар: id = " + cartEntry.getProduct().getId() +
                        ", name = " + cartEntry.getProduct().getName() +
                        ", количество: " + cartEntry.getQuantity() +
                        ", цена на момент покупки: " + cartEntry.getLastPrice());
            }
        }
    }

    public void detailsByProduct(Optional<Product> product) {
        System.out.println("Товар: " + product);
        System.out.println("Количество заказов: " + product.orElseThrow().getOrders().size());
        Set<User> userList = new HashSet<>();
        for (Order order : product.get().getOrders()) {
            userList.add(order.getUser());
        }
        System.out.println("Количество покупателей: " + userList.size());
        System.out.println("Список покупателей: \n" + userList);
    }
}
