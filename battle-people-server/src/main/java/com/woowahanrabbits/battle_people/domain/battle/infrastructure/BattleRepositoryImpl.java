package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;

public class BattleRepositoryImpl implements BattleRepository{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(BattleBoard battleBoard) {

	}
}
