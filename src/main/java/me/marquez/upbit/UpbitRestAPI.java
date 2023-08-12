package me.marquez.upbit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.*;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public class UpbitRestAPI {
	private static final String SERVER_URL = "https://api.upbit.com/";
	private static final Gson gson = new Gson();

	protected UpbitRestAPI() {}

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
	private HttpUriRequest makeRequest(Method method, String uri, String body) throws URISyntaxException {
		HttpRequest.Builder builder = HttpRequest.newBuilder();
		builder.uri(new URI(uri));
		switch(method) {
			case DELETE -> 	builder.DELETE();
			case GET -> 	builder.GET();
			case POST -> 	builder.POST(HttpRequest.BodyPublishers.ofString(body));
			case PUT -> 	builder.PUT(HttpRequest.BodyPublishers.ofString(body));
		}
		return (HttpUriRequest) builder.build();
	}
	private String request(Method method, String accessKey, String secretKey, String endpoint, MultiValuedMap<String, String> params) throws Exception{
		String queryString = params != null ? makeQueryString(params) : null;

		HttpClient client = HttpClientBuilder.create().build();
		String uri = SERVER_URL + (endpoint.startsWith("/") ? "" : "/") + endpoint + (queryString == null ? "" : ("?" + queryString));
		HttpUriRequest request = makeRequest(method, uri, gson.toJson(params));
		request.setHeader("Content-Type", "application/json");

		if(accessKey != null) {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			JWTCreator.Builder jwt = JWT.create()
					.withClaim("access_key", accessKey)
					.withClaim("nonce", UUID.randomUUID().toString());
			if(queryString != null) {
				MessageDigest md = MessageDigest.getInstance("SHA-512");
				md.update(queryString.getBytes(StandardCharsets.UTF_8));

				String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));
				jwt = jwt.withClaim("query_hash", queryHash)
						.withClaim("query_hash_alg", "SHA512");
			}
			String jwtToken = jwt.sign(algorithm);

			String authenticationToken = "Bearer " + jwtToken;
			request.addHeader("Authorization", authenticationToken);
		}

		HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();

		return EntityUtils.toString(entity, "UTF-8");
	}

	private String get(String accessKey, String secretKey, String endpoint, MultiValuedMap<String, String> params) throws Exception{
		return request(Method.GET, accessKey, secretKey, endpoint, params);
	}
	private String get(String accessKey, String secretKey, String endpoint) throws Exception{
		return get(accessKey, secretKey, endpoint, null);
	}
	private String get(String endpoint, MultiValuedMap<String, String> params) throws Exception{
		return get(null, null, endpoint, params);
	}
	private String get(String endpoint) throws Exception{
		return get(null, null, endpoint, null);
	}

	private String post(String accessKey, String secretKey, String endpoint, MultiValuedMap<String, String> params) throws Exception{
		return request(Method.POST, accessKey, secretKey, endpoint, params);
	}
	private String post(String accessKey, String secretKey, String endpoint) throws Exception{
		return post(accessKey, secretKey, endpoint, null);
	}

	private String delete(String accessKey, String secretKey, String endpoint, MultiValuedMap<String, String> params) throws Exception{
		return request(Method.DELETE, accessKey, secretKey, endpoint, params);
	}
	private String delete(String accessKey, String secretKey, String endpoint) throws Exception{
		return delete(accessKey, secretKey, endpoint, null);
	}

	/*
	=============================================
					EXCHANGE API
			[주문 요청] 초당 8회, 분당 200회
		   [주문 요청 외] 초당 30회, 분당 900회
	=============================================
	*/

	//자산 - 전체 계좌 조회
	protected String getAccounts(String secretKey, String accessKey) throws Exception {
		return get(accessKey, secretKey, "v1/accounts");
	}

	//주문 - 주문 가능 정보
	protected String getOrderChance(String accessKey, String secretKey, String market) throws Exception {
		return get(accessKey, secretKey, "v1/orders/chance",
				ParamMaker.create().add("market", market).build());
	}

	//주문 - 개별 주문 조회
	protected String getOrder(String accessKey, String secretKey, String uuid, boolean isIdentifier) throws Exception {
		return get(accessKey, secretKey, "v1/order",
				ParamMaker.create().addIf("identifier", uuid, () -> isIdentifier).add("uuid", uuid).build());
	}

	//주문 - 주문 리스트 조회
	protected String getAllOrder(String accessKey, String secretKey) throws Exception {
		return get(accessKey, secretKey, "v1/orders",
				ParamMaker.create().add("state", "wait").build());
	}

	//주문 - 주문 취소 접수
	protected String cancelCoinSpecific(String accessKey, String secretKey, String uuid, boolean bool) throws Exception {
		return delete(accessKey, secretKey, "v1/order",
				ParamMaker.create().addIf("identifier", uuid, () -> bool)
						.add("uuid", uuid).build());
	}
	protected String cancelCoinSpecific(String accessKey, String secretKey, String uuid) throws Exception {
		return cancelCoinSpecific(accessKey, secretKey, uuid, false);
	}

	protected static enum Side {
		BID, //매수
		ASK; //매도
	}
	//주문 - 주문하기
	protected String postCoin(String secretKey, String accessKey, Side side, String coin, String price, String uuid) throws Exception {
		return post(accessKey, secretKey, "v1/orders",
				ParamMaker.create().add("market", coin)
						.add("side", side.name().toLowerCase())
						.addIf("price", price, () -> side == Side.BID)
						.addIf("volume", price, () -> side == Side.ASK)
						.addIf("ord_type", "price", () -> side == Side.BID)
						.addIf("ord_type", "market", () -> side == Side.ASK)
						.add("identifier", uuid).build());
	}
	protected String postCoinSpecific(String secretKey, String accessKey, Side side, String coin, double target, double amount, String uuid) throws Exception {
		return post(accessKey, secretKey, "v1/orders",
				ParamMaker.create().add("market", coin)
						.add("side", side.name().toLowerCase())
						.add("volume", amount)
						.add("price", target)
						.add("ord_type", "limit")
						.add("identifier", uuid).build());
	}

	/*
	=============================================
					QUOTATION API
				 초당 10회, 분당 600회
				 		IP 기준
	=============================================
	*/
	//시세 종목 조회 - 마켓 코드 조회
	protected String getAllCoins() throws Exception {
		return get("v1/market/all", ParamMaker.create().add("isDetails", "false").build());
	}

	//시세 체결 조회 - 최근 체결 내역
	protected String getTrades(String coin, int count) throws Exception {
		return get("v1/trades/ticks", ParamMaker.create().add("market", coin).add("count", count).build());
	}

	//시세 현재가(Ticker) 조회 - 현재가 정보
	protected String getCoinInfo(String markets) throws Exception {
		return get("v1/ticker", ParamMaker.create().add("markets", markets).build());
	}

	//시세 호가 정보(Orderbook) 조회 - 호가 정보 조회
	protected String getOrderBook(String coin) throws Exception {
		return get("v1/orderbook", ParamMaker.create().add("markets", coin).build());
	}
}
