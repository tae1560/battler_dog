package contest.lab.gala.callback;

import java.util.ArrayList;

import contest.lab.gala.data.RankingData;

public interface RankingCallback {
	public void didSuccessGetRanking(ArrayList<RankingData> array);
}
