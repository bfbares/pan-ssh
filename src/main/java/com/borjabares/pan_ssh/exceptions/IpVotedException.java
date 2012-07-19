package com.borjabares.pan_ssh.exceptions;

@SuppressWarnings("serial")
public class IpVotedException extends Exception {
	private String ip;
	private long linkId;

	public IpVotedException(String ip, long linkId) {
		super("Ip " + ip + "already voted link " + linkId);
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public long getLinkId() {
		return linkId;
	}

}
