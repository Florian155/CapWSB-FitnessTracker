package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;

import java.util.List;
import java.util.Optional;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {
    /**
     * Tworzy nowego użytkownika.
     *
     * @param user Użytkownik do utworzenia
     * @return Utworzony użytkownik
     */
     User createUser(User user);
    /**
     * Pobiera użytkowników starszych niż określony wiek.
     *
     * @param age Próg wiekowy
     * @return Lista użytkowników starszych niż określony wiek
     */
     List<User> getUsersOlderThanAge(int age);
    /**
     * Aktualizuje istniejącego użytkownika.
     *
     * @param userId ID użytkownika do aktualizacji
     * @param user   Zaktualizowane dane użytkownika
     * @return Zaktualizowany użytkownik
     */
     User update(Long userId, User user);
    /**
     * Usuwa użytkownika na podstawie ID.
     *
     * @param userId ID użytkownika do usunięcia
     * @throws UserNotFoundException Jeśli użytkownik o podanym ID nie został znaleziony
     */
    void deleteUser(Long userId) throws UserNotFoundException;
    /**
     * Pobiera użytkownika na podstawie określonego parametru.
     *
     * @param param Parametr, na podstawie którego ma zostać pobrany użytkownik.
     *              Może to być np. imię, nazwisko, adres e-mail, data urodzenia, id itp.
     * @return Opcjonalny obiekt zawierający użytkownika spełniającego podane kryteria,
     *         lub pusty, jeśli użytkownik nie został znaleziony.
     */
    Optional<User> getUserByParam(String param);


}
