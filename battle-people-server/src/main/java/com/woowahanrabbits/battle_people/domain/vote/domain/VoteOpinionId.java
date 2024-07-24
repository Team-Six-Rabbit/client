package com.woowahanrabbits.battle_people.domain.vote.domain;

import lombok.Data;

@Data
public class VoteOpinionId {
	private int voteOpinionIndex;
	private Long voteInfoId;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		VoteOpinionId that = (VoteOpinionId)obj;

		if (voteOpinionIndex != that.voteOpinionIndex) {
			return false;
		}
		return voteInfoId.equals(that.voteInfoId);
	}

	@Override
	public int hashCode() {
		int result = voteOpinionIndex;
		result = 31 * result + voteInfoId.hashCode();
		return result;
	}
}
