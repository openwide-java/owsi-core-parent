package fr.openwide.core.jpa.more.business.link.model;

public enum ExternalLinkErrorType {

	HTTP,
	INVALID_IDN,
	URI_SYNTAX,
	MALFORMED_URL,
	IO,
	TIMEOUT,
	UNKNOWN_HTTPCLIENT_ERROR;
	
}