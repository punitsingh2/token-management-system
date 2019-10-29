package com.tms.token.assignment;

public class Token {

	private TokenId tokenId;

	private String status;
	
	public Token() {
		// TODO Auto-generated constructor stub
	}
	
	public Token(final TokenId tokenId) {
		this.tokenId = tokenId;
	}

	public TokenId getTokenId() {
		return tokenId;
	}

	public void setTokenId(TokenId tokenId) {
		this.tokenId = tokenId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Token(" + tokenId +", " + status + ")";
	}


}
