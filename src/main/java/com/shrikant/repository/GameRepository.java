package com.shrikant.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shrikant.model.Game;


@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {

}
