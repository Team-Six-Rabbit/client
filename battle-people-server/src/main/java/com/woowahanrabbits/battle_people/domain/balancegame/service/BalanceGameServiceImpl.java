package com.woowahanrabbits.battle_people.domain.balancegame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.balancegame.dto.BalanceGameResponse;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.CreateBalanceGameRequest;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.vote.domain.UserVoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.dto.VoteOpinionDtoWithVoteCount;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalanceGameServiceImpl implements BalanceGameService {

	private final VoteInfoRepository voteInfoRepository;
	private final VoteOpinionRepository voteOpinionRepository;
	private final UserVoteOpinionRepository userVoteOpinionRepository;

	@Override
	public void addBalanceGame(CreateBalanceGameRequest createBalanceGameRequest, User user) {

		VoteInfo voteInfo = VoteInfo.builder()
			.title(createBalanceGameRequest.getTitle())
			.startDate(createBalanceGameRequest.getStartDate())
			.endDate(createBalanceGameRequest.getEndDate())
			.category(createBalanceGameRequest.getCategory())
			.detail(createBalanceGameRequest.getDetail())
			.currentState(5)
			.build();
		voteInfoRepository.save(voteInfo);

		for (int i = 0; i < 2; i++) {
			VoteOpinion voteOpinion = VoteOpinion.builder()
				.voteOpinionIndex(i)
				.voteInfoId(voteInfo.getId())
				.user(user)
				.opinion(createBalanceGameRequest.getOpinions().get(i))
				.build();

			voteOpinionRepository.save(voteOpinion);
		}
	}

	@Override
	public List<BalanceGameResponse> getBalanceGameByConditions(Integer category, int status, int page,
		User user, int size) {
		Pageable pageable = PageRequest.of(page, size);
		System.out.println(size);
		List<VoteInfo> list = (category == null)
			? voteInfoRepository.findAllByCurrentState(status, pageable).getContent()
			: voteInfoRepository.findAllByCategoryAndCurrentState(category, status, pageable).getContent();

		List<BalanceGameResponse> returnList = new ArrayList<>();

		for (VoteInfo voteInfo : list) {
			returnList.add(convertToBalanceGameResponse(voteInfo, user));
		}

		return returnList;
	}

	@Override
	public BalanceGameResponse getBalanceGameById(Long id, User user) {
		VoteInfo voteInfo = voteInfoRepository.findById(id).orElseThrow(NoSuchElementException::new);

		return convertToBalanceGameResponse(voteInfo, user);
	}

	public BalanceGameResponse convertToBalanceGameResponse(VoteInfo voteInfo, User user) {
		List<VoteOpinion> voteOpinions = voteOpinionRepository.findByVoteInfoId(voteInfo.getId());
		List<VoteOpinionDtoWithVoteCount> voteOpinionDtoWithVoteCounts = convertToVoteOpinionDtos(voteInfo.getId(),
			voteOpinions);
		BalanceGameResponse bgr = new BalanceGameResponse(voteInfo, voteOpinionDtoWithVoteCounts);
		UserVoteOpinion uvo = userVoteOpinionRepository.findByUserIdAndVoteInfoId(user.getId(), voteInfo.getId());
		bgr.setUserVote(uvo == null ? null : uvo.getVoteInfoIndex());
		return bgr;
	}

	private List<VoteOpinionDtoWithVoteCount> convertToVoteOpinionDtos(Long voteInfoId,
		List<VoteOpinion> voteOpinions) {
		List<VoteOpinionDtoWithVoteCount> voteOpinionDtoWithVoteCounts = new ArrayList<>();
		int totalVotes = 0;
		int[] cnt = new int[voteOpinions.size()];

		for (int i = 0; i < voteOpinions.size(); i++) {
			VoteOpinion vote = voteOpinions.get(i);
			VoteOpinionDtoWithVoteCount voteOpinionDtoWithVoteCount = new VoteOpinionDtoWithVoteCount(vote);
			cnt[i] = userVoteOpinionRepository.findByVoteInfoIdAndVoteInfoIndex(voteInfoId, vote.getVoteOpinionIndex())
				.size();
			voteOpinionDtoWithVoteCount.setCount(cnt[i]);
			voteOpinionDtoWithVoteCounts.add(voteOpinionDtoWithVoteCount);
			totalVotes += cnt[i];
		}

		if (totalVotes > 0) {
			for (int i = 0; i < voteOpinions.size(); i++) {
				int percentage = (cnt[i] * 100) / totalVotes;
				voteOpinionDtoWithVoteCounts.get(i).setPercentage(percentage);
			}
		} else {
			voteOpinionDtoWithVoteCounts.forEach(dto -> dto.setPercentage(0));
		}

		return voteOpinionDtoWithVoteCounts;
	}

}
