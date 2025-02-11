package com.esprit.services;

import java.util.List;

public interface Iservice <T>{
    void ajouter (T t);
    void modifier (T t);
    void supprimer(T t);
    List<T> rechercher();

}
