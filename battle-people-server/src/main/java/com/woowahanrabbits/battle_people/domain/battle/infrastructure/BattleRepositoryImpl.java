package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import java.util.List;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
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

	@Override
	public List<BattleBoard> findByUserIdAndType(Long userId, String type) {
		String queryString = "SELECT b FROM BattleBoard b ";

		if (type.equals("received")) {
			queryString += "WHERE b.oppositeUser.id = :userId and b.currentState = 1 ";
		} else if (type.equals("sent")) {
			queryString += "WHERE b.registUser.id = :userId ";
		}

		TypedQuery<BattleBoard> query = entityManager.createQuery(queryString, BattleBoard.class);
		query.setParameter("userId", userId);

		return query.getResultList();
	}
}
