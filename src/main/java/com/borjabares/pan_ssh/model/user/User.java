package com.borjabares.pan_ssh.model.user;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.borjabares.pan_ssh.util.GlobalNames.Level;
import com.borjabares.pan_ssh.util.Trimmer;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Entity
@Validations(requiredStrings = {
		@RequiredStringValidator(fieldName = "login", message = "Debe proporcionar un nombre de usuario.", key = "error.login.required", trim = true, shortCircuit = true),
		@RequiredStringValidator(fieldName = "email", message = "Debe proporcionar un correo electrónico.", key = "error.email.required", shortCircuit = true),
		@RequiredStringValidator(fieldName = "password", message = "Debe proporcionar una contraseña.", key = "error.password.required", trim = true, shortCircuit = true) }, 
		stringLengthFields = {
		@StringLengthFieldValidator(fieldName = "login", minLength = "4", maxLength = "15", trim = true, message = "El nombre de usuario debe tener entre ${minLength} y ${maxLength} caracteres.", key = "error.login.length"),
		@StringLengthFieldValidator(fieldName = "password", minLength = "6", maxLength = "15", trim = true, message = "La contraseña debe tener entre ${minLength} y ${maxLength} caracteres.", key = "error.password.length") }, 
		emails = { 
		@EmailValidator(fieldName = "email", message = "Debe introducir un correo electrónico válido.", key = "error.email.validator") }
)
public class User {

	private long userId;
	private String username;
	private String login;
	private String password;
	private Calendar created;
	private Calendar lastlogin;
	private float karma;
	private Level level;
	private String lang;
	private String url;
	private String email;
	private String ip;
	private long version;

	public User() {
		this.created = Calendar.getInstance();
		this.lastlogin = Calendar.getInstance();
		this.karma = 10.0F;
		this.level = Level.NORMAL;
		this.lang = "es";
	}

	public User(String login, String password, String email, String ip) {
		this.login = Trimmer.trim(login);
		this.password = Trimmer.trim(password);
		this.email = email;
		this.ip = ip;
		this.created = Calendar.getInstance();
		this.lastlogin = Calendar.getInstance();
		this.karma = 10.0F;
		this.level = Level.NORMAL;
		this.lang = "es";
	}

	@SequenceGenerator(name = "UserIdGenerator", sequenceName = "UserSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UserIdGenerator")
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = Trimmer.trim(login);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = Trimmer.trim(password);
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Calendar lastlogin) {
		this.lastlogin = lastlogin;
	}

	public float getKarma() {
		return karma;
	}

	public void setKarma(float karma) {
		this.karma = karma;
	}

	@Enumerated(EnumType.STRING)
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Version
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "User [ \nuserId=" + userId + ", \nusername=" + username
				+ ", \nlogin=" + login + ", \npassword=" + password
				+ ", \ncreated=" + created.get(Calendar.DAY_OF_MONTH) + "/"
				+ (created.get(Calendar.MONTH) + 1) + "/"
				+ created.get(Calendar.YEAR) + ", \nlastlogin="
				+ lastlogin.get(Calendar.DAY_OF_MONTH) + "/"
				+ (lastlogin.get(Calendar.MONTH) + 1) + "/"
				+ lastlogin.get(Calendar.YEAR) + ", \nkarma=" + karma
				+ ", \nlevel=" + level + ", \nlang=" + lang + ", \nurl=" + url
				+ ", \nemail=" + email + ", \nip=" + ip + ", \nversion="
				+ version + "]";
	}

}
