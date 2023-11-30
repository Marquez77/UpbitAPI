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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class UpbitCore implements UpbitAPI.Exchange, UpbitAPI.Quotation {
	private static final String SERVER_URL = "https://api.upbit.com";

	private String accessKey;
	private String secretKey;

	/*
	=============================================
				   HTTP REST API
				  GET/POST/DELETE
	=============================================
	*/
	private String makeQueryString(MultiValuedMap<String, String> params) {
		if(params.isEmpty())
			return null;
		ArrayList<String> queryElements = new ArrayList<>();
		for(Map.Entry<String, String> entity : params.entries()) {
			queryElements.add(entity.getKey() + "=" + URLEncoder.encode(entity.getValue(), StandardCharsets.UTF_8));
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
	private String request(Method method, String accessKey, String secretKey, String endpoint, String body) throws UpbitAPIException {
		try {
			String queryString = body != null ? makeQueryString(DataMapper.jsonToMap(body)) : null;

			HttpClient client = HttpClientBuilder.create().build();
			String uri = SERVER_URL + (endpoint.startsWith("/") ? "" : "/") + endpoint + (queryString == null ? "" : ("?" + queryString));
			HttpUriRequest request = makeRequest(method, uri, body);
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
			if (statusCode == 200 || statusCode == 201) {
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

	private String get(String accessKey, String secretKey, String endpoint, String body) throws UpbitAPIException{
		return request(Method.GET, accessKey, secretKey, endpoint, body);
	}
	private String get(String accessKey, String secretKey, String endpoint) throws UpbitAPIException{
		return get(accessKey, secretKey, endpoint, null);
	}
	private String get(String endpoint, String body) throws UpbitAPIException{
		return get(null, null, endpoint, body);
	}
	private String get(String endpoint) throws UpbitAPIException{
		return get(null, null, endpoint, null);
	}

	private String post(String accessKey, String secretKey, String endpoint, String body) throws UpbitAPIException{
		return request(Method.POST, accessKey, secretKey, endpoint, body);
	}
	private String post(String accessKey, String secretKey, String endpoint) throws UpbitAPIException{
		return post(accessKey, secretKey, endpoint, null);
	}

	private String delete(String accessKey, String secretKey, String endpoint, String body) throws UpbitAPIException{
		return request(Method.DELETE, accessKey, secretKey, endpoint, body);
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
		return DataMapper.jsonToObject(json, GetAccounts.Response[].class);
	}

	@Override
	public GetOrdersChance.Response getOrdersChance(GetOrdersChance.Request request) {
		String json = get(accessKey, secretKey, GetOrdersChance.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, GetOrdersChance.Response.class);
	}

	@Override
	public GetOrder.Response getOrder(GetOrder.Request request) {
		String json = get(accessKey, secretKey, GetOrder.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, GetOrder.Response.class);
	}

	@Override
	public GetOrders.Response[] getOrders(GetOrders.Request request) {
		String json = get(accessKey, secretKey, GetOrders.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, GetOrders.Response[].class);
	}

	@Override
	public DeleteOrder.Response deleteOrder(DeleteOrder.Request request) {
		String json = delete(accessKey, secretKey, DeleteOrder.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, DeleteOrder.Response.class);
	}

	@Override
	public PostOrders.Response postOrders(PostOrders.Request request) {
		String json = post(accessKey, secretKey, PostOrders.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, PostOrders.Response.class);
	}

	@Override
	public GetWithdraws.Response[] getWithdraws(GetWithdraws.Request request) {
		String json = get(accessKey, secretKey, GetWithdraws.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, GetWithdraws.Response[].class);
	}

	@Override
	public GetWithdraw.Response getWithdraw(GetWithdraw.Request request) {
		String json = get(accessKey, secretKey, GetWithdraw.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, GetWithdraw.Response.class);
	}

	@Override
	public GetWithdrawsChance.Response getWithdrawsChance(GetWithdrawsChance.Request request) {
		String json = get(accessKey, secretKey, GetWithdrawsChance.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, GetWithdrawsChance.Response.class);
	}

	@Override
	public PostWithdrawsCoin.Response postWithdrawsCoin(PostWithdrawsCoin.Request request) {
		String json = post(accessKey, secretKey, PostWithdrawsCoin.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, PostWithdrawsCoin.Response.class);
	}

	@Override
	public PostWithdrawsKRW.Response postWithdrawsKRW(PostWithdrawsKRW.Request request) {
		String json = post(accessKey, secretKey, PostWithdrawsKRW.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, PostWithdrawsKRW.Response.class);
	}

	@Override
	public GetWithdrawsCoinAddresses.Response[] getWithdrawsCoinAddresses() {
		String json = get(accessKey, secretKey, GetWithdrawsCoinAddresses.END_POINT);
		System.out.println(json);
		return DataMapper.jsonToObject(json, GetWithdrawsCoinAddresses.Response[].class);
	}


	/*
	=============================================
			   UPBIT API (Quotation)
			 IMPLEMENTATION INTERFACE
	=============================================
	*/
	@Override
	public GetMarketAll.Response[] getMarketAll(GetMarketAll.Request request) {
		String json = get(GetMarketAll.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, GetMarketAll.Response[].class);
	}

	@Override
	public GetCandlesMinutes.Response[] getCandlesMinutes(GetCandlesMinutes.Unit unit, GetCandlesMinutes.Request request) {
		String json = get(GetCandlesMinutes.END_POINT + "/" + unit.getUnit(), DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, GetCandlesMinutes.Response[].class);
	}

	@Override
	public GetCandlesDays.Response[] getCandlesDays(GetCandlesDays.Request request) {
		String json = get(GetCandlesDays.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, GetCandlesDays.Response[].class);
	}

	@Override
	public GetCandlesWeeks.Response[] getCandlesWeeks(GetCandlesWeeks.Request request) {
		String json = get(GetCandlesWeeks.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, GetCandlesWeeks.Response[].class);
	}

	@Override
	public GetCandlesMonths.Response[] getCandlesMonths(GetCandlesMonths.Request request) {
		String json = get(GetCandlesMonths.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, GetCandlesMonths.Response[].class);
	}

	@Override
	public GetTradesTicks.Response[] getTradesTicks(GetTradesTicks.Request request) {
		String json = get(GetTradesTicks.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, GetTradesTicks.Response[].class);
	}

	@Override
	public GetTicker.Response[] getTicker(GetTicker.Request request) {
		String json = get(GetTicker.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, GetTicker.Response[].class);
	}

	@Override
	public GetOrderBook.Response[] getOrderBook(GetOrderBook.Request request) {
		String json = get(GetOrderBook.END_POINT, DataMapper.objectToJson(request));
		return DataMapper.jsonToObject(json, GetOrderBook.Response[].class);
	}
}
