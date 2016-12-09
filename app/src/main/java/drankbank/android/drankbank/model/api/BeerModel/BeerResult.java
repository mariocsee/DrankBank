package drankbank.android.drankbank.model.api.BeerModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Veronica on 12/6/16.
 *
 * Layout from BeerDB Call:
 *     {
 *     "status" : "success",
 *     "numberOfPages" : 1,
 *     "data" : [
 *          {"id" : "Uu2ExM",
 *          "abv" : "7.7",
 *          "description" : "A big malty beer with caramel & toffee flavors that has a clean & smooth finish. \r\n\r\nIngredients: Imported German barley & wheat malt, Northern Brewer & Vanguard Hops.",
 *          "name" : "Goosinator Smoked Doppelbock 2007",
 *          "glass" : {
 *              "id" : 5,
 *              "createDate" : "2012-01-03 02:41:33",
 *              "name" : "Pint"},
 *          "style" : {
 *              "id" : 90,
 *              "category" : {
 *                  "id" : 7,
 *                  "createDate" : "2012-03-21 20:06:46",
 *                  "name" : "European-germanic Lager"},
 *              "srmMax" : "30",
 *              "ibuMax" : "27",
 *              "srmMin" : "12",
 *              "description" : "Malty sweetness is dominant but should not be cloying. Malt character is more reminiscent of fresh and lightly toasted Munich- style malt, more so than caramel or toffee malt character. Some elements of caramel and toffee can be evident and contribute to complexity, but the predominant malt character is an expression of toasted barley malt. Doppelbocks are full bodied and deep amber to dark brown in color. Astringency from roast malts is absent. Alcoholic strength is high, and hop rates increase with gravity. Hop bitterness and flavor should be low and hop aroma absent. Fruity esters are commonly perceived but at low to moderate levels. Diacetyl should be absent",
 *              "fgMin" : "1.014",
 *              "ibuMin" : "17",
 *              "createDate" : "2012-03-21 20:06:46",
 *              "fgMax" : "1.02",
 *              "abvMax" : "8",
 *              "ogMin" : "1.074",
 *              "abvMin" : "6.5",
 *              "name" : "German-Style Doppelbock",
 *              "categoryId" : 7},
 *          "createDate" : "2012-01-03 02:43:17",
 *          "availableId" : 3,
 *          "type" : "beer",
 *          "styleId" : 90,
 *          "updateDate" : "2012-03-22 13:04:30",
 *          "glasswareId" : 5,
 *          "available" : {
 *              "id" : "3",
 *              "description" : "Beer is not available.",
 *              "name" : "Not Available"},
 *          "isOrganic" : "N",
 *          "status" : "verified",
 *          "statusDisplay" : "Verified"}
 *          ],
 *      "currentPage" : 1}
 *
 * POJO generated from: http://www.jsonschema2pojo.org/
 */

public class BeerResult {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("currentPage")
    @Expose
    private Integer currentPage;
    @SerializedName("numberOfPages")
    @Expose
    private Integer numberOfPages;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("data")
    @Expose
    private List<Datum> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

}
