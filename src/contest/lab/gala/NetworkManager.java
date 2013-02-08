package contest.lab.gala;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import contest.lab.gala.callback.JoinCallback;
import contest.lab.gala.callback.LoginCallback;
import contest.lab.gala.callback.RankingCallback;
import contest.lab.gala.data.RankingData;

public class NetworkManager {
	
	public static int requestLogin = 1;
	public static int requestJoin = 2;
	public static int requestRanking = 3;
	
	public static int messageMatching = 11;
	public static int messageAttack = 12;
	
	protected static String domainString = "http://soma2.vps.phps.kr:4000";
	protected static String loginPath = domainString + "/login";
	protected static String joinPath = domainString + "/join";
	protected static String getRankingPath = domainString + "/get_ranking";
	
	
	private static NetworkManager _instance = null;
	public static NetworkManager getInstance() {
		if (_instance == null) {
			_instance = new NetworkManager();
		}
		return _instance;
	}
	
	public String sendRequest(String message, int kindOfRequest, Object callback) {
		/*
		 * �ﰢ���� ������ �ʿ� (HTTP)
		 * �α��� ��û
		 * ȸ�� ����
		 * ����
		 * 
		 */
		
		if (kindOfRequest == NetworkManager.requestLogin) {
			// id & password parsing
			// message : "id password"
			String[] messages = message.split(" ");
			
			String id = null;
			String password = null;
			if (messages.length == 2) {
				id = messages[0];
				password = messages[1];
				
				String result = sendHttpRequest(loginPath + "?id=" + id + "&password=" + password);
				if (result.equalsIgnoreCase("success")) {
					// �α��� ������
					if (callback instanceof LoginCallback) {
						LoginCallback newCallback = (LoginCallback)callback;
						newCallback.didSuccessLogin();
					}
				}
			}
		} else if (kindOfRequest == NetworkManager.requestJoin) {
			String[] messages = message.split(" ");
			
			String id = null;
			String password = null;
			if (messages.length == 2) {
				id = messages[0];
				password = messages[1];
				
				String result = sendHttpRequest(joinPath + "?id=" + id + "&password=" + password);
				if (result.equalsIgnoreCase("success")) {
					// �α��� ������
					if (callback instanceof JoinCallback) {
						JoinCallback newCallback = (JoinCallback)callback;
						newCallback.didSuccessJoin();
					}
				}
			}
		} else if (kindOfRequest == NetworkManager.requestRanking) {
			// String message
			if (message != null) {
				String id = message;
				
				String result = sendHttpRequest(getRankingPath + "?id=" + id);
				if (result != null) {
					// TODO : parse result
					ArrayList<RankingData> array = null;
					
					if (callback instanceof RankingCallback) {						
						RankingCallback newCallback = (RankingCallback)callback;
						newCallback.didSuccessGetRanking(array);
					}
				}
			}
		}
		
//		ȸ������ / �α���(HTTP) => ������ : ���̵� �н�����
//		 * ��ŷ�ޱ� (HTTP) => �޴°� : ���̵�&��ŷ
		
		return null;
		// ������ �ʿ�(HTTP), ������ �ʿ� ���°�(����)
	}
	public void sendMessage(String message, int kindOfMessage) {
		
	}
	
	public void receiveMessage(String message, int kindOfMessage) {
		
	}
	
	
	/*
	 *
	 * ȸ������ / �α���(HTTP) => ������ : ���̵� �н�����
	 * ��ŷ�ޱ� (HTTP) => �޴°� : ���̵�&��ŷ
	 * 
	 * ����
	 *  => ������ ��� & ���� ��� 
	 * ��û / ����
	 * ���� (��ų ���� , ��ų ����, ���� WIN/LOSE)
	 * 
	 * 
	 */
	
	
	
	protected String sendHttpRequest(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			BufferedReader oBufReader = new BufferedReader(new InputStreamReader(url.openStream()));
			String strBuffer;
			String strRslt = ""; 
			while((strBuffer = oBufReader.readLine()) != null)
			{
				if(strBuffer.length() > 1)
				{
					strRslt += strBuffer;
				}
			}
			
			return strRslt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
