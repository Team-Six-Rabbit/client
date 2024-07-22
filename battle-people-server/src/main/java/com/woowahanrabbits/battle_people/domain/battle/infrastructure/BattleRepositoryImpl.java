package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.user.domain.User;

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

	// @Override
	// public Page<BattleBoard> findByUserIdAndType(Long userId, String type, Pageable pageable) {
	// 	String queryString = "SELECT b FROM BattleBoard b ";
	//
	// 	if(type.equals("received")) {
	// 		queryString += "WHERE b.oppositeUserId = :userId";
	// 	} else if(type.equals("sent")) {
	// 		queryString += "WHERE b.registUserId = :userId";
	// 	}
	//
	// 	TypedQuery<BattleBoard> query = entityManager.createQuery(queryString, BattleBoard.class);
	// 	query.setParameter("userId", userId);
	//
	// 	// 페이징 처리를 위한 쿼리
	// 	int totalRows = query.getResultList().size();
	// 	query.setFirstResult((int) pageable.getOffset());
	// 	query.setMaxResults(pageable.getPageSize());
	//
	// 	return new PageImpl<>(query.getResultList(), pageable, totalRows);

	@Override
	public Page<BattleBoard> findByUserIdAndType(Long userId, String type, Pageable pageable) {
		String queryString = "SELECT b FROM BattleBoard b ";

		if (type.equals("received")) {
			queryString += "WHERE b.oppositeUser.id = :userId";
		} else if (type.equals("sent")) {
			queryString += "WHERE b.registUser.id = :userId";
		}

		TypedQuery<BattleBoard> query = entityManager.createQuery(queryString, BattleBoard.class);
		query.setParameter("userId", userId);

		// 페이징 처리를 위한 쿼리
		int totalRows = query.getResultList().size();
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		return new PageImpl<>(query.getResultList(), pageable, totalRows);
	}

	@Override
	public List<Map<String, Object>> findAllByCategoryAndStatusAndConditions(int category, int status,
		Pageable pageable, User user) {

		String queryString = "SELECT b FROM BattleBoard b JOIN b.voteInfo v WHERE b.currentState = :status";

		if(category!=7) {
			queryString += "AND v.category = :category";
		}

		TypedQuery<BattleBoard> query = entityManager.createQuery(queryString, BattleBoard.class);

		// 페이징 처리를 위한 쿼리
		int totalRows = query.getResultList().size();
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		// return new PageImpl<>(query.getResultList(), pageable, totalRows);
		return List.of();
	}
}