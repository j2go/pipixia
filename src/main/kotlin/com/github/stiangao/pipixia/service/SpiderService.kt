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

    fun catchOneDistrict(destId: Long) {
        val extension = SOARequestExtension("protocal", "https")
        val head = SOARequestHead("09031075311291104867", "", "1.0", "01", "8888", "09", null, listOf(extension))
        val body = SOARequestBody(destId, 0, 0.0, 0.0, 0, 1, 200, 0,
                listOf(), listOf(), listOf(), listOf(), "", 0, 0, 0, 0, 2, 0,
                false, true, "json", head)
        val bodyJson = gson.toJson(body)

        var jsonObj: JsonObject = gson.fromJson(getResult(bodyJson), JsonObject::class.java)

        var totalNum = jsonObj.getAsJsonPrimitive("TotalCount").asInt

        val district = saveDistrict(jsonObj.getAsJsonObject("District"))
        var lastId = saveRestaurantsAndGetLastId(jsonObj.getAsJsonArray("Restaurants"), district)

        var pageIndex = 2

        while (pageIndex < totalNum / 200) {
            jsonObj = getPage(destId, pageIndex)
            val theLastId = saveRestaurantsAndGetLastId(jsonObj.getAsJsonArray("Restaurants"), district)
            if (lastId == theLastId) {
                print("提前结束。 pre over")
                return
            }
            lastId = theLastId
            println("~~~~~ $pageIndex -- " + System.currentTimeMillis())
            pageIndex++
        }

    }

    fun getPage(destId: Long, pageIndex: Int): JsonObject {
        val extension = SOARequestExtension("protocal", "https")
        val head = SOARequestHead("09031075311291104867", "", "1.0", "01", "8888", "09", null, listOf(extension))
        val body = SOARequestBody(destId, 0, 0.0, 0.0, 0, pageIndex, 200, 0,
                listOf(), listOf(), listOf(), listOf(), "", 0, 0, 0, 0, 2, 0,
                false, true, "json", head)
        val bodyJson = gson.toJson(body)

        return gson.fromJson(getResult(bodyJson), JsonObject::class.java)
    }

    private fun saveRestaurantsAndGetLastId(restaurants: JsonArray, district: District): Long {
        var entities: MutableList<Restaurant> = mutableListOf()
        var lastId: Long = 0
        restaurants.forEach({
            var jsonObj = it.asJsonObject

            var restaurantEntity = Restaurant()

            val restaurantId = jsonObj.getAsJsonPrimitive("RestaurantId").asLong

            lastId = restaurantId

            if (restaurantRepo.findById(restaurantId).isPresent) {
                LOGGER.info("----- skip $restaurantId")

            } else {
                restaurantEntity.id = restaurantId
                restaurantEntity.district = district
                restaurantEntity.poiId = jsonObj.getAsJsonPrimitive("PoiId").asLong
                restaurantEntity.name = jsonObj.getAsJsonPrimitive("Name").asString
                restaurantEntity.ggCoordLat = jsonObj.getAsJsonObject("GGCoord").getAsJsonPrimitive("Lat").asDouble
                restaurantEntity.ggCoordLng = jsonObj.getAsJsonObject("GGCoord").getAsJsonPrimitive("Lng").asDouble
                restaurantEntity.bCoordLat = jsonObj.getAsJsonObject("BCoord").getAsJsonPrimitive("Lat").asDouble
                restaurantEntity.bCoordLng = jsonObj.getAsJsonObject("BCoord").getAsJsonPrimitive("Lng").asDouble
                restaurantEntity.imageUrl = jsonObj.getAsJsonPrimitive("ImageUrl").asString
                restaurantEntity.imageUrl2 = jsonObj.getAsJsonPrimitive("ImageUrl2").asString
                restaurantEntity.averagePrice = jsonObj.getAsJsonPrimitive("AveragePrice").asInt
                restaurantEntity.currencyUnit = jsonObj.getAsJsonPrimitive("CurrencyUnit").asString
                restaurantEntity.commentScore = jsonObj.getAsJsonPrimitive("CommentScore").asDouble
                restaurantEntity.commentCount = jsonObj.getAsJsonPrimitive("CommentCount").asInt
                restaurantEntity.distanceNum = jsonObj.getAsJsonPrimitive("DistanceNum").asInt
                restaurantEntity.canBook = jsonObj.getAsJsonPrimitive("IsBook").asBoolean
                restaurantEntity.haveProduct = jsonObj.getAsJsonPrimitive("IsHaveProduct").asBoolean
                restaurantEntity.isPromotion = jsonObj.getAsJsonPrimitive("IsPromotion").asBoolean
                restaurantEntity.recommendType = jsonObj.getAsJsonPrimitive("RecommandType").asInt
                restaurantEntity.feature = jsonObj.getAsJsonPrimitive("Feature")?.asString ?: ""
                restaurantEntity.shiMeiLinType = jsonObj.getAsJsonPrimitive("ShiMeiLinType").asInt
                restaurantEntity.haveHotelProduct = jsonObj.getAsJsonPrimitive("IsHaveHotelProduct").asBoolean
                restaurantEntity.landmarkName = jsonObj.getAsJsonPrimitive("LandmarkName")?.asString ?: ""
                restaurantEntity.landmarkDistance = jsonObj.getAsJsonPrimitive("LandmarkDistance")?.asString ?: ""
                restaurantEntity.shiMeiLinChainTypeName = jsonObj.getAsJsonPrimitive("MeiShiLinChainTypeName")?.asString ?: ""
                restaurantEntity.shiMeiLinName = jsonObj.getAsJsonPrimitive("ShiMeiLinName")?.asString ?: ""


                var tags = jsonObj.getAsJsonArray("TagNameList")
                tags.forEach({
                    var tag = tagRepo.findByName(it.asString)
                    if (tag.isPresent) {
                        restaurantEntity.tags.add(tag.get())
                    } else {
                        var tagEntity = Tag()
                        tagEntity.name = it.asString
                        tagEntity = tagRepo.save(tagEntity)
                        restaurantEntity.tags.add(tagEntity)
                    }
                })
                LOGGER.info("----- add $restaurantId")
                entities.add(restaurantEntity)

            }
        })
        LOGGER.info("---------- save entities.  $lastId")
        restaurantRepo.saveAll(entities)
        return lastId
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