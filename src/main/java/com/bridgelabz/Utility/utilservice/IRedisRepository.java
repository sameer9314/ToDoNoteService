package com.bridgelabz.Utility.utilservice;

/**
 * @author sameer
 *
 */
public interface IRedisRepository {

	/**
	 * @param token
	 */
	void setToken(String token);
	/**
	 * @param userId
	 * @return
	 */
	public String getToken(String userId);
	/**
	 * @param userId
	 */
	public void deleteToken(String userId) ;

}
