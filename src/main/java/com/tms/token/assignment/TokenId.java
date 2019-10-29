package com.tms.token.assignment;

public class TokenId implements ValueObject<TokenId> {

	private String id;
	
	public TokenId() {
		// TODO Auto-generated constructor stub
	}

	public TokenId(final TokenId tokenId) {

		this.id = tokenId.getId();
	}

	public TokenId(final String id) {

		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "TokenId(" + id + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		TokenId tokenId = (TokenId) o;

		if (id != null ? !id.equals(tokenId.id) : tokenId.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public boolean sameValueAs(TokenId other) {

		return this.equals(other);
	}
}