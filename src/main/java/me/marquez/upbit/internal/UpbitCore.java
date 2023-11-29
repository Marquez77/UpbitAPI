package me.marquez.upbit.internal;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.marquez.upbit.UpbitAPI;
import me.marquez.upbit.entity.exchange.accounts.GetAccounts;
import me.marquez.upbit.entity.exchange.orders.*;
import me.marquez.upbit.entity.exchange.withdraws.*;
import me.marquez.upbit.entity.quotation.GetOrderBook;
import me.marquez.upbit.entity.quotation.GetTicker;
import me.marquez.upbit.entity.quotation.candles.GetCandlesDays;
import me.marquez.upbit.entity.quotation.candles.GetCandlesMinutes;
import me.marquez.upbit.entity.quotation.candles.GetCandlesMonths;
import me.marquez.upbit.entity.quotation.candles.GetCandlesWeeks;
import me.marquez.upbit.entity.quotation.market.GetMarketAll;
import me.marquez.upbit.entity.quotation.trades.GetTradesTicks;
import me.marquez.upbit.exception.UpbitAPIException;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class UpbitCore implements UpbitAPI.Exchange, UpbitAPI.Quotation {
	private static final String SERVER_URL = "https://api.upbit.com/";

	private String accessKey;
	private String secretKey;

	/*
	=============================================
				   HTTP REST API
				  GET/POST/DELETE
	=============================================
	*/
	private String makeQueryString(MultiValuedMap<String, String> params) {
		ArrayList<String> queryElements = new ArrayList<>();
		for(Map.Entry<String, String> entity : params.entries()) {
			queryElements.add(entity.getKey() + "=" + entity.getValue());
		}
		return String.join("&", queryElements.toArray(String[]::new));
	}

	private enum Method {
		DELETE, GET, POST, PUT;
	}
	private HttpUriRequest makeRequest(Method method, String uri, String body) throws UnsupportedEncodingException {
		HttpUriRequest request = null;
		switch(method) {
			case DELETE -> 	request = new HttpDelete(uri);
			case GET -> 	request = new HttpGet(uri);
			case POST -> 	{
				request = new HttpPost(uri);
				((HttpPost)request).setEntity(new StringEntity(body));
			}
			case PUT -> 	{
				request = new HttpPut(uri);
				((HttpPut)request).setEntity(new StringEntity(body));
			}
		}
		return request;
	}
	private String request(Method method, String accessKey, String secretKey, String endpoint, MultiValuedMap<String, String> params) throws UpbitAPIException {
		try {
			String queryString = params != null ? makeQueryString(params) : null;

			HttpClient client = HttpClientBuilder.create().build();
			String uri = SERVER_URL + (endpoint.startsWith("/") ? "" : "/") + endpoint + (queryString == null ? "" : ("?" + queryString));
			HttpUriRequest request = makeRequest(method, uri, DataMapper.GSON.toJson(params));
			request.setHeader("Content-Type", "application/json");

			if (accessKey != null) {
				Algorithm algorithm = Algorithm.HMAC256(secretKey);
				JWTCreator.Builder jwt = JWT.create()
						.withClaim("access_key", accessKey)
						.withClaim("nonce", UUID.randomUUID().toString());
				if (queryString != null) {
					MessageDigest md = MessageDigest.getInstance("SHA-512");
					md.update(queryString.getBytes(StandardCharsets.UTF_8));

					String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));
					jwt = jwt.withClaim("query_hash", queryHash)
							.withClaim("query_hash_alg", "SHA512");
				}
				String jwtToken = jwt.sign(algorithm);

				String authenticationToken = "Bearer " + jwtToken;
				request.setHeader("Authorization", authenticationToken);
			}

			HttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, "UTF-8");
			if (statusCode == 200) {
				return result;
			} else {
				if(result.contains("error")) {
					JsonObject error = JsonParser.parseString(result).getAsJsonObject().get("error").getAsJsonObject();
					throw new UpbitAPIException(error.get("name").getAsString(), error.get("message").getAsString());
				}
				throw new UpbitAPIException(result);
			}
		}catch(UnsupportedEncodingException | NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}catch(IOException e) {
			UpbitAPIException ex = new UpbitAPIException("");
			ex.initCause(e);
			throw ex;
		}
	}

	private String get(String accessKey, String secretKey, String endpoint, MultiValuedMap<String, String> params) throws UpbitAPIException{
		return request(Method.GET, accessKey, secretKey, endpoint, params);
	}
	private String get(String accessKey, String secretKey, String endpoint) throws UpbitAPIException{
		return get(accessKey, secretKey, endpoint, null);
	}
	private String get(String endpoint, MultiValuedMap<String, String> params) throws UpbitAPIException{
		return get(null, null, endpoint, params);
	}
	private String get(String endpoint) throws UpbitAPIException{
		return get(null, null, endpoint, null);
	}

	private String post(String accessKey, String secretKey, String endpoint, MultiValuedMap<String, String> params) throws UpbitAPIException{
		return request(Method.POST, accessKey, secretKey, endpoint, params);
	}
	private String post(String accessKey, String secretKey, String endpoint) throws UpbitAPIException{
		return post(accessKey, secretKey, endpoint, null);
	}

	private String delete(String accessKey, String secretKey, String endpoint, MultiValuedMap<String, String> params) throws UpbitAPIException{
		return request(Method.DELETE, accessKey, secretKey, endpoint, params);
	}
	private String delete(String accessKey, String secretKey, String endpoint) throws UpbitAPIException{
		return delete(accessKey, secretKey, endpoint, null);
	}


	/*
	=============================================
			   UPBIT API (Exchange)
			 IMPLEMENTATION INTERFACE
	=============================================
	*/
	@Override
	public GetAccounts.Response[] getAccounts() {
		String json = get(accessKey, secretKey, GetAccounts.END_POINT);
		return DataMapper.jsonToClass(json, GetAccounts.Response[].class);
	}

	@Override
	public GetOrdersChance.Response getOrdersChance(GetOrdersChance.Request request) {
		return null;
	}

	@Override
	public GetOrder.Response getOrder(GetOrder.Request request) {
		return null;
	}

	@Override
	public GetOrders.Response[] getOrders(GetOrders.Request request) {
		return null;
	}

	@Override
	public DeleteOrder.Response deleteOrder(DeleteOrder.Request request) {
		return null;
	}

	@Override
	public PostOrders.Response postOrders(PostOrders.Request request) {
		return null;
	}

	@Override
	public GetWithdraws.Response[] getWithdraws(GetWithdraws.Request request) {
		return null;
	}

	@Override
	public GetWithdraw.Response getWithdraw(GetWithdraw.Request request) {
		return null;
	}

	@Override
	public GetWithdrawsChance.Response getWithdrawsChance(GetWithdrawsChance.Request request) {
		return null;
	}

	@Override
	public PostWithdrawsCoin.Response postWithdrawsCoin(PostWithdrawsCoin.Request request) {
		return null;
	}

	@Override
	public PostWithdrawsKRW.Response postWithdrawsKRW(PostWithdrawsKRW.Request request) {
		return null;
	}

	@Override
	public GetWithdrawsCoinAddresses.Response getWithdrawsCoinAddresses() {
		return null;
	}


	/*
	=============================================
			   UPBIT API (Quotation)
			 IMPLEMENTATION INTERFACE
	=============================================
	*/
	@Override
	public GetMarketAll.Response[] getMarketAll(GetMarketAll.Request request) {
		String json = get(GetMarketAll.END_POINT, DataMapper.classToParameter(request));
		return DataMapper.jsonToClass(json, GetMarketAll.Response[].class);
	}

	@Override
	public GetCandlesMinutes.Response getCandlesMinutes(GetCandlesMinutes.Unit unit, GetCandlesMinutes.Request request) {
		return null;
	}

	@Override
	public GetCandlesDays.Response getCandlesDays(GetCandlesDays.Request request) {
		return null;
	}

	@Override
	public GetCandlesWeeks.Response getCandlesWeeks(GetCandlesWeeks.Request request) {
		return null;
	}

	@Override
	public GetCandlesMonths.Response getCandlesMonths(GetCandlesMonths.Request request) {
		return null;
	}

	@Override
	public GetTradesTicks.Response getTradesTicks(GetTradesTicks.Request request) {
		return null;
	}

	@Override
	public GetTicker.Response getTicker(GetTicker.Request request) {
		return null;
	}

	@Override
	public GetOrderBook.Response getOrderBook(GetOrderBook.Request request) {
		return null;
	}
}
