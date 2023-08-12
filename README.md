# UpbitAPI
업비트 OpenAPI를 Java에서 쉽게 사용하기 위한 API 입니다.\
현재 개발 중이며 아래와 같은 방식으로 사용할 수 있도록 개발할 예정입니다.
```java
//Upbit OpenAPI 정보
final String ACCESS_KEY = "";
final String SECRET_KEY = "";

//API 인스턴스 생성
UpbitApi api = new UpbitApiBuilder().access(ACCESS_KEY).secret(SECRET_KEY).build();

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

```