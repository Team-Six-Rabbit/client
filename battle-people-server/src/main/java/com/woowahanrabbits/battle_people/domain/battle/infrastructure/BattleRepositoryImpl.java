// package com.woowahanrabbits.battle_people.domain.battle.infrastructure;
//
// import java.util.List;
//
// import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
//
// import jakarta.persistence.EntityManager;
// import jakarta.persistence.PersistenceContext;
// import jakarta.persistence.Query;
// import jakarta.persistence.TypedQuery;
// import jakarta.transaction.Transactional;
//
// public class BattleRepositoryImpl implements BattleRepositoryCustom {
//
// 	@PersistenceContext
// 	private EntityManager entityManager;
//
// 	@Override
// 	@Transactional
// 	public void updateBattleBoardStatus(Long battleId, int currentState, String rejectionReason) {
// 		String queryString = "UPDATE BattleBoard b SET b.currentState = :currentState";
//
// 		if (rejectionReason != null) {
// 			queryString += ", b.rejectionReason = :rejectionReason";
// 		}
//
// 		queryString += " WHERE b.id = :battleId";
//
// 		Query query = entityManager.createQuery(queryString);
// 		query.setParameter("currentState", currentState);
// 		query.setParameter("battleId", battleId);
//
// 		if (rejectionReason != null) {
// 			query.setParameter("rejectionReason", rejectionReason);
// 		}
//
// 		query.executeUpdate();
// 	}
//
// 	// @Override
// 	// public Page<BattleBoard> findByUserIdAndType(Long userId, String type, Pageable pageable) {
// 	// 	String queryString = "SELECT b FROM BattleBoard b ";
// 	//
// 	// 	if(type.equals("received")) {
// 	// 		queryString += "WHERE b.oppositeUserId = :userId";
// 	// 	} else if(type.equals("sent")) {
// 	// 		queryString += "WHERE b.registUserId = :userId";
// 	// 	}
// 	//
// 	// 	TypedQuery<BattleBoard> query = entityManager.createQuery(queryString, BattleBoard.class);
// 	// 	query.setParameter("userId", userId);
// 	//
// 	// 	// 페이징 처리를 위한 쿼리
// 	// 	int totalRows = query.getResultList().size();
// 	// 	query.setFirstResult((int) pageable.getOffset());
// 	// 	query.setMaxResults(pageable.getPageSize());
// 	//
// 	// 	return new PageImpl<>(query.getResultList(), pageable, totalRows);
//
// 	@Override
// 	public List<BattleBoard> findByCategoryAndCurrentState(Integer category, int currentState) {
// 		String queryString = "SELECT bb FROM BattleBoard bb join VoteInfo vi "
// 			+ "on bb.voteInfo.id = vi.id where bb.currentState = :currentState";
//
// 		if (category != null) {
// 			queryString += "and vi.category = :category";
// 		}
//
// 		TypedQuery<BattleBoard> query = entityManager.createQuery(queryString, BattleBoard.class);
// 		if (category != null) {
// 			query.setParameter("category", category);
// 		}
// 		query.setParameter("currentState", currentState);
//
// 		return query.getResultList();
// 	}
// }
