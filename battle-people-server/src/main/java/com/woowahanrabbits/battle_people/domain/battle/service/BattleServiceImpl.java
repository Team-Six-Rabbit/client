package com.woowahanrabbits.battle_people.domain.battle.service;

import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleRegistDto;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.VoteInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

	private final BattleRepository battleRepository;
	private final VoteInfoRepository voteInfoRepository;

	@Override
	public void registBattle(BattleRegistDto battleRegistDto) {
		// VoteInfo 엔티티를 생성하고 저장
		VoteInfo voteInfo = battleRegistDto.getVoteInfo();
		voteInfo = voteInfoRepository.save(voteInfo);

		// 저장된 VoteInfo의 ID를 BattleBoard에 설정
		Long voteInfoId = voteInfo.getId();
		BattleBoard battleBoard = battleRegistDto.getBattleBoard();
		battleBoard.setVoteInfoId(voteInfoId);

		// BattleBoard 엔티티를 저장
		battleRepository.save(battleBoard);

		System.out.println(battleRegistDto.toString());
	}
}
