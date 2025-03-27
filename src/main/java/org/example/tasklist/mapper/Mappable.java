package org.example.tasklist.mapper;

import java.util.List;

public interface Mappable<E, D> {
    D toDto(E entity);

    List<D> toDto(List<E> entities);

    E toModel(D dto);

    List<E> toModel(List<D> dtos);
}
