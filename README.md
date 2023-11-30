# UpbitAPI
업비트 OpenAPI를 Java에서 쉽게 사용하기 위한 API 입니다.\
Deposits 관련 API를 제외하고 전부 테스트 완료되었습니다.
---
## Example
```java
//Upbit OpenAPI 정보
final String ACCESS_KEY = "";
final String SECRET_KEY = "";

//Exchange API 인스턴스 생성
UpbitAPI.Exchange api = new UpbitAPI.createExchangeAPI(ACCESS_KEY, SECRET_KEY);

//API 요청 인스턴스 생성
UUID uniqueId = UUID.randomUUID();
var request = PostOrders.Request.builder()
        .market("KRW-BTC")
        .side(Side.BID)
        .volume(0.01D)
        .price(30000000.0D)
        .ord_type(OrderType.LIMIT)
        .identifier(uniqueId)
        .build();

try{
    var response = api.postOrders(request); //요청 및 응답 수신
    System.out.println("Order state: " + response.getState());
}catch(UpbitApiException e) { //오류 발생 시
    System.out.println("Error: " + e.getMessage());
}

//Quotation API
var response = UpbitAPI.QUOTATION.getMarketAll(
        GetMarketAll.Request.builder()
            .isDetails(true)
            .build()
);
System.out.println(Arrays.toString(response));

```