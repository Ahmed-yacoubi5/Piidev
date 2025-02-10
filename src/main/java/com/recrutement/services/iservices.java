package com.recrutement.services;

import java.util.List;

public interface iservices<T> {

    void ajouter(T t);
    void modifier(T t);
    void supprimer(T t);
    List<T> rechercher(); }