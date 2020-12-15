package network_teamproject;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	private String ID;
    private String PW;
    private String Name;
    private String email;
	private String nickname;
	private String birth;
	public ArrayList<Integer> requires=new ArrayList<>();
	public ArrayList<User> friends = new ArrayList<>();
	private String today="nice to meet you";
	
	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String online="false";
	public String login_time="default";
	public String logout_time="default";
	public int number=0;
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}


	
	
	public String getLogout_time() {
		return logout_time;
	}

	public void setLogout_time(String logout_time) {
		this.logout_time = logout_time;
	}

	public int login_num;
    
    public String getLogin_time() {
		return login_time;
	}

	public void setLogin_time(String login_time) {
		this.login_time = login_time;
	}

	public int getLogin_num() {
		return login_num;
	}

	public void setLogin_num(int login_num) {
		this.login_num = login_num;
	}




	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public User(String iD, String pW, String name,String email, String nickname, String birth) {
        this.ID = iD;
        this.PW = pW;
        this.Name = name;
        this.email=email;
        this.nickname=nickname;
        this.birth=birth;
    }
	public User() {
        this.ID = "";
        this.PW = "";
        this.Name = "";
        this.email="";
        this.nickname="";
        this.birth="";
    }
 
    public String getID() {
        return ID;
    }
 
    public String getPW() {
        return PW;
    }
 
    public String getName() {
        return Name;
    }
 
    public void setID(String iD) {
        ID = iD;
    }
 
    public void setPW(String pW) {
        PW = pW;
    }
 
    public void setName(String name) {
        Name = name;
    }
    public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    @Override
    public String toString() {
        return "ID : " + ID + " PW : " + PW + " Name : " + Name;
    }
}
