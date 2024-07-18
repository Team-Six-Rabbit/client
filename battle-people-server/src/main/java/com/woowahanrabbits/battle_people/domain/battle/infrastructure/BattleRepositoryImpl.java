package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

public class BattleRepositoryImpl implements BattleRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void updateBattleBoardStatus(Long battleId, String rejectionReason) {
		String queryString = "UPDATE BattleBoard b SET b.currentState = :currentState";

		if (rejectionReason != null) {
			queryString += ", b.rejectionReason = :rejectionReason";
		}

		queryString += " WHERE b.id = :battleId";

		Query query = entityManager.createQuery(queryString);
		query.setParameter("currentState", rejectionReason == null ? 2 : 1);
		query.setParameter("battleId", battleId);

		if (rejectionReason != null) {
			query.setParameter("rejectionReason", rejectionReason);
		}

		query.executeUpdate();
	}
}