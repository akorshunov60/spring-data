package com.example.spring_data.service;

import com.example.spring_data.model.entity.User;
import com.example.spring_data.model.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Инициация LAZY-полей должна выполняться, пока транзакция, где была запрошена сущность, еще не закрыта.
     * В данном случае мы открывает транзакцию до входа в метод с помощью {@link Transactional} и после получения данных из репозитория можем
     * воспользоваться статическим методом Hibernate.initialize() для явной инициализации требуемых LAZY-полей
     */
//    @Override
//    @Transactional
//    public User findById(Long id) {
//        return userRepository.findById(id).stream().peek(it -> Hibernate.initialize(it.getProducts())).findFirst().orElse(null);
//    }

    /**
     * Данный запрос упадет с ошибкой, т.к. транзакция не была явно открыта и запросить LAZY-поля не выйдет
     */
    @Override
    public User findByIdWithoutTransaction(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Использование встроенного метода сохранения требует явного открытия транзакции, как и любая другая операция изменения в БД
     */
    @Override
    @Transactional
    public void create(User user) {
        userRepository.save(user);
    }

    /**
     * Данный запрос не сработает - сохранение без явного открытия транзакции невозможно
     */
    @Override
    public void createWithoutTransaction(User user) {
        userRepository.save(user);
    }
}
