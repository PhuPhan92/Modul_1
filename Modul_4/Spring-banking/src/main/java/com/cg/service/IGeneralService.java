package com.cg.service;

import java.util.Optional;
import java.util.List;

public interface IGeneralService <T>{
List <T> findAll();
Optional <T> findById (Long id);
Boolean existById (Long id);
T save(T t);
void delete (T t);
void deleteById (Long id);
}
