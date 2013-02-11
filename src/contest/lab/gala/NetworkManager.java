package contest.lab.gala;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import contest.lab.gala.callback.GetDamagedCallback;
import contest.lab.gala.callback.JoinCallback;
import contest.lab.gala.callback.LoginCallback;
import contest.lab.gala.callback.RankingCallback;
import contest.lab.gala.data.RankingData;
import contest.lab.gala.data.SkillType;
import contest.lab.gala.util.CommonUtils;

public class NetworkManager {
	
	public static int requestLogin = 1;
	public static int requestJoin = 2;
	public static int requestRanking = 3;
	
	public static int messageMatching = 11;
	public static int messageAttack = 12;
	
	public static NetworkManager getInstance() {
		if (_instance == null) {
			_instance = new NetworkManager();
		}
		return _instance;
	}
	
	public void setGetDamagedCallback(GetDamagedCallback callback) {
		this.getDamagedCallback = callback;
	}
	
	public void doLogin(final String id, final String password, final LoginCallback callback) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				String result = CommonUtils.requestWithGet(loginPath + "?id=" + id + "&password=" + password);
				debug("result : " + result);
				if (result != null) {
					try {
						JSONObject jsonResult = new JSONObject(result);
						if (jsonResult.getString("status").equalsIgnoreCase("success")) {
							callback.didSuccessLogin();
						} else {
							debug("login failed");
						}		
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}				
			}
		}).start();
	}
	
	public void doJoin(final String id, final String password, final int selected_character, final JoinCallback callback) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String result = CommonUtils.requestWithGet(joinPath + "?id=" + id + "&password=" + password + "&character=" + selected_character);
				debug("result : " + result);
				if (result != null) {
					try {
						JSONObject jsonResult = new JSONObject(result);
						if (jsonResult.getString("status").equalsIgnoreCase("success")) {
							callback.didSuccessJoin();
						} else {
							debug("join failed");
							debug(jsonResult.getString("message"));
						}		
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public String sendRequest(String message, int kindOfRequest, Object callback) {
		
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
					ArrayList<RankingData> array = null;
					
					if (callback instanceof RankingCallback) {						
						RankingCallback newCallback = (RankingCallback)callback;
						newCallback.didSuccessGetRanking(array);
					}
				}
			}
		}
		
		return null;
	}
	public void sendMessage(String message, int kindOfMessage) {
		
	}
	
	public void receiveMessage(String message, int kindOfMessage) {
		
	}
	
	public void startSocketWithUsername(String username) {
		debug("startSocketWithUsername");
		
		// start socket
		makeSocketConnection();
		
		// send username to server
		sendUsername(username);
	}
	
	public void sendUsername(String username) {
		// make JSON data
		Map<String, String> map = new HashMap<String, String>();
		map.put(KEY_TYPE, TYPE_SEND_USERNAME);
		map.put("username", username);
		sendJSONWithSocket(map);
	}
	
	public void sendMatchingRequest(String friend_name) {
		// make JSON data
		Map<String, String> map = new HashMap<String, String>();
		map.put(KEY_TYPE, TYPE_MATCHING_REQUEST);
		map.put("friend_name", friend_name);
		sendJSONWithSocket(map);
	}
	
	public void sendAttack(SkillType skillType) {
		debug("sendAttack started");
		
		// make JSON data
		Map<String, String> map = new HashMap<String, String>();
		map.put(KEY_TYPE, TYPE_SEND_ATTACK);
		map.put("skill_type", skillType.toString());
		sendJSONWithSocket(map);
		
		debug("sendAttack ended");
	}
	
	// private -----------------------------------------------------------------
	
	private static final String domainString = "http://soma2.vps.phps.kr:4000";
	private static final String loginPath = domainString + "/login";
	private static final String joinPath = domainString + "/join";
	private static final String getRankingPath = domainString + "/get_ranking";
	private static final String socketIpString = "27.102.204.239";
	private static final int socketPort = 1234;
	
	
	private static final String KEY_TYPE = "type";
	private static final String TYPE_SEND_USERNAME = "username";
	private static final String TYPE_MATCHING_REQUEST = "matching_request";
	
	// TODO : DEBUG - TEST
	// SEND
	private static final String TYPE_SEND_ATTACK = "test_attack_skill";
	
	// RECEIVE
	private static final String TYPE_SEND_DAMAGED = "test_damaged_skill";
	
	
	private GetDamagedCallback getDamagedCallback = null;
	
	private static NetworkManager _instance = null;
	private Socket socket = null;
	private BufferedWriter networkWriter = null;
	private BufferedReader networkReader = null;
	
	private void makeSocketConnection() {
		try {
			socket = new Socket(socketIpString, socketPort);
			
			networkWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			networkReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			debug("makeSocketConnection completed");
			
			startReadingThread();
		} catch (UnknownHostException e) {
			debug("UnknownHostException");
			debug(e.getStackTrace().toString());
			e.printStackTrace();
		} catch (IOException e) {
			debug("IOException");
			debug(e.getStackTrace().toString());
			e.printStackTrace();
		}
	}
	
	private void startReadingThread() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				debug("startReadingThread");
				
				String line;
//				String text = "";
                while (true) {
                	try {
						line = networkReader.readLine();
//						text += line;
						
						if (line != null) {
							try {
								JSONObject receivedData = new JSONObject(line);
								
								parseReceivedData(receivedData);
							} catch (JSONException e) {
								e.printStackTrace();
							} 
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
				
			}
		}).start();
	}
	
	private void parseReceivedData(JSONObject jsonObject) {
		try {
			String dataTypeString = jsonObject.getString("type");
			
			debug("dataTypeString : " + dataTypeString);
			
			if (dataTypeString != null) {
				if (dataTypeString.equalsIgnoreCase("attack_skill")) {
					String skillTypeString = jsonObject.getString("skill_type");
					int skillType = Integer.parseInt(skillTypeString);
					
					this.getDamagedCallback.didGetDamaged(SkillType.parseInt(skillType));
				}				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendJSONWithSocket(Map<String, String> map) {
		// make JSON data
		
		JSONObject jsonData = new JSONObject();
		try {
			for (Map.Entry<String,String> entry : map.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  
			  jsonData.put(key, value);			  
			}
			sendStringWithSocket(jsonData.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void sendStringWithSocket (String message) {
		if (networkWriter != null) {
			PrintWriter out = new PrintWriter(networkWriter, true);
			out.println(message);
			out.flush();
		}	
	}
	
	@SuppressWarnings("unused")
	private void closeSocketConnection() {
		try {
			if (networkWriter != null) {
				networkWriter.close();				
			}
			if (networkReader != null) {
				networkReader.close();				
			}
			if (socket != null) {
				socket.close();				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected String sendHttpRequest(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			BufferedReader oBufReader = new BufferedReader(new InputStreamReader(url.openStream()));
			String strBuffer = oBufReader.readLine(); 
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
	
	private void debug(String string) {
		Log.e("galalab", string);
	}
}
