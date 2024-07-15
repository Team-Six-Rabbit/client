package com.woowahanrabbits.battle_people.domain.battle.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleRegistDto;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;

public class BattleServiceImpl implements BattleService{

	private final BattleRepository battleRepository;

	public BattleServiceImpl(BattleRepository battleRepository) {
		this.battleRepository = battleRepository;
	}

	@Override
	public void registBattle(BattleRegistDto battleRegistDto) {
		// BattleRegistDto를 BattleBoard 엔티티로 변환
		BattleBoard battleBoard = new BattleBoard();
		battleBoard.setRegistUserId(battleRegistDto.getRegistUserId());
		battleBoard.setOppositeUserId(battleRegistDto.getOppositeUserId());
		battleBoard.setMinPeopleCount(battleRegistDto.getMinPeopleCount());
		battleBoard.setMaxPeopleCount(battleRegistDto.getMaxPeopleCount());
		battleBoard.setDetail(battleRegistDto.getDetail());
		battleBoard.setBattleRule(battleRegistDto.getBattleRule());

		// 리포지토리를 통해 데이터베이스에 저장
		battleRepository.save(battleBoard);
	}
}
