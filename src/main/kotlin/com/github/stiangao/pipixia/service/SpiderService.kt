package com.github.stiangao.pipixia.service

import com.github.stiangao.pipixia.domain.*
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by shitiangao on 2017/8/11.
 */
@Service
class SpiderService(
        @Autowired val dishRepo: DishRepository,
        @Autowired val districtRepo: DistrictRepository,
        @Autowired val restaurantRepo: RestaurantRepository,
        @Autowired val tagRepo: TagRepository
) {

    val LOGGER: Logger = LoggerFactory.getLogger(SpiderService::class.java)
    val gson = Gson()

    fun getResult(body: String): String {
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val client = OkHttpClient()

        val headers = Headers.Builder()
                .add("origin: https://m.ctrip.com")
                .add("x-requested-with: XMLHttpRequest")
                .add("accept-language: zh-CN,zh;q=0.8,en;q=0.6")
                .add("content-type: application/json")
                .add("accept: application/json")
                .add("authority: m.ctrip.com")
                .add("referer: https://m.ctrip.com/webapp/you/foods/104.html?timestamp=1502431507000")
                .add("user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36")
                .add("cookie: _abtest_userid=b9e064e9-a801-41dc-9e55-ee2be93b3c11; _fpacid=09031075311291104867; GUID=09031075311291104867; __utma=1.2089910032.1435151733.1493196583.1498556173.3; __utmz=1.1498556173.3.2.utmcsr=ctrip.com|utmccn=(referral)|utmcmd=referral|utmcct=/; cticket=D6442A32726DEE176B50B96B81C59D9B172C7F20E913AFF54790C659337C6CDD; DUID=u=9949DCB34FEF6B1FFB818C2AF465009C&v=0; IsNonUser=F; UUID=10BA7032C1D541F3B4B883E17A64C3FF; IsPersonalizedLogin=F; __zpspc=9.8.1502249469.1502249469.1%231%7Cbaidu%7Ccpc%7Cbaidu81%7C%25E6%2590%25BA%25E7%25A8%258B%7C%23; MKT_Pagesource=PC; appFloatCnt=1; _bfi=p1%3D290102%26p2%3D0%26v1%3D214%26v2%3D0; ASP.NET_SessionId=swfm5ipdt1awgqj4gy0rohxj; _gat=1; page_time=1500283400762%2C1500283406183%2C1500283508649%2C1500283635991%2C1500283666751%2C1501060949049%2C1501060954334%2C1501060980216%2C1501061302936%2C1501061377291%2C1501061411863%2C1501061418497%2C1501061421882%2C1501061488060%2C1501061500404%2C1501061506567%2C1501061509594%2C1501061512985%2C1501061538268%2C1501061542512%2C1501061546166%2C1501061553757%2C1502249469576%2C1502431464162%2C1502431468558; _RF1=140.207.231.5; _RSG=Z14FeGy8Wl6X5RiyiGSgSA; _RGUID=6be023c4-283c-4063-a72a-710b3cb1b161; ASP.NET_SessionSvc=MTAuOC4xODkuNjV8OTA5MHxqaW5xaWFvfGRlZmF1bHR8MTUwMDUzNzIyOTY5NQ; _bfa=1.1435151727451.2jm6k2.1.1501061554325.1502431507622.24.218.155007; _jzqco=%7C%7C%7C%7C1502431463808%7C1.1600118091.1464352053753.1502431468327.1502431507873.1502431468327.1502431507873.0.0.0.126.126; _ga=GA1.2.2089910032.1435151733; _gid=GA1.2.1845850677.1502431463")
                .build()
//        val body = RequestBody.create(mediaType, "{\"ViewDestId\":\"104\"," +
//                "\"BrowseVersion\":0,\"Lon\":0,\"Lat\":0,\"CurrentDestId\":0,\"PageIndex\":1,\"PageSize\":10,\"DistanceFilter\":0," +
//                "\"PriceFilter\":[],\"CuisineFilter\":[],\"SceneFilter\":[],\"SellFilter\":[],\"FoodFilter\":\"\",\"ZoneId\":0," +
//                "\"PositionLat\":0,\"PositionLon\":0,\"MeiShiLinType\":0,\"Platform\":2,\"OrderType\":0,\"IsOnlyList\":false,\"IsMeiShiLin\":true," +
//                "\"head\":{\"cid\":\"09031075311291104867\",\"ctok\":\"\",\"cver\":\"1.0\",\"lang\":\"01\",\"sid\":\"8888\"," +
//                "syscode\":\"09\",\"auth\":null,\"extension\":[{\"name\":\"protocal\",\"value\":\"https\"}]},\"contentType\":\"json\"}")
        val request = Request.Builder()
                .url("https://m.ctrip.com/restapi/soa2/10332/json/GetHomePageRestaruantListV706?_fxpcqlniredt=09031075311291104867")
                .headers(headers)
                .post(RequestBody.create(mediaType, body))
                .build()
        val response = client.newCall(request).execute()
        println(response.headers().toString())
        println(response.code())

        return response.body()?.string() ?: ""
    }

    fun getOne(): String {
        val extension = SOARequestExtension("protocal", "https")
        val head = SOARequestHead("09031075311291104867", "", "1.0", "01", "8888", "09", null, listOf(extension))
        val body = SOARequestBody(104, 0, 0.0, 0.0, 0, 1, 100, 0,
                listOf(), listOf(), listOf(), listOf(), "", 0, 0, 0, 0, 2, 0,
                false, true, "json", head)
        val bodyJson = Gson().toJson(body)

        return getResult(bodyJson)
    }

    fun catchOneDistrict(destId: Long): String {
        val extension = SOARequestExtension("protocal", "https")
        val head = SOARequestHead("09031075311291104867", "", "1.0", "01", "8888", "09", null, listOf(extension))
        val body = SOARequestBody(destId, 0, 0.0, 0.0, 0, 1, 200, 0,
                listOf(), listOf(), listOf(), listOf(), "", 0, 0, 0, 0, 2, 0,
                false, true, "json", head)
        val bodyJson = gson.toJson(body)

        var jsonObj: JsonObject = gson.fromJson(getResult(bodyJson), JsonObject::class.java)

        val district = saveDistrict(jsonObj.getAsJsonObject("District"))
        saveRestaurants(jsonObj.getAsJsonArray("Restaurants"), district)
        return jsonObj.getAsJsonObject("ResponseStatus").toString()
    }

    private fun saveRestaurants(restaurants: JsonArray, district: District) {
        restaurants.forEach({
            var obj = it.asJsonObject

            var entity = Restaurant()

            val restaurantId = obj.getAsJsonPrimitive("RestaurantId").asLong

            if (restaurantRepo.findById(restaurantId).isPresent) {
                LOGGER.info("----- skip $restaurantId")
            } else {
                entity.id = restaurantId
                entity.district = district
                entity.poiId = obj.getAsJsonPrimitive("PoiId").asLong
                entity.name = obj.getAsJsonPrimitive("Name").asString
                entity.ggCoordLat = obj.getAsJsonObject("GGCoord").getAsJsonPrimitive("Lat").asDouble
                entity.ggCoordLng = obj.getAsJsonObject("GGCoord").getAsJsonPrimitive("Lng").asDouble
                entity.bCoordLat = obj.getAsJsonObject("BCoord").getAsJsonPrimitive("Lat").asDouble
                entity.bCoordLng = obj.getAsJsonObject("BCoord").getAsJsonPrimitive("Lng").asDouble
                entity.imageUrl = obj.getAsJsonPrimitive("ImageUrl").asString
                entity.imageUrl2 = obj.getAsJsonPrimitive("ImageUrl2").asString
                entity.averagePrice = obj.getAsJsonPrimitive("AveragePrice").asInt
                entity.currencyUnit = obj.getAsJsonPrimitive("CurrencyUnit").asString
                entity.commentScore = obj.getAsJsonPrimitive("CommentScore").asDouble
                entity.commentCount = obj.getAsJsonPrimitive("CommentCount").asInt
                entity.distanceNum = obj.getAsJsonPrimitive("DistanceNum").asInt
                entity.canBook = obj.getAsJsonPrimitive("IsBook").asBoolean
                entity.haveProduct = obj.getAsJsonPrimitive("IsHaveProduct").asBoolean
                entity.isPromotion = obj.getAsJsonPrimitive("IsPromotion").asBoolean
                entity.recommandType = obj.getAsJsonPrimitive("RecommandType").asInt
                entity.feature = obj.getAsJsonPrimitive("Feature").asString
                entity.shiMeiLinType = obj.getAsJsonPrimitive("ShiMeiLinType").asInt
                entity.haveHotelProduct = obj.getAsJsonPrimitive("IsHaveHotelProduct").asBoolean
                entity.landmarkName = obj.getAsJsonPrimitive("LandmarkName")?.asString ?: ""
                entity.landmarkDistance = obj.getAsJsonPrimitive("LandmarkDistance")?.asString ?: ""

                LOGGER.info("----- save $restaurantId")
                restaurantRepo.save(entity)
            }
        })
    }

    private fun saveDistrict(jsonDistrict: JsonObject): District {

        val districtId = jsonDistrict.getAsJsonPrimitive("DistrictId").asLong

        val district = districtRepo.findById(districtId)
        if (district.isPresent) {
            return district.get()
        }
        var districtEntity = District()

        districtEntity.id = districtId
        districtEntity.name = jsonDistrict.getAsJsonPrimitive("DistrictName").asString
        districtEntity.enName = jsonDistrict.getAsJsonPrimitive("EName").asString
        districtEntity.inChina = jsonDistrict.getAsJsonPrimitive("InChina").asBoolean
        districtEntity.isOversea = jsonDistrict.getAsJsonPrimitive("IsOversea").asBoolean

        districtRepo.save(districtEntity)

        return districtEntity
    }

}

data class SOARequestExtension(val name: String,
                               val value: String)

data class SOARequestHead(val cid: String,
                          val ctok: String,
                          val cver: String,
                          val lang: String,
                          val sid: String,
                          val syscode: String,
                          val auth: String?,
                          val extension: List<SOARequestExtension>)

data class SOARequestBody(val ViewDestId: Long,
                          val BrowseVersion: Int,
                          val Lon: Double,
                          val Lat: Double,
                          val CurrentDestId: Int,
                          val PageIndex: Int,
                          val PageSize: Int,
                          val DistanceFilter: Int,
                          val PriceFilter: List<Int>,
                          val CuisineFilter: List<Int>,
                          val SceneFilter: List<Int>,
                          val SellFilter: List<Int>,
                          val FoodFilter: String,
                          val ZoneId: Int,
                          val PositionLat: Int,
                          val PositionLon: Int,
                          val MeiShiLinType: Int,
                          val Platform: Int,
                          val OrderType: Int,
                          val IsOnlyList: Boolean,
                          val IsMeiShiLin: Boolean,
                          val contentType: String,
                          val head: SOARequestHead)