package contest.lab.gala.callback;

import contest.lab.gala.data.User;


public interface OnGameEndedCallback {
	public void onGameEnded(boolean isWin, User user);
}
