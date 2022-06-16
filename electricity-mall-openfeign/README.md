# electricity-mall-openfeign

//@FeignClient(name = "helloFeign", url = "ELECTRICITY-MALL-EUREKA-CLIENT")
java.net.UnknownHostException: ELECTRICITY-MALL-EUREKA-CLIENT

java.net.ConnectException: No route to host (connect failed)


//@FeignClient("ELECTRICITY-MALL-EUREKA-CLIENT")
java.net.SocketTimeoutException: connect timed out


com.netflix.client.ClientException: Load balancer does not have available server for client: ELECTRICITY-MALL-EUREKA-CLIENT
Operation timed out (Connection timed out)
