package com.codestates.pre027.PreProjectStackOverFlow.favorite.repository;

import com.codestates.pre027.PreProjectStackOverFlow.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {

}
