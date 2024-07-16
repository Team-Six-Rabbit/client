package com.woowahanrabbits.battle_people.domain.battle.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleRegistDto;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

	private final BattleRepository battleRepository;
	private final VoteInfoRepository voteInfoRepository;
	private final UserRepository userRepository;

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

	@Override
	public List<?> getBattleList(String type, long userId) {
		List<BattleBoard> list = null;
		if(type.equals("get")) {
			list = battleRepository.findByRegistUserIdAndCurrentState(userId, 0);
		}
		if(type.equals("made")) {
			list = battleRepository.findByOppositeUserIdAndCurrentState(userId, 0);
		}

		return list.stream().map(battleBoard -> {
			Map<String, Object> map = new HashMap<>();
			map.put("id", battleBoard.getId());
			map.put("regist_user", userRepository.findById(battleBoard.getRegistUserId()));
			map.put("opposite_user", userRepository.findById(battleBoard.getOppositeUserId()));
			map.put("vote_info", voteInfoRepository.findById(battleBoard.getVoteInfoId()));
			map.put("min_people_count", battleBoard.getMinPeopleCount());
			map.put("max_people_count", battleBoard.getMaxPeopleCount());
			map.put("detail", battleBoard.getDetail());
			map.put("battle_rule", battleBoard.getBattleRule());
			map.put("regist_date", battleBoard.getRegistDate());
			map.put("current_state", battleBoard.getCurrentState());
			map.put("vote_opinion", )


			return map;
		}).collect(Collectors.toList());
	}

	private BattleBoard convertToDto(BattleBoard battleBoard) {
		BattleBoard dto = new BattleBoard();
		dto.setId(battleBoard.getId());
		dto.setRegistUserId(battleBoard.getRegistUserId());
		dto.setOppositeUserId(battleBoard.getOppositeUserId());
		dto.setVoteInfoId(battleBoard.getVoteInfoId());
		dto.setMinPeopleCount(battleBoard.getMinPeopleCount());
		dto.setMaxPeopleCount(battleBoard.getMaxPeopleCount());
		dto.setDetail(battleBoard.getDetail());
		dto.setBattleRule(battleBoard.getBattleRule());
		dto.setRegistDate(battleBoard.getRegistDate());
		dto.setCurrentState(battleBoard.getCurrentState());
		dto.setRejectionReason(battleBoard.getRejectionReason());
		dto.setImageUrl(battleBoard.getImageUrl());
		dto.setLiveUri(battleBoard.getLiveUri());
		dto.setDeleted(battleBoard.isDeleted());

		// 추가로 VoteOpinion, User 정보 등을 설정할 수 있습니다.

		return dto;
	}
}
