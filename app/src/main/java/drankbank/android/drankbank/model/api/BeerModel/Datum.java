package drankbank.android.drankbank.model.api.BeerModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Veronica on 12/6/16.
 */

public class Datum {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("abv")
    @Expose
    public String abv;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("glass")
    @Expose
    public Glass glass;
    @SerializedName("style")
    @Expose
    public Style style;
    @SerializedName("createDate")
    @Expose
    public String createDate;
    @SerializedName("availableId")
    @Expose
    public Integer availableId;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("styleId")
    @Expose
    public Integer styleId;
    @SerializedName("updateDate")
    @Expose
    public String updateDate;
    @SerializedName("glasswareId")
    @Expose
    public Integer glasswareId;
    @SerializedName("available")
    @Expose
    public Available available;
    @SerializedName("isOrganic")
    @Expose
    public String isOrganic;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("statusDisplay")
    @Expose
    public String statusDisplay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Glass getGlass() {
        return glass;
    }

    public void setGlass(Glass glass) {
        this.glass = glass;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getAvailableId() {
        return availableId;
    }

    public void setAvailableId(Integer availableId) {
        this.availableId = availableId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getGlasswareId() {
        return glasswareId;
    }

    public void setGlasswareId(Integer glasswareId) {
        this.glasswareId = glasswareId;
    }

    public Available getAvailable() {
        return available;
    }

    public void setAvailable(Available available) {
        this.available = available;
    }

    public String getIsOrganic() {
        return isOrganic;
    }

    public void setIsOrganic(String isOrganic) {
        this.isOrganic = isOrganic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDisplay() {
        return statusDisplay;
    }

    public void setStatusDisplay(String statusDisplay) {
        this.statusDisplay = statusDisplay;
    }
}
